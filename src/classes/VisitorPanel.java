package classes;

import java.awt.*;
import javax.swing.*;

public class VisitorPanel extends JPanel {

    private JButton reserve;
    private JButton reservations;
    private JLabel titleLabel;
    private JLabel helloLabel;
    private Person person;
    private JFrame frame;

    public VisitorPanel(Person person) {
        this.person = person;
        
        setSize(new Dimension(600, 300));
        
        // Postavljanje tamne teme
        Color backgroundColor = new Color(30, 30, 30);
        Color textColor = new Color(200, 200, 200);
        Color buttonColor = new Color(100, 100, 255);

        setBackground(backgroundColor);
        setLayout(new GridBagLayout()); // Centriranje elemenata

        // Naslov
        titleLabel = new JLabel("Select Your Option");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(textColor);

        // Pozdravna poruka
        helloLabel = new JLabel("Hello, " + person.getName() + "!");
        helloLabel.setFont(new Font("Arial", Font.BOLD, 20));
        helloLabel.setForeground(textColor);

        // Dugmad
        reserve = new JButton("Reserve");
        reservations = new JButton("Reservations");

        styleButton(reserve, buttonColor);
        styleButton(reservations, buttonColor);

        // Postavljanje elemenata u GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER; // Centriranje

        gbc.gridy = 0;
        add(titleLabel, gbc);

        gbc.gridy = 1;
        add(helloLabel, gbc);

        gbc.gridy = 2;
        add(reserve, gbc);

        gbc.gridy = 3;
        add(reservations, gbc);

        addListeners();
    }

    private void styleButton(JButton button, Color color) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(220, 50)); // Veća dugmad
    }

    private void addListeners() {
    	reserve.addActionListener(e -> {
			
			SelectMovie_Tab newTab = new SelectMovie_Tab(person, true);
			
			frame = (JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
			
			if (frame != null) {
			    frame.setContentPane(newTab); // Postavljamo novi panel kao glavni sadržaj
			    frame.setLocation(760, 350);
			    frame.pack(); // Automatski prilagođava veličinu na osnovu sadržaja
			    frame.setTitle("Choose a movie!");
			    frame.revalidate(); // Ponovno validiraj raspored
			    frame.repaint(); // Osveži prikaz
			}

		});
		
		reservations.addActionListener(e -> {
			
			ViewReservationsPanel newTab = new ViewReservationsPanel(person);
			
			frame = (JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
			
			if (frame != null) {
			    frame.setContentPane(newTab); // Postavljamo novi panel kao glavni sadržaj
			    frame.setLocation(700, 320);
			    frame.setTitle("Your reservations!");
			    frame.pack();
			    frame.revalidate(); // Ponovno validiraj raspored
			    
			    frame.repaint(); // Osveži prikaz
			}
		});
    }
}
