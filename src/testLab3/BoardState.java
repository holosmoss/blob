package testLab3;

import java.util.ArrayList;
import java.util.HashMap;

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
	
	public void updateBoard(Move change){
		//Update the positions of the moved pieces in our internal state (before and after) according to the Move
		Piece pieceMoved;
		int pieceMovedID = change.getPieceID();
		if(change.getCurrentColor() == Client.WHITE){
			pieceMoved = this.whitePieces.get(pieceMovedID);
			//change position in our internal white array
			this.whitePieces.get(pieceMovedID).setCol(change.getToColumn());
			this.whitePieces.get(pieceMovedID).setRow(change.getToRow());
			if(change.isEating()){
				//Removing the pieces that are eaten by the new move (this is why whitePieces needs to be an HashMap)
				this.blackPieces.remove(change.getEatedPieceID());
			}
		}else{
			pieceMoved = this.blackPieces.get(change.getPieceID());
			//change position in our internal black array
			this.blackPieces.get(pieceMovedID).setCol(change.getToColumn());
			this.blackPieces.get(pieceMovedID).setRow(change.getToRow());
			//TODO if the move is eating a pieces update the white array
			if(change.isEating()){
				//Removing the pieces that are eaten by the new move (this is why whitePieces needs to be an HashMap)
				this.whitePieces.remove(change.getEatedPieceID());
			}
		}
		this.state[ change.getFromColumn() ][change.getFromRow() ] = 0; //ancienne case maintenant vide
		this.state[ change.getToColumn() ][ change.getToRow() ] = pieceMoved.getValeur(); //nouvelle case = type pièce
		
	}
	
}
