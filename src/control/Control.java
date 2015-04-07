package control;

import java.awt.Color;

import javax.swing.JOptionPane;

import model.Black;
import model.Flag;
import model.Piece;
import model.Player;
import model.White;
import view.Cell;
import view.Gui;

public class Control {
	private Gui gui;
	private Cell selectedCell;
	private boolean started;
	private int turn;
	private Player players[];
	private Flag track;

	// private boolean started
	public Control() {
		track=null;
		players = new Player[2];
		players[0] = new Black();
		players[1] = new White();
		
		players[0].setPlayerEnemy(players[1]);
		players[1].setPlayerEnemy(players[0]);
		players[0].setTurn(true);
		gui = new Gui(this, players);
		gui.initializeBoard();
		gui.refreshBoard();
		gui.prepare();

		turn = 0;
		started = false;
		selectedCell = null;
	}

	public void cellClick(Cell cell) {
		gui.clearLog();
		
		
		
		if (cell.getPiece() != null && !cell.getPiece().getMaster().isTurn()
				&& !gui.isWarn(cell.getPoint())) {
			gui.addLog(cell.getPiece().getStats());
		}

		if (selectedCell == null) {
			selectedCell = cell;
			cell.setSelected(true);
			if (selectedCell.getPiece() != null) {
				if (selectedCell.getPiece().getMaster().isTurn()) {
					gui.clearRadar();
					gui.getRadar(selectedCell, started, players[turn % 2]);
				}
			} else
				gui.clearRadar();
		} else {
			if (selectedCell == cell) {
				selectedCell.setSelected(false);
				selectedCell = null;
				gui.clearRadar();
			} else {
				if (selectedCell.getPiece() != null) {
					if (selectedCell.getPiece().getMaster().isTurn()) {
						if (gui.isValid(cell.getPoint())) {
							selectedCell.getPiece()
									.setLocation(cell.getPoint());
							selectedCell.setSelected(false);
							selectedCell = null;
							gui.clearRadar();
							gui.refreshBoard();

							if (started && cell.getPiece() instanceof Flag) {
								System.out.println("A");
								if (players[0].isTurn()) {
									if (cell.getPoint().x == 5) {
										track=(Flag) cell.getPiece();
									}
								} else {
									if (cell.getPoint().x == 0) {
										track=(Flag) cell.getPiece();
									}
								}
							}

							if (started) {
								switchTurn();
							}

						} else if (gui.isWarn(cell.getPoint())) {
							if (cell.getPiece().challenge(
									selectedCell.getPiece()) == selectedCell
									.getPiece().challenge(cell.getPiece())
									&& !(cell.getPiece() instanceof Flag)) {
								if(cell.getPiece()==null) System.out.println("a");
								if(selectedCell.getPiece()==null) System.out.println("b");
								cell.getPiece()
										.addKill(selectedCell.getPiece());
								selectedCell.getPiece()
										.addKill(cell.getPiece());
								cell.getPiece().setDead(selectedCell.getPiece());
								selectedCell.getPiece().setDead(cell.getPiece());
							} else if (selectedCell.getPiece().challenge(
									cell.getPiece())) {
								if(selectedCell.getPiece()==null) System.out.println("c");
								selectedCell.getPiece()
										.addKill(cell.getPiece());
								
								cell.getPiece().setDead(selectedCell.getPiece());
								selectedCell.getPiece().setLocation(
										cell.getPoint());
								if (cell.getPiece() instanceof Flag) {
									gui.setWinner(selectedCell.getPiece()
											.getMaster());
								}
							} else {
								if(cell.getPiece()==null) System.out.println("d");
								cell.getPiece()
										.addKill(selectedCell.getPiece());
								selectedCell.getPiece().setDead(cell.getPiece());

								if (selectedCell.getPiece() instanceof Flag) {
									gui.setWinner(cell.getPiece().getMaster());
								}
							}
							selectedCell.setSelected(false);
							selectedCell = null;
							gui.clearRadar();
							if (started) {
								switchTurn();
							}

						} else {
							selectedCell.setSelected(false);
							selectedCell = cell;
							cell.setSelected(true);
							gui.clearRadar();
							if (selectedCell.getPiece() != null
									&& selectedCell.getPiece().getMaster()
											.isTurn()) {
								gui.getRadar(cell, started, players[turn % 2]);
							}
						}
					} else {
						selectedCell.setSelected(false);
						selectedCell = cell;
						cell.setSelected(true);
						if (selectedCell.getPiece() != null
								&& selectedCell.getPiece().getMaster().isTurn()) {
							gui.clearRadar();
							gui.getRadar(cell, started, players[turn % 2]);
						}
					}

				} else {
					selectedCell.setSelected(false);
					selectedCell = cell;
					selectedCell.setSelected(true);
					if (selectedCell.getPiece() != null) {
						gui.clearRadar();
						if (selectedCell.getPiece().getMaster().isTurn()) {
							gui.getRadar(selectedCell, started,
									players[turn % 2]);
						}
					}
				}
				gui.refreshBoard();
			}

		}
	}

	public void switchTurn() {
		
		turn++;
		gui.setCaption("Player " + (turn % 2 + 1) + "(Turn " + (turn) + ")");
		if (turn % 2 == 0) {
			players[0].setTurn(true);
			players[1].setTurn(false);
		} else {
			players[0].setTurn(false);
			players[1].setTurn(true);
		}
		
		if(track!=null&&track.isAlive()&&track.getMaster().isTurn()){
			gui.setWinner(track.getMaster());
		}
		
		gui.setHide(true);
		JOptionPane.showConfirmDialog(gui, "Player " + ((turn) % 2 + 1)
				+ "(Turn " + (turn) + ")", "Player " + ((turn) % 2 + 1)
				+ "(Turn " + (turn) + ")", JOptionPane.PLAIN_MESSAGE);
		gui.setHide(false);

	}

	public void finishFirstPreparation() {
		gui.setHide(true);
		JOptionPane.showConfirmDialog(gui, "Player " + ((turn+1) % 2 + 1)
				+ "(Turn " + (turn+1) + ")", "Player " + ((turn+1) % 2 + 1)
				+ "(Turn " + (turn+1) + ")", JOptionPane.PLAIN_MESSAGE);
		gui.setHide(false);
		players[0].setTurn(false);
		players[1].setTurn(true);
		turn++;

		if (selectedCell != null)
			selectedCell.setSelected(false);
		selectedCell = null;
		gui.clearRadar();
		gui.refreshBoard();

	}

	public void finishSecondPreparation() {
		// TODO Auto-generated method stub
		gui.setHide(true);
		JOptionPane.showConfirmDialog(gui, "Player " + ((turn+1) % 2 + 1)
				+ "(Turn " + (turn+1) + ")", "Player " + ((turn+1) % 2 + 1)
				+ "(Turn " + (turn+1) + ")", JOptionPane.PLAIN_MESSAGE);
		gui.setHide(false);
		started = true;
		players[0].setTurn(true);
		players[1].setTurn(false);

		if (selectedCell != null)
			selectedCell.setSelected(false);
		selectedCell = null;
		gui.clearRadar();
		gui.refreshBoard();

		turn = 0;
		gui.setCaption("Player " + (turn % 2 + 1) + "(Turn " + (turn / 2) + ")");
	}

}
