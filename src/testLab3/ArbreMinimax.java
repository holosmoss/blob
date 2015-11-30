package testLab3;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ArbreMinimax {

	private Generateur gene;
	private Evaluateur eval;
	private long startTime;
	
	static final int MAX_DEPTH = 9; //profondeur de recherche max plus c'est grand
									//plus c'est précis, mais prendra plus de temps
    static final int TEMPS_MAX = 1900; // temps max en millisecondes
    //TODO this is juste for test
    private int incrementLeaf = 1;
    private int decrementLeaf = Integer.MAX_VALUE;
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
     * @param board - l'état du board
     * @param currentPlayer - true = current, false = enemy
     * @param depth - profondeur de recherche max
     * @param alpha 
     * @param beta
     * @return
     */
    
    public int alphaBeta(BoardState lastState, Move move, int depth, int alpha, int beta) {
    	boolean maxNode;
    	int nextColor;
		if(move.getCurrentColor() == Client.color){
    		maxNode  = true;
    		nextColor = Client.enemyColor;
    	}else{
    		maxNode = false;
    		nextColor = Client.color;
    	}
    	//Client.print("----alphaBeta  : a="+alpha+" b="+beta+" is max "+maxNode+"------------profondeur : "+depth);
    	//Client.print("the received state : ");
    	//lastState.affichageGrille();
    	//generate the new BoardState for our move : and generate the moves from that boardState.
    	BoardState tempState = new BoardState(lastState,move);
		//Client.print("~~~~~~!!!!~~~~~ time elapsed  "+(System.currentTimeMillis() - startTime));
    	if ( System.currentTimeMillis() - startTime > TEMPS_MAX ) {
    		//TODO what do we return ?
            //Client.print("time out "+alpha);
    		int leafValue = eval.leafValue(tempState, move, nextColor);
    		return leafValue;
        }
    	
    	
    	

    	//TODO pour tets
    	//Client.print("the changed state : ");
    	//tempState.affichageGrille();
    	//TODO is the eval in the minimax just for this ?
    	//TODO or is the boardstate the one that should do this ?
    	//Eval if we are in a game ending state.
    	int endingValue = eval.isGameFinish(tempState, Client.color);
    	if( endingValue != 0){
    		//we have either lost or won with this move. return its value since its a leaf
    		//Client.print("END --- "+endingValue);
    		return endingValue;
    	}
    	//verify depth
    	if(depth == 0){
    		//we reached the max depth of the tree
    		//evaluate current move as a leaf and return its value
    		incrementLeaf = incrementLeaf+1;
    		//System.out.println("----alphaBeta  : Leaf ! "+randomNum);
    		int leafValue = eval.leafValue(tempState, move, nextColor);
    		//Client.print("END leaf --- "+leafValue);
    		decrementLeaf = decrementLeaf-1;
    		return leafValue;//incrementLeaf;//incrementLeaf;//
    	}
    	//we are not at the end of the tree so lets generate an other generation
    	//TODO use a test list (hardcoded moves for us and them(we need to know the active player));
    	//TODO the move.GetCurrentcolor needs to change created as the proper type so the inverse of our current color ?
    	ArrayList<Move> childs = gene.generateurMouvement(tempState,nextColor);
    	
    	//attribut les scores des moves et les tris ICI
    	childs = this.eval.sortMoveList(childs, tempState, nextColor);
    	
    	
    	if(maxNode){
    		//TODO eval should order them (differently for min max ??)
    		//we are in a max node
    		for(Move child : childs){
    			//check for the 
    			//TODO test creating a new temp boardState for the next gen which is our parent state with the parent move applied
                //Client.print(">>>>>>>parent depth "+depth);
                //lastState.affichageGrille();
    			alpha = Math.max(alpha, alphaBeta(tempState, child, depth - 1, alpha, beta) );
                if(alpha >= beta)
                {
            		//Client.print("----alphaBeta  : pruned!  max : return ::"+beta);
                	//TODO do we return alpha or beta ?
                    return beta;
                }
    		}
    		//Client.print("----alphaBeta  : return "+alpha);

    		return alpha;
    	}else{
    		for (Move child : childs) {
    			//TODO test creating a new temp boardState for the next gen which is our parent state with the parent move applied
    			//TODO instead of tempState used to generate the moves 
                //Client.print(">>>>>>>parent depth "+depth);
                //lastState.affichageGrille();
    			beta = Math.min(beta, alphaBeta(tempState, child, depth - 1, alpha, beta));
                if (beta <= alpha) {
                	//Client.print("----alphaBeta  : pruned!  min "+alpha);
                	//TODO do we return alpha or beta ?
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
		Client.print("===================================minimax getBestMove :");
		currentState.affichageGrille();
    	//TODO pour tets
		//currentState.affichageGrille();
		//TODO we could just reference the client real state here right ???
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		int bestScore = Integer.MIN_VALUE;
		Move bestMove = null;
		//TODO find the best places to check time
		this.startTime = System.currentTimeMillis();
		//TODO this always start with our color right ?
		ArrayList<Move> possibleMoves = gene.generateurMouvement(currentState, Client.color);
		
		//tri la list selon le score de chaque move, 
		//c'est ici qu'on attribut un score à nos move
		possibleMoves = this.eval.sortMoveList(possibleMoves, currentState, Client.color);
		
		
		for(Move move : possibleMoves){
			//Loop the 1st gen moves to find the one which result in the best score
			if(bestMove == null){
				//System.out.println("===================================minimax BEST : "+move.toString());
				bestMove = move;
			}
			//TODO is the way to decide if max a true false for currentPlayer ? 
			//TODO do we give the current State ? or a tempstate ?
			//MAX_DEPTH
			alpha = Math.max(alpha, alphaBeta(currentState, move, MAX_DEPTH,  alpha,beta));
			if(alpha > bestScore)
	        {
	            bestMove = move;
	            bestScore = alpha;
	        	//System.out.println("===================================minimax new BEST : "+move.toString());
	        }
			
		}
    	System.out.println("===================minimax ("+bestScore+") return :"+bestMove.getCurrentColor()+":"+bestMove.toString());
		
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
	protected int minimax(Move move, int d){
		return 1;
	}
	
}
