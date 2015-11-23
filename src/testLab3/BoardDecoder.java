package testLab3;

public class BoardDecoder {
	
	/**
	 * Décode les coordonnés d'un move pour les mettres sous forme de String
	 * avant de l'envoyer au server pour jouer un coup.
	 * @param row
	 * @param col
	 * @return string - ex: A3 au lieu de 05
	 */
	public String decodeToString(int row, int col){
		String MoveCoordinate = "";
        switch (col){
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
            default:
            	break;
        }
        switch (row){
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
            default:
            	break;
        }		
		return MoveCoordinate;
	}
	/**
	 * decode une colonne du board reçu par le serveur pour correspondre
	 * à la bonne valeur d'un tableau de int[col][row]
	 * @param col
	 * @return int
	 */
	public int decodeColToInt(char col){
		int coord = 0;
		
		switch (col){
        case 'A': coord = 0;
            break;
        case 'B': coord = 1;
            break;
        case 'C': coord = 2;
            break;
        case 'D': coord = 3;
            break;
        case 'E': coord = 4;
            break;
        case 'F': coord = 5;
            break;
        case 'G': coord = 6;
            break;
        case 'H': coord = 7;
            break;
        default:
        	break;
		}

		
		return coord;    
	}
	
	/**
	 * decode une ligne du board reçu par le serveur pour correspondre
	 * à la bonne valeur d'un tableau de int[col][row]
	 * @param row
	 * @return int
	 */
	public int decodeRowToInt(int row ){
		int coord = 0;
		
		switch (row){
        case 8: coord = 0;
            break;
        case 7: coord = 1;
            break;
        case 6: coord = 2;
            break;
        case 5: coord = 3;
            break;
        case 4: coord = 4;
            break;
        case 3: coord = 5;
            break;
        case 2: coord = 6;
            break;
        case 1: coord = 7;
            break;
        default:
        	break;
    }
		
		return coord;    
	}
	
	/**
	 * Décode le dernier coup jouer par l'ennemi qui nous est envoyé
	 * sous forme de string. On récupère les valeurs correspondante
	 * dans un tableau de int[4]
	 * @param s
	 * @return int[4] - {fromCol, fromRow, toCol, toRow}
	 */
	public int[] decodeEnemyLastMove(String s){
		int[] coord = new int[4];
		String strTmp = null;
		
		//remplace l'espace et le trait d'union dans A2 - A3 pour A2A3
		strTmp = s.replaceAll("[^a-zA-Z0-9\\s]", "");
		strTmp = s.replaceAll(" +", "");

		coord[0] = decodeColToInt( strTmp.charAt(0) );// fromCol
		coord[1] = decodeRowToInt( strTmp.charAt(1) );// fromRow
		coord[2] = decodeRowToInt( strTmp.charAt(2) );// toCol
		coord[3] = decodeRowToInt( strTmp.charAt(3) );// toRow
		
		return coord;
	}

}
