package classes;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

public class DataBase_manager {				//klasa za manipulisanje sa bazom
	
	static String url = "jdbc:sqlite:Cinema.db";  // Konektuj se na bazu samo kada treba!
	
	public static void initDB() {

    	try (Connection conn = DriverManager.getConnection(url);
             Statement statement = conn.createStatement()) {
			
			String initVisitors = "Create table if not exists Visitors("
								+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
			                    + "Name TEXT NOT NULL default '',"
			                    + "Surname TEXT NOT NULL default '',"
			                    + "Username Text Not Null default '',"
			                    + "Password Text Not Null Default '')";
			
			String initEmployees = "Create table if not exists Employees("
								+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
			                    + "Name TEXT NOT NULL default '',"
			                    + "Surname TEXT NOT NULL default '',"
			                    + "Username Text Not Null default '',"
			                    + "Password Text Not Null Default '')";
			
			statement.execute(initVisitors);
			statement.execute(initEmployees);
			
			String addAdmin = "INSERT INTO Employees (Name, Surname, Username, Password)"
							+ "VALUES ('Admin', 'Admin', 'Admin', 'Admin')";
			statement.execute(addAdmin);
			
			String initMovies = "Create table if not exists Movies("
								+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
			                    + "Title TEXT NOT NULL default '',"
			                    + "ReleaseDate DATE,"
			                    + "Actors TEXT NOT NULL default '',"
			                    + "Description TEXT default '')";
			statement.execute(initMovies);
			
			String initTheaters = "Create table if not exists CinemaHalls("
								+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
			                    + "NumOfSeats INTEGER NOT NULL default 0)";
			
			statement.execute(initTheaters);
			
			String createTheaters = "INSERT INTO CinemaHalls(NumOfSeats)"
								+ "VALUES (60), (60), (50), (50), (50);";

			statement.execute(createTheaters);
			
			String createReservations = "Create table if not exists Reservations("
								+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
								+ "MovieID INTEGER NOT NULL default 0,"
								+ "numOfSeat INTEGER NOT NULL default 0,"
								+ "ProjectionID Integer NOT NULL,"		
								+ "PersonID Integer Not NUll,"
								+ "FOREIGN KEY (ProjectionID) REFERENCES Projections(id),"		
								+ "FOREIGN KEY (PersonID) REFERENCES Visitors(id),"
								+ "FOREIGN KEY (MovieID) REFERENCES Movies(id));";

			statement.execute(createReservations);
			
			String createtimeSlots = "Create table if not exists TimeSlots("
								+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
								+ "Date DATE Not NULL,"
								+ "CinemaHallID Integer NOT NULL,"
								+ "Time STRING Not NULL default '',"
								+ "FOREIGN KEY (CinemaHallID) REFERENCES CinemaHalls(id));";
			
			statement.execute(createtimeSlots);
			
			String createProjections = "Create table if not exists Projections("
								+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
								+ "MovieID INTEGER NOT NULL default 0,"
								+ "TimeSlotID INTEGER NOT NULL default -1,"
								+ "FOREIGN KEY (MovieID) REFERENCES Movies(id),"
								+ "FOREIGN KEY (TimeSlotID) REFERENCES TimeSlots(id));";
			
			statement.execute(createProjections);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	WriteMovies_API();
	}
	
	public static Person ReadPerson(String username, boolean visitor) {
		
		String findPerson;
		
		if(visitor) {
			findPerson = "Select * from Visitors"
						+ " where Username = ?;";
		} else {
			
			findPerson = "Select * from Employees"
						+ " where Username = ?;";
		}
			
		
		try (Connection conn = DriverManager.getConnection(url);
				PreparedStatement pstmt = conn.prepareStatement(findPerson)) {
			pstmt.setString(1, username);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next())
				return new Person(rs.getString("Name"), rs.getString("Username"), rs.getString("Surname"), rs.getString("Password"), rs.getInt("id"));
		
		} catch (SQLException e) {
	        e.printStackTrace();
	    }
		
		return null;
	}
	
