package testLab3;

public class ArbreMinimax {

	private Evaluateur eval;
	private Generateur gene;

	/**
	(* the minimax value of n, searched to depth d *)
	 fun minimax(n: node, d: int): int =
	   if leaf(n) or depth=0 return evaluate(n)
	   if n is a max node
	      v := L
	      for each child of n
	         v' := minimax (child,d-1)
	         if v' > v, v:= v'
	      return v
	   if n is a min node
	      v := W
	      for each child of n
	         v' := minimax (child,d-1)
	         if v' < v, v:= v'
	      return v
	   **/
	protected ArbreMinimax(Evaluateur eval, Generateur gene){
		this.eval = eval;
		this.gene = gene;
	}
	/**
	 * Récursion sur les node pour généré les prochaine nodes (gene) avec leurs valeurs (eval)
	 * jusqu'à une profondeur d
	 * @param node
	 * @param d
	 * @return value de la node
	 */
	protected int minimax(Node node, int d){
		return 1;
	}
	
	
	/**
	 * Node de l'arbre minimax
	 * @author Holos
	 *
	 */
	private class Node{
		private Node(){
			//TODO doit connaitre sont statut min ou max
			//TODO doit connaitre le move qu'elle représente (changement) au board state
		}
	}
}
