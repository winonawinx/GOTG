package view;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class TransparentPanel extends JPanel {
	{
		setOpaque(false);
	}

	public void paintComponent(Graphics g) {
		g.setColor(getBackground());
		Rectangle r = g.getClipBounds();
		g.fillRect(r.x, r.y, r.width, r.height);
		super.paintComponent(g);
	}
}