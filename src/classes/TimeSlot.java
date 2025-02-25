package classes;

public class TimeSlot {

	private String time;
	private String date;
	private int CinemaHallId;
	
	public TimeSlot(String time, String date, int cinemaHallId) {
		this.time = time;
		this.date = date;
		CinemaHallId = cinemaHallId;
	}
	
	public String getTime() {
		return time;
	}
	public String getDate() {
		return date;
	}
	public int getCinemaHallId() {
		return CinemaHallId;
	}
	
}
