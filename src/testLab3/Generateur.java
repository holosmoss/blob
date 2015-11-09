package testLab3;

import java.util.ArrayList;
import java.util.List;

public class Generateur {
	
	//pour vérifier si le dernier coup était valide
	private int[][] boardAvant = new int[8][8];	
	private int[][] boardApres = new int[8][8];
	private int nosPou;
	private int nosPeu;
	private int leurPou;
	private int leurPeu;
	//TODO do we get a reference of this via the ArbeMinimax ? or is client singleton
	protected ArrayList<int[]> us = new ArrayList<int[]>();
	protected ArrayList<int[]> them = new ArrayList<int[]>();
	/**
	 * Le generateur est appelé par l'arbre pour connaitre les enfants de  la node
	 * Il evalue tout les move possible selon la position et le type de la piece ainsi que les piece voisine
	 */
	public Generateur(){
		if(Client.color == 1){
			us = Client.whites;
			them = Client.blacks;
			leurPou = 3;
			leurPeu = 4;
			nosPou = 1;
			nosPeu = 2;
		}else{
			us = Client.blacks;
			them = Client.whites;
			leurPou = 1;
			leurPeu = 2;
			nosPou = 3;
			nosPeu = 4;
		}	
	}
	protected List<Node>  procreate(Node node){
		List<Node> childList;
		//for our node ( parent boardState with our move added) we look all possibles moves for each pawns
		//TODO find a way to get a proper updated board state (with eating etc)
		if(node.isMax){
			childList = ourMoves();
		}else{
			childList = theirMoves();
		}
		
		//TODO we need to check every peon positions
		//^? really all of them, how could we diminish the amount needed ?
			//??is the board state actually juste the list of peons and their position
			//! we record the move only, and if this node is the one being used for children we imprint the next board state
			//Childrens use their parent board state to evaluate the moves
			//??? how to minimize the amount of childrens repetitions ? checksum du boardstate in a hashmap ?
				//TODO for every peon (in order of importance =  our strategy)
			//TODO find evry possible moves of its 3
			//return them as nodes
		return childList;
	}
	private List<Node> ourMoves(){
		List<Node> moves = new ArrayList<Node> ();
		//loop all of us and find moves for nosPou et nosPeu
		for(int[] coord : us){
			if(coord[2] == nosPeu){
				//pousseur amis
				//add the nodes in order of importance: diag first since they might be eating
				//TODO fill in the details of each nodes
				if(canPeuDiag(coord[0], coord[1], Client.color, 1)){
					//TODO check for eatings
					Node diagPlus = new Node();
					moves.add(diagPlus);
				}
				if(canPeuDiag(coord[0], coord[1], Client.color, -1)){
					//TODO check for eatings
					Node diagMinus = new Node();
					moves.add(diagMinus);
				}
				if(!isBlocked(coord[0], coord[1], Client.color)){
					//we are not blocked forward, add the node
					Node forward = new Node();
					moves.add(forward);
				}
			}else{
				//poussable amis
				//add the nodes in order of importance: diag first since they might be eating
				if(canPou(coord[0], coord[1], Client.color, 1)){
					//TODO check for eatings
					Node diagPlus = new Node();
					moves.add(diagPlus);
				}
				if(canPou(coord[0], coord[1], Client.color, -1)){
					//TODO check for eatings
					Node diagMinus = new Node();
					moves.add(diagMinus);
				}
				if(canPou(coord[0], coord[1], Client.color, 0)){
					//forward is a possible move
					Node forward = new Node();
					moves.add(forward);
				}
				//TODO use the boardState to check other pieces
				//if pousseur in places check for moves acordin to the places (-1,0,+1)
			}
		}
		return moves;
	}
	private List<Node> theirMoves(){
		List<Node> moves = new ArrayList<Node> ();
		//loop all of them and find moves for leurPou et leurPeu
		for(int[] coord : them){
			if(coord[2] == leurPeu){
				//pousseur enemis
			}else{
				//poussable enemis
			}
		}
		return moves;
	}
	/**
	 * Function to check for any pawns straight ahead (works for both pou and peu)
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean isBlocked(int x, int y,int color){
		//change the row modifier based on color
		int ymod = (color == 1) ? 1 : -1;
		if(Client.board[x][y+ymod] != 0){
			//si il y a un piont en avant de nous
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @param color
	 * @param xmod -1,1 = the location of the diag we want to go to
	 * @return
	 */
	private boolean canPeuDiag(int x, int y, int color, int xmod){
		if(color == 1){
			if(Client.board[x-xmod][y-1] == 1 || Client.board[x-xmod][y-1] == 2 ){
				//no pawns of our color at target location
				return false;
			}else{
				return true;
			}
		}else{
			if(Client.board[x-xmod][y+1] == 3 || Client.board[x-xmod][y+1] == 4 ){
				//no pawns of our color at target location
				return false;
			}else{
				return true;
			}
		}
			
		
	}
	/**
	 * Function to detect the pou as a peu in the proper position to move hime to the desired xmod 
	 * on the next row based on colors. Also confirms its not blocked by its own color
	 * 
	 * @param x
	 * @param y
	 * @param color
	 * @param xmod : 0 = straight ahead, -1 and 1 = diagonals (the value is for the location we want to go)
	 * @return
	 */
	private boolean canPou(int x, int y, int color,int xmod){
		//is the pou able to go to x+xmod
		if(color == 1){
			//white pou
			if(Client.board[x-xmod][y-1] == 1 || Client.board[x-xmod][y-1] == 2 ){
				//is blocked by one of its color
				return false;
			}else if(Client.board[x+xmod][y-1] == 2){
				//as a peu to push to xModified +1y
				return true;
			}else{
				return false;
			}
		}else{
			//Black pou
			if(Client.board[x-xmod][y+1] == 3 || Client.board[x-xmod][y+1] == 4 ){
				//is blocked by one of its color
				return false;
			}else if(Client.board[x+xmod][y+1] == 4){
				//as a peu to push to xModified -1y
				return true;
			}else{
				return false;
			}
		}
	}
	
	/**
	 * Node utilis/ par l<arbre min max
	 * @author Holos
	 *
	 */ 
	protected class Node{
		protected boolean isMax;
		private Node(){
			//TODO doit connaitre sont statut min ou max
			//TODO doit connaitre le move qu'elle représente (changement) au board state
			//TODO is leaf:
				//TODO info pour leval 
				//manger un pousseur 
				//bouger un pousseur a une place safe
				//
		}
	}

}
