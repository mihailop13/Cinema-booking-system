package classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MovieInfo_Panel extends JPanel { // podaci o filmovima

    private JLabel MovieName;
    private JLabel Actors;
    private JLabel desc;

    private Person person;
    private Movie movie;

    public MovieInfo_Panel(Movie movie, Person person) {

        this.MovieName = new JLabel(movie.getMovieName() + ", Release date: " + movie.getReleaseDate());
        this.desc = new JLabel("<html><body><i>Description:</i> " + movie.getDescription() + "</body></html>");
        this.movie = movie;
        this.person = person;
        String act = "Starring: " + movie.getActors();

        this.Actors = new JLabel(act);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new Color(30, 30, 30)); // Tamnija pozadina za panel
        this.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 1)); // Ivica sa svetlijom sivom
        addListeners();
        populatePanel();
    }

    private void populatePanel() {
        this.setPreferredSize(new Dimension(480, 120)); // Postavite dimenzije panela
        this.setMaximumSize(new Dimension(480, 120)); // Ograničite maksimalnu visinu panela

        this.add(Box.createVerticalStrut(10)); // Razmak od vrha
        this.MovieName.setForeground(new Color(255, 255, 255)); // Svetla boja za naziv filma
        this.add(MovieName);
        this.add(Box.createVerticalStrut(5)); // Razmak između elemenata

        this.Actors.setForeground(new Color(200, 200, 200)); // Svetla siva za glumce
        this.add(Actors);
        this.add(Box.createVerticalStrut(5)); // Razmak između elemenata

        this.desc.setForeground(new Color(180, 180, 180)); // Svetla siva za opis filma
        this.add(desc);
        this.add(Box.createVerticalStrut(10)); // Razmak od dna
    }

    public Movie getMovie() {
        return movie;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Pozivanje roditeljske metode za ispravno crtanje
        g.setColor(new Color(100, 100, 100)); // Koristi svetliju sivu za liniju
        g.drawLine(0, this.getHeight() - 1, this.getWidth(), this.getHeight() - 1); // Crtanje linije
    }

    private void addListeners() {
        MovieName.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                MovieName.setForeground(Color.BLUE); // Plavi tekst pri prelasku mišem
            }

            @Override
            public void mouseExited(MouseEvent e) {
                MovieName.setForeground(Color.WHITE); // Vraća belu boju kad miš napusti
            }

            @Override
            public void mouseClicked(MouseEvent e) {
            	new selectProjectionFrame(movie, person);
            }
        });
    }
}
