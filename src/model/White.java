	package model;

import java.awt.Point;

public class White extends Player {
	public White(){
		super();
		populate();
	}
	private void populate(){
		addPiece(new General(new Point(4,3), this));
		addPiece(new LtGeneral(new Point(5,1), this));
		addPiece(new Colonel(new Point(4,2), this));
		addPiece(new Captain(new Point(5,5), this));
		addPiece(new FirstLt(new Point(4,0), this));
		addPiece(new Private(new Point(4,6), this));
		addPiece(new Private(new Point(4,4), this));
		addPiece(new Private(new Point(4,1), this));
		addPiece(new Spy(new Point(4,5), this));
		addPiece(new Flag(new Point(5,3), this));
	}
}
