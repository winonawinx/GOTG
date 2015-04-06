package model;

import java.awt.Point;

public class Flag extends Piece {

	public Flag(Point pt, Player master) {
		super("C:\\Users\\Weh\\workspace\\GOTG\\src\\flag.png",
				"Flag", Piece.FLAG, pt, master);
	}

	@Override
	public boolean challenge(Piece enemy) {
		if(enemy instanceof Flag) return true;
		return false;
	}
}
