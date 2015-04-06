package model;

import java.awt.Point;

public class Private extends Piece {

	public Private(Point pt, Player master) {
		super("C:\\Users\\Weh\\workspace\\GOTG\\src\\private.png",
				"Private", Piece.PRIVATE, pt, master);
	}

	@Override
	public boolean challenge(Piece enemy) {
		boolean status = true;
		switch (enemy.getType()) {
		case Piece.GENERAL_OF_THE_ARMY:
		case Piece.LIEUTENTANT_GENERAL:
		case Piece.COLONEL:
		case Piece.CAPTAIN:
		case Piece.FIRST_LIEUTENANT:
		case Piece.PRIVATE:
			status = false;
			break;
		case Piece.FLAG:
		case Piece.SPY:
			status = true;
		}
		return status;
	}
}
