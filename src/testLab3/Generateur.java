package testLab3;

public class Generateur {
	
	//pour v�rifier si le dernier coup �tait valide
	private int[][] boardAvant = new int[8][8];	
	private int[][] boardApres = new int[8][8];
	
	/**
	 * Le generateur est appel� par l'arbre pour connaitre les enfants de  la node
	 * Il evalue tout les move possible selon la position et le type de la piece ainsi que les piece voisine
	 */
	

	/**
	 * Node utilis/ par l<arbre min max
	 * @author Holos
	 *
	 */ 
	protected class Node{
		private Node(){
			//TODO doit connaitre sont statut min ou max
			//TODO doit connaitre le move qu'elle repr�sente (changement) au board state
			//TODO is leaf:
				//TODO info pour leval 
				//manger un pousseur 
				//bouger un pousseur a une place safe
				//
		}
	}

}
