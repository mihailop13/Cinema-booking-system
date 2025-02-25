package classes;

import java.awt.*;
import java.io.File;
import javax.swing.*;

public class Main_Frame extends JFrame {

    JButton visitor = new JButton("Visitor");
    JButton employee = new JButton("Employee");
    JLabel cinema = new JLabel("Cinema", SwingConstants.CENTER);

    public Main_Frame() {
        File dbFile = new File("Cinema.db");
        if (dbFile.exists())
            dbFile.delete();
        DataBase_manager.initDB();
        DataBase_manager.initTimeSlots();
        DataBase_manager.initProjections();

        setTitle("Cinema");
        setBounds(700, 300, 700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        populateWindow();
        addListeners();

        setVisible(true);
    }

    private void populateWindow() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(new Color(30, 30, 30));

        cinema.setForeground(Color.WHITE);
        cinema.setFont(new Font("Arial", Font.BOLD, 26));
        visitor.setFont(new Font("Arial", Font.BOLD, 16));
        employee.setFont(new Font("Arial", Font.BOLD, 16));
        
        styleButton(visitor);
        styleButton(employee);

        cinema.setAlignmentX(Component.CENTER_ALIGNMENT);
        visitor.setAlignmentX(Component.CENTER_ALIGNMENT);
        employee.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(Box.createVerticalStrut(50));
        content.add(cinema);
        content.add(Box.createVerticalStrut(30));
        content.add(visitor);
        content.add(Box.createVerticalStrut(15));
        content.add(employee);

        this.add(content);
    }

    private static void styleButton(JButton button) {
        // Podesi iste veličine za oba dugmeta
        Dimension size = new Dimension(200, 45); 
        button.setPreferredSize(size);
        button.setMinimumSize(size);
        button.setMaximumSize(size);

        // Osnovni izgled
        button.setBackground(new Color(100, 100, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Unutrašnji border (padding) da tekst ne bude "na ivici"
        button.setMargin(new Insets(10, 10, 10, 10));
    }


    private void addListeners() {
        visitor.addActionListener(e -> new Login_Form(true));
        employee.addActionListener(e -> new Login_Form(false));
    }
}

