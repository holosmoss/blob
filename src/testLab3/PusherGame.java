package testLab3;

import java.util.ArrayList;

public class PusherGame {
	
	private Generateur gene;
    private Evaluateur eval;
    //TODO playerColor is a static value in client
	private ArbreMinimax minimaxTree;
    
    //constructeur... appelé dans le client avant de démarrer une partie noir ou blanc
    public PusherGame(int clientColor, ArrayList<Piece> white, ArrayList<Piece> black){
    	//TODO this probably needs to be removed
    	this.gene = new Generateur(clientColor, white, black);
    	
    	this.eval = new Evaluateur(this.gene, clientColor);
    	
    }
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
