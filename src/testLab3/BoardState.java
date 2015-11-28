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
		this.whitePieces = origin.whitePieces;
		this.blackPieces = origin.blackPieces;
		this.state = origin.getState();
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
		
		if(change.getCurrentColor() == Client.WHITE){
			pieceMoved = this.whitePieces.get(pieceMovedID);
			//change position in our internal white array
			this.whitePieces.get(pieceMovedID).setCol(change.getToColumn());
			this.whitePieces.get(pieceMovedID).setRow(change.getToRow());
			if( isMoveEating(change.getToColumn(), change.getToRow() ) ){
				//Removing the pieces that are eaten by the new move
				this.blackPieces.remove( getPieceID(change.getToColumn(), 
													   change.getToRow(), 
													   Client.BLACK) 
									   );
			}
		}else{
			pieceMoved = this.blackPieces.get(change.getPieceID());
			//change position in our internal black array
			this.blackPieces.get(pieceMovedID).setCol(change.getToColumn());
			this.blackPieces.get(pieceMovedID).setRow(change.getToRow());
			
			if( isMoveEating(change.getToColumn(), change.getToRow() ) ){
				//Removing the pieces that are eaten by the new move
				this.whitePieces.remove( getPieceID(change.getToColumn(), 
						   							   change.getToRow(), 
						   							   Client.WHITE) 
									   );
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
		HashMap<Integer,Piece> hashMap = (color == Client.WHITE) ? whitePieces:blackPieces;
		for(Entry<Integer, Piece> entry : hashMap.entrySet()) {
		    //int key = entry.getKey();
		    Piece value = entry.getValue();
		    if(value.getCol() == x && value.getRow() == y){
		    	return value.getID();
		    }
		}
		//no id found
		return -1;
	}
	
	
	/**
	 * Vérifie si la destination d'un move va manger une pièce
	 * @param x
	 * @param y
	 * @return true si manger
	 */
	public boolean isMoveEating(int x, int y){
		int valeurAtXY = this.state[x][y];
		
		if(valeurAtXY != 0){
			return true;
		}
		else
			return false;
	}
	
}
