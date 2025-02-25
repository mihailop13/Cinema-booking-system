package classes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class Seat extends JPanel {

	boolean reserved;
	
	int number;
	
	Color color;
	
	public Seat(int num, boolean reserved) {
		
		this.number = num;
		
		this.reserved = reserved;
		
		this.setSize(new Dimension(80,80));
		
		if(!reserved)
			color = Color.GREEN;
		else
			color = Color.BLUE;
		
		setBackground(new Color(30, 30, 30));
		
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if(color == Color.BLUE)
					return;
				else if(color == Color.RED)
					color = Color.GREEN;
				else if(color == Color.GREEN) 
					color = Color.RED;
				repaint();
			}
		});
	}
	
	public Color getColor() {
		return color;
	}

	public int getNumber() {
		return number;
	}

	public void SetColor(Color color) {
		this.color = color;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, this.getWidth(), this.getHeight());
		
		g.setColor(color);
		g.fillOval(0, 0, this.getWidth(), this.getHeight());
		
	}
	
}
