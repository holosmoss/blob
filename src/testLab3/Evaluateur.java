package testLab3;

import java.util.ArrayList;
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
	
	//méthode qui recoit le boardState et une liste de move, qu'on peut changer l'ordre par la suite
	public List<Move> sortMoveList(List<Move> moveList, BoardState boardState){
		List<Move> tmpList = new ArrayList<Move>();
		
		
		return tmpList;
	}
	
	
	public int leafValue(BoardState boardState){
		
		
		return 0;
	}

}
