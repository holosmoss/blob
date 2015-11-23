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
    private int pieceID; //référence sur la pièce associé au move
    
    private BoardDecoder decoder;


    
	public Move(int fromRow, int fromColumn, int toRow, int toColumn, int ID){
		
		this.fromRow = fromRow;
		this.fromColumn = fromColumn;
		this.toRow = toRow;
		this.toColumn = toColumn;
		this.pieceID = ID;
		
		this.decoder = new BoardDecoder();
		
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
    
    public int getPieceID() {
		return pieceID;
	}
    
    
    /**
     * permet de changer les coordonnés en int d'un move 
     * en coordonné accepté par le board
     * Exemple: 00 = A8 -> ligne 0 Colone 0 du board int[][]
     * 
     * @param row
     * @param col
     * @return string - ex: A8 plutot que 00
     */
    public String getMoveCoordinate(int row, int col) {
        
        return this.decoder.decodeToString(row, col);
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
