package classes;

public class Person {

	private int personID;
	private static int id;
	private String Name;
	private String Username;
	private String Surname;
	private String Pass;
	
	public Person(String name, String username, String surname, String pass) {
		personID = id++;
		Name = name;
		Username = username;
		Surname = surname;
		Pass = pass;
	}
	
	public Person(String name, String username, String surname, String pass, int id) {
		personID = id;
		Name = name;
		Username = username;
		Surname = surname;
		Pass = pass;
	}

	public int getId() {
		return personID;
	}
	public String getName() {
		return Name;
	}

	public String getUsername() {
		return Username;
	}

	public String getSurname() {
		return Surname;
	}

	public String getPass() {
		return Pass;
	}
	
}
