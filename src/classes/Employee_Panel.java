package classes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Employee_Panel extends JPanel {						//ono sto vidi zaposleni

	JTabbedPane tabs = new JTabbedPane();
	JPanel AddEmployee = new AddAccount_Tab(false);
	JPanel AddMovie = new AddMovie_Tab();
	addProjectionPanel AddProjection= new addProjectionPanel();
	SelectMovie_Tab SelectMovie_Tab;
	
	public Employee_Panel(Person person) {
		
		SelectMovie_Tab = new SelectMovie_Tab(person, false);
		
		this.setBackground(new Color(30, 30, 30));
		
		this.setBounds(new Rectangle(new Dimension(500, 400)));
		
	    tabs.add("Print ticket", SelectMovie_Tab);
		tabs.add("Add movie", AddMovie);
		tabs.add("Add employee", AddEmployee);
		tabs.add("Add projection", AddProjection);
		
		tabs.addChangeListener(new ChangeListener() {
             @Override
             public void stateChanged(ChangeEvent e) {
                 
                 switch (tabs.getSelectedIndex()) {
                 
	                 case 0:{
	                	 SelectMovie_Tab.refreshInfoPanels();
	                 }
	                 case 3:{
						AddProjection.refreshComboLists();
	                 }
                 }
             }
         });
		
		this.add(tabs);
	}
	
}
