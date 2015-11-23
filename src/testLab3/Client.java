package testLab3;

import java.io.*;
import java.net.*;
import java.util.ArrayList;


class Client {
	
	static int color = 0;
	static int enemyColor = 0;
	static String strTmp = "null";
	static boolean isColorPlayable;
	
	//liste de tableau correspondant aux pièces
    static ArrayList<Piece> whites = new ArrayList<Piece>();
    static ArrayList<Piece> blacks = new ArrayList<Piece>();
    
    static int[][] board = new int[8][8];
    
    static PusherGame game;
    
    static BoardDecoder decoder = new BoardDecoder();
    static int[] coord = new int[4];

	//valeur des couleurs des joueurs
	static final int WHITE = 1;
	static final int BLACK = 2;

    
	public static void main(String[] args) {
		Socket MyClient;
		BufferedInputStream input;
		BufferedOutputStream output;
		String move;
		BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
		
		//choix de la couleur avant la connexion
		while( !isColorPlayable ){
			if( !isColorPlayable ){
				System.out.println("Veuillez choisir la couleur de vos pièces, 1 pour blanc, 2 pour noir: ");
				
				try {
					strTmp = console.readLine();
					color = Integer.parseInt(strTmp);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if( color == 1 ){
				isColorPlayable = true;
				enemyColor = 2;
				
			}
			else if(color == 2){
				isColorPlayable = true;
				enemyColor = 1;
			}
			else
				System.out.println("Erreur! Couleur inconnue!");
		}
		
		try {
			MyClient = new Socket("localhost", 8888);
		   	input    = new BufferedInputStream(MyClient.getInputStream());
			output   = new BufferedOutputStream(MyClient.getOutputStream());
			
			
			
			
			
			color = Integer.parseInt( strTmp );
		   	
			while(1 == 1){
				char cmd = 0;
			   	
	            cmd = (char)input.read();
	            		
	            // Début de la partie en joueur blanc
	            if(cmd == '1'){
	                byte[] aBuffer = new byte[1024];
	                //we set our color value to white

					int size = input.available();
					//System.out.println("size " + size);
					input.read(aBuffer,0,size);
	                String s = new String(aBuffer).trim();
	                //System.out.println(s);
	                String[] boardValues;
	                boardValues = s.split(" ");
	                int x=0,y=0;
	                int value;
	                
	                //on lit toutes les cases du board envoyé par le server
	                // 1 2 2 2 2 2 2 2 2 1 1 1 1 1 1 1 1 0 0 0 0 0 0 0 0 etc ...
	                for(int i=0; i<boardValues.length;i++){

	                	value = Integer.parseInt(boardValues[i]);
	                    board[x][y] = value;
	                    
	                    //si la case n'est pas vide, ajoute la pièce
	                    if(value != 0 ){
	                    	dispatchPieces(x,y,value, i);
	                    }
	                    x++;
	                    if(x == 8){
	                        x = 0;
	                        y++;
	                    }
	                }
	                
	                //instancie le jeu, you know, also magic
	                game = new PusherGame(color, whites, blacks);
	                
	                //choose the first move!
	                move = game.chooseMove();
	
	                System.out.println("Nouvelle partie! Vous jouer blanc, entrez votre premier coup : "); 
	                
	                //move = console.readLine();
					output.write(move.getBytes(),0,move.length());
					output.flush();
					System.out.println(move);
	            }
	            
	            // Début de la partie en joueur Noir
	            if(cmd == '2'){
	                System.out.println("Nouvelle partie! Vous jouer noir, attendez le coup des blancs");
	                byte[] aBuffer = new byte[1024];
	                //we set our color value to white
	                color = 2;
	                enemyColor = 1;
					int size = input.available();
					//System.out.println("size " + size);
					input.read(aBuffer,0,size);
	                String s = new String(aBuffer).trim();
	                //System.out.println(s);
	                String[] boardValues;
	                boardValues = s.split(" ");
	                int x=0,y=0;
	                
	                //même histoire avec les pions noirs, voir blanc plus haut pour détail
	                for(int i=0; i<boardValues.length;i++){
	                	
	                	int value = Integer.parseInt(boardValues[i]);
	                    board[x][y] = value;
	                    if(value != 0 ){
	                    	dispatchPieces(x,y,value, i);
	                    }
	                    x++;
	                    if(x == 8){
	                        x = 0;
	                        y++;
	                    }
	                }
	                
	                game = new PusherGame(color, whites, blacks);
	                
	            }
	
	
				// Le serveur demande le prochain coup
				// Le message contient aussi le dernier coup joué.
				if(cmd == '3'){
					byte[] aBuffer = new byte[16];
					
					int size = input.available();
					//System.out.println("size " + size);
					input.read(aBuffer,0,size);
					
					String s = new String(aBuffer);
					System.out.println("Dernier coup : "+ s);
					coord = decoder.decodeEnemyLastMove(s);
					
					// update la liste de pièce ennemi du generateur avec la dernier coup jouer
					game.getGene().updateTheirPiecesList(coord[0], coord[1], coord[2], coord[3]);
					
					
					
					//notre liste est updaté dans l'évaluateur
					
			       	System.out.println("Entrez votre coup : ");
			       	
			       	//envoie notre prochain coup
			       	move = game.chooseMove();
					
					//move = console.readLine();
					output.write(move.getBytes(),0,move.length());
					output.flush();
					System.out.println(move);
					
				}
				// Le dernier coup est invalide
				if(cmd == '4'){
					System.out.println("Coup invalide, entrez un nouveau coup : ");
			       	
					move = console.readLine();
					output.write(move.getBytes(),0,move.length());
					output.flush();
					
				}
	        }
		}
		catch (IOException e) {
	   		System.out.println(e);
		}
	
    }
	
	/**
	 * fonction qui rempli la liste de pièces
	 * chaque pièces est décrite avec une coordonné X(colonne) Y(ligne) et une
	 * valeur correspondante à sa couleur et type de pièces
	 * 3 = pushy blanc, 4 = pousseur blanc, le reste est noir
	 * @param x
	 * @param y
	 * @param val
	 */
	private static void dispatchPieces(int x, int y, int val, int ID){
		
		Piece piece = new Piece(val, x, y, ID);

		if(val == 3 || val == 4){
			whites.add(piece);
		}else{
			blacks.add(piece);
		}
	}
}

