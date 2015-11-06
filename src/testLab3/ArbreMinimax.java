package testLab3;

import testLab3.Generateur.Node;

public class ArbreMinimax {

	//TODO est-ce quon le met dans le generateur a la place (pour ordonee les feuilles)
	private Evaluateur eval;
	private Generateur gene;


	protected ArbreMinimax(Evaluateur eval, Generateur gene){
		this.eval = eval;
		this.gene = gene;
	}
	/**
	 * Récursion sur les node pour généré les prochaine nodes (gene) avec leurs valeurs (eval)
	 * jusqu'à une profondeur d
	 * L'algo se fait en profondeur first pour atteindre les feuille du premier enfant et les evaluer
	 * Le prunage se fera donc au fur et a mesure que lont creer larbre
	 * 
	 * @param node
	 * @param d
	 * @return value de la node
	 */
	protected int minimax(Node node, int d){
		return 1;
	}
	
}
