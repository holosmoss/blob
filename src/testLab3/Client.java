package testLab3;

import java.io.*;
import java.net.*;
import java.util.ArrayList;


class Client {
	static int color;
	static int enemyColor;
	
	//liste de tableau correspondant aux pi�ces
    static ArrayList<Piece> whites = new ArrayList<Piece>();
    static ArrayList<Piece> blacks = new ArrayList<Piece>();
    
    static int[][] board = new int[8][8];
    
    static PusherGame game;
    
    static BoardDecoder decoder = new BoardDecoder();
    static int[] coord = new int[4];

    

    
	public static void main(String[] args) {
		Socket MyClient;
		BufferedInputStream input;
		BufferedOutputStream output;
		String move;
		try {
			MyClient = new Socket("localhost", 8888);
		   	input    = new BufferedInputStream(MyClient.getInputStream());
			output   = new BufferedOutputStream(MyClient.getOutputStream());
			
			BufferedReader console = new BufferedReader(new InputStreamReader(System.in));  
		   	
			while(1 == 1){
				char cmd = 0;
			   	
	            cmd = (char)input.read();
	            		
	            // D�but de la partie en joueur blanc
	            if(cmd == '1'){
	                byte[] aBuffer = new byte[1024];
	                //we set our color value to white
	                color = 1;
	                enemyColor = 2;
					int size = input.available();
					//System.out.println("size " + size);
					input.read(aBuffer,0,size);
	                String s = new String(aBuffer).trim();
	                //System.out.println(s);
	                String[] boardValues;
	                boardValues = s.split(" ");
	                int x=0,y=0;
	                int value;
	                
	                //on lit toutes les cases du board envoy� par le server
	                // 1 2 2 2 2 2 2 2 2 1 1 1 1 1 1 1 1 0 0 0 0 0 0 0 0 etc ...
	                for(int i=0; i<boardValues.length;i++){

	                	value = Integer.parseInt(boardValues[i]);
	                    board[x][y] = value;
	                    
	                    //si la case n'est pas vide, ajoute la pi�ce
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
	            
	            // D�but de la partie en joueur Noir
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
	                System.out.println(s);
	                String[] boardValues;
	                boardValues = s.split(" ");
	                int x=0,y=0;
	                
	                //m�me histoire avec les pions noirs, voir blanc plus haut pour d�tail
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
				// Le message contient aussi le dernier coup jou�.
				if(cmd == '3'){
					byte[] aBuffer = new byte[16];
					
					int size = input.available();
					//System.out.println("size " + size);
					input.read(aBuffer,0,size);
					
					String s = new String(aBuffer);
					System.out.println("Dernier coup : "+ s);
					coord = decoder.decodeEnemyLastMove(s);
					
					// update la liste de pi�ce ennemi du generateur avec la dernier coup jouer
					game.getGene().updateTheirPiecesList(coord[0], coord[1], coord[2], coord[3]);
					
					
					
					//notre liste est updat� dans l'�valuateur
					
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
	 * fonction qui rempli la liste de pi�ces
	 * chaque pi�ces est d�crite avec une coordonn� X(colonne) Y(ligne) et une
	 * valeur correspondante � sa couleur et type de pi�ces
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

