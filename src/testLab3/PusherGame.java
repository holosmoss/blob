package testLab3;

import java.util.ArrayList;

public class PusherGame {
	
	private Generateur gene;
    private Evaluateur eval;
    
    //constructeur... appelé dans le client avant de démarrer une partie noir ou blanc
    public PusherGame(int clientColor, ArrayList<Piece> white, ArrayList<Piece> black){
    	
    	this.gene = new Generateur(clientColor, white, black);
    	
    	this.eval = new Evaluateur(this.gene, clientColor);
    	
    }
    
    
    public Generateur getGene() {
		return this.gene;
	}

	public Evaluateur getEval() {
		return this.eval;
	}

    
    

}
