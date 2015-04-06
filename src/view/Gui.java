package view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;

import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Font;

import javax.swing.border.EmptyBorder;

import model.Black;
import model.Piece;
import model.Player;
import model.White;

import javax.swing.UIManager;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;

import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.JScrollPane;

import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

import net.miginfocom.swing.MigLayout;

import javax.swing.SpringLayout;

import control.Control;

public class Gui extends JFrame implements ActionListener {
	private JPanel panTop, panBottom;
	private JLabel logo;
	private JLabel lblAuthors;
	private JPanel panCenter;
	private JTextArea logs;
	private JPanel panSide;
	private JScrollPane scrollPane;
	private JLabel lblCaption;
	private JPanel initLog;

	private Player players[];
	private int turn;
	private boolean inGame;
	private ArrayList<Point> valid;
	private ArrayList<Point> warn;
	private Piece onPiece;
	private JPanel panBoard, panHide;
	private GridLayout gl;
	private HashMap<Point, Cell> map;

	private Control control;

	public Gui(Control control, Player[] players) {
		this.control = control;
		this.setVisible(true);
		// this.setResizable(false);
		this.setTitle("Game of the Generals");
		this.setSize(new Dimension(1300, 1000));

		this.players = players;

		inGame = false;
		valid = new ArrayList<Point>();
		warn = new ArrayList<Point>();
		map = new HashMap<Point, Cell>();

		panTop = new JPanel();
		panTop.setBackground(new Color(47, 79, 79));
		getContentPane().add(panTop, BorderLayout.NORTH);

		logo = new JLabel("");
		panTop.add(logo);
		logo.setIcon(new ImageIcon(
				"C:\\Users\\Weh\\workspace\\GotGprob\\src\\GotG.png"));

		panBottom = new JPanel();
		panBottom.setBackground(new Color(47, 79, 79));
		getContentPane().add(panBottom, BorderLayout.SOUTH);

		lblAuthors = new JLabel("ERIVE - FERNANDEZ - POBLETE - QUINDOZA - TAN");
		lblAuthors.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAuthors.setForeground(new Color(192, 192, 192));
		panBottom.add(lblAuthors);

		panCenter = new JPanel();
		panCenter.setBorder(new EmptyBorder(0, 0, 0, 0));

		getContentPane().add(panCenter, BorderLayout.CENTER);
		SpringLayout sl_panCenter = new SpringLayout();
		panCenter.setLayout(sl_panCenter);
		
		panHide = new JPanel();
		sl_panCenter.putConstraint(SpringLayout.NORTH, panHide, 20,
				SpringLayout.NORTH, panCenter);
		sl_panCenter.putConstraint(SpringLayout.WEST, panHide, 20,
				SpringLayout.WEST, panCenter);
		sl_panCenter.putConstraint(SpringLayout.SOUTH, panHide, -20,
				SpringLayout.SOUTH, panCenter);
		sl_panCenter.putConstraint(SpringLayout.EAST, panHide, -20,
				SpringLayout.EAST, panCenter);
		panCenter.add(panHide);
		panHide.setBackground(Color.gray);
		panHide.setVisible(false);

		panBoard = new JPanel();
		sl_panCenter.putConstraint(SpringLayout.NORTH, panBoard, 20,
				SpringLayout.NORTH, panCenter);
		sl_panCenter.putConstraint(SpringLayout.WEST, panBoard, 20,
				SpringLayout.WEST, panCenter);
		sl_panCenter.putConstraint(SpringLayout.SOUTH, panBoard, -20,
				SpringLayout.SOUTH, panCenter);
		sl_panCenter.putConstraint(SpringLayout.EAST, panBoard, -20,
				SpringLayout.EAST, panCenter);
		panCenter.add(panBoard);
		gl = new GridLayout(7, 6, 0, 0);
		panBoard.setLayout(gl);

		panSide = new JPanel();
		panSide.setBackground(new Color(95, 158, 160));
		panSide.setPreferredSize(new Dimension(375, 10));
		getContentPane().add(panSide, BorderLayout.WEST);

		scrollPane = new JScrollPane();
		scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		scrollPane.setBackground(new Color(95, 158, 160));
		scrollPane.getViewport().setBackground(null);

		initLog = new JPanel();
		initLog.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		initLog.setBackground(new Color(95, 158, 160));
		scrollPane.setViewportView(initLog);

		logs = new JTextArea();
		logs.setFont(new Font("Monospaced", Font.PLAIN, 22));
		logs.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		logs.setBackground(new Color(95, 158, 160));
		logs.setForeground(Color.white);
		logs.setLineWrap(true);
		logs.setWrapStyleWord(true);
		logs.setEditable(false);
		// scrollPane.setViewportView(logs);
		panSide.setLayout(new BorderLayout(0, 0));
		panSide.add(scrollPane);

		lblCaption = new JLabel("Initial Phase");
		lblCaption.setForeground(new Color(255, 255, 255));
		lblCaption.setBorder(new EmptyBorder(15, 10, 0, 0));
		lblCaption.setFont(new Font("Tahoma", Font.PLAIN, 21));
		panSide.add(lblCaption, BorderLayout.NORTH);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.repaint();
		this.revalidate();
	}

