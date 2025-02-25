package classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class addProjectionPanel extends JPanel {

    JLabel headline;
    JLabel Movie;
    JLabel TimeSlot;
    JLabel Question;

    JComboBox<String> movies;
    JComboBox<String> timeSlots;

    JButton OK;

    JFrame frame;

    public addProjectionPanel() {
        frame = (JFrame) this.getTopLevelAncestor();

        // Postavljanje boja i fontova
        Color backgroundColor = new Color(30, 30, 30);
        Color textColor = Color.WHITE;
        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font headlineFont = new Font("Arial", Font.BOLD, 20);
        Font questionFont = new Font("Arial", Font.ITALIC, 12);

        this.setBackground(backgroundColor);
        this.setLayout(new GridBagLayout());

        // Kreiranje elemenata
        headline = new JLabel("Add new projections");
        headline.setFont(headlineFont);
        headline.setForeground(textColor);

        Movie = new JLabel("Movie:");
        Movie.setFont(labelFont);
        Movie.setForeground(textColor);

        TimeSlot = new JLabel("Time Slot:");
        TimeSlot.setFont(labelFont);
        TimeSlot.setForeground(textColor);

        Question = new JLabel("Want to create new time slot?");
        Question.setFont(questionFont);
        Question.setForeground(Color.GRAY);

        // Stilizacija dugmeta
        OK = new JButton("OK");
        OK.setBackground(new Color(100, 100, 255));
        OK.setForeground(Color.WHITE);
        OK.setFocusPainted(false);
        OK.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 255), 2));
        OK.setPreferredSize(new Dimension(100, 30));

        // Efekat belog okvira na hover
        OK.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                OK.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                OK.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 255), 2));
            }
        });
        
        OK.addActionListener(e -> {
        		try {
					if(timeSlots.getSelectedIndex() == 0)
						throw new Exception("Choose time slot!");
					if(movies.getSelectedIndex() == 0)
						throw new Exception("Choose movie!");
					ArrayList<Movie> moviesList = DataBase_manager.ReadMovies();
	        		int SpaceIndex = timeSlots.getItemAt(timeSlots.getSelectedIndex()).lastIndexOf(" ");
	        		String s = timeSlots.getItemAt(timeSlots.getSelectedIndex()).substring(SpaceIndex + 1, timeSlots.getItemAt(timeSlots.getSelectedIndex()).length());
	        		
	        		int hallID = Integer.parseInt(s);
	        		ArrayList<TimeSlot> timeSlotsList = DataBase_manager.readFreeTimeSlots();
	        		TimeSlot selectedTimeSlot = null;
	        		for(TimeSlot ts : timeSlotsList) {
	        			if(timeSlots.getItemAt(timeSlots.getSelectedIndex()).equals(ts.getDate() + ", " + ts.getTime() + ", Hall: " + ts.getCinemaHallId())) {
	        				selectedTimeSlot = ts;
	        				break;
	        			}
	        		}
					DataBase_manager.writeProjection(moviesList.get(movies.getSelectedIndex() - 1), selectedTimeSlot.getTime(), selectedTimeSlot.getDate(), hallID);
		           	JOptionPane.showMessageDialog(this, "Projection added!", "Success", JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(this, e2.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
				}
		 });

        // ComboBox-ovi koji su prazni dok se ne izabere nešto
        movies = new JComboBox<>();
        movies.addItem("Select a movie...");

        timeSlots = new JComboBox<>();
        timeSlots.addItem("Select a time slot...");

        // Smanjujemo dimenzije JComboBox-a
        Dimension comboSize = new Dimension(200, 25);
        movies.setPreferredSize(comboSize);
        timeSlots.setPreferredSize(comboSize);

        refreshComboLists();

        // Dodavanje hover efekta na pitanje
        Question.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                Question.setForeground(Color.BLUE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Question.setForeground(Color.GRAY);
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
            	new AddTimeSlot_Frame();
            
            }
        });

        // Podešavanje rasporeda
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(headline, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        this.add(Movie, gbc);

        gbc.gridx = 1;
        this.add(movies, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        this.add(TimeSlot, gbc);

        gbc.gridx = 1;
        this.add(timeSlots, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        this.add(Question, gbc);

        gbc.gridy++;
        this.add(OK, gbc);
    }

    public void refreshComboLists() {
        movies.removeAllItems();
        movies.addItem("Select a movie...");

        timeSlots.removeAllItems();
        timeSlots.addItem("Select a time slot...");

        ArrayList<Movie> moviesList = DataBase_manager.ReadMovies();
        for (Movie m : moviesList) {
            movies.addItem(m.getMovieName());
        }

        ArrayList<TimeSlot> timeSlotsList = DataBase_manager.readFreeTimeSlots();
        for (TimeSlot ts : timeSlotsList) {
            timeSlots.addItem(ts.getDate() + ", " + ts.getTime() + ", Hall: " + ts.getCinemaHallId());
        }

        revalidate();
        repaint();
    }
}
