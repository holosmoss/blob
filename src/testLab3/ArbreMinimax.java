package testLab3;


import java.util.List;

import testLab3.Generateur.Node;

public class ArbreMinimax {

	//TODO est-ce quon le met dans le generateur a la place (pour ordonee les feuilles)
	private Evaluateur eval;
	private Generateur gene;
	private long startTime;
	private List<Node> nodeList;
	
	static final int MAX_DEPTH = 6; //profondeur de recherche max plus c'est grand
									//plus c'est précis, mais prendra plus de temps
    static final int TEMPS_MAX = 4900; // temps max en millisecondes


    /*
     * Constructeur par defaut
     */
    public ArbreMinimax(){
    	gene = new Generateur();
    	
    	
    }
    

    /**
     * 
     * @param board - l'état du board
     * @param currentPlayer - true = current, false = enemy
     * @param depth - profondeur de recherche max
     * @param alpha 
     * @param beta
     * @return
     */
    public int alphaBeta(Node node, boolean currentPlayer, int depth, int alpha, int beta) {
    	if ( System.currentTimeMillis() - startTime > TEMPS_MAX ) {
            return Integer.MIN_VALUE;
        }
    	if (depth <= 0) {
//            return node.move.score; // score associé au move de la node max ??
        }

        this.nodeList = gene.getNodeList(); //TODO 

        if (currentPlayer) { // notre AI tente de maximiser le score
            for (Node NodeTemp : nodeList) {
                alpha = Math.max(alpha, alphaBeta(NodeTemp, false, depth - 1, alpha, beta) );

                if (beta <= alpha) {
                    break; // prune
                }
            }
            return alpha;
        } else { // ennemi tente de minimiser le score
            for (Node NodeTemp : nodeList) {
                beta = Math.min(beta, alphaBeta(NodeTemp, true, depth - 1, alpha, beta));
                if (beta <= alpha) {
                    break; // prune
                }
            }
            return beta;
        }
    }
	
	/**
	 * retourne le best move selon le score retourné par l'arbre
	 * @return BestMove - le meilleur coup déterminer par l'arbre minMaxAlphaBeta
	 */
	public Move getBestMove(Node node){
		
		int best;
		this.startTime = System.currentTimeMillis();
	
		
		best = alphaBeta(node, true, MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE);
		
		Move bestMove = new Move(); //move vide
		
		//TODO bestMove = la node qui a scoré le plus...
		
		
		return bestMove;
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
