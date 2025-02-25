package classes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ReservationInfoPanel extends JPanel {

    private JLabel Movie;
    private JLabel dateTime;
    private JLabel seatsLabel;

    public ReservationInfoPanel(Reservation res) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(500, 200));  
        setBackground(new Color(40, 40, 40)); // Tamnija siva pozadina za panele pojedinačnih rezervacija
        setBorder(BorderFactory.createLineBorder(new Color(80, 80, 80), 1)); // Blagi okvir

        Movie = new JLabel("Movie: " + res.getProjection().getMovie().getMovieName());
        Movie.setForeground(Color.WHITE);

        dateTime  = new JLabel("Date and time: " + res.getProjection().getDate() + ", " + res.getProjection().getTime());
        dateTime.setForeground(Color.WHITE);
        seatsLabel = new JLabel("Your seat(s) : " + formatSeats(res));
        seatsLabel.setForeground(Color.WHITE);

        populatePanel();
    }

    private void populatePanel() {
        add(Movie);
        add(Box.createVerticalStrut(10));
        add(dateTime);
        add(Box.createVerticalStrut(10));
        add(seatsLabel);
    }

    private String formatSeats(Reservation res) {
        StringBuilder Seats = new StringBuilder();
        for (int i = 0; i < res.getSeats().size(); i++) {
            Seats.append(res.getSeats().get(i));
            if (i < res.getSeats().size() - 1) {
                Seats.append(", ");
            } else {
                Seats.append(".");
            }
        }
        return Seats.toString();
    }
}