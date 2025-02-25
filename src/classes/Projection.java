package classes;

import java.util.ArrayList;

public class Projection {					//klasa predstavlja JEDNU projekciju

	private Movie movie;
	private String time;
	private String Date;
	private ArrayList<Integer> reservedSeats;
	private int hallID;
	private static int id = 1;
	private int ProjectionID;
	
	public Projection(Movie movie,  String time, String Date, int hallID) {
		super();
		this.ProjectionID = id++;
		this.hallID = hallID;
		this.movie = movie;
		this.time = time;
		this.Date = Date;
	}

	public int getProjectionID() {
		return ProjectionID;
	}

	public String getDate() {
		return Date;
	}
	
	public Movie getMovie() {
		return movie;
	}

	public String getTime() {
		return time;
	}
	
	public int getHallID() {
		return hallID;
	}

	public void reserveSeat(int seatID) {
		if(reservedSeats.indexOf(seatID) != -1)
			reservedSeats.add(seatID);
	}

	public ArrayList<Integer> getReservedSeats() {
		return reservedSeats;
	}
}
