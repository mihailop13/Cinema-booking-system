package classes;

import java.util.ArrayList;

public class Reservation {							//cuva podatke o rezervaciji

	private Projection projection;
	private Person person;
	private ArrayList<Integer> seats;
	
	public Reservation(Projection projection, Person person, ArrayList<Integer> seats) {
		super();
		this.projection = projection;
		this.person= person;
		this.seats = seats;
	}
	
	public Person getPerson() {
		return person;
	}
	
	public Projection getProjection() {
		return projection;
	}

	public ArrayList<Integer> getSeats() {
		return seats;
	}
}
