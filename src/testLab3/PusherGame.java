package testLab3;


public class PusherGame {
	
	private Generateur gene;
    private Evaluateur eval;
	private ArbreMinimax minimaxTree;
    
 
    public PusherGame(){
    	//the real Board is static in client
    	//instantiate the minimax tree that will decide what moves we play
    	this.minimaxTree = new ArbreMinimax();
    }
    
    public Generateur getGene() {
		return this.gene;
	}

	public Evaluateur getEval() {
		return this.eval;
	}

    public String chooseMove(){
    	//ask the minimax to return the best move extrapolated from the current realBoardState
    	Move bestMove = this.minimaxTree.getBestMove(Client.realBoardState);
    	return bestMove.toString();
    }
    

}
