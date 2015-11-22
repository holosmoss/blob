package testLab3;

import java.util.List;

public class Evaluateur {
	

	private Generateur gene;
	private int playerColor;
	
	public Evaluateur(Generateur generateur, int playerColor) {
		
		this.gene = generateur;
		this.playerColor = playerColor;
		
	}

	public String chooseMove() {
		
		List<Move> moveList = this.gene.generateurMouvement(this.playerColor);
		Move bestMove = null;
		String from;
		String to;
				
		//retourne un move aléatoire de la liste fourni par le générateur
		bestMove = moveList.get( randomNumberWithRange(1, moveList.size() ) );
		
		from = bestMove.getMoveCoordinate(bestMove.getFromRow(), bestMove.getFromColumn() );
		to = bestMove.getMoveCoordinate(bestMove.getToRow(), bestMove.getToColumn() );
		
		//retourne les coordonnées du move sous forme de String
		return from+to ; //ex: A2A3 from A2 to A3
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
	

}
