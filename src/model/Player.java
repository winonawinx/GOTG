package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class Player {
	private ArrayList<Piece> pieces;
	private boolean turn;
	
	public Player(){
		pieces=new ArrayList<Piece>();
	}
	
	public void addPiece(Piece p){
		pieces.add(p);
	}
	
	public Iterator getPieces(){
		return pieces.iterator();
	}
	
	public boolean isTurn(){
		return turn;
	}
	
	public void setTurn(boolean status){
		turn=status;
		for (int i = 0; i < pieces.size(); i++) {
			pieces.get(i).setPlay(status);
		}
	}
	
	public Piece getPieceAt(Point p){
		for (Piece piece : pieces) {
			if(p.x==piece.getLocation().x && p.y==piece.getLocation().y)
				return piece;
		}
		return null;
	}

	public void switchBoard() {
		for (Piece piece : pieces) {
			int x=piece.getLocation().x;
			int y=piece.getLocation().y;
			piece.setLocation(new Point(Math.abs(6-x),Math.abs(5-y)));
		}
	}
	
	
}
