package classes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;

import javax.swing.*;
import java.util.ArrayList;

public class ViewReservationsPanel extends JPanel {

    private JLabel yourRes = new JLabel("Your current reservations:");
    private ArrayList<ReservationInfoPanel> reservationsInfo = new ArrayList<>();
    private ArrayList<Reservation> reservations = new ArrayList<>();
    private Person person;
    
    private JButton back = new JButton("Back");

    public ViewReservationsPanel(Person person) {
        this.person = person;
        setLayout(new BorderLayout()); 
        setBackground(new Color(30, 30, 30));  // Dark background color

        reservations = DataBase_manager.readReservations(person.getId());

        for (Reservation r : reservations)
            reservationsInfo.add(new ReservationInfoPanel(r));

        setPreferredSize(new Dimension(600, 300)); // Ensure height is at least 300

        populateWindow();
    }

    private void populateWindow() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS)); // Ensuring vertical stacking
        content.setBackground(new Color(30, 30, 30)); // Dark background for the reservation panel
        content.setPreferredSize(new Dimension(600, reservationsInfo.size() * 150)); // Dynamic size based on number of reservations

        // Styling for the header label
        yourRes.setForeground(Color.WHITE); // White text color
        yourRes.setFont(new Font("Arial", Font.BOLD, 18)); // Bold, larger font size for header
        yourRes.setAlignmentX(CENTER_ALIGNMENT); // Centering the header
        content.add(yourRes);
        
        if (reservationsInfo.isEmpty()) {
            JLabel noReservations = new JLabel("No reservations found.");
            noReservations.setForeground(Color.LIGHT_GRAY); // Slightly lighter text for no data
            noReservations.setFont(new Font("Arial", Font.ITALIC, 14)); // Italic font for 'no data' message
            noReservations.setAlignmentX(CENTER_ALIGNMENT);
            content.add(noReservations);
        } else {
            // Adding reservation panels
            for (ReservationInfoPanel rp : reservationsInfo) {
                rp.setAlignmentX(CENTER_ALIGNMENT); // Center each reservation panel
                content.add(rp);
            }
        }

        //Creating a scrollable area
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Prevent horizontal scrolling
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smoother scrolling experience

        // Adding scrollPane to the main panel
        add(scrollPane);
        
        // Set up back button
        back.setAlignmentX(CENTER_ALIGNMENT); // Center the back button
        back.setBackground(new Color(100, 100, 255));
        back.setForeground(Color.WHITE);
        back.setFocusPainted(false);
        back.addActionListener(e -> {
            VisitorPanel visitorPanel = new VisitorPanel(person);
            JFrame frame = (JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
            if (frame != null) {
            	frame.setLocation(new Point(900, 400));
                frame.getContentPane().removeAll();
                frame.setContentPane(visitorPanel); // Postavljamo novi panel kao glavni sadržaj
                frame.pack(); // Automatski prilagođava veličinu na osnovu sadržaja
                frame.setTitle("Visitor Panel");
                frame.revalidate(); // Ponovno validiraj raspored
                frame.repaint(); // Osveži prikaz
            }
        });

        // Adding the back button at the bottom
        JPanel backPanel = new JPanel();
        backPanel.setBackground(new Color(30, 30, 30)); // Matching background color
        backPanel.add(back);
        add(backPanel, BorderLayout.SOUTH);
    }
}
