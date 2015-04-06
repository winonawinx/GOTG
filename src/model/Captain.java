package model;

import java.awt.Point;

public class Captain extends Piece {

	public Captain(Point pt, Player master) {
		super("C:\\Users\\Weh\\workspace\\GOTG\\src\\cptn.png",
				"Captain", Piece.CAPTAIN , pt, master);
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
			status = false;
			break;
		case Piece.FIRST_LIEUTENANT:
		case Piece.PRIVATE:
		case Piece.FLAG:
			status = true;
		}
		return status;
	}
}
