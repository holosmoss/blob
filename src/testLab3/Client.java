package testLab3;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;


class Client {
	
	static int color = 0;
	static int enemyColor = 0;
	static String strTmp = "null";
	static boolean isColorPlayable;
	
	//TODO HashMap so that we can still use ID and also delete entry without fucking the ID (which was index in arrays
	//TODO no need for static its in the BoardState
	static HashMap<Integer,Piece> whites = new HashMap<Integer,Piece>();
    static HashMap<Integer,Piece> blacks = new HashMap<Integer,Piece>();
	
//    static ArrayList<Piece> whites = new ArrayList<Piece>();
//    static ArrayList<Piece> blacks = new ArrayList<Piece>();
    
    //TODO this should become a BoardState, no need for this thing static
    static int[][] board = new int[8][8];
	static BoardState realBoardState;

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
		
		
		//TODO is this a thing we want ?
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
			
			
			
			
			//set static values
			//TODO  is this the right place to set the color ? or is it the cmd value
			color = Integer.parseInt( strTmp );
            game = new PusherGame();

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
	                    	dispatchPieces(x,y,value,i);
	                    }
	                    x++;
	                    if(x == 8){
	                        x = 0;
	                        y++;
	                    }
	                }
	                //instancie le board static selon notre parsing du texte de depart
	                realBoardState = new BoardState(board,whites,blacks);
	                
	                //choose the first move!
	                move = game.chooseMove();
	                
	                System.out.println("Nouvelle partie! Vous jouer blanc, entrez votre premier coup : "); 
	                
	                //move = console.readLine();
					output.write(move.getBytes(),0,move.length());
					//TODO update RealBoardState
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
	                
	                //instancie le board static selon notre parsing du texte de depart
	                realBoardState = new BoardState(board,whites,blacks);
	                
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
					
					// Update le realBoardState avec le move adverse
					//TODO we need to test if this eats one of ours and delete add this to the move.
					//TODO id enemi ? genre un reverse HashMap search pour les coord 0-1 qui nous retourne le ID : meme chose pour le eaten
					
					Move newMove = new Move(coord[0], coord[1], coord[2], coord[3],12,enemyColor);
					realBoardState.updateBoard(newMove);
					//game.getGene().updateTheirPiecesList(coord[0], coord[1], coord[2], coord[3]);
					
					
					
					//notre liste est updaté dans l'évaluateur
					
			       	System.out.println("Entrez votre coup : ");
			       	
			       	//envoie notre prochain coup
			       	move = game.chooseMove();
					
			    	//TODO update RealBoardState
					//move = console.readLine();
					output.write(move.getBytes(),0,move.length());
					output.flush();
					System.out.println(move);
					
				}
				// Le dernier coup est invalide
				if(cmd == '4'){
					System.out.println("Coup invalide, entrez un nouveau coup : ");
			       	
					move = console.readLine();
					//TODO what do we do here ?
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
	private static void dispatchPieces(int x, int y, int val, int id){
		//the ID is the index of the piece in its color arrayList
		if(val == 3 || val == 4){
			Piece piece = new Piece(val, x, y, whites.size());
			whites.put(id, piece);
			//whites.add(piece);
		}else{
			Piece piece = new Piece(val, x, y, blacks.size());
			blacks.put(id, piece);
			//blacks.add(piece);
		}
	}
}

