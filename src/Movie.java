package cathay;

public class Movie {
	
	/* attributes */
	
	private String title = null;
	private int movieCode = -1; 
	private int date = 0; //in YYYYMMDD format
	private int time = 0; //in HHMM format
	
	FileEditor fe = new FileEditor();
	String fileName = null;
	MovieDataBase mdb = null;   // for getting information from the data base
	
	
	
	/* Constructor */
	
	public Movie(String title, int date, int time, int movieId) {	// only for existing movie files
		
		fileName = title + "_intro.txt";
		this.title = title;
		this.date = date;
		this.time = time;
        this.movieCode = movieId;
		mdb = new MovieDataBase(title);

	}
	
	
	
	
	/*Get the basic info stored in the data base and the showtime set by the admin*/
	
	public void getMovieInfo(String title) {
		fe.readFile(title + "_intro.txt");
		System.out.println("Show date: " + date);
        System.out.println("Showtime: " + time);
		}       
	
	
	
	/* Update show time */

	public void setDate(int newDate) {
		this.date = newDate;
		System.out.println(">>>> The Movie " + title + " has been set to " + time + " on " + date);	
	}
	
	public void setTime(int newTime) {
		this.time = newTime;
		System.out.println(">>>> The Movie " + title + " has been set to " + time + " on " + date);
	}

	
    /* Get values of attributes */	
	
	public String getTitle() {   // may be redundant
		return title;
	}
	
	public int getMovieCode() {
		return movieCode;
	}
	

	public String getType() {
		return mdb.getType();
	}
	
	public int getDate() {
		return date;
	}
	
	public int getTime() {
		return time;
	}

	
	public String getStatus() {
		return mdb.getStatus();
	}
	
}