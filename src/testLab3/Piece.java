package testLab3;

public class Piece {
	


	//type de piece, 
	//1 = pushy noir, 2 = pousseur noir
    //3 = pushy blanc, 4 = pousseur blanc
    private int valeur;    
    
    
    
    //Position de la piece sur le board, le board �tant un int[][]
    private int x;
    private int y;

    /**
     * Constructeur
     * @param valeur - type de la pi�ce et couleur 
     * @param x - coordonn�e en X du tableau int[X][Y] X = col
     * @param y - coordonn�e en Y du tableau int[X][Y] Y = row
     */
	public Piece(int valeur, int x, int y){
    	this.valeur = valeur;    	
    	this.x = x;
    	this.y = y;
    }
	
    public int getValeur() {
		return valeur;
	}
    
	public int getCol() {
		return x;
	}


	public int getRow() {
		return y;
	}
    
    
	
}
