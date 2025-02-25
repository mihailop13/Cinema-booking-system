package classes;

import java.awt.*;
import java.util.Properties;
import javax.swing.*;
import org.jdatepicker.impl.*;

public class AddTimeSlot_Frame extends JFrame {

    private JLabel headerLabel = new JLabel("Create New Time Slot");
    private JLabel timeLabel = new JLabel("Time:");
    private JLabel dateLabel = new JLabel("Date:");
    private JLabel hallLabel = new JLabel("Hall:");

    private JComboBox<String> timeComboBox;
    private JComboBox<String> hallComboBox;
    private JButton createButton = new JButton("Create");
    private JDatePickerImpl datePicker;

    public AddTimeSlot_Frame() {
        setTitle("Create New Time Slot");
        setBounds(860, 370, 400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Dark theme colors
        Color backgroundColor = new Color(30, 30, 30);   //Dark gray background
        Color textColor = new Color(200, 200, 200);      //Light gray text
        Color buttonColor = new Color(100, 100, 255);    //Light blue button

        getContentPane().setBackground(backgroundColor);

        // Header label
        headerLabel.setForeground(textColor);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabel.setBounds(80, 10, 300, 30);

        // Date Picker
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        // Time ComboBox
        timeComboBox = new JComboBox<>();
        timeComboBox.addItem("Select time...");
        for (String s : DataBase_manager.getSlots()) {
            timeComboBox.addItem(s);
        }

        // Hall ComboBox
        hallComboBox = new JComboBox<>(new String[]{"0", "1", "2", "3", "4"});

        // Styling components
        dateLabel.setForeground(textColor);
        timeLabel.setForeground(textColor);
        hallLabel.setForeground(textColor);
        createButton.setBackground(buttonColor);
        createButton.setForeground(Color.WHITE);

        datePicker.getComponent(0).setBackground(Color.DARK_GRAY);
        datePicker.getComponent(0).setForeground(Color.WHITE);
        datePicker.getComponent(1).setBackground(Color.DARK_GRAY);
        datePicker.getComponent(1).setForeground(Color.WHITE);

        timeComboBox.setBackground(Color.DARK_GRAY);
        timeComboBox.setForeground(Color.WHITE);

        hallComboBox.setBackground(Color.DARK_GRAY);
        hallComboBox.setForeground(Color.WHITE);

        // Positioning elements
        dateLabel.setBounds(30, 60, 80, 25);
        datePicker.setBounds(120, 60, 200, 25);
        timeLabel.setBounds(30, 100, 80, 25);
        timeComboBox.setBounds(120, 100, 200, 25);
        hallLabel.setBounds(30, 140, 80, 25);
        hallComboBox.setBounds(120, 140, 200, 25);
        createButton.setBounds(140, 200, 100, 30);

        // Adding components
        add(headerLabel);
        add(dateLabel);
        add(datePicker);
        add(timeLabel);
        add(timeComboBox);
        add(hallLabel);
        add(hallComboBox);
        add(createButton);

        setVisible(true);
        
        addListener();
    }

	private void addListener() {
		createButton.addActionListener(e ->{
			String date = String.format("%04d-%02d-%02d", 
    			    datePicker.getModel().getYear(), 
    			    datePicker.getModel().getMonth() + 1, 
    			    datePicker.getModel().getDay());
			String time = timeComboBox.getSelectedItem().toString();
			String hall = hallComboBox.getSelectedItem().toString();
			
			try {
				if(date.equals(""))
					throw new Exception("Choose Date!");
				if(time.equals("Select time..."))
					throw new Exception("Choose time!");
				if(DataBase_manager.CheckTimeSlot(date, time, Integer.parseInt(hall)))
					throw new Exception("Time slot already exists!");
				DataBase_manager.writeTimeSlot(date, time, Integer.parseInt(hall));
				JOptionPane.showMessageDialog(this, "Time slot added!", "Success", JOptionPane.INFORMATION_MESSAGE);
				
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
	}
}
