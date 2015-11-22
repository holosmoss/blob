package testLab3;

import java.util.ArrayList;
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
	
	//liste des moves des deux joueurs, cette liste est rempli dans le main
	// avec dispatchPieces()
	private ArrayList<Piece> us = new ArrayList<Piece>();
	private ArrayList<Piece> them = new ArrayList<Piece>();

	
	
	/**
	 * Le generateur est appel� par l'arbre pour connaitre les enfants de  la node
	 * Il evalue tout les move possible selon la position et le type de la piece ainsi que les pieces voisines
	 */
	public Generateur(int clientColor, ArrayList<Piece> white, ArrayList<Piece> black ){
		
		this.clientColor = clientColor;
		
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
	
	/*
	 * Une fois le generateur cr�� et le premier coup jou�, on utilisera
	 * cette methode pour update les liste de pi�ces
	 */
	public void updatePlayerPiecesList(ArrayList<Piece> white, ArrayList<Piece> black){
		if(this.clientColor == WHITE){
			this.us = white;
			this.them = black;
		}
		else{
			this.us = black;
			this.them = white;
		}
		
	}
	
	public List<Move>  generateurMouvement(int couleur){
		List<Move> listMove;

		if(couleur == WHITE){
			listMove = ourMoves(couleur);
		}else{
			listMove = theirMoves(couleur);
		}
		
		return listMove;
	}
	

	
	private List<Move> ourMoves(int couleur){
		List<Move> moves = new ArrayList<Move> ();
		
		//loop all of us and find moves for nosPushy et nosPusher
		for(Piece piece : us){
			if(piece.getValeur() == nosPusher){
				
				//add the moves in order of importance: 
				//diag first since they might be eating				
				
				//diag � droite xmod = 1
				if(canPusherMoveDiag(piece.getX(), piece.getY(), couleur, 1)){
					
					Move diagPlus = new Move(piece.getX(),
											 piece.getX(),
											 piece.getX()+1,
											 piece.getY()-1 );
					moves.add(diagPlus);
				}
				//diag � gauche xmod = -1
				if(canPusherMoveDiag(piece.getX(), piece.getY(), couleur, -1)){
					
					Move diagMinus = new Move(piece.getX(),
							 				  piece.getX(),
							 				  piece.getX()-1,
							 				  piece.getY()-1 );
					moves.add(diagMinus);
				}
				if(!isBlocked(piece.getX(), piece.getY(), couleur)){
					//we are not blocked forward, add the node X + 0, Y - 1
					Move forward = new Move(piece.getX(),
			 				  				piece.getX(),
			 				  				piece.getX(),
			 				  				piece.getY()-1 );
					moves.add(forward);
				}
			}
			else{
				//pushy amis
				
				if(canBePush(piece.getX(), piece.getY(), couleur, 1)){
					
					Move diagPlus = new Move(piece.getX(),
							 				 piece.getX(),
							 				 piece.getX()+1,
							 				 piece.getY()-1 );
					
					moves.add(diagPlus);
				}
				if(canBePush(piece.getX(), piece.getY(), couleur, -1)){
					
					Move diagMinus = new Move(piece.getX(),
											  piece.getX(),
											  piece.getX()-1,
											  piece.getY()-1 );
					moves.add(diagMinus);
				}
				if(canBePush(piece.getX(), piece.getY(), couleur, 0)){
					//forward is a possible move
					Move forward = new Move(piece.getX(),
				  							piece.getX(),
				  							piece.getX(),
				  							piece.getY()-1 );
					moves.add(forward);
				}
			}
		}
		return moves;
	}
	
	/*
	 * Pour les moves de l'adversaire
	 */
	private List<Move> theirMoves(int couleur){
		List<Move> moves = new ArrayList<Move> ();
		//loop all of them and find moves for leurPushy et leurPusher
		for(Piece piece : them){
			if(piece.getValeur() == nosPusher){
				
				//add the moves in order of importance: 
				//diag first since they might be eating				
				
				//diag � droite xmod = 1
				if(canPusherMoveDiag(piece.getX(), piece.getY(), couleur, 1)){
					
					Move diagPlus = new Move(piece.getX(),
											 piece.getX(),
											 piece.getX()+1,
											 piece.getY()-1 );
					moves.add(diagPlus);
				}
				//diag � gauche xmod = -1
				if(canPusherMoveDiag(piece.getX(), piece.getY(), couleur, -1)){
					
					Move diagMinus = new Move(piece.getX(),
							 				  piece.getX(),
							 				  piece.getX()-1,
							 				  piece.getY()-1 );
					moves.add(diagMinus);
				}
				if(!isBlocked(piece.getX(), piece.getY(), couleur)){
					//we are not blocked forward, add the node X + 0, Y - 1
					Move forward = new Move(piece.getX(),
			 				  				piece.getX(),
			 				  				piece.getX(),
			 				  				piece.getY()-1 );
					moves.add(forward);
				}
			}
			else{
				//pushy ennemi
				
				if(canBePush(piece.getX(), piece.getY(), couleur, 1)){
					
					Move diagPlus = new Move(piece.getX(),
							 				 piece.getX(),
							 				 piece.getX()+1,
							 				 piece.getY()-1 );
					
					moves.add(diagPlus);
				}
				if(canBePush(piece.getX(), piece.getY(), couleur, -1)){
					
					Move diagMinus = new Move(piece.getX(),
											  piece.getX(),
											  piece.getX()-1,
											  piece.getY()-1 );
					moves.add(diagMinus);
				}
				if(canBePush(piece.getX(), piece.getY(), couleur, 0)){
					//forward is a possible move
					Move forward = new Move(piece.getX(),
				  							piece.getX(),
				  							piece.getX(),
				  							piece.getY()-1 );
					moves.add(forward);
				}
			}
		}
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
			//si xmod = 1 check diag gauche, si -1 x--1 = x+1 alors diag droite
			if(Client.board[x-xmod][y-1] == 3 || 
			   Client.board[x-xmod][y-1] == 4 ||
			   (x-xmod) < 0 || 
			   (x-xmod) > 7
			   ){
				//pas de pi�ces � nous dans la prochain case et
				//check si on sort pas du board
				return false;
			}else{
				//true si vide ou si ennemi alors on mange
				return true;
			}
			
		}else{
			
			if(Client.board[x-xmod][y+1] == 1 || 
			   Client.board[x-xmod][y+1] == 2 ||
			   (x-xmod) < 0 || 
			   (x-xmod) > 7 
			   ){
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
			if(Client.board[x-xmod][y-1] == 3 || Client.board[x-xmod][y-1] == 4 ){
				//is blocked by one of its color
				return false;
			}else if(Client.board[x+xmod][y-1] == 4){
				//has a pusher to push to xModified -1y
				return true;
			}else{
				return false;
			}
		}else{
			//Black pushy
			if(Client.board[x-xmod][y+1] == 1 || Client.board[x-xmod][y+1] == 2 ){
				//is blocked by one of its color
				return false;
			}else if(Client.board[x+xmod][y+1] == 2){
				//has a pusher to push to xModified +1y
				return true;
			}else{
				return false;
			}
		}
	}
	

	
		
	/**
	 * Node utilis� par l'arbre min max
	 * @author Holos
	 *
	 */ 
	protected class Node{
		

		
		protected boolean isMax;
		protected int typeMove;
		protected Move moveAssociatedToTheNode;
		
		
		protected Node(int typeMove){
			
			this.typeMove = typeMove;
			
			//classe useless a ce moment la du jeu
		}
		
		protected Node(){
			
		}
		
		
	}

}
