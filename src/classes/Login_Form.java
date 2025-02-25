package classes;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class Login_Form extends JFrame {

    JTextField username = new JTextField();
    JPasswordField password = new JPasswordField();
    JLabel register = new JLabel("Register");
    JLabel UserLabel = new JLabel("Username:");
    JLabel PassLabel = new JLabel("Password:");
    JButton submit = new JButton("Submit");
    JPanel content = new JPanel();
    boolean visitor;

    private void addListeners() {
        register.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                register.setForeground(Color.BLUE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                register.setForeground(Color.WHITE);
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                setLocation(810, 370);
                getContentPane().removeAll();
                AddAccount_Tab panel = new AddAccount_Tab(visitor);
                getContentPane().add(panel);
                revalidate();
                pack();
                repaint();
            }
        });
        
        submit.addActionListener(e -> {
			String pass = new String(password.getPassword());
			String user = new String(username.getText());
    		if(user.equals("")) {
    			JOptionPane.showMessageDialog(this, "Enter username!", "Error", JOptionPane.ERROR_MESSAGE);
    			return;
    		} else if(pass.equals("")) {
    			JOptionPane.showMessageDialog(this, "Enter password!", "Error", JOptionPane.ERROR_MESSAGE);
    			return;
    		}
    		
    		if(DataBase_manager.checkPerson(user, pass, visitor)) {
    			
    			this.getContentPane().removeAll();
    			JPanel panel;
    			if(visitor){
    				setTitle("Choose!");
    				panel = new VisitorPanel(DataBase_manager.ReadPerson(user, true));
    			}
    			else {
    				setTitle("Employee");
    				panel = new Employee_Panel(DataBase_manager.ReadPerson(user, false));
    				setLocation(new Point(800, 350));
    			}
    			
    			this.getContentPane().add(panel);
    			Rectangle bounds = new Rectangle(this.getX(), this.getY(), panel.getWidth(), panel.getHeight());
    			this.setBounds(bounds);
    			this.revalidate();
    			pack();
    			this.repaint();
    		} else {
    			JOptionPane.showMessageDialog(this, "Your account has not been found!", "Error", JOptionPane.ERROR_MESSAGE);
    			return;
    		}
    	});
        
    }

    public Login_Form(boolean visitor) {
        this.visitor = visitor;
        setTitle("Login");
        setBounds(875, 425, 400, 300);
        setResizable(false);
        populateWindow();
        addListeners();
        setVisible(true);
    }

    public void populateWindow() {
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(new Color(30, 30, 30));

        UserLabel.setForeground(Color.WHITE);
        PassLabel.setForeground(Color.WHITE);
        register.setForeground(Color.WHITE);
        submit.setBackground(new Color(100, 100, 255));
        submit.setForeground(Color.WHITE);

        Dimension textFieldSize = new Dimension(200, 30);
        username.setMaximumSize(textFieldSize);
        password.setMaximumSize(textFieldSize);

        UserLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        PassLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        username.setAlignmentX(Component.CENTER_ALIGNMENT);
        password.setAlignmentX(Component.CENTER_ALIGNMENT);
        submit.setAlignmentX(Component.CENTER_ALIGNMENT);
        register.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(Box.createVerticalStrut(20));
        content.add(UserLabel);
        content.add(username);
        content.add(Box.createVerticalStrut(10));
        content.add(PassLabel);
        content.add(password);
        content.add(Box.createVerticalStrut(20));
        content.add(submit);
        content.add(Box.createVerticalStrut(10));
        if (visitor) {
            content.add(register);
        }
        this.add(content);
    }
}

