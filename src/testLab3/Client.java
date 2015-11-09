package testLab3;

import java.io.*;
import java.net.*;
import java.util.ArrayList;


class Client {
	static int color;
	static int enemyColor;
    static ArrayList<int[]> whites = new ArrayList<int[]>();
    static ArrayList<int[]> blacks = new ArrayList<int[]>();
    static int[][] board = new int[8][8];
    
	public static void main(String[] args) {
		Socket MyClient;
		BufferedInputStream input;
		BufferedOutputStream output;
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
	                System.out.println(s);
	                String[] boardValues;
	                boardValues = s.split(" ");
	                int x=0,y=0;
	                for(int i=0; i<boardValues.length;i++){
	                	/*
	                	 * TODO Doit creer un tableau des pions seulement
	                	 */
	                	int value = Integer.parseInt(boardValues[i]);
	                    board[x][y] = value;
	                    if(value != 0 ){
	                    	dispatchPawns(x,y,value);
	                    }
	                    x++;
	                    if(x == 8){
	                        x = 0;
	                        y++;
	                    }
	                }
	
	                System.out.println("Nouvelle partie! Vous jouer blanc, entrez votre premier coup : ");
	                /*
	                 * TODO Arbre give us a move
	                 */
	                String move = null;
	                move = console.readLine();
					output.write(move.getBytes(),0,move.length());
					output.flush();
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
	                for(int i=0; i<boardValues.length;i++){
	                	/*
	                	 * TODO Doit creer un tableau des pions seulement
	                	 */
	                	int value = Integer.parseInt(boardValues[i]);
	                    board[x][y] = value;
	                    if(value != 0 ){
	                    	dispatchPawns(x,y,value);
	                    }
	                    x++;
	                    if(x == 8){
	                        x = 0;
	                        y++;
	                    }
	                }
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
					String move = null;
					move = console.readLine();
					output.write(move.getBytes(),0,move.length());
					output.flush();
					
				}
				// Le dernier coup est invalide
				if(cmd == '4'){
					System.out.println("Coup invalide, entrez un nouveau coup : ");
			       	String move = null;
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
	private static void dispatchPawns(int x, int y, int val){
		int[] coord = new int[3];
    	coord[0] = x;
    	coord[1] = y;
    	coord[2] = val;
		if(val == 1 || val ==2){
			whites.add(coord);
		}else{
			blacks.add(coord);
		}
	}
}

