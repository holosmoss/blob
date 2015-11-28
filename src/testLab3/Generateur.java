package testLab3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Generateur {
	
	//les types de moves qu'une node peut faire
	static final int MOVE_FORWARD = 1;
	static final int MOVE_DIAG = 2;
	static final int MOVE_EAT = 3;
	
	//valeur des couleurs des joueurs
	static final int WHITE = 1;
	static final int BLACK = 2;

	private int nosPushy;
	private int nosPusher;
	private int leurPushy;
	private int leurPusher;
	
	//couleur du joueur
	private int clientColor;
	
	private BoardDecoder decoder;
	
	//liste des moves des deux joueurs, cette liste est rempli dans le main
	// avec dispatchPieces()
	private ArrayList<Piece> us = new ArrayList<Piece>();
	private ArrayList<Piece> them = new ArrayList<Piece>();

	
	public Generateur(){
		//TODO new constructor;
		
	}
	
	/**
	 * Le generateur est appelé par l'arbre pour connaitre les enfants de  la node
	 * Il evalue tout les move possible selon la position et le type de la piece ainsi que les pieces voisines
	 */
	public Generateur(int clientColor, ArrayList<Piece> white, ArrayList<Piece> black ){
		
		this.clientColor = clientColor;
		this.decoder = new BoardDecoder();
		
		if(clientColor == WHITE){
			this.us = white;
			this.them = black;
			this.leurPushy = 1;
			this.leurPusher = 2;
			this.nosPushy = 3;
			this.nosPusher = 4;
		}
		else{
			this.us = black;
			this.them = white;
			this.leurPushy = 3;
			this.leurPusher = 4;
			this.nosPushy = 1;
			this.nosPusher = 2;
		}	
	}
	
	/**
	 * TODO remove to BoardState
	 * Update une pièce p de notre liste de pièce
	 * @param p
	 * @return int - correspondant au type de la pièce updaté soit un pusher(4) ou pushy(3)
	 */
	public int updateOurPiecesList(int ID, int col, int row){
		int type = 5; //init a un type inexistant
		for(int i = 0; i < this.us.size(); i++){
			//TODO if the ID is the index we can go straight to it.
			if( this.us.get(i).getID() == ID ){
				
				//update la pièce dans la liste
				this.us.get(i).setCol( col );
				this.us.get(i).setRow( row );
				type = this.us.get(i).getValeur();
				// quitte la boucle
				i = this.us.size(); 
			}
		}
		return type;
	}

	
	/**
	 * Update une pièce p de la liste de pièce ennemi
	 * @param p
	 * @return int - correspondant au type de la pièce updaté soit un pusher(2) ou pushy(1)
	 */
	public int updateTheirPiecesList(int fromCol, int fromRow, int ToCol, int ToRow){		
		int type = 5;
		for(int i = 0; i < this.them.size(); i++){
			
			//s'il s'agit de la pièce sur la case int[fromCol][fromRow]
			if( this.them.get(i).getCol() == fromCol && 
				this.them.get(i).getRow() == fromRow ){
				
				//update la pièce dans la liste
				this.them.get(i).setCol( ToCol );
				this.them.get(i).setRow( ToRow );
				type = this.them.get(i).getValeur();
				
				// quitte la boucle
				i = this.them.size(); 
			}
		}
		return type;
	}
	
	
	public ArrayList<Move>  generateurMouvement(BoardState state, int currentColor){
		System.out.println("------generateurMouvement ");
		//TODO us the state to find moves
		//TODO how do we determine the active player (and its color)
		ArrayList<Move> listMove = new ArrayList<Move>();
		HashMap<Integer, Piece> pieces;
		//choix des pieces qui seront considere
		if(currentColor == Client.WHITE){
			pieces = state.getWhitePieces();
		}else{
			pieces = state.getBlackPieces();
		}
		//TODO we need to have a generator that takes the pieces and adjusts the direction and handles both color values for moves.
		
		
		
		
//		if(couleur == WHITE){
//			listMove = ourMoves(couleur);
//		}else{
//			listMove = theirMoves(couleur);
//		}
		return listMove;
	}
/*
 * 		//TODO hardcoded move tree
		
		//TODO how do we deal with ID ? TODO dry run until we have them
		ArrayList<ArrayList<Move>> listMove = new ArrayList<ArrayList<Move>>();
		ArrayList<Move> turn1 = new ArrayList<Move>();
		turn1.add(new Move(6,1,5,1,49,1));
		turn1.add(new Move(6,2,5,2,50,1));
		turn1.add(new Move(6,3,5,3,51,1));
		listMove.add(turn1);
		//TODO we return the list for the 1st option first since depth first
		ArrayList<Move> round2 = new ArrayList<Move>();
		round2.add(new Move(1,1,2,1,9,2));
		round2.add(new Move(1,2,2,2,10,2));
		round2.add(new Move(1,3,2,3,11,2));
		listMove.add(round2);
		ArrayList<Move> round3 = new ArrayList<Move>();
		round3.add(new Move(7,1,6,1,57,1));
		round3.add(new Move(6,2,5,2,50,1));
		round3.add(new Move(6,3,5,3,51,1));
		listMove.add(round3);
 */

	
	private ArrayList<Move> ourMoves(int couleur){
		ArrayList<Move> moves = new ArrayList<Move> ();
		
		//loop all of us and find moves for nosPushy et nosPusher
//		for(Piece piece : us){
//			if(piece.getValeur() == nosPusher){
//				
//				//add the moves in order of importance: 
//				//diag first since they might be eating				
//				
//				//diag à droite xmod = 1
//				if(canPusherMoveDiag(piece.getCol(), piece.getRow(), couleur, 1)){
//					
//					Move diagPlus = new Move(piece.getRow(),
//											 piece.getCol(),
//											 piece.getRow()-1,
//											 piece.getCol()+1,
//											 piece.getID() );
//					moves.add(diagPlus);
//				}
//				//diag à gauche xmod = -1
//				if(canPusherMoveDiag(piece.getCol(), piece.getRow(), couleur, -1)){
//					
//					Move diagMinus = new Move(piece.getRow(),
//							 				  piece.getCol(),
//							 				  piece.getRow()-1,
//							 				  piece.getCol()-1,
//							 				  piece.getID() );
//					moves.add(diagMinus);
//				}
//				if(!isBlocked(piece.getCol(), piece.getRow(), couleur)){
//					//we are not blocked forward, add the node X + 0, Y - 1
//					Move forward = new Move(piece.getRow(),
//			 				  				piece.getCol(),
//			 				  				piece.getRow()-1,
//			 				  				piece.getCol(),
//							 				piece.getID() );
//					moves.add(forward);
//				}
//			}
//			else{
//				//pushy amis
//				
//				if(canBePush(piece.getCol(), piece.getRow(), couleur, 1)){
//					
//					Move diagPlus = new Move(piece.getRow(),
//							 				 piece.getCol(),
//							 				 piece.getRow()-1,
//							 				 piece.getCol()+1,
//							 				 piece.getID()  );
//					
//					moves.add(diagPlus);
//				}
//				if(canBePush(piece.getCol(), piece.getRow(), couleur, -1)){
//					
//					Move diagMinus = new Move(piece.getRow(),
//											  piece.getCol(),
//											  piece.getRow()-1,
//											  piece.getCol()-1,
//							 				  piece.getID() );
//					moves.add(diagMinus);
//				}
//				if(canBePush(piece.getCol(), piece.getRow(), couleur, 0)){
//					//forward is a possible move
//					Move forward = new Move(piece.getRow(),
//				  							piece.getCol(),
//				  							piece.getRow()-1,
//				  							piece.getCol(),
//							 				piece.getID() );
//					moves.add(forward);
//				}
//			}
//		}
		return moves;
	}
	
	/*
	 * Pour les moves de l'adversaire
	 */
	private ArrayList<Move> theirMoves(int couleur){
		ArrayList<Move> moves = new ArrayList<Move> ();
		//loop all of them and find moves for leurPushy et leurPusher
//		for(Piece piece : them){
//			if(piece.getValeur() == leurPusher){
//				
//				//add the moves in order of importance: 
//				//diag first since they might be eating				
//				
//				//diag à droite xmod = 1
//				if(canPusherMoveDiag(piece.getCol(), piece.getRow(), couleur, 1)){
//					
//					Move diagPlus = new Move(piece.getRow(),
//											 piece.getCol(),
//											 piece.getRow()+1,
//											 piece.getCol()+1,
//							 				 piece.getID() );
//					moves.add(diagPlus);
//				}
//				//diag à gauche xmod = -1
//				if(canPusherMoveDiag(piece.getCol(), piece.getRow(), couleur, -1)){
//					
//					Move diagMinus = new Move(piece.getRow(),
//							 				  piece.getCol(),
//							 				  piece.getRow()+1,
//							 				  piece.getCol()-1,
//							 				  piece.getID() );
//					moves.add(diagMinus);
//				}
//				if(!isBlocked(piece.getCol(), piece.getRow(), couleur)){
//					//we are not blocked forward, add the node X + 0, Y + 1
//					Move forward = new Move(piece.getRow(),
//			 				  				piece.getCol(),
//			 				  				piece.getRow()+1,
//			 				  				piece.getCol(),
//							 				piece.getID() );
//					moves.add(forward);
//				}
//			}
//			else{
//				//pushy ennemi
//				
//				if(canBePush(piece.getCol(), piece.getRow(), couleur, 1)){
//					
//					Move diagPlus = new Move(piece.getRow(),
//							 				 piece.getCol(),
//							 				 piece.getRow()+1,
//							 				 piece.getCol()+1,
//							 				 piece.getID() );
//					
//					moves.add(diagPlus);
//				}
//				if(canBePush(piece.getCol(), piece.getRow(), couleur, -1)){
//					
//					Move diagMinus = new Move(piece.getRow(),
//											  piece.getCol(),
//											  piece.getRow()+1,
//											  piece.getCol()-1,
//							 				  piece.getID() );
//					moves.add(diagMinus);
//				}
//				if(canBePush(piece.getCol(), piece.getRow(), couleur, 0)){
//					//forward is a possible move
//					Move forward = new Move(piece.getRow(),
//				  							piece.getCol(),
//				  							piece.getRow()+1,
//				  							piece.getCol(),
//							 				piece.getID() );
//					moves.add(forward);
//				}
//			}
//		}
		return moves;
	}
	/**
	 * Function to check for any pieces straight 
	 * ahead (works for both pushy and pusher)
	 * 
	 * @param x
	 * @param y
	 * @return true if blocked, false if not
	 */
	private boolean isBlocked(int x, int y, int color){
		//change the row modifier based on color
		int ymod = (color == WHITE) ? 1 : -1;
		if(Client.board[x][y-ymod] != 0){
			//s'il y a un pion en avant de nous
			return true;
		}
		else
			return false;
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @param color
	 * @param xmod -1,1 = the location of the diag we want to go to
	 * @return
	 */
	private boolean canPusherMoveDiag(int x, int y, int color, int xmod){
		if(color == WHITE){
			
			if( isOutOfBound(x, xmod) )
				return false;
			//si xmod = 1 check diag gauche, si -1 x--1 = x+1 alors diag droite
			else if( Client.board[x-xmod][y-1] == 3 || Client.board[x-xmod][y-1] == 4){
				//pas de pièces à nous dans la prochain case et
				//check si on sort pas du board
				return false;
			}else{
				//true si vide ou si ennemi alors on mange
				return true;
			}
			
		}else{
			
			if( isOutOfBound(x, xmod) )
				return false;
			
			else if( Client.board[x-xmod][y+1] == 1 || Client.board[x-xmod][y+1] == 2){
				return false;
			}else{
				//true si vide ou si ennemi alors on mange
				return true;
			}
		}
			
		
	}
	/**
	 * Function to detect if the pushy has a pusher in the proper position to move him to the desired xmod 
	 * on the next row based on colors. Also confirms its not blocked by its own color
	 * 
	 * @param x
	 * @param y
	 * @param color
	 * @param xmod : 0 = straight ahead, -1 and 1 = diagonals (the value is for the location we want to go)
	 * @return
	 */
	private boolean canBePush(int x, int y, int color, int xmod){
		//is the pushy able to go to x+xmod
		if(color == WHITE){
			//white pushy
			
			if( isOutOfBound(x, xmod) )
				return false;
			else if( Client.board[x-xmod][y-1] == 3 || Client.board[x-xmod][y-1] == 4 ){
				//is blocked by one of its color
				return false;
			}else if( Client.board[x+xmod][y+1] == 4){
				//has a pusher to push to xModified -1y
				return true;
			}else{
				return false;
			}
		}else{
			//Black pushy
			
			if( isOutOfBound(x, xmod) )
				return false;
			else if( Client.board[x-xmod][y+1] == 1 || Client.board[x-xmod][y+1] == 2 ){
				//is blocked by one of its color
				return false;
			}else if( Client.board[x+xmod][y-1] == 2){
				//has a pusher to push to xModified +1y
				return true;
			}else{
				return false;
			}
		}
	}
	
	/**
	 * Methode pour vérifier si on tente de déplacer un pion en dehors du board
	 * @param x - valeur en X original
	 * @param xmod - valeur en X de destination
	 * @return
	 */
	private boolean isOutOfBound(int x, int xmod){
		//TODO are those all good ?
		if( (x == 0 && xmod == 1) || (x == 7 && xmod == -1) || (x+xmod < 0) || (x-xmod) > 7 || (x+xmod) > 7 )
			return true;
		else
			return false;		
	}

	

}
