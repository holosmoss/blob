package testLab3;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Evaluateur {
	

	private Generateur gene;
	private int playerColor;
	
	 //TODO this is juste for test
    private int incrementLeaf = 1;
    
	//valeur des couleurs des joueurs
	static final int WHITE = 1;
	static final int BLACK = 2;
	
	static final int DIR_WHITE = -1;
	static final int DIR_BLACK = 1;
	
	//multiplicateur pour les lignes, utile pour 
	//évaluer les moves en profondeur sur le board
	
	//utilise ROW_X_WHITE
    private static final int ROW_0_BLACK = 1;
    private static final int ROW_1_BLACK = 2;
    private static final int ROW_2_BLACK = 3;
    private static final int ROW_3_BLACK = 5;
    private static final int ROW_4_BLACK = 6;
    private static final int ROW_5_BLACK = 7;
    private static final int ROW_6_BLACK = 8;
    private static final int ROW_7_BLACK = 10000;
    
    private static final int ROW_0_WHITE = 10000;
    private static final int ROW_1_WHITE = 8;
    private static final int ROW_2_WHITE = 7;
    private static final int ROW_3_WHITE = 6;
    private static final int ROW_4_WHITE = 5;
    private static final int ROW_5_WHITE = 3;
    private static final int ROW_6_WHITE = 2;
    private static final int ROW_7_WHITE = 1;
    
    private static final int EAT_SCORE = 1000;
    private static final int BASIC_SCORE = 10;
	
	
	
	public Evaluateur(Generateur generateur, int playerColor) {
		
		this.gene = generateur;
		this.playerColor = playerColor;

		
	}


	
	
	/**
	 * retourne un entier compris entre [min,max] inclusivement
	 * @param min
	 * @param max
	 * @return
	 */
	public int randomNumberWithRange(int min, int max)
	{
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}
	
	/**
	 * Détermine si le prochain état du board causé par le prochain move termine la partie
	 * @param state - l'état du board
	 * @param color - la couleur du joueur dont le move a été fait
	 * @return
	 */
	public int isGameFinish(BoardState state, int color){
		int score = 0;
		
		//si on est blanc
		if(color == WHITE){
			//check si on gagne
			if( !isTherePusherLeft(state, BLACK) || isThereAPieceOnWinningRow(state, WHITE) )
				score = Integer.MAX_VALUE;
						
			//check si on perd
			if( !isTherePusherLeft(state, WHITE) || isThereAPieceOnWinningRow(state, BLACK))
				score = Integer.MIN_VALUE;
		}
		//autrement on est noir
		else{
			//check si on gagne
			if( !isTherePusherLeft(state, WHITE) || isThereAPieceOnWinningRow(state, BLACK) )
				score = Integer.MAX_VALUE;
						
			//check si on perd
			if( !isTherePusherLeft(state, BLACK) || isThereAPieceOnWinningRow(state, WHITE) )
				score = Integer.MIN_VALUE;
			
		}
		return score;
	}
	
	/**
	 * Détermine si le prochain état du board causé par le prochain move laisse des pusher
	 * dans le jeu
	 * @param state - état du board
	 * @return boolean - true/false s'il en reste ou non
	 */
	public boolean isTherePusherLeft(BoardState state, int color){
		//check s'il rest au moins 1 pusher de notre couleur si oui return true
		HashMap<Integer, Piece> hashMap;
		//choix des pieces
		if(color == WHITE){
			hashMap=state.getWhitePieces();
		}
		else{
			hashMap=state.getBlackPieces();
		}
		//test pour pusher
		for(Entry<Integer, Piece> entry : hashMap.entrySet()) {
			 Piece piece = entry.getValue();
			 int value = piece.getValeur();
			 if(value == 2 || value == 4){
				 return true;
			 }
		}
		return false;
	}
	/**
	 * check si un pièce de couleur "color" gagne la partie
	 * @param state - l'état du board a évaluer
	 * @param color - couleur qu'on évalue
	 * @return boolean - true/false
	 */
	public boolean isThereAPieceOnWinningRow(BoardState state, int color){
		int board[][] = state.getState();
		
		//check si les blanc on une piece gagnante, winning row = 0 donc board[i][0]
		if(color == WHITE){
			for(int i =0; i < 8; i++){
				//check si la dernière ligne ennemi contient un pusher ou un pushy
				if(board[i][0] == 3 || board[i][0] == 4)
					return true;
			}
		}
		//check si les noir on une piece gagnante, winning row = 7 donc board[i][7]
		else{
			for(int i =0; i < 8; i++){
				//check si la dernière ligne ennemi contient un pusher ou un pushy
				if(board[i][7] == 1 || board[i][7] == 2)
					return true;
			}
			
		}
		
		return false;
		
	}
	
	/**
	 * Méthode qui va regarder les coordonnées X et Y d'un move pour
	 * déterminer si ce dernier mange, sinon retourne un score normal
	 * Le score est multiplier par la constante de ligne
	 * @param state - état du board avant le move
	 * @param move - move à évaluer
	 * @param color - couleur du joueur
	 * @return score associé au move
	 */
	public int canEat(BoardState state, Move move, int color){
		int score = 0;
		
		int toX = move.getToColumn(); 
		int	toY = move.getToRow();
		
		//si on peut manger
		if( state.isMoveEating(toX, toY, color) ){			
			score = EAT_SCORE*rowMultiplier(toY,color);
			move.setScore(score);
		}
		//sinon c'est un move normal
		else{
			score = BASIC_SCORE*rowMultiplier(toY,color);
			move.setScore(score);
		}
		
		return score;
	}

	
	/**
	 * Méthode qui reçoit une liste de move, elle leur attribut des score
	 * En suite la liste est trié
	 * @param moveList
	 * @param state
	 * @param color
	 * @return
	 */
	public ArrayList<Move> sortMoveList(ArrayList<Move> moveList, BoardState state, int color){
		ArrayList<Move> sortedList = moveList;
		int tmpScore = 0;
		if(favoredIdList == null){
			findFavoredPieces(state);
		}
		//boucle qui attribut un score au move de la list
		for(int i = 0; i<sortedList.size(); i++){
			tmpScore = giveScore(state,  sortedList.get(i), color);
			sortedList.get(i).setScore(tmpScore);
		}

		//tri la liste care les Move implemente "Comparable"
		Collections.sort(sortedList);

		return sortedList;
	}
	private ArrayList<Integer> favoredIdList;
	//TODO how do we evaluate the enemies ?
	private ArrayList<Integer> enemyfavoredIdList;
	private void findFavoredPieces(BoardState state){
		HashMap<Integer, Piece> ourPieces = (Client.color == Client.WHITE) ? state.getWhitePieces() : state.getBlackPieces();
		//TODO  will we have to remove them if they get eaten ?
		//TODO if calling unexisting key in the hashMaps return an error we might
		favoredIdList = new ArrayList<Integer>();
		for(Entry<Integer, Piece> entry : ourPieces.entrySet()) {
			//id of the piece
		    int key = entry.getKey();
		    Piece piece = entry.getValue();
		    if(piece.getCol() < 4){
		    	//those piece are on the left side of the board which we favor
		    	//TODO we need to remember their ID to spot them in moves
		    	//TODO is knowing hteir type usefull or can we just call the ID in the state to find out each time ?
		    		//is that long anyways.
		    	favoredIdList.add(key);
		    	
		    }
		}
	}
	
	private int giveScore(BoardState state, Move move, int color){
		int score = 0;
		if(color == Client.color){
			int movedID = move.getPieceID();
			HashMap<Integer,Piece> ourPieces = (color == Client.WHITE) ? state.getWhitePieces():state.getBlackPieces();
			Piece pieceMoved = ourPieces.get(movedID);
			
			//Favoritisme pour la gauche
			if(favoredIdList.contains(movedID)){
				//+ 10 pts parceque on aime cette piece
				score += 10;
				//TODO this test for null is just weird, what happens when a favored piece is eaten
				if(ourPieces.get(movedID) != null){
					if(pieceMoved.getValeur()%2 == 0){
						//on aime plus les Pushers !
						score += 10;
					}
				}
			}
			//Safety first TODO isProtected  return a nullPointerSometime
//			if(isProtected(state, pieceMoved)){
//				//GROS bonus parceque on avance et rest protégé
//				score += 15;
//			}
//			if(isProtecting(state, pieceMoved)){
//				//bonus parceque on prot`ge une de nos pièce
//				score += 10;
//			}
		}else{
			//enemy moves
			//TODO do we keep a favored for them too ? or do we have a mostWanted list or enemyOf the state.
		}
		return score;
	}
	private boolean isProtected(BoardState state, Piece piece){
		//TODO verify the states pushers behind + pushy that could move to protect
		//TODO do we want the pushy protection to be less important or is the rest of our heuristic making this redundant
		//Client.print("im in!"+piece.getID());
		int[][] board = state.getState();
		int ourPusher;
		int ourPushy;
		//row behind us
		int rowMod;
		if(Client.color == Client.WHITE){
			ourPusher = 2;
			ourPushy = 1;
			rowMod = 1;
		}else{
			ourPusher = 4;
			ourPushy = 3;
			rowMod = -1;
		}
		int rowBehind = piece.getRow()+rowMod;
		
		if(rowBehind >= 0 && rowBehind <= 7){
			//can be protected
			//check left
			int leftCol = piece.getCol()-1;
			if(leftCol >= 0){
				//still on the board
				if(board[leftCol][rowBehind] == ourPusher ){
					return true;//we have a pusher backing us
				}
				if(board[leftCol][rowBehind] == ourPushy){
					//we have a pushy to the left
					int secondRowBehind = piece.getRow()+(2*rowMod);
					int secondColLeft = piece.getCol()-2;
					if(secondColLeft >= 0 && secondRowBehind >= 0 && secondRowBehind <= 7){
						//still on board
						if(board[secondColLeft][secondRowBehind] == ourPusher){
							//that pushy can be pushed to protect
							return true;
						}
					}
				}
			}
			//check right
			int rightCol = piece.getCol()+1;
			if(rightCol <= 7){
				//still on the board
				//still on the board
				if(board[rightCol][rowBehind] == ourPusher ){
					return true;//we have a pusher backing us
				}
				if(board[rightCol][rowBehind] == ourPushy){
					//we have a pushy to the left
					int secondRowBehind = piece.getRow()+(2*rowMod);
					int secondColRight = piece.getCol()+2;
					if(secondColRight <= 7 && secondColRight >= 0 && secondRowBehind <= 7){
						//still on board
						if(board[secondColRight][secondRowBehind] == ourPusher){
							//that pushy can be pushed to protect
							return true;
						}
					}
				}
			}
		}
		//no one can help
		return false;
	}
	
	private boolean isProtecting(BoardState state, Piece piece){
		//TODO check state if we have pieces at our protectable diags
		return false;
	}
	private boolean isSafe(BoardState state, Piece piece){
		//TODO should this be bool or int return for granularity ?
		//is there an enemy that can eat us ?
		int[][] board = state.getState();
		int theirPusher;
		int theirPushy;
		//row in front of us
		int rowMod;
		//is there a piece that could eat us
		boolean canBeEaten = false;
		if(Client.color == Client.WHITE){
			theirPusher = 4;
			theirPushy = 3;
			rowMod = -1;
		}else{
			theirPusher = 2;
			theirPushy = 1;
			rowMod = 1;
		}
	    int rowInFront = piece.getRow()+rowMod;
		if(rowInFront >= 0 && rowInFront <= 7){
			//still on board
			//check left
			int leftCol = piece.getCol()-1;
			if(leftCol >= 0){
				//still on the board
				if(board[leftCol][rowInFront] == theirPusher ){
					canBeEaten = true;//a pusher can eat us
				}
				if(board[leftCol][rowInFront] == theirPushy ){
					//a pushy could eat us
					int secondLeftCol = piece.getCol()-2;
					int secondRowInFront = piece.getRow()+(2*rowMod);
					if(secondLeftCol >= 0 && secondRowInFront >= 0 && secondRowInFront <=7){
						if(board[secondLeftCol][secondRowInFront] == theirPusher){
							canBeEaten = true;//the pushy could be pushed to eat
						}
					}
				}
			}
			//TODO do we want a double eating value  (could be balanced with double protected value)
			//check right
			int rightCol = piece.getCol()+1;
			if(rightCol <= 7){
				if(board[rightCol][rowInFront] == theirPusher ){
					canBeEaten = true;//a pusher can eat us
				}
				if(board[rightCol][rowInFront] == theirPushy ){
					int secondRighttCol = piece.getCol()+2;
					int secondRowInFront = piece.getRow()+(2*rowMod);
					if(secondRighttCol <= 7 && secondRowInFront >= 0 && secondRowInFront <=7){
						if(board[secondRighttCol][secondRowInFront] == theirPusher){
							canBeEaten = true;//the pushy could be pushed to eat
						}
					}
				}
			}
		}
		//return false if we can be eaten and have no protection
		if(canBeEaten && !isProtected(state, piece)){
			return false;
		}else{
			return true;
		} //TIEIT
	}
	
	
	public int leafValue(BoardState boardState){
		int score = 0;
		HashMap<Integer, Piece> ourPieces = (Client.color == Client.WHITE) ? boardState.getWhitePieces() : boardState.getBlackPieces();
		
		for(Entry<Integer, Piece> entry : ourPieces.entrySet()) {
		    Piece piece = entry.getValue();
		    if(piece.getValeur()%2 == 0){
		    	//pusher!
		    	//TODO do we want the multiplier for unsafe pieces to be 0 so that we avoid moving forward without protection ?
		    	int safetyMultiplier = 1;//TODO this is bugged(isSafe(boardState, piece))? 2:1;
		    	score += safetyMultiplier*rowMultiplier(piece.getRow(),Client.color);
		    }
		}
		return score;
	}
	
	/**
	 * Méthode pour interpretté les constante multiplicateur des lignes
	 * @param row - la ligne ou on ce trouve
	 * @param color - la couleur du joueur
	 * @return le multiplicateur correspondant
	 */
	private int rowMultiplier(int row, int color){
        if(color == WHITE) {
            switch (row){
                case 0:
                    return ROW_0_WHITE;
                case 1:
                    return ROW_1_WHITE;
                case 2:
                    return ROW_2_WHITE;
                case 3:
                    return ROW_3_WHITE;
                case 4:
                    return ROW_4_WHITE;
                case 5:
                    return ROW_5_WHITE;
                case 6:
                    return ROW_6_WHITE;
                case 7:
                    return ROW_7_WHITE;
                default:
                    return 1;
            }
        }else{
            switch (row){
                case 0:
                    return ROW_0_BLACK;
                case 1:
                    return ROW_1_BLACK;
                case 2:
                    return ROW_2_BLACK;
                case 3:
                    return ROW_3_BLACK;
                case 4:
                    return ROW_4_BLACK;
                case 5:
                    return ROW_5_BLACK;
                case 6:
                    return ROW_6_BLACK;
                case 7:
                    return ROW_7_BLACK;
                default:
                    return 1;
            }
        }
    }

}
