package model;

import java.awt.Point;

public class Spy extends Piece {

	public Spy(Point pt, Player master) {
		super("C:\\Users\\Weh\\workspace\\GOTG\\src\\spy.png",
				"Spy", Piece.SPY, pt, master);
	}

	@Override
	public boolean challenge(Piece enemy) {
		boolean status = true;
		switch (enemy.getType()) {
		case Piece.PRIVATE:
		case Piece.SPY:
			status = false;
			break;
		case Piece.GENERAL_OF_THE_ARMY:

		case Piece.LIEUTENTANT_GENERAL:
		case Piece.COLONEL:
		case Piece.CAPTAIN:
		case Piece.FIRST_LIEUTENANT:
		case Piece.FLAG:
			status = true;
		}
		return status;
	}
}
