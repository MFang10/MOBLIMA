package cathay;
import java.util.*;


public class Cinema {

	private int cinemaId = -1;
	private int cineplexId = -1;
	private String cinemaClass = null;
	LinkedList<Movie> movieList = new LinkedList<Movie>();
	LinkedList<Layout> layoutList = new LinkedList<Layout>();


	/* Constructor */

	public Cinema(int cinemaId, int cineplexId, String cineClass) {
		this.cinemaId = cinemaId;
		this.cineplexId = cineplexId;
		this.cinemaClass = cineClass;
	}



	/* add movie to the list and associate it with a clean layout */

	public void addMovie(String title, int date, int time) {
		for(int i =0; i<movieList.size(); i++) {
			if((movieList.get(i).getTitle() == title) && (movieList.get(i).getDate() == date) && (movieList.get(i).getTime() == time)) {
				System.out.println(">>>> This movie item is already in Cinema " + cinemaId);
				return;
			}
		}

		Movie mv = new Movie(title, date, time, cinemaId); // assume staff only enters existing movies, Movie can handle the file not found error.
		movieList.add(mv);
		System.out.println(">>>> " + title + " has been added");
		int mvIndex = movieList.size() - 1;

		Layout ly = new Layout(cinemaClass);
		layoutList.add(mvIndex, ly);
	}



	/* Delete a movie from the movie list. Check for existence on the movie list first. The corresponding layout list will be deleted as well */

	public void deleteMovie(String title) {
		for(int i =0; i<movieList.size(); i++) {
			if(movieList.get(i).getTitle() == title) {
				movieList.remove(i);
				layoutList.remove(i);
				System.out.println(">>>> The Movie " + title + " has been removed from Cinema " + cinemaId);
				return;
			}
		}

		System.out.println("This movie does not exist in the current movie list");
	}



	/* Print the following information of a movie in the cinema: info in data base, show time in that cinema, associated layout */

	public void printOneMovieInfo(String title) {
		int i = 0;
		System.out.println("In Cinema" + cinemaId);
		for(i = 0; i <= movieList.size(); i++) {
			if(movieList.get(i).getTitle() == title) {
				break;
			}
		}
		movieList.get(i).getMovieInfo(title);
	}




    /* Print all the movies available in that cinema, including their show times */

	public void printAllMovieInfo() {

		if(movieList.size()!= 0) {
			System.out.println("Current movies in Cinema" + cinemaId + ":");
			for(int i = 1; i <= movieList.size(); i++) {
				System.out.print( i + ". ");
				System.out.print(movieList.get(i-1).getTitle() + " ");
				System.out.print(movieList.get(i-1).getStatus() + " ");
				System.out.print(movieList.get(i-1).getDate() + " ");
				int timeInt = movieList.get(i-1).getTime();
				String printTime;
				if (timeInt < 10)
					printTime = "000" + Integer.toString(timeInt);
				else if (timeInt <100)
					printTime = "00" + Integer.toString(timeInt);
				else if (timeInt <1000)
					printTime = "0" + Integer.toString(timeInt);
				else 
					printTime = Integer.toString(timeInt);
				System.out.println(printTime);
			}
		}
		else System.out.println("There is currently no movie in Cinema " + cinemaId);
	}

	/*Pass in the new cineplex id. However, the use of this method is not encouraged */

	public void setCineplexId(int newId) {       // to be used by the cineplex class
		cineplexId = newId;
	}


	/* used to set the class of a cinema*/

	public void setClass(String newClass) {
		cinemaClass = newClass;
	}


	/* Get the attributes */

	public int getCinemaId() { return cinemaId;}


	public int getCineplexId() {return cineplexId;}


	public String getMovieClass() {return cinemaClass;}


	/* get a movie's corresponding layout. verified with title and showtimes */

	public void getLayout(String title, int date, int time) {
		for(int i =0; i<movieList.size(); i++) {
			if((movieList.get(i).getTitle() == title) && (movieList.get(i).getDate() == date) && (movieList.get(i).getTime() == time)) {
				layoutList.get(i).getLayout();
			}
		}
	}
}