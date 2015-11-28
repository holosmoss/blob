package testLab3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Generateur {
	
	//TODO what is this for ?
	//les types de moves qu'une node peut faire
	static final int MOVE_FORWARD = 1;
	static final int MOVE_DIAG = 2;
	static final int MOVE_EAT = 3;
	


	

	
	public Generateur(){
		//TODO new constructor ? needs anything ?;
		
	}

	/**
	 * Passe tout les piece de la couleur choisie dans le boardState et ajoute ses move possible a la liste returned
	 * @param state
	 * @param currentColor
	 * @return
	 */
	public ArrayList<Move>  generateurMouvement(BoardState state, int currentColor){
//		System.out.println("------generateurMouvement ");
		//TODO us the state to find moves
		//TODO how do we determine the active player (and its color)
		ArrayList<Move> moveList = new ArrayList<Move>();
		HashMap<Integer, Piece> pieces;
		//choix des pieces qui seront considere
		if(currentColor == Client.WHITE){
			pieces = state.getWhitePieces();
		}else{
			pieces = state.getBlackPieces();
		}
		//TODO we need to have a generator that takes the pieces and adjusts the direction and handles both color values for moves.
		
		for(Entry<Integer, Piece> entry : pieces.entrySet()) {
		    int key = entry.getKey();
		    Piece piece = entry.getValue();
		    int pieceType = piece.getValeur();
//		    Client.print("piece : "+pieceType+" || col : "+piece.getCol()+" row :"+piece.getRow());
		    //TODO this can be shortened a lot since the moves are the same for both type of pieces its juste the tests thats change.
		    switch(pieceType){
		    case 1:
		    	//poussable noir
		    	if(canBePush(state, piece.getCol(), piece.getRow(), Client.BLACK, 1)){
		    		//diag à droite xmod = 1
		    		Move diagPlus = new Move(piece.getRow(),
							 piece.getCol(),
							 piece.getRow()+1,
							 piece.getCol()+1,
							 piece.getID(),
							 Client.BLACK);
		    		moveList.add(diagPlus);
		    	}
		    	if(canBePush(state, piece.getCol(), piece.getRow(), Client.BLACK, -1)){
		    		//diag à gauche xmod = -1
		    		Move diagMoin = new Move(piece.getRow(),
							 piece.getCol(),
							 piece.getRow()+1,
							 piece.getCol()-1,
							 piece.getID(),
							 Client.BLACK);
		    		moveList.add(diagMoin);
		    	}
		    	if(canBePush(state, piece.getCol(), piece.getRow(), Client.BLACK, 0)){
		    		//forward is a possible move
		    		Move forward = new Move(piece.getRow(),
							 piece.getCol(),
							 piece.getRow()+1,
							 piece.getCol(),
							 piece.getID(),
							 Client.BLACK);
		    		moveList.add(forward);
		    	}
		    	break;
		    case 2:
		    	//pousseur noir
		    	if(canPusherMoveDiag(state, piece.getCol(), piece.getRow(), Client.BLACK, 1)){
		    		//diag à droite xmod = 1
		    		Move diagPlus = new Move(piece.getRow(),
							 piece.getCol(),
							 piece.getRow()+1,
							 piece.getCol()+1,
							 piece.getID(),
							 Client.BLACK);
		    		moveList.add(diagPlus);
		    	}
		    	if(canPusherMoveDiag(state, piece.getCol(), piece.getRow(), Client.BLACK, -1)){
		    		//diag à gauche xmod = -1
		    		Move diagMoin = new Move(piece.getRow(),
							 piece.getCol(),
							 piece.getRow()+1,
							 piece.getCol()-1,
							 piece.getID(),
							 Client.BLACK);
		    		moveList.add(diagMoin);
		    	}
		    	if(!isBlocked(state, piece.getCol(), piece.getRow(), Client.BLACK)){
		    		//we are not blocked forward, add the move X + 0, Y - 1
		    		Move forward = new Move(piece.getRow(),
							 piece.getCol(),
							 piece.getRow()+1,
							 piece.getCol(),
							 piece.getID(),
							 Client.BLACK);
		    		moveList.add(forward);
		    	}
		    	break;
		    case 3:
//		    	Client.print("----poussable Blanc");
		    	//poussable blanc
		    	if(canBePush(state, piece.getCol(), piece.getRow(), Client.WHITE, 1)){
		    		//diag à droite xmod = 1
		    		Move diagPlus = new Move(piece.getRow(),
							 piece.getCol(),
							 piece.getRow()-1,
							 piece.getCol()+1,
							 piece.getID(),
							 Client.WHITE);
		    		moveList.add(diagPlus);
		    	}
		    	if(canBePush(state, piece.getCol(), piece.getRow(), Client.WHITE, -1)){
		    		//diag à gauche xmod = -1
		    		Move diagMoin = new Move(piece.getRow(),
							 piece.getCol(),
							 piece.getRow()-1,
							 piece.getCol()-1,
							 piece.getID(),
							 Client.WHITE);
		    		moveList.add(diagMoin);
		    	}
		    	if(canBePush(state, piece.getCol(), piece.getRow(), Client.WHITE, 0)){
		    		//forward is a possible move
		    		Move forward = new Move(piece.getRow(),
							 piece.getCol(),
							 piece.getRow()-1,
							 piece.getCol(),
							 piece.getID(),
							 Client.WHITE);
		    		moveList.add(forward);
		    	}
		    	break;
		    case 4:
//		    	Client.print("----Pousseur Blanc");
		    	//pousseur blanc
		    	if(canPusherMoveDiag(state, piece.getCol(), piece.getRow(), Client.WHITE, 1)){
		    		//diag à droite xmod = 1
		    		Move diagPlus = new Move(piece.getRow(),
							 piece.getCol(),
							 piece.getRow()-1,
							 piece.getCol()+1,
							 piece.getID(),
							 Client.WHITE);
		    		moveList.add(diagPlus);
		    	}
		    	if(canPusherMoveDiag(state, piece.getCol(), piece.getRow(), Client.WHITE, -1)){
		    		//diag à gauche xmod = -1
		    		Move diagMoin = new Move(piece.getRow(),
							 piece.getCol(),
							 piece.getRow()-1,
							 piece.getCol()-1,
							 piece.getID(),
							 Client.WHITE);
		    		moveList.add(diagMoin);
		    	}
		    	if(!isBlocked(state, piece.getCol(), piece.getRow(), Client.WHITE)){
		    		//we are not blocked forward, add the move X + 0, Y - 1
		    		Move forward = new Move(piece.getRow(),
							 piece.getCol(),
							 piece.getRow()-1,
							 piece.getCol(),
							 piece.getID(),
							 Client.WHITE);
		    		moveList.add(forward);
		    	}
		    	break;
		    }
		}
		
		return moveList;
	}
 
	
	
	

	/**
	 * Function to check for any pieces straight 
	 * ahead (works for both pushy and pusher)
	 * 
	 * @param col
	 * @param row
	 * @return true if blocked, false if not
	 */
	private boolean isBlocked(BoardState state, int col, int row, int color){
		int[][] board = state.getState();

		//change the row modifier based on color
		int ymod = (color ==Client.WHITE) ? 1 : -1;
		if(board[col][row-ymod] != 0){
			//s'il y a un pion en avant de nous
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 
	 * @param col
	 * @param row
	 * @param color
	 * @param xmod -1,1 = the location of the diag we want to go to (gauche et droite = same for both color)
	 * @return
	 */
	private boolean canPusherMoveDiag(BoardState state, int col, int row, int color, int xmod){
		int[][] board = state.getState();
		if(color == Client.WHITE){
			if( isOutOfBound(col, row, xmod, color) )
				return false;
			//si xmod = 1 on veut la droite donc col + xmod
			else if( board[col+xmod][row-1] == 3 || board[col+xmod][row-1] == 4){
				//same team
				return false;
			}else{
				//true si vide ou si ennemi alors on mange
				return true;
			}
			
		}else{
			if( isOutOfBound(col, row, xmod, color) )
				return false;
			//si xmod = 1 on veut la droite donc col + xmod
			else if( board[col+xmod][row+1] == 1 || board[col+xmod][row+1] == 2){
				//same team
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
	 * @param col
	 * @param row
	 * @param color
	 * @param xmod : 0 = straight ahead, -1 and 1 = diagonals (the value is for the location we want to go)
	 * @return
	 */
	private boolean canBePush(BoardState state, int col, int row, int color, int xmod){
		int[][] board = state.getState();
		//is the pushy able to go to x+xmod
		//Client.print("canBePush : x:"+col+" y"+row+" xmod="+xmod+ " for :"+color);
		if(color == Client.WHITE){
			//white pushy
			//Client.print("out of bound ? "+isOutOfBound(col, row, xmod, color));
			
			if( isOutOfBound(col, row, xmod, color) )
				return false;
			else if( board[col+xmod][row-1] == 3 || board[col+xmod][row-1] == 4 ){
				//is blocked by one of its color
				return false;
			}else if(col-xmod > 0 && col-xmod < 7 && board[col-xmod][row+1] == 4){
				//has a pusher to push to xModified -1y
				return true;
			}else{
				return false;
			}
		}else{
			//Black pushy
			
			if( isOutOfBound(col, row, xmod, color) )
				return false;
			else if(board[col+xmod][row+1] == 1 || board[col+xmod][row+1] == 2 ){
				//is blocked by one of its color
				return false;
			}else if(col-xmod > 0 && col-xmod < 7 &&  board[col-xmod][row-1] == 2){
				//has a pusher to push to xModified +1y
				return true;
			}else{
				return false;
			}
		}
	}
	
	/**
	 * Methode pour vérifier si on tente de déplacer un pion en dehors du board
	 * @param col - valeur en X original
	 * @param xmod - valeur en X de destination
	 * @return
	 */
	private boolean isOutOfBound(int col, int row, int xmod, int color){
		//TODO are those all good ?
		if(color == Client.WHITE){
			 if(col+xmod > 7 || col+xmod < 0 || row-1 < 0){
				//sort des col ou row
				return true;
			}else{
				return false;
			}
		}else{
			if(col+xmod > 7 || col+xmod < 0 || row+1 > 7){
				//sort des col ou row
				return true;
			}else{
				return false;
			}
		}
//		//TODO is this function good enough
//		if( (col == 0 && xmod == 1) || (col == 7 && xmod == -1) || (col+xmod < 0) || (col-xmod) > 7 || (col+xmod) > 7 )
//			return true;
//		else
//			return false;		
	}
	

	

}
