package model;

import java.awt.Point;

public class FirstLt extends Piece {

	public FirstLt(Point pt, Player master) {
		super("C:\\Users\\Weh\\workspace\\GOTG\\src\\1stLt.png",
				"First Lieutenant", Piece.FIRST_LIEUTENANT, pt, master);
	}

	@Override
	public boolean challenge(Piece enemy) {
		boolean status = true;
		switch (enemy.getType()) {
		case Piece.SPY:
		case Piece.GENERAL_OF_THE_ARMY:
		case Piece.LIEUTENTANT_GENERAL:
		case Piece.COLONEL:
		case Piece.CAPTAIN:
		case Piece.FIRST_LIEUTENANT:
			status = false;
			break;
		case Piece.PRIVATE:
		case Piece.FLAG:
			status = true;
		}
		return status;
	}
}
