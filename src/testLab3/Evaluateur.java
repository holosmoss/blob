package testLab3;

import java.util.ArrayList;
import java.util.List;

public class Evaluateur {
	

	private Generateur gene;
	private int playerColor;
	
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
	 * Détermine si le move termine la partie
	 * @param m
	 * @return
	 */
	public int isGameFinish(BoardState boardState){
		int score = 0;
		
		
		
		
		return score;
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
