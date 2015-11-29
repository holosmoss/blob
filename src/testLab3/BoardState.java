package testLab3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class BoardState {
	
	private int[][] state;
	private HashMap<Integer,Piece> whitePieces;
	private HashMap<Integer,Piece> blackPieces;

	public BoardState(int[][] rawBoard, HashMap<Integer,Piece> whites, HashMap<Integer,Piece> blacks) {
		this.state = rawBoard;
		this.whitePieces = whites;
		this.blackPieces = blacks;
	}

	public BoardState(BoardState origin, Move change){
		// deep copy all values
		this.whitePieces = new HashMap<Integer,Piece>();
		this.blackPieces = new HashMap<Integer,Piece>();
		this.state = new int[8][8];
		for(Entry<Integer, Piece> entry : origin.whitePieces.entrySet()) {
			int key = entry.getKey();
			Piece piece = entry.getValue();
			Piece newPiece = new Piece(piece.getValeur(), piece.getCol(), piece.getRow(), piece.getID());
			this.whitePieces.put(key, newPiece);
		}
		for(Entry<Integer, Piece> entry : origin.blackPieces.entrySet()) {
			int key = entry.getKey();
			Piece piece = entry.getValue();
			Piece newPiece = new Piece(piece.getValeur(), piece.getCol(), piece.getRow(), piece.getID());
			this.blackPieces.put(key, newPiece);
		}
		for (int row = 0; row < 8; row++) 
		{
			for (int col = 0; col < 8; col++){
				this.state[col][row] = origin.getState()[col][row];
			}
		}
		//and update new board
		updateBoard(change);
	}

	public int[][] getState() {
		return state;
	}
		
	public HashMap<Integer, Piece> getWhitePieces() {
		return whitePieces;
	}

	public HashMap<Integer, Piece> getBlackPieces() {
		return blackPieces;
	}

	public void updateBoard(Move change){
		//Update the positions of the moved pieces in our internal state (before and after) according to the Move
		Piece pieceMoved;
		int pieceMovedID = change.getPieceID();
		//affichageGrille();
		//Client.print(pieceMovedID+" updateBoard -=-=-=-==--=-=-=-= :"+change.getCurrentColor()+": "+change.getToColumn()+"."+change.getToRow());
		if(change.getCurrentColor() == Client.WHITE){
			
			pieceMoved = this.whitePieces.get(pieceMovedID);
			//change position in our internal white array
			
			this.whitePieces.get(pieceMovedID).setCol(change.getToColumn());
			this.whitePieces.get(pieceMovedID).setRow(change.getToRow());
			if( isMoveEating(change.getToColumn(), change.getToRow(), Client.WHITE) ){
				//Client.print(">>>>>>>>>>>"+pieceMoved.getValeur()+" mange "+state[change.getToColumn()][change.getToRow()]+" with ID "+getPieceID(change.getToColumn(), change.getToRow(), Client.BLACK));
				//Removing the pieces that are eaten by the new move
				//Client.print("blac size "+this.blackPieces.size());
				this.blackPieces.remove( getPieceID(change.getToColumn(), 
													   change.getToRow(), 
													   Client.BLACK) 
									   );
				//Client.print("blac newsize "+this.blackPieces.size());
			}
		}else{
			
			pieceMoved = this.blackPieces.get(pieceMovedID);
			//change position in our internal black array
			this.blackPieces.get(pieceMovedID).setCol(change.getToColumn());
			this.blackPieces.get(pieceMovedID).setRow(change.getToRow());

			if( isMoveEating(change.getToColumn(), change.getToRow(), Client.BLACK) ){
				//Client.print(">>>>>>>>>>>"+pieceMoved.getValeur()+" mange "+state[change.getToColumn()][change.getToRow()]+" with ID "+getPieceID(change.getToColumn(), change.getToRow(), Client.WHITE));

				//Removing the pieces that are eaten by the new move
				//Client.print("whit size "+this.whitePieces.size());
				this.whitePieces.remove( getPieceID(change.getToColumn(), 
						   							   change.getToRow(), 
						   							   Client.WHITE) 
									   );
				//Client.print("whit newsize "+this.whitePieces.size());
			}
		}
		
		this.state[ change.getFromColumn() ][change.getFromRow() ] = 0; //ancienne case maintenant vide
		this.state[ change.getToColumn() ][ change.getToRow() ] = pieceMoved.getValeur(); //nouvelle case = type pièce
		
	}
	
	/**
	 * Retour l'ID de la pièce a la position (X,Y) ou -1 si il ny en a pas
	 * @param x - coordonné en X
	 * @param y - coordonné en Y
	 * @param color - la liste dans la quelle on souhaite chercher la pièce
	 * @return
	 */
	public int getPieceID(int x, int y, int color) {
		//Client.print("~~x:"+x+"~~~y:"+y+"~~");
		HashMap<Integer,Piece> hashMap = (color == Client.WHITE) ? whitePieces:blackPieces;
		for(Entry<Integer, Piece> entry : hashMap.entrySet()) {
		    int key = entry.getKey();
		    Piece value = entry.getValue();
		   // Client.print(key+"~ x:"+value.getCol()+"~y:"+value.getRow());
		    if(value.getCol() == x && value.getRow() == y){
		    	return value.getID();
		    }
		}
		//Client.print("~~~~~~~");
		//no id found
		return -1;
	}
	
	
	/**
	 * Vérifie si la destination d'un move va manger une pièce
	 * @param x
	 * @param y
	 * @return true si manger
	 */
	public boolean isMoveEating(int x, int y, int color){
		int valeurAtXY = this.state[x][y];
		int pusher;
		int pushy;
		//on ajoute les pion de la meme couleurs comme safety
		if(color == Client.WHITE){
			pusher = 4;
			pushy = 3;
		}else{
			pusher = 2;
			pushy = 1;
		}
		if(valeurAtXY != 0 && valeurAtXY != pusher && valeurAtXY != pushy){
			return true;
		}
		else
			return false;
	}
	/**
	 * Fonction qui print le board en 8 par 8
	 * @param grille
	 */
	public void affichageGrille(){
		
		System.out.println("-------------");
		for (int row = 0; row < 8; row++) 
		{
			for (int col = 0; col < 8; col++){
				System.out.print(state[col][row]);
			}
			System.out.println();
		}
		System.out.println("-------------");
	}
}