	public void initializeBoard() {
		for (int i = 0; i < gl.getRows(); i++) {
			for (int j = 0; j < gl.getColumns(); j++) {
				Cell c = new Cell(new Point(j, i), control);
				panBoard.add(c);
				map.put(new Point(j, i), c);
			}
		}
	}

	public void refreshBoard() {
		for (int i = 0; i < gl.getRows(); i++) {
			for (int j = 0; j < gl.getColumns(); j++) {
				map.get(new Point(j, i)).setPiece(null);
			}
		}
		for (int i = 0; i < players.length; i++) {
			Iterator it = players[i].getPieces();
			while (it.hasNext()) {
				Piece temp = (Piece) it.next();
				if (temp.isAlive()) {
					// System.out.println("there: "
					// + new Point(temp.getLocation()));
					map.get(new Point(temp.getLocation())).setPiece(temp);
					// System.out.println(temp.getLocation());
				}
			}
		}

	}

	public boolean isValid(Point pt) {
		// System.out.println(valid);
		// System.out.println(pt);
		if (valid.contains(pt))
			return true;
		return false;
	}

	public boolean isWarn(Point pt) {
		if (warn.contains(pt))
			return true;
		return false;
	}

	public void clearRadar() {
		for (int i = 0; i < valid.size(); i++) {
			map.get(valid.get(i)).setBackground(Color.white);
		}
		for (int i = 0; i < warn.size(); i++) {
			map.get(warn.get(i)).setBackground(Color.white);
		}
		valid.removeAll(valid);
		warn.removeAll(warn);
	}

