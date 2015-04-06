package model;

import java.awt.Point;

public class Colonel extends Piece {

	public Colonel(Point pt, Player master) {
		super("C:\\Users\\Weh\\workspace\\GOTG\\src\\clnl.png",
				"Colonel", Piece.COLONEL , pt, master);
	}

	@Override
	public boolean challenge(Piece enemy) {
		boolean status = true;
		switch (enemy.getType()) {
		case Piece.SPY:
		case Piece.GENERAL_OF_THE_ARMY:
		case Piece.LIEUTENTANT_GENERAL:
		case Piece.COLONEL:
			status = false;
			break;
		case Piece.CAPTAIN:
		case Piece.FIRST_LIEUTENANT:
		case Piece.PRIVATE:
		case Piece.FLAG:
			status = true;
		}
		return status;
	}
}
