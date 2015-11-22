package testLab3;

import java.io.*;
import java.net.*;
import java.util.ArrayList;


class Client {
	static int color;
	static int enemyColor;
	
	//liste de tableau correspondant aux pièces
    static ArrayList<Piece> whites = new ArrayList<Piece>();
    static ArrayList<Piece> blacks = new ArrayList<Piece>();
    
    static int[][] board = new int[8][8];
    
    static PusherGame game;
    

    
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
	            		
	            // Début de la partie en joueur blanc
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
	                
	                //on lit toutes les cases du board envoyé par le server
	                // 1 2 2 2 2 2 2 2 2 1 1 1 1 1 1 1 1 0 0 0 0 0 0 0 0 etc ...
	                for(int i=0; i<boardValues.length;i++){

	                	value = Integer.parseInt(boardValues[i]);
	                    board[x][y] = value;
	                    
	                    //si la case n'est pas vide, ajoute la pièce
	                    if(value != 0 ){
	                    	dispatchPieces(x,y,value);
	                    }
	                    x++;
	                    if(x == 8){
	                        x = 0;
	                        y++;
	                    }
	                }
	                
	                game = new PusherGame(color, whites, blacks);
	                
	                move = game.getEval().chooseMove();
	
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
	                System.out.println(s);
	                String[] boardValues;
	                boardValues = s.split(" ");
	                int x=0,y=0;
	                
	                //même histoire avec les pions noirs, voir blanc plus haut pour détail
	                for(int i=0; i<boardValues.length;i++){
	                	
	                	int value = Integer.parseInt(boardValues[i]);
	                    board[x][y] = value;
	                    if(value != 0 ){
	                    	dispatchPieces(x,y,value);
	                    }
	                    x++;
	                    if(x == 8){
	                        x = 0;
	                        y++;
	                    }
	                }
	                
	                game = new PusherGame(color, whites, blacks);
	                //game.;
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
					/*
					 * TODO updater le model complet et le model des pions seulement
					 * ^?COMENT just reloop ? Est-ce long ?
					 */
			       	System.out.println("Entrez votre coup : ");
			       	/*
	                 * TODO Arbre give us a move
	                 */
					
					move = console.readLine();
					output.write(move.getBytes(),0,move.length());
					output.flush();
					
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
	 * fonction qui rempli la liste de tableau de pièces
	 * chaque pièces est décrite avec une coordonné X Y et une
	 * valeur correspondante à sa couleur et type de pièces
	 * 3 = pushy blanc, 4 = pousseur blanc, le reste est noir
	 * @param x
	 * @param y
	 * @param val
	 */
	private static void dispatchPieces(int x, int y, int val){
		
		Piece piece = new Piece(val, x, y);

		if(val == 3 || val == 4){
			whites.add(piece);
		}else{
			blacks.add(piece);
		}
	}
}

