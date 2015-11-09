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
	
	
	private int fromRow;
	private int fromColumn;
	private int toRow;
	private int toColumn;
    private int score;

    public Move(){
    	this.fromRow = 0;
		this.fromColumn = 0;
		this.toRow = 0;
		this.toColumn = 0;
    }
    
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
