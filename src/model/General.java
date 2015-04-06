package model;

import java.awt.Point;

public class General extends Piece {

	public General(Point pt, Player master) {
		super("C:\\Users\\Weh\\workspace\\GOTG\\src\\gnrl.png",
				"General of the Army", Piece.GENERAL_OF_THE_ARMY, pt, master);
	}

	@Override
	public boolean challenge(Piece enemy) {
		boolean status = true;
		switch (enemy.getType()) {
		case Piece.SPY:
		case Piece.GENERAL_OF_THE_ARMY:
			status = false;
			break;
		case Piece.LIEUTENTANT_GENERAL:
		case Piece.COLONEL:
		case Piece.CAPTAIN:
		case Piece.FIRST_LIEUTENANT:
		case Piece.PRIVATE:
		case Piece.FLAG:
			status = true;
		}
		return status;
	}
}
