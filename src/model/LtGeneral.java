package model;

import java.awt.Point;

public class LtGeneral extends Piece {

	public LtGeneral(Point pt, Player master) {
		super("C:\\Users\\Weh\\workspace\\GOTG\\src\\ltGnrl.png", "Lieutenant General", Piece.LIEUTENTANT_GENERAL, pt, master);
	}

	@Override
	public boolean challenge(Piece enemy) {
		boolean status = true;
		switch (enemy.getType()) {
		case Piece.SPY:
		case Piece.GENERAL_OF_THE_ARMY:
		case Piece.LIEUTENTANT_GENERAL:
			status = false;
			break;
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
