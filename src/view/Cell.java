package view;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import control.Control;
import model.Piece;

public class Cell extends JButton implements ActionListener{
	private Piece pc;
	private Point pt;
	private Control control;
	public Cell(Point pt, Control control){
		this.pt=pt;
		this.control=control;
		pc=null;
		this.addActionListener(this);
//		this.setContentAreaFilled(false);
//		this.setBorderPainted(false);
		this.setBackground(Color.white);
	}
	
	public void setPiece(Piece pc){
		this.pc=pc;
		if(pc!=null) this.setIcon(pc.getImg());
		else this.setIcon(null);
		this.repaint();
		this.revalidate();
	}
	
	public void setSelected(boolean stat){
		if(stat){
			if (pc != null) {
				if (pc.getMaster().isTurn())
					this.setBackground(Color.blue);
				else
					this.setBackground(Color.red);
			} else {
				this.setBackground(Color.LIGHT_GRAY);
			}
		}
		else{
			setBackground(Color.white);
		}
	}
	
	
	public Piece getPiece(){
		return pc;
	}
	
	public Point getPoint(){
		return pt;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("here: "+pt);
		control.cellClick(this);
		this.repaint();
		this.revalidate();
	}
	
	
}
