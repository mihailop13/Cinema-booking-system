package classes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class selectProjectionFrame extends JFrame {

	private Person person;
    private Movie movie;
    private JLabel movieTitleLabel;
    private JButton reserveButton = new JButton("Reserve");
    private JTabbedPane tabbedPane = new JTabbedPane();
    private ArrayList<Projection> projections = new ArrayList<Projection>();
    private ArrayList<String> dates;
    private HashMap<String, JList<String>> projectionsListsMap = new HashMap<>();
    
    public selectProjectionFrame(Movie movie, Person person) {
        super();
        setTitle(movie.getMovieName());
        setBackground(new Color(30, 30, 30));
        setLocation(730, 300);
        this.person = person;
        this.movie = movie;
        this.projections = DataBase_manager.getSpecificProjections(this.movie.getId());
        this.movieTitleLabel = new JLabel(this.movie.getMovieName());
        dates = new ArrayList<String>();
        for(Projection p : projections) {
        	if(dates.indexOf(p.getDate()) == -1) {
        		dates.add(p.getDate());
        	}
        }
        populateWindow();
        addListeners();
        setVisible(true);
    }

    private void addListeners() {
    	reserveButton.addActionListener(e ->{
    		String selectedDate = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
    		JList<String> currentList = projectionsListsMap.get(selectedDate);
    		if(currentList.getSelectedIndex() == -1) {
				 JOptionPane.showMessageDialog(this, "You have to select projection!", "Error!", JOptionPane.ERROR_MESSAGE);
				 return;
			}
    		this.getContentPane().removeAll();
			String time = currentList.getSelectedValue().substring(0, currentList.getSelectedValue().lastIndexOf('-') - 1);
			Projection currentProjection = null;
			String hallID = currentList.getSelectedValue().substring(currentList.getSelectedValue().lastIndexOf(' ') + 1);
			for(Projection p : projections) {
				if(p.getDate().equals(selectedDate) && p.getTime().equals(time) && p.getMovie().getMovieName().equals(movieTitleLabel.getText()) && p.getHallID() == Integer.parseInt(hallID))
					currentProjection = p;
			}
			ReservationsPanel rp = new ReservationsPanel(currentProjection, person);
			this.add(rp);
			this.revalidate();
			this.repaint();
		});
		
	}

    private void populateWindow() {
        // Set overall layout
        this.setLayout(new BorderLayout());
        this.setMinimumSize(new Dimension(600, 500)); // Ensure a decent window size
        this.setBackground(new Color(30, 30, 30)); // Dark background for the whole frame

        // Movie title panel with reduced height
        JPanel moviePanel = new JPanel();
        moviePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        moviePanel.setBackground(new Color(30, 30, 30)); // Dark background for the title panel
        moviePanel.setPreferredSize(new Dimension(600, 60)); // Reduced height

        movieTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        movieTitleLabel.setForeground(Color.WHITE);

        moviePanel.add(movieTitleLabel);
        add(moviePanel, BorderLayout.NORTH); // Add movie title panel at the top

        if (dates.isEmpty()) {
            // Create a centered panel with a message indicating no projections available
            JPanel emptyPanel = new JPanel();
            emptyPanel.setLayout(new BoxLayout(emptyPanel, BoxLayout.Y_AXIS)); // Stack components vertically
            emptyPanel.setBackground(new Color(30, 30, 30)); // Dark background for the empty panel

            // Create label with no projections message
            JLabel emptyLabel = new JLabel("No projections available for this movie.");
            emptyLabel.setForeground(Color.LIGHT_GRAY); // Light gray text for visibility
            emptyLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Larger font for emphasis
            emptyLabel.setAlignmentX(CENTER_ALIGNMENT);

            // Add the empty label to the panel
            emptyPanel.add(emptyLabel);

            // Add back button at the bottom
            JButton backButton = new JButton("Back");
            backButton.setFont(new Font("Arial", Font.PLAIN, 16));
            backButton.setBackground(new Color(0, 150, 136)); // Modern blue-green color
            backButton.setForeground(Color.WHITE);
            backButton.setFocusPainted(false);
            backButton.setAlignmentX(CENTER_ALIGNMENT);
            backButton.addActionListener(e -> {
                // Logic to navigate back, if needed (you can implement the transition to the previous screen here)
                this.dispose(); // Or you can switch back to the previous screen.
            });

            // Add back button to the panel
            emptyPanel.add(Box.createVerticalGlue()); // Push the button to the bottom
            emptyPanel.add(backButton);

            // Add the empty panel to the center of the frame
            add(emptyPanel, BorderLayout.CENTER);
        } else {
            // Tabs with projections
            for (String s : dates) {
                tabbedPane.addTab(s, createProjectionPanel(s));
            }

            add(tabbedPane, BorderLayout.CENTER); // Add tabbed pane in the center of the frame

            // Reserve button at the bottom, smaller size
            reserveButton.setFont(new Font("Arial", Font.PLAIN, 16));
            reserveButton.setForeground(Color.WHITE);
            reserveButton.setBackground(new Color(0, 150, 136)); // Modern blue-green color
            reserveButton.setPreferredSize(new Dimension(150, 40)); // Smaller button size
            reserveButton.setFocusPainted(false);
            reserveButton.setAlignmentX(CENTER_ALIGNMENT);

            JPanel reservePanel = new JPanel();
            reservePanel.setBackground(new Color(30, 30, 30)); // Dark background for the button panel
            reservePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            reservePanel.add(reserveButton);

            add(reservePanel, BorderLayout.SOUTH); // Add the button panel at the bottom
        }
    }

    JList<String> projectionsList;
    
    private JPanel createProjectionPanel(String date) {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(0, 1)); // Vertical list of projections
        contentPanel.setBackground(new Color(30, 30, 30)); // Dark background for content panel

        DefaultListModel<String> model = new DefaultListModel<>();
        for (Projection p : projections) {
        	if(p.getDate().equals(date)) {
        		model.addElement(p.getTime() + " - Hall: " + p.getHallID());
        	}
        }

        projectionsList = new JList<>(model);
        projectionsList.setFont(new Font("Arial", Font.PLAIN, 18));
        projectionsList.setForeground(Color.WHITE);
        projectionsList.setBackground(new Color(40, 40, 40)); // Dark background for list
        projectionsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        projectionsList.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Add JScrollPane to allow scrolling when there are many projections
        JScrollPane scrollPane = new JScrollPane(projectionsList);
        scrollPane.setPreferredSize(new Dimension(450, 200)); // Set scrollable area size
        contentPanel.add(scrollPane);
        
        projectionsListsMap.put(date, projectionsList);
        
        return contentPanel;
    }
}
