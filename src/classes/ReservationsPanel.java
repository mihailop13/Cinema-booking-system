package classes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ReservationsPanel extends JPanel {											//sluzi za biranje sedista

    private int numOfSeats;
    private JButton reserve;
    private Person person;
	private Projection projection;
    
    public ReservationsPanel(Projection projection, Person person) {
        reserve = new JButton("Reserve");
        reserve.setFont(new Font("Arial", Font.BOLD, 14));
        reserve.setBackground(new Color(0, 153, 51));
        reserve.setForeground(Color.WHITE);
        reserve.setBorder(new EmptyBorder(10, 20, 10, 20));
        reserve.setFocusPainted(false);
        reserve.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
        
        setLayout(new GridLayout(5, 1)); // Dodato još jedan red za informacije o sedištima
        this.projection = projection;
        this.person = person;
        
        numOfSeats = DataBase_manager.getNumOFSeats(projection.getHallID());
        
        populateWindow();
        addListener();
    }

    private void addListener() {
        reserve.addActionListener(e -> {
            ArrayList<Integer> numOfSeat = new ArrayList<>();
            for (int i = 0; i < numOfSeats; i++) {
                if (seats[i].getColor() == Color.RED)
                    numOfSeat.add(i);
            }
            if(numOfSeat.size() == 0) {
            	JOptionPane.showMessageDialog(null, "Select your seats first.", "Error!", JOptionPane.ERROR_MESSAGE);
            	return;
            }
            Reservation res = new Reservation(projection, person, numOfSeat);
            DataBase_manager.writeReservation(res);
            new ReservationConfirmation_Frame(res);
            for (int i = 0; i < numOfSeats; i++) {
                if (seats[i].getColor() == Color.RED)
                	seats[i].SetColor(Color.BLUE);
            }
            repaint();
        });
    }

    Seat[] seats;
    
    private void populateWindow() {
        this.setBackground(new Color(30, 30, 30));  // Tamna pozadina panela

        JPanel screen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                int rectWidth = getWidth() / 2;
                int rectHeight = 20;
                int rectX = (getWidth() - rectWidth) / 2;
                int rectY = 5;
                g.fillRect(rectX, rectY, rectWidth, rectHeight);

                g.setFont(new Font("Arial", Font.BOLD, 16));
                FontMetrics metrics = g.getFontMetrics();
                int textX = rectX + (rectWidth - metrics.stringWidth("Screen")) / 2;
                int textY = rectY + ((rectHeight - metrics.getHeight()) / 2) + metrics.getAscent();
                g.setColor(Color.BLACK);
                g.drawString("Screen", textX, textY);
            }
        };
        screen.setPreferredSize(new Dimension(600, 50));
        screen.setBackground(new Color(30, 30, 30));
        this.add(screen);

        // Seats Panel
        JPanel seatsPanel = new JPanel(new GridLayout(numOfSeats / 10, 10));
        seatsPanel.setBackground(new Color(30, 30, 30));
        seatsPanel.setSize(new Dimension(this.getWidth(), numOfSeats / 10 * 80)); // Povećan panel za sedišta

        seats = new Seat[numOfSeats];
        ArrayList<Integer> reserved = DataBase_manager.readReservedSeats(projection.getProjectionID());

        for (int i = 0; i < numOfSeats; i++) {
            seats[i] = new Seat(i, reserved.indexOf(i) != -1);
            seatsPanel.add(seats[i]);
        }
        this.add(seatsPanel);

        // Info Panel (Status of Seats)
        JPanel info = new JPanel() {
        	 @Override
    	    public void paint(Graphics g) {
    	        super.paint(g);
    	        g.setColor(Color.GREEN);
    	        g.fillOval(20, 5, 20, 20);
    	        g.setColor(Color.RED);
    	        g.fillOval(220, 5, 20, 20);
    	        g.setColor(Color.BLUE);
    	        g.fillOval(420, 5, 20, 20);
    	        
    	        // Dodati tekst pored boja, sada sa povećanim razmakom
    	        g.setColor(Color.WHITE);
    	        g.drawString("Free", 45, 20);     		// Razmaknuto
    	        g.drawString("Selected", 245, 20);      // Razmaknuto
    	        g.drawString("Reserved", 445, 20); 		// Razmaknuto
    	    }
        };
        info.setBackground(new Color(30, 30, 30));
        info.setSize(new Dimension(this.getWidth(), 40)); // Povećan panel za informacije
        this.add(info);

        // Reserve Button
        this.add(reserve);
    }
}