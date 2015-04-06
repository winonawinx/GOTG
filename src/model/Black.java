package model;

import java.awt.Point;

public class Black extends Player{
	public Black(){
		super();
		populate();
	}
	private void populate(){
		addPiece(new General(new Point(1,3), this));
		addPiece(new LtGeneral(new Point(0,5), this));
		addPiece(new Colonel(new Point(1,4), this));
		addPiece(new Captain(new Point(0,1), this));
		addPiece(new FirstLt(new Point(1,6), this));
		addPiece(new Private(new Point(1,0), this));
		addPiece(new Private(new Point(1,2), this));
		addPiece(new Private(new Point(1,5), this));
		addPiece(new Spy(new Point(1,1), this));
		addPiece(new Flag(new Point(0,3), this));
	}
	
	public void setStats()
	{
		
		
	}
	
}
