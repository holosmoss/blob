package testLab3;


import java.util.ArrayList;
import java.util.List;


public class ArbreMinimax {

	private Generateur gene;
	private Evaluateur eval;
	private long startTime;
	
	static final int MAX_DEPTH = 6; //profondeur de recherche max plus c'est grand
									//plus c'est pr�cis, mais prendra plus de temps
    static final int TEMPS_MAX = 4900; // temps max en millisecondes


    /*
     * Constructeur par defaut
     */
    public ArbreMinimax(){
    	gene = new Generateur();
    	//TODO how do we handle this
    	eval = new Evaluateur(gene, 1);
    }
    

    /**
     * 
     * @param board - l'�tat du board
     * @param currentPlayer - true = current, false = enemy
     * @param depth - profondeur de recherche max
     * @param alpha 
     * @param beta
     * @return
     */
    public int alphaBeta(BoardState lastState, Move move, int depth, int alpha, int beta) {
    	/* 
    	 * 	
    	 * TODO use the timer to control what happens at max time
    	 *if ( System.currentTimeMillis() - startTime > TEMPS_MAX ) {
            return Integer.MIN_VALUE;
        }
    	 *
    	 *
    	 *TODO currentPlayer is knowable from the Move currentColor and the client ourcolor...
    	 *
    	 */
    	boolean maxNode;
    	if(move.getCurrentColor() == Client.color){
    		maxNode  = true;
    	}else{
    		maxNode = false;
    	}
    	System.out.println("----alphaBeta  : a="+alpha+" b="+beta+" is max "+maxNode);

    	
    	//generate the new BoardState for our move : and generate the moves from that boardState.
    	BoardState tempState = new BoardState(lastState,move);
    	
    	//TODO is the eval in the minimax just for this ?
    	//TODO or is the boardstate the one that should do this ?
    	//Eval if we are in a game ending state.
    	int endingValue = eval.isGameFinish(tempState, Client.color);
    	if( endingValue != 0){
    		//we have either lost or won with this move. return its value since its a leaf
    		return endingValue;
    	}
    	//verify depth
    	if(depth == 0){
    		//we reached the max depth of the tree
    		//evaluate current move as a leaf and return its value
    		System.out.println("----alphaBeta  : Leaf ! "+eval.leafValue(tempState));
    		return eval.leafValue(tempState);
    	}
    	//we are not at the end of the tree so lets generate an other generation
    	//TODO use a test list (hardcoded moves for us and them(we need to know the active player));
    	ArrayList<Move> childs = gene.generateurMouvement(tempState,move.getCurrentColor());
    	
    	
    	if(maxNode){
    		//TODO eval should order them (differently for min max ??)
    		//we are in a max node
    		for(Move child : childs){
    			//check for the 
                alpha = Math.max(alpha, alphaBeta(tempState, child, depth - 1, alpha, beta) );
                if(alpha >= beta)
                {
            		System.out.println("----alphaBeta  : pruned!  max");
                	//TODO do we return alpha or beta ?
                    return beta;
                }
    		}
    		return alpha;
    	}else{
    		for (Move child : childs) {
                beta = Math.min(beta, alphaBeta(tempState, child, depth - 1, alpha, beta));
                if (beta <= alpha) {
            		System.out.println("----alphaBeta  : pruned!  min");
                	//TODO do we return alpha or beta ?
                    return alpha; 
                }
            }
            return beta;
    	}
    }
	
	/**
	 * retourne le best move selon le score retourn� par l'arbre
	 * @return BestMove - le meilleur coup d�terminer par l'arbre minMaxAlphaBeta
	 */
	public Move getBestMove(BoardState currentState){
		System.out.println("--minimax getBestMove :");
		//TODO we could just reference the client real state here right ???
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		int bestScore = Integer.MIN_VALUE;
		Move bestMove = null;
		//TODO find the best places to check time
		//this.startTime = System.currentTimeMillis();
		//TODO this always start with our color right ?
		ArrayList<Move> possibleMoves = gene.generateurMouvement(currentState, Client.color);
		
		for(Move move : possibleMoves){
			//Loop the 1st gen moves to find the one which result in the best score
			if(bestMove == null){
				System.out.println("--minimax BEST : "+move.toString());
				bestMove = move;
			}
			//TODO is the way to decide if max a true false for currentPlayer ? 
			//TODO do we give the current State ? or a tempstate ?
			//MAX_DEPTH
			alpha = Math.max(alpha, alphaBeta(currentState, move, 2,  alpha,beta));
			if(alpha > bestScore)
	        {
	            bestMove = move;
	            bestScore = alpha;
	        	System.out.println("--minimax new BEST : "+move.toString());
	        }
			
		}
    	System.out.println("--minimax return : ");
		//each node do stuff like : currentNode.getState().getCurrentPlayer().
		//So that the BoardState
		//TODO(we create 1st gen Child here and see which gets a best score.)
		//TODO loop them and choose the one where the best value is found
		//best = alphaBeta(move, true, MAX_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE);
		//TODO get a handle on the Move that returns the chosen path
		
		/**
		 * GameState is used to transmit the move we made (so this should be a Move...)
		 * http://stackoverflow.com/questions/15447580/java-minimax-alpha-beta-pruning-recursion-return
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
		return bestMove;
	}
	
	
	/**
	 * R�cursion sur les node pour g�n�r� les prochaine nodes (gene) avec leurs valeurs (eval)
	 * jusqu'� une profondeur d
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
