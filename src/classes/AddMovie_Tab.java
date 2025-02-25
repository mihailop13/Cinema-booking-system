package classes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class AddMovie_Tab extends JPanel { // koristi se kada zaposleni hoće da doda film

    JLabel Title = new JLabel("Title:");
    JLabel Release_date = new JLabel("Release date:");
    JLabel Description = new JLabel("Description");
    JLabel ActorsLabel = new JLabel("Actors:");

    JTextField Actors = new JTextField();
    JButton OK = new JButton("OK");

    JDatePickerImpl datePicker;

    JTextArea Description_Area = new JTextArea();
    JTextField TitleText = new JTextField();

    public AddMovie_Tab() {
        populateWindow();
        addListener();
    }

    private void addListener() {
        OK.addActionListener(e -> {
        	try {
        		
        		if(TitleText.getText().equals(""))
        			throw new TitleException();
        		if(Description_Area.getText().equals(""))
        			throw new DescriptionException();
        		if(Actors.getText().equals(""))
        			throw new ActorsException();
        		
        		String date = String.format("%04d-%02d-%02d", 
        			    datePicker.getModel().getYear(), 
        			    datePicker.getModel().getMonth() + 1, 
        			    datePicker.getModel().getDay());
        		if(date.equals(""))
        			throw new ReleaseDateException();
        		
                DataBase_manager.writeMovie(new Movie(TitleText.getText(), date, Description_Area.getText(), Actors.getText()));
            	JOptionPane.showMessageDialog(getTopLevelAncestor(), "Movie added!", "Success", JOptionPane.INFORMATION_MESSAGE);
				
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(getTopLevelAncestor(), "Error!", e2.getMessage(), JOptionPane.ERROR_MESSAGE);
			}
        });
    }

    private void populateWindow() {
        this.setLayout(new GridLayout(1, 2));

        // Panel sa podacima
        JPanel data = new JPanel();
        data.setLayout(new BoxLayout(data, BoxLayout.Y_AXIS));
        data.setBackground(new Color(30, 30, 30)); // Tamna pozadina za formu

        // Podesavanje fontova za label
        Title.setFont(Title.getFont().deriveFont(16f));
        Title.setForeground(Color.WHITE); // Tekst u beloj boji
        Title.setAlignmentX(Component.CENTER_ALIGNMENT);

        Release_date.setFont(Release_date.getFont().deriveFont(16f));
        Release_date.setForeground(Color.WHITE); // Tekst u beloj boji
        Release_date.setAlignmentX(Component.CENTER_ALIGNMENT);

        Description.setFont(Description.getFont().deriveFont(16f));
        Description.setForeground(Color.WHITE); // Tekst u beloj boji
        Description.setAlignmentX(Component.CENTER_ALIGNMENT);

        ActorsLabel.setFont(ActorsLabel.getFont().deriveFont(16f));
        ActorsLabel.setForeground(Color.WHITE); // Tekst u beloj boji
        ActorsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Postavke dimenzija za tekstualne komponente
        Dimension textFieldSize = new Dimension(200, 30);
        TitleText.setPreferredSize(textFieldSize);
        TitleText.setFont(TitleText.getFont().deriveFont(14f));
        TitleText.setBackground(new Color(255, 255, 255));

        Actors.setPreferredSize(textFieldSize);
        Actors.setFont(Actors.getFont().deriveFont(14f));
        Actors.setBackground(new Color(255, 255, 255));

        Description_Area.setPreferredSize(new Dimension(200, 120)); // Veća visina za Description_Area
        Description_Area.setFont(Description_Area.getFont().deriveFont(14f));
        Description_Area.setBackground(new Color(255, 255, 255));
        Description_Area.setMargin(new java.awt.Insets(10, 20, 10, 10)); // Dodajte horizontalni razmak

        // Postavljanje JDatePicker-a
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        // Podesavanje dugmeta OK
        OK.setFont(OK.getFont().deriveFont(14f));
        OK.setBackground(new Color(0, 122, 255)); // Plava pozadina
        OK.setForeground(Color.WHITE); // Bela boja teksta
        OK.setPreferredSize(new Dimension(100, 40));
        OK.setAlignmentX(Component.CENTER_ALIGNMENT); // Poravnanje dugmeta u sredinu

        // Dodavanje komponenata u panel
        data.add(Title);
        data.add(Box.createVerticalStrut(10));
        data.add(TitleText);
        data.add(Box.createVerticalStrut(10));
        data.add(Release_date);
        data.add(Box.createVerticalStrut(10));
        data.add(datePicker);
        data.add(Box.createVerticalStrut(10));
        data.add(ActorsLabel);
        data.add(Box.createVerticalStrut(10));
        data.add(Actors);
        data.add(Box.createVerticalStrut(20)); // Razmak između polja i TextArea
        data.add(OK);
        data.add(Box.createVerticalStrut(10));

        this.add(data);

        // Panel za Description
        JPanel dsc = new JPanel();
        dsc.setLayout(new BoxLayout(dsc, BoxLayout.Y_AXIS));
        dsc.setBackground(new Color(30, 30, 30)); // Tamna pozadina za description
        dsc.add(Description);
        dsc.add(Box.createVerticalStrut(10));
        dsc.add(Box.createHorizontalStrut(20)); // Dodajte horizontalni razmak pre Description_Area
        dsc.add(Description_Area);

        this.add(dsc);
    }
}





