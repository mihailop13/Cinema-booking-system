package classes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.*;
import java.util.ArrayList;

public class SelectMovie_Tab extends JPanel {  // panel gde stoji ponuda PROJEKCIJA filmova

    ArrayList<MovieInfo_Panel> InfoPanels = new ArrayList<MovieInfo_Panel>();
    Person person;
    boolean visitor;

    public SelectMovie_Tab(Person person, boolean visitor) {
        this.person = person;
        this.visitor = visitor;
        setLocation(new Point(750, 320));
        this.setBounds(new Rectangle(new Dimension(500, 300)));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Koristi BoxLayout za bolju kontrolu
        populateWindow();
    }

    JScrollPane scrollPane;
    JPanel contentPanel;
    JButton backButton; // Dugme za povratak na VisitorPanel
    
    private void populateWindow() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); // Podesite BoxLayout za listu filmova
        contentPanel.setBackground(new Color(30, 30, 30)); // Pozadina panela

        scrollPane = new JScrollPane(contentPanel);
        
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smoother scrolling experience
        scrollPane.setPreferredSize(new Dimension(500, 300)); // Dimenzije scroll-a

        ArrayList<Movie> movies = DataBase_manager.ReadMovies();

        for (Movie movie : movies) {
            InfoPanels.add(new MovieInfo_Panel(movie, person));
        }

        for (MovieInfo_Panel movieInfo : InfoPanels) {
            contentPanel.add(movieInfo);
            contentPanel.add(Box.createVerticalStrut(10)); // Razmak između svakog panela
        }

        if(visitor) {
        	// Dugme za povratak na VisitorPanel
            backButton = new JButton("Back");
            backButton.setPreferredSize(new Dimension(100, 50));
            backButton.setBackground(new Color(100, 100, 255));
            backButton.setForeground(Color.WHITE);
            backButton.setFont(new Font("Arial", Font.BOLD, 16));
            backButton.addActionListener(e -> {
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
        }
        this.add(scrollPane);
        if(visitor) {
        	JPanel back = new JPanel(new BorderLayout());
        	back.setBackground(new Color(30, 30, 30));
        	back.add(backButton, BorderLayout.CENTER);
        	add(back);
        }
        	
    }

    public void refreshInfoPanels() { //refresuje info panele
        contentPanel.removeAll();

        ArrayList<Movie> movies = DataBase_manager.ReadMovies();
        InfoPanels = new ArrayList<MovieInfo_Panel>();

        int cnt = 0;

        for (Movie movie : movies) {
            InfoPanels.add(new MovieInfo_Panel(movie, person));
            contentPanel.add(InfoPanels.get(cnt++));
            contentPanel.add(Box.createVerticalStrut(10)); // Razmak između svakog panela
        }

        revalidate();
    }
}
