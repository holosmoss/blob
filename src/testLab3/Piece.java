package testLab3;

public class Piece {
	


	//type de piece, 
	//1 = pushy noir, 2 = pousseur noir
    //3 = pushy blanc, 4 = pousseur blanc
    private int valeur;    
    
    //identificateur de la pièce pour la retrouvé facilement
    private int ID;
    
    //Position de la piece sur le board, le board étant un int[][]
    private int x; //colonne
    private int y; //ligne

    /**
     * Constructeur
     * @param valeur - type de la pièce et couleur 
     * @param x - coordonnée en X du tableau int[X][Y] X = col
     * @param y - coordonnée en Y du tableau int[X][Y] Y = row
     */
	public Piece(int valeur, int x, int y, int ID){
    	this.valeur = valeur;    	
    	this.x = x;
    	this.y = y;
    	this.ID = ID;
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

	public int getID() {
		return this.ID;
	}

	public void setCol(int x) {
		this.x = x;
	}

	public void setRow(int y) {
		this.y = y;
	}
	
	
    
    
	
}
