package testLab3;


import testLab3.Generateur.Node;

public class ArbreMinimax {

	//TODO est-ce quon le met dans le generateur a la place (pour ordonee les feuilles)
	private Evaluateur eval;
	private Generateur gene;
	private 
	

    static final int TEMPS_MAX = 4900; // temps max en millisecondes
    static final int TEMPS_MARGE = 100; //on garde 100ms pour envoer le Move

    /*
     * Constructeur par defaut
     */
    public ArbreMinimax(){
    	gene = new Generateur();
    	
    	
    }
    

    /**
     * 
     * @param board - l'état du board
     * @param currentPlayer
     * @param depth
     * @param maxDepth
     * @param alpha
     * @param beta
     * @return
     */
    public int alphaBeta(Board board, boolean currentPlayer, int depth, int maxDepth, int alpha, int beta) {
    	if ( /*calculer si temps de 4.5s dépassé*/ ) {
            return 0;
        } else if (depth >= maxDepth) {
            return 0;//retourne de quoi si la prof. de recherche est trop big
        }

        List<Move> moveList = gene.getMoveList(); //TODO

        if (currentPlayer) { // notre AI tente de maximiser le score
            for (Move moveTemp : moveList) {
                alpha = Math.max(alpha, alphaBeta(moveTemp, false, depth + 1, alpha, beta));

                if (beta <= alpha) {
                    break; // prune
                }
            }
            return alpha;
        } else { // ennemi tente de minimiser le score
            for (Move moveTemp : moveList) {
                beta = Math.min(beta, alphaBeta(moveTemp, true, depth + 1, alpha, beta));
                if (beta <= alpha) {
                    break; // prune
                }
            }
            return beta;
        }
    }
	
	/**
	 * 
	 * @return BestMove - le meilleur coup déterminer par l'arbre minMaxAlphaBeta
	 */
	public Move getBestMove(){
		Move bestMove = new Move(); //move vide
		
		
		
		
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