	public void getRadar(Cell selectedCell, boolean started, Player turn) {
		// TODO Auto-generated method stub
		Point pt = selectedCell.getPoint();
		// System.out.println(pt);
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			Cell cell = (Cell) pair.getValue();
			Point p = (Point) pair.getKey();

			if (started) {
				if (cell.getPiece() != null) {
					if (!cell.getPiece().getMaster().isTurn()) {
						if ((p.getX() - 1 == pt.getX() || p.getX() + 1 == pt
								.getX()) && p.getY() == pt.getY()) {
							cell.setBackground(Color.orange);
							warn.add(p);
						}
						if (p.getX() == pt.getX()
								&& (p.getY() - 1 == pt.getY() || p.getY() + 1 == pt
										.getY())) {
							cell.setBackground(Color.orange);
							warn.add(p);
						}

					}

				} else {
					if ((p.getX() - 1 == pt.getX() || p.getX() + 1 == pt.getX())
							&& p.getY() == pt.getY()) {
						cell.setBackground(Color.green);
						valid.add(p);
					}
					if (p.getX() == pt.getX()
							&& (p.getY() - 1 == pt.getY() || p.getY() + 1 == pt
									.getY())) {
						cell.setBackground(Color.green);
						valid.add(p);
					}
				}
			} else {
				if (cell.getPiece() == null) {
					if (turn == players[0]) {
						if (p.getX() < 2) {
							cell.setBackground(Color.green);
							valid.add(p);
						}
					} else {
						if (p.getX() > 3) {
							cell.setBackground(Color.green);
							valid.add(p);
						}
					}
				}
			}
		}
		// System.out.println("radar: " + valid);
	}

	public boolean checkAdj(Cell cell) {
		boolean stat = false;
		System.out.println("here");

		if (cell.getPiece().getMaster() == players[0]) {
			Iterator it = players[1].getPieces();
			while (it.hasNext()) {
				Piece p = (Piece) it.next();
				if (p.getLocation().x == 5
						&& (p.getLocation().y - 1 == cell.getPoint().y || p
								.getLocation().y + 1 == cell.getPoint().y)) {
					stat=true;
				}
			}
		} else {
			Iterator it = players[0].getPieces();
			while (it.hasNext()) {
				Piece p = (Piece) it.next();
				if (p.getLocation().x == 0
						&& (p.getLocation().y - 1 == cell.getPoint().y || p
								.getLocation().y + 1 == cell.getPoint().y)) {
					stat=true;
				}
			}
		}
		return stat;
	}

	public void setWinner(Player p) {
		setCaption("Game Finish");
		clearLog();
		
		players[0].setTurn(true);
		players[1].setTurn(true);
		
		refreshBoard();
		
		this.repaint();
		this.revalidate();

		if (p == players[0])
			JOptionPane.showMessageDialog(this, "Player 1 wins!");
		else
			JOptionPane.showMessageDialog(this, "Player 2 wins!");

		System.exit(0);

	}

	public void prepare() {
		initLog.setLayout(new MigLayout("", "[60px]", "[26px]"));
		JLabel p = new JLabel("Player 1");
		p.setForeground(Color.white);
		p.setFont(new Font("Monospaced", Font.PLAIN, 22));
		initLog.add(p, "span");

		p = new JLabel("   Prepare your board");
		p.setForeground(Color.white);
		p.setFont(new Font("Monospaced", Font.PLAIN, 22));
		initLog.add(p, "");
		initLog.add(p, "newline");

		JButton btn = new JButton("Ready");
		btn.setFont(new Font("Monospaced", Font.BOLD, 22));
		btn.setActionCommand("P1");
		btn.setForeground(new Color(95, 158, 160));
		btn.setBackground(Color.white);
		btn.addActionListener(this);
		initLog.add(btn, "newline,  grow");

		this.repaint();
		this.revalidate();
	}
	
	public void setHide(boolean stat){
		panBoard.setVisible(!stat);
		panHide.setVisible(stat);
	}

	public void switchBoard() {
		for (int i = 0; i < players.length; i++) {
			players[i].switchBoard();
		}
	}

	public void setCaption(String text) {
		lblCaption.setText(text);
	}

	public void clearLog() {
		logs.setText("");
	}

	public void addLog(String text) {
		logs.append("" + text);
		logs.repaint();
		logs.revalidate();
	}

	public void switchTurn() {
		System.out.println("switch");
		
		players[0].setTurn(!players[0].isTurn());
		players[1].setTurn(!players[1].isTurn());
		switchBoard();

		int temp;
		if (players[0].isTurn())
			temp = 1;
		else
			temp = 2;
		turn++;
		setCaption("Player " + temp + " (Turn " + (turn / 2) + ")");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn = ((JButton) e.getSource());

		if (e.getActionCommand().equals("P1")) {
			btn.setEnabled(false);

			JLabel p = new JLabel("Player 2");
			p.setForeground(Color.white);
			p.setFont(new Font("Monospaced", Font.PLAIN, 22));
			initLog.add(new JLabel(), "newline");
			initLog.add(p, "newline, span");

			p = new JLabel("   Prepare your board");
			p.setForeground(Color.white);
			p.setFont(new Font("Monospaced", Font.PLAIN, 22));
			initLog.add(p, "");
			initLog.add(p, "newline");

			JButton temp = new JButton("Ready");
			temp.setFont(new Font("Monospaced", Font.BOLD, 22));
			temp.setActionCommand("P2");
			temp.setForeground(new Color(95, 158, 160));
			temp.setBackground(Color.white);
			temp.addActionListener(this);
			initLog.add(temp, "newline,  grow");

			players[0].setTurn(false);
			players[1].setTurn(true);

			control.finishFirstPreparation();
			initLog.repaint();
			initLog.revalidate();
		} else {
			// switchTurn();

			control.finishSecondPreparation();
			scrollPane.setViewportView(logs);
		}
	}

}