	public static void WritePerson(Person person, boolean visitor) {
		
		if(visitor) {
			String addPerson = "INSERT INTO Visitors (Name, Surname, Username, Password)"
					+ "VALUES (?, ?, ?, ?)";
			
			try (Connection conn = DriverManager.getConnection(url);
			        PreparedStatement pstmt = conn.prepareStatement(addPerson)) {
			        
			        pstmt.setString(1, person.getName());
			        pstmt.setString(2, person.getSurname());
			        pstmt.setString(3, person.getUsername());
			        pstmt.setString(4, person.getPass());

			        pstmt.executeUpdate();

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
			
		} else {
			String addPerson = "INSERT INTO Employees (Name, Surname, Username, Password)"
					+ "VALUES (?, ?, ?, ?)";
			
			try (Connection conn = DriverManager.getConnection(url);
			         PreparedStatement pstmt = conn.prepareStatement(addPerson)) {
			        
					pstmt.setString(1, person.getName());
			        pstmt.setString(2, person.getSurname());
			        pstmt.setString(3, person.getUsername());
			        pstmt.setString(4, person.getPass());
	
				    pstmt.executeUpdate();

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
	}
	
	public static void writeMovie(Movie movie) {

		movies.add(movie);
		
		String addMovie = "INSERT INTO Movies (Title, ReleaseDate, Actors, Description)"
						+ "VALUES (?, ?, ?, ?)";
		
		try (Connection conn = DriverManager.getConnection(url);
		         PreparedStatement pstmt = conn.prepareStatement(addMovie)) {
		        
		        pstmt.setString(1, movie.getMovieName());
		        pstmt.setString(2, movie.getReleaseDate());
		        pstmt.setString(3, movie.getActors());
		        pstmt.setString(4, movie.getDescription());
		        pstmt.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public static boolean checkPerson(String username, String password, boolean visitor) {

		if(visitor) {
			String query = "SELECT * FROM Visitors WHERE Username = ? AND Password = ?";
			
			try (Connection conn = DriverManager.getConnection(url);
			         PreparedStatement pstmt = conn.prepareStatement(query)) {
			        
			        pstmt.setString(1, username);
			        pstmt.setString(2, password);

			        ResultSet rs = pstmt.executeQuery();

			        if (rs.next()) {
			            return true;  // Korisnik postoji
			        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
			
		} else {
			String query = "SELECT * FROM Employees WHERE Username = ? AND Password = ?";
			
			try (Connection conn = DriverManager.getConnection(url);
			         PreparedStatement pstmt = conn.prepareStatement(query)) {
			        
			        pstmt.setString(1, username);
			        pstmt.setString(2, password);

			        ResultSet rs = pstmt.executeQuery();

			        if (rs.next()) {
			            return true;  // Korisnik postoji
			        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}
		
		return false;
	}
	
    private static final String BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjMTY0NTE0MjliM2QwY2M3MjNhMDVhOWYwZDAxMmU4MyIsIm5iZiI6MTczODg0OTIwNS4zMzcsInN1YiI6IjY3YTRiYmI1MWZjYmZlZTU0ZDJmZDg4YSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.aN9RVtbf_tE1qMfmZT2MayZZYNiJdrJO1lVeDLtGJHo";
	
	private static JSONObject getData() {
		
		HttpRequest request = HttpRequest.newBuilder()
    		    .uri(URI.create("https://api.themoviedb.org/3/discover/movie"))								//ovaj URL je endpoint
    		    .header("accept", "application/json")
    		    .header("Authorization", BEARER_TOKEN)
    		    .build();
    		HttpResponse<String> response = null;
			try {
				response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
			String responseBody = response.body();

	        JSONObject jsonResponse = new JSONObject(responseBody);
	        
        return jsonResponse;
	}
	
	static ArrayList<Movie> movies = new ArrayList<Movie>();
	
	public static ArrayList<Movie> ReadMovies() {
		return movies;
	}
	
	private static int numOfMovies = 10;
	
	public static void WriteMovies_API() {
		JSONObject data = getData();
		
		JSONArray results = data.getJSONArray("results");
		for(int i = 0; i < numOfMovies; i++) {
			JSONObject movie_JSON = results.getJSONObject(i);
			String actors = getActors(movie_JSON.getInt("id"));
			Movie movie = new Movie(movie_JSON.getString("original_title"), movie_JSON.getString("release_date"), movie_JSON.getString("overview"), actors);
            writeMovie(movie);
		}
	}

	private static String getActors(int id) {
        try {
            String url = "https://api.themoviedb.org/3/movie/" + id + "/credits";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("accept", "application/json")
                    .header("Authorization", BEARER_TOKEN)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonResponse = new JSONObject(response.body());

            JSONArray castArray = jsonResponse.getJSONArray("cast");

            StringBuilder actors = new StringBuilder();
            for (int i = 0; i < Math.min(5, castArray.length()); i++) { // Uzimamo prvih 5 glumaca
                JSONObject actor = castArray.getJSONObject(i);
                actors.append(actor.getString("name"));
                if (i < Math.min(5, castArray.length()) - 1) {
                    actors.append(", ");
                }
            }
            return actors.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching actors!";
        }
    }
	
	public static int getNumOFSeats(int id) {															//zameniti sa hashMap
		
		String query = "Select NumOfSeats from CinemaHalls Where id = ?;";
		
		try (Connection conn = DriverManager.getConnection(url); 
	           PreparedStatement selectCinemaHall = conn.prepareStatement(query);){
	           
			   selectCinemaHall.setInt(1, id + 1);
			
		       ResultSet rs = selectCinemaHall.executeQuery();
		       
		       if(rs.next())
		    	   return rs.getInt("NumOFSeats");
		       
		       
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		
		return -1;
		
	}
	
	static ArrayList<Reservation> reservations = new ArrayList<Reservation>();
	
	public static void writeReservation(Reservation res) {												//upisujemo rezervaciju
		
		reservations.add(res);
		
		String addReservation = "INSERT INTO Reservations (MovieID, numOfSeat, ProjectionID, PersonID)"
						+ "VALUES (?, ?, ?, ?)";
		
		try (Connection conn = DriverManager.getConnection(url); 
		           PreparedStatement insertReservation = conn.prepareStatement(addReservation);){
		    
			for(Integer i : res.getSeats()) {						//za svako rezervisano mesto
				insertReservation.setInt(1, res.getProjection().getMovie().getId());
				insertReservation.setInt(2, i);
				insertReservation.setInt(3, res.getProjection().getProjectionID());
				insertReservation.setInt(4, res.getPerson().getId());
				insertReservation.executeUpdate();
			}
			
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public static ArrayList<Integer> readReservedSeats(int ProjectionID) {								//Cita zauzeta sedista u sali, mora da se izmeni da se pretrazuje po terminima
		
		String query = "Select group_concat(Reservations.numOfSeat) as 'Reserved' from Reservations Where ProjectionID = ?"
				+ "Group By Reservations.ProjectionID;";
		
		ArrayList<Integer> reserved = new ArrayList<Integer>();
		
		try (Connection conn = DriverManager.getConnection(url); 
	           PreparedStatement selectCinemaHall = conn.prepareStatement(query);){
	           
			   selectCinemaHall.setInt(1, ProjectionID);
			
		       ResultSet rs = selectCinemaHall.executeQuery();
		       
		       String seats = null;
		       
		       if(rs.next()) {
		    	   seats = rs.getString("Reserved");
		    	   String[] seatsSplited = seats.split(",");
			       
			       for(int i = 0; i < seatsSplited.length; i++ ) {
			    	   reserved.add(Integer.parseInt(seatsSplited[i]));
			       }
		       }
		       
		    } catch (SQLException e) {
		        e.printStackTrace();
		}
		return reserved;
	}
	
	public static ArrayList<Reservation> readReservations(int personId){								//cita rezervacije za jednu osobu
		
		ArrayList<Reservation> reservations_tmp = new ArrayList<Reservation>();
		
		for(Reservation r : reservations) {
			if(r.getPerson().getId() == personId)
				reservations_tmp.add(r);
		}
		
		return reservations_tmp;
	}
	
	private static int numOfHalls = 5;
	
	private static String[] slots = {"10:00-13:00", "13:00-16:00", "16:00-19:00", "19:00-22:00"};
	
	public static String[] getSlots() {
		return slots;
	}
	
	public static void initTimeSlots() {
		String addTime = "INSERT INTO TimeSlots (Date, CinemaHallID, Time)"
						+ "VALUES (?, ?, ?)";
		
		try (Connection conn = DriverManager.getConnection(url); 
		           PreparedStatement addTimeSlot = conn.prepareStatement(addTime);){

	        // Današnji datum
	        LocalDate today = LocalDate.now();
	        
	        LocalDate[] days = new LocalDate[5];
	        
	        days[0] = today;
	        
	        for (int i = 1; i < 5; i++) {
	            LocalDate futureDate = today.plusDays(i);
	            days[i] = futureDate;
	        }
	        for(LocalDate ld : days) {							//za svaki dan
	        	
	        	addTimeSlot.setString(1, ld.toString());
	        	int i = 0;
	        	while(i < numOfHalls) {							//za svaku salu
	        		addTimeSlot.setInt(2, i);
	        		for(String s : slots) {						//za svaki termin
	        			addTimeSlot.setString(3, s);
	        			addTimeSlot.executeUpdate();
	        		}
				    i++;
	        	}
	        }
			       
	    } catch (SQLException e) {
	        e.printStackTrace();

	    }
	}
	
	private static int numOfTimeSlots = numOfHalls * 4 * 5;		//ima 4 termina puta 5 sledecih dana
	
	public static void initProjections() {						//nasumicno dodeljujemo termine filmovima i tako pravimo projekcije
		
		ArrayList<Integer> timeSlotsID = new ArrayList<Integer>();
		
		int cnt = 0;													//brojac
		
		while(cnt < numOfTimeSlots / 2) {								//ocemo pola da popunimo
			Random random = new Random();
	        int randomMovieID = random.nextInt(numOfMovies); 			//Generišem random id nekog film
			int randomNumber = random.nextInt(numOfTimeSlots);
			while(timeSlotsID.indexOf(randomNumber) != -1) {			//da se ne desi duplikat
				randomNumber = random.nextInt(numOfTimeSlots);
			}
			timeSlotsID.add(randomNumber);
			writeProjection(movies.get(randomMovieID), randomNumber);	
			cnt++;
		}
	}
	
	public static int getTimeSlotID(String time, String date, int hallID) {															//trazi da TimeSlot po IDju
		
		String selectTimeSlot = "Select id From TimeSlots WHERE TimeSlots.Time = ? and TimeSlots.Date = ? and TimeSlots.CinemaHallID = ?;";
		
		try (Connection conn = DriverManager.getConnection(url); 
		           PreparedStatement selectTimeSlots = conn.prepareStatement(selectTimeSlot);){
			
			selectTimeSlots.setString(1, time);
			selectTimeSlots.setString(2, date);
			selectTimeSlots.setInt(3, hallID);
			
			ResultSet rs = selectTimeSlots.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	private static TimeSlot getTimeSlot(int TimeSlotID) {																				//dobija timeSlot na osnovu id

		String selectTimeSlot = "Select Time, Date, CinemaHallID From TimeSlots WHERE TimeSlots.id = ?;";
		
		try (Connection conn = DriverManager.getConnection(url); 
		           PreparedStatement selectTimeSlots = conn.prepareStatement(selectTimeSlot);){
			
			selectTimeSlots.setInt(1, TimeSlotID + 1);		// + 1 zato sto random krece od 0
			
			ResultSet rs = selectTimeSlots.executeQuery();
			
			if(rs.next()) {
				return new TimeSlot(rs.getString("Time"), rs.getString("Date"), rs.getInt("CinemaHallID"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void writeTimeSlot(String Date, String Time, int HallID) {
		
		String addTime = "INSERT INTO TimeSlots (Date, CinemaHallID, Time)"
						+ "VALUES (?, ?, ?)";
		
		try (Connection conn = DriverManager.getConnection(url); 
		           PreparedStatement addTimeSlot = conn.prepareStatement(addTime);){

        	addTimeSlot.setString(1, Date);
        	addTimeSlot.setInt(2, HallID);
        	addTimeSlot.setString(3, Time);
        	addTimeSlot.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public static ArrayList<TimeSlot> readFreeTimeSlots(){																						//vraca slobodne termine
		
		ArrayList<TimeSlot> timeSlots = new ArrayList<TimeSlot>();
		
		String selectTimeSlot = "Select Time, Date, CinemaHallID From TimeSlots;";
		
		try (Connection conn = DriverManager.getConnection(url); 
		           Statement selectTimeSlots = conn.createStatement();){
			
			ResultSet rs = selectTimeSlots.executeQuery(selectTimeSlot);
			
			while(rs.next()) {
				boolean reserved = false;
				for(Projection p : projections) {
					if(p.getDate().equals(rs.getString("Date")) && p.getTime().equals(rs.getString("Time")) && p.getHallID() == rs.getInt("CinemaHallID"))
						reserved = true;
				}
				if(reserved)
					continue;
				timeSlots.add(new TimeSlot(rs.getString("Time"), rs.getString("Date"), rs.getInt("CinemaHallID")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return timeSlots;
		
	}
	
	private static ArrayList<Projection> projections = new ArrayList<Projection>();
	
	public static void writeProjection(Movie movie,  String time, String Date, int hallID) {													//upisuje jednu projekciju u bazu podataka i u ArrayList, preklopljeno sa podacima
		
		int timeSlotId = getTimeSlotID(time, Date, hallID);
		
		String addProjectionquery = "INSERT INTO Projections (MovieID, TimeSlotID)"
				  					+"VALUES (?, ?);";
		
		try (Connection conn = DriverManager.getConnection(url); 
		           PreparedStatement addProjection = conn.prepareStatement(addProjectionquery);){
			
			addProjection.setInt(1, movie.getId());
			addProjection.setInt(2, timeSlotId);
			
			addProjection.executeUpdate();
			
			projections.add(new Projection(movie, time, Date, hallID));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void writeProjection(Movie movie,  int TimeSlotID) {																			//upisuje jednu projekciju u bazu podataka i u ArrayList, preklopljeno sa TimeSlotID
		
		TimeSlot timeSlot = getTimeSlot(TimeSlotID);
		
		String addProjectionquery = "INSERT INTO Projections (MovieID, TimeSlotID)"
				  					+"VALUES (?, ?);";
		
		try (Connection conn = DriverManager.getConnection(url); 
		           PreparedStatement addProjection = conn.prepareStatement(addProjectionquery);){
			
			addProjection.setInt(1, movie.getId());
			addProjection.setInt(2, TimeSlotID);
			
			addProjection.executeUpdate();
			
			projections.add(new Projection(movie, timeSlot.getTime(), timeSlot.getDate(), timeSlot.getCinemaHallId()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Projection> getProjections(){							//vraca sve projekcije
		return projections;
	}

	public static ArrayList<Projection> getSpecificProjections(int MovieID) {		//vraca projekcije za specifican film

		ArrayList<Projection> projections_tmp = new ArrayList<Projection>();
		
		for(Projection p : projections) {
			if(p.getMovie().getId() == MovieID)
				projections_tmp.add(p);
		}
		
		return projections_tmp;
	}
	
	public static boolean CheckTimeSlot(String Date, String time,int hallID) {
		
		String selectTimeSlot = "SELECT Time, Date, CinemaHallID FROM TimeSlots WHERE Time = ? AND Date = ? AND CinemaHallID = ?;";

		try (Connection conn = DriverManager.getConnection(url); 
		           PreparedStatement addProjection = conn.prepareStatement(selectTimeSlot);){
			
			addProjection.setString(1, time);
			addProjection.setString(2, Date);
			addProjection.setInt(3, hallID);
			
			ResultSet rs = addProjection.executeQuery();
			
			if(rs.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
}