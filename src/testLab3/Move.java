package testLab3;

/**
 * Classe qui décrit un mouvement possible d'une pièce
 * Sa provenance "from" row ou column, et sa destination "to" row et column
 * 
 * On devrait populer une liste de Move valide dans le generateur de move
 * pour l'utiliser dans l'arbre MinMaxAlphaBeta
 *
 * @author Joel
 *
 */

public class Move implements Comparable<Move>{
	
	
	private int fromRow; // from X
	private int fromColumn; // from Y
	private int toRow; // to X
	private int toColumn; // to Y
    private int score;


    
	public Move(int fromRow, int fromColumn, int toRow, int toColumn){
		
		this.fromRow = fromRow;
		this.fromColumn = fromColumn;
		this.toRow = toRow;
		this.toColumn = toColumn;
		
	}

    public Move(int fromRow, int fromColumn, int toRow, int toColumn, int score){

        this.fromRow = fromRow;
        this.fromColumn = fromColumn;
        this.toRow = toRow;
        this.toColumn = toColumn;
        this.score = score;

    }
    
	public int getFromRow() {
		return fromRow;
	}

	public void setFromRow(int fromRow) {
		this.fromRow = fromRow;
	}

	public int getFromColumn() {
		return fromColumn;
	}

	public void setFromColumn(int fromColumn) {
		this.fromColumn = fromColumn;
	}

	public int getToRow() {
		return toRow;
	}

	public void setToRow(int toRow) {
		this.toRow = toRow;
	}

	public int getToColumn() {
		return toColumn;
	}

	public void setToColumn(int toColumn) {
		this.toColumn = toColumn;
	}

    public int getScore(){
        return this.score;
    }

    public void setScore(int score){
        this.score = score;
    }
    
    
    /**
     * permet de changer les coordonnés en int d'un move en coordonné accepté par le board
     * Exemple: 00 = A8 -> ligne 0 Colone 0 du board int[][]
     * TODO effacer si pas utile
     * @param ligne
     * @param colonne
     * @return
     */
    public String getMoveCoordinate(int ligne, int colonne) {
        String MoveCoordinate = "";
        switch (colonne){
            case 0: MoveCoordinate += "A";
                break;
            case 1: MoveCoordinate += "B";
                break;
            case 2: MoveCoordinate += "C";
                break;
            case 3: MoveCoordinate += "D";
                break;
            case 4: MoveCoordinate += "E";
                break;
            case 5: MoveCoordinate += "F";
                break;
            case 6: MoveCoordinate += "G";
                break;
            case 7: MoveCoordinate += "H";
                break;
        }
        switch (ligne){
            case 0: MoveCoordinate += "8";
                break;
            case 1: MoveCoordinate += "7";
                break;
            case 2: MoveCoordinate += "6";
                break;
            case 3: MoveCoordinate += "5";
                break;
            case 4: MoveCoordinate += "4";
                break;
            case 5: MoveCoordinate += "3";
                break;
            case 6: MoveCoordinate += "2";
                break;
            case 7: MoveCoordinate += "1";
                break;
        }
        return MoveCoordinate;
    }
    
    @Override
    public int compareTo(Move move){
        if(this.score == move.getScore()){
            return 0;
        }else if(this.score > move.getScore()){
            return 1;
        }else{
            return -1;
        }
    }

}
