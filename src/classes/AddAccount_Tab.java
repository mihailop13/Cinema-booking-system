package classes;

import java.awt.*;
import javax.swing.*;

public class AddAccount_Tab extends JPanel {									
    JLabel Name = new JLabel("Name:");
    JLabel Username = new JLabel("Username:");
    JLabel Surname = new JLabel("Surname:");
    JLabel Password = new JLabel("Password:");
    JLabel Headline;

    JTextField NameText = new JTextField();
    JTextField UsernameText = new JTextField();
    JTextField SurnameText = new JTextField();
    JPasswordField PasswordText = new JPasswordField();

    JButton submit = new JButton("Submit");
    boolean visitor;

    public AddAccount_Tab(boolean visitor) {
        super();
        setPreferredSize(new Dimension(470, 325));
        setBackground(new Color(30, 30, 30)); // Tamna pozadina
        this.visitor = visitor;

        if (visitor)
            Headline = new JLabel("Register new visitor account");
        else
            Headline = new JLabel("Register new employee");

        Headline.setForeground(Color.WHITE);
        Headline.setFont(new Font("Arial", Font.BOLD, 16));
        Headline.setAlignmentX(Component.CENTER_ALIGNMENT);

        addListener();
        setupUI();
    }

    private void setupUI() {
        this.setLayout(new BorderLayout());

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(new Color(30, 30, 30));
        content.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.setPreferredSize(new Dimension(450, 350)); // Omogućava scroll da radi pravilno

        // Stilizacija polja
        Dimension textFieldSize = new Dimension(170, 30);
        setFieldProperties(UsernameText, textFieldSize);
        setFieldProperties(PasswordText, textFieldSize);
        setFieldProperties(SurnameText, textFieldSize);
        setFieldProperties(NameText, textFieldSize);

        setLabelProperties(Username);
        setLabelProperties(Password);
        setLabelProperties(Surname);
        setLabelProperties(Name);

        submit.setAlignmentX(Component.CENTER_ALIGNMENT);
        submit.setBackground(new Color(100, 100, 255));
        submit.setForeground(Color.WHITE);
        submit.setFocusPainted(false);

        content.add(Box.createVerticalGlue());
        content.add(Headline);
        content.add(Box.createVerticalStrut(15));

        addField(content, Name, NameText);
        addField(content, Surname, SurnameText);
        addField(content, Username, UsernameText);
        addField(content, Password, PasswordText);

        content.add(Box.createVerticalStrut(20));
        content.add(submit);
        content.add(Box.createVerticalGlue());

        // Dodaj JScrollPane
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smoother scrolling experience
        scrollPane.setBorder(null);

        this.add(scrollPane, BorderLayout.CENTER);
    }

    private void setFieldProperties(JTextField field, Dimension size) {
        field.setPreferredSize(size);
        field.setMaximumSize(size);
        field.setMinimumSize(size);
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        field.setBackground(new Color(50, 50, 50));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    private void setLabelProperties(JLabel label) {
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(Color.WHITE);
    }

    private void addField(JPanel panel, JLabel label, JTextField textField) {
        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(textField);
        panel.add(Box.createVerticalStrut(15));
    }

    private void addListener() {
        submit.addActionListener(e -> {
        	try {
        		if(NameText.getText().equals(""))
        			throw new EmptyName();
        		if(UsernameText.getText().equals(""))
        			throw new EmptyUsername();
        		if(SurnameText.getText().equals(""))
        			throw new EmptySurname();
        		if(new String(PasswordText.getPassword()).equals(""))
        			throw new EmptyPassword();
        		if(DataBase_manager.ReadPerson(UsernameText.getText(), visitor) != null)
        			throw new UserAlreadyExists_Exception();
        		DataBase_manager.WritePerson(new Person(NameText.getText(), UsernameText.getText(), SurnameText.getText(), new String(PasswordText.getPassword())), visitor);
            	JOptionPane.showMessageDialog(getTopLevelAncestor(), "User registered!", "Success", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(getTopLevelAncestor(), e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
        	if(visitor) {
        		JFrame frame = (JFrame) getTopLevelAncestor();
        		frame.dispose();
        		new Login_Form(visitor);
        	}
        });
    }
}