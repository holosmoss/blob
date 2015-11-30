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
    private static final int ROW_1_BLACK = 5;
    private static final int ROW_2_BLACK = 10;
    private static final int ROW_3_BLACK = 25;
    private static final int ROW_4_BLACK = 50;
    private static final int ROW_5_BLACK = 125;
    private static final int ROW_6_BLACK = 250;
    private static final int ROW_7_BLACK = 500;
    
    private static final int ROW_0_WHITE = 500;
    private static final int ROW_1_WHITE = 250;
    private static final int ROW_2_WHITE = 125;
    private static final int ROW_3_WHITE = 50;
    private static final int ROW_4_WHITE = 25;
    private static final int ROW_5_WHITE = 10;
    private static final int ROW_6_WHITE = 5;
    private static final int ROW_7_WHITE = 1;
    
    private static final int COL_A = 4;
    private static final int COL_B = 3;
    private static final int COL_C = 2;
    private static final int COL_D = 2;
    private static final int COL_E = 2;
    private static final int COL_F = 2;
    private static final int COL_G = 3;
    private static final int COL_H = 4;
    
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
	
	public int isPositionDangerous(BoardState state, Move move, int color){
		int score = 0;
		int toX = move.getToColumn(); 
		int	toY = move.getToRow();
		int[][] coord = state.getState();
		
		
		//si on est blanc
		if(color == WHITE){
			
			//check si la case destination à un pusher ennemi à gauche(-1)
			//si oui on risque de ce faire bouffer la, alors inverse le EAT_SCORE
			if( (toX-1) >=0 && (toY-1) >=0 && coord[toX-1][toY-1] == 2)
				score = ~EAT_SCORE+1; // = -1000
			
			//idem pour la droite(+1)
			if( (toX+1) <=7 && (toY-1) >=0 &&coord[toX+1][toY-1] == 2)
				score = ~EAT_SCORE+1;
			
			//check s'il la case destination à un pushy ennemi à gauche(-1)
			if( (toX-1) >=0 && (toY-1) >=0 && coord[toX-1][toY-1] == 1)			
				//check si le pushy a un pusher
				if( (toX-2) >=0 && (toY-2) >=0 && coord[toX-2][toY-2] == 2);
					score = ~EAT_SCORE+1;
					
			//idem pour la droite(+1)
			if( (toX+1) <=7 && (toY-1) >=0 && coord[toX+1][toY-1] == 1)			
				//check si le pushy a un pusher
				if( (toX+2) <=7 && (toY-2) >=0 && coord[toX+2][toY-2] == 2);
					score = ~EAT_SCORE+1;		
				
		
		}
		//autrement on est noir
		else{
			//check si la case destination à un pusher ennemi à gauche(-1)
			//si oui on risque de ce faire bouffer la, alors inverse le EAT_SCORE
			if( (toX-1) >=0 && (toY+1) <=7 && coord[toX-1][toY+1] == 4)
				score = ~EAT_SCORE+1;
			
			//idem pour la droite(+1)
			if( (toX+1) <=7 && (toY+1) <=7 &&coord[toX+1][toY+1] == 4)
				score = ~EAT_SCORE+1;
			
			//check s'il la case destination à un pushy ennemi à gauche(-1)
			if( (toX-1) >=0 && (toY+1) <=7 && coord[toX-1][toY+1] == 3)			
				//check si le pushy a un pusher
				if( (toX-2) >=0 && (toY+2) <=7 && coord[toX-2][toY+2] == 4);
					score = ~EAT_SCORE+1;
					
			//idem pour la droite(+1)
			if( (toX+1) <=7 && (toY+1) <=7 && coord[toX+1][toY+1] == 3)			
				//check si le pushy a un pusher
				if( (toX+2) <=7 && (toY+2) <=7 && coord[toX+2][toY+2] == 4);
					score = ~EAT_SCORE+1;	
			
		
					
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
		
		//boucle qui attribut un score au move de la list
		for(int i = 0; i<sortedList.size(); i++){
			tmpScore = giveScore(state,  sortedList.get(i), color);
			sortedList.get(i).setScore(tmpScore);
		}
		
		//tri la liste care les Move implemente "Comparable"
		Collections.sort(sortedList);
		
		
		return sortedList;
	}
	
	/**
	 * Méthode qui calcule le score d'un move par rapport à l'état du board
	 * @param state
	 * @param move
	 * @param color
	 * @return
	 */
	private int giveScore(BoardState state, Move move, int color){
		
		int score = isGameFinish(state, color);
		
		//check si ce move terminerais la game, we never know !
		if( score != 0 ){
			return score;
		}
		//check si la position du move serait dangereuse
		score = isPositionDangerous(state, move, color);		
		
		//sinon il s'agit d'un move normal ou qui mange
		if(score != 0){
			score = canEat(state, move, color);
			return score;
		}
		
		return score;
	}
	
	
	public int leafValue(BoardState state, Move move){
		
		
		return 0;
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
	
	private int colMultiplier(int col){
        switch (col){
            case 0:
                return COL_A;
            case 1:
                return COL_B;
            case 2:
                return COL_C;
            case 3:
                return COL_D;
            case 4:
                return COL_E;
            case 5:
                return COL_F;
            case 6:
                return COL_G;
            case 7:
                return COL_H;
            default:
                return 1;
        }
    }

}
