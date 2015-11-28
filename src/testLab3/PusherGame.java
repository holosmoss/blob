package testLab3;


public class PusherGame {
	

	private ArbreMinimax minimaxTree;
    
 
    public PusherGame(){
    	//the real Board is static in client
    	//instantiate the minimax tree that will decide what moves we play
    	this.minimaxTree = new ArbreMinimax();
    }
    

    public Move chooseMove(){
    	//ask the minimax to return the best move extrapolated from the current realBoardState
    	Move bestMove = this.minimaxTree.getBestMove(Client.realBoardState);
    	return bestMove;
    }
    

}
