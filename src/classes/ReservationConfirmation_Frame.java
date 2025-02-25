package classes;

import java.awt.*;
import javax.swing.*;

public class ReservationConfirmation_Frame extends JFrame {  // frame koji iskoci kada se rezervise mesto

    JLabel yourReservation = new JLabel("Your reservation has been confirmed!");
    JLabel Name;
    JLabel Surname;
    JLabel Movie;
    JLabel Seats;
    JButton OK = new JButton("OK");

    public ReservationConfirmation_Frame(Reservation res) {
        super();

        setTitle("Your reservation");
        setBounds(800, 400, 350, 400);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Da se zatvori na klik X

        // Postavljamo pozadinu celokupnog prozora i komponenata
        getContentPane().setBackground(new Color(30, 30, 30));  // Tamna pozadina za celu fram

        // Postavljamo boje, fontove i izgled
        yourReservation.setFont(new Font("Arial", Font.BOLD, 16));
        yourReservation.setAlignmentX(CENTER_ALIGNMENT);
        yourReservation.setForeground(Color.WHITE); // Belo za tekst

        Name = new JLabel("Name: " + res.getPerson().getName());
        Name.setFont(new Font("Arial", Font.PLAIN, 14));
        Name.setAlignmentX(CENTER_ALIGNMENT);
        Name.setForeground(Color.WHITE); // Belo za tekst

        Surname = new JLabel("Surname: " + res.getPerson().getSurname());
        Surname.setFont(new Font("Arial", Font.PLAIN, 14));
        Surname.setAlignmentX(CENTER_ALIGNMENT);
        Surname.setForeground(Color.WHITE); // Belo za tekst

        Movie = new JLabel("Movie: " + res.getProjection().getMovie().getMovieName());
        Movie.setFont(new Font("Arial", Font.PLAIN, 14));
        Movie.setAlignmentX(CENTER_ALIGNMENT);
        Movie.setForeground(Color.WHITE); // Belo za tekst

        String seats = "";
        for (int i = 0; i < res.getSeats().size(); i++) {
            if (i < res.getSeats().size() - 1)
                seats += res.getSeats().get(i) + ", ";
            else
                seats += res.getSeats().get(i) + ".";
        }
        Seats = new JLabel("Seats number: " + seats);
        Seats.setFont(new Font("Arial", Font.PLAIN, 14));
        Seats.setAlignmentX(CENTER_ALIGNMENT);
        Seats.setForeground(Color.WHITE); // Belo za tekst

        OK.setFont(new Font("Arial", Font.BOLD, 14));
        OK.setBackground(new Color(0, 153, 51)); // Zeleni za dugme
        OK.setForeground(Color.WHITE); // Bela boja teksta na dugmetu
        OK.setAlignmentX(CENTER_ALIGNMENT);
        OK.setFocusPainted(false);
        OK.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));

        OK.addActionListener(e -> {
            this.dispose(); // Zatvara prozor kada klikneš na "OK"
        });

        populateWindow(); // Dodavanje komponenti u prozor
        setVisible(true);
    }

    private void populateWindow() {
        // Dodavanje svih komponenti u prozor
        add(Box.createVerticalStrut(20)); // Razmak
        add(yourReservation);
        add(Box.createVerticalStrut(10)); // Razmak
        add(Name);
        add(Box.createVerticalStrut(5));  // Razmak
        add(Surname);
        add(Box.createVerticalStrut(5));  // Razmak
        add(Movie);
        add(Box.createVerticalStrut(5));  // Razmak
        add(Seats);
        add(Box.createVerticalStrut(20)); // Razmak
        add(OK);
    }
}
