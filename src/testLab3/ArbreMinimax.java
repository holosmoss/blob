package testLab3;


import java.util.List;


public class ArbreMinimax {

	//TODO est-ce quon le met dans le generateur a la place (pour ordonee les feuilles)
	private Evaluateur eval;
	private Generateur gene;
	private long startTime;
	private List<Move> moveList;
	
	static final int MAX_DEPTH = 6; //profondeur de recherche max plus c'est grand
									//plus c'est précis, mais prendra plus de temps
    static final int TEMPS_MAX = 4900; // temps max en millisecondes


    /*
     * Constructeur par defaut
     */
    public ArbreMinimax(){
    	//gene = new Generateur();
    	
    	
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
    public int alphaBeta(Move move, boolean currentPlayer, int depth, int alpha, int beta) {
    	/* if its a final move (win, lose) return value (heuristic)
    	 * 	TODO we need the boardstate for the eval of the win lose state
    	 *  ?? TODO is the board state a better place to decide this ? on le laisse dans eval pour linstant
    	 * if its maximum depth (leaf node) return value (heuristic)
    	 * else produce childrens 
    	 * 		recursion with pruning alpha beta
    	 * 	
    	 * TODO have a boardState object to store the Pieces position
    	 * -the BoardState should know who is the currentPlayer (to know if its color is ours and if we do a min or a max node to use ?)
    	 *if ( System.currentTimeMillis() - startTime > TEMPS_MAX ) {
            return Integer.MIN_VALUE;
        }
    	 *
    	 *
    	 */
    	//is the boolean currentPlayer appropriate ???
    	if(currentPlayer){
    		//we are in a max node
    		//TODO why does the gene ask for color it knows it already right ?
    		//TODO genreator is assuming we are white ? wtf is going on there
    		List<Move> childs = gene.generateurMouvement(1);
    		for(Move child : childs){
    			//check for the 
                alpha = Math.max(alpha, alphaBeta(child, false, depth - 1, alpha, beta) );
                if(alpha >= beta)
                {
                	//TODO do we return alpha or beta ?
                    return beta;
                }
    		}
    		return alpha;
    	}else{
    		for (Move NodeTemp : moveList) {
                beta = Math.min(beta, alphaBeta(NodeTemp, true, depth - 1, alpha, beta));
                if (beta <= alpha) {
                    return alpha; 
                }
            }
            return beta;
    	}
    }
	
	/**
	 * retourne le best move selon le score retourné par l'arbre
	 * @return BestMove - le meilleur coup déterminer par l'arbre minMaxAlphaBeta
	 */
	public Move getBestMove(BoardState currentState){
		
		int best;
		this.startTime = System.currentTimeMillis();
		
		//each node do stuff like : currentNode.getState().getCurrentPlayer().
		//So that the BoardState
		//TODO(we create 1st gen Child here and see which gets a best score.)
		//TODO loop them and choose the one where the best value is found
		//best = alphaBeta(move, true, MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE);
		//TODO get a handle on the Move that returns the chosen path
		
		/**
		 * GameState is used to transmit the move we made (so this should be a Move...)
		 * 
		 * 
		 * public GameState move(GameState state) 
		 	int alpha = -INFINITY;
		    int beta = INFINITY;
		    int bestScore = -Integer.MAX_VALUE;
		    GameTreeNode gameTreeRoot = new GameTreeNode(state);
		    GameState bestMove = null;
		    for(GameTreeNode child: gameTreeRoot.getChildren())
		    {
		        if(bestMove == null)
		        {
		            bestMove = child.getState();
		        }
		        alpha = Math.max(alpha, miniMax(child, plyDepth - 1, alpha, beta));
		        if(alpha > bestScore)
		        {
		            bestMove = child.getState();
		            bestScore = alpha;
		        }
		    }
		    return bestMove;
		 */
		//TODO return the Move to the Game that will update the BoardState
		return new Move(1,1,1,1,1,1);
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
	protected int minimax(Move move, int d){
		return 1;
	}
	
}
