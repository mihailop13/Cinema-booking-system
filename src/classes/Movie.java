package classes;

public class Movie {									//klasa cuva podatke o filmu

	private int ID;
	private static int id = 1;
	private String MovieName;
	private String Actors;
	private String releaseDate;
	private String description;
	
	public Movie(String MovieName, String releaseDate, String desc, String Actors) {
		
		ID = this.id++;
        this.MovieName = MovieName;
        this.Actors = Actors;
        this.releaseDate = releaseDate;
        this.description = desc;
	}

	public int getId() {
		return ID;
	}

	
	public String getMovieName() {
		return MovieName;
	}


	public String getActors() {
		return Actors;
	}


	public String getReleaseDate() {
		return releaseDate;
	}


	public String getDescription() {
		return description.substring(0, description.length()/2) + "\n" + description.substring(description.length()/2 + 1, description.length());
	}
	
	/*@Override
	public boolean equals(Object obj) {
		
		if(!(obj instanceof Movie))
			return false;
		Movie movieTmp = (Movie) obj;
		if(MovieName.equals(movieTmp.getMovieName()))
			return true;
		
		return false;
	}*/
	
}
