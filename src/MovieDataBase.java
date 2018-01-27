package cathay;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/* This class is used as a tool for operations on the movie DB files */

public class MovieDataBase {
	Scanner sc = new Scanner(System.in);
	static FileEditor fe = new FileEditor();	
	File file = null;
	String fileName = null;
	static HashMap<Integer, String> mdbMap = null; // HashMap for movie database.
	List<String> movieDataBaseList = null;

	/* Constructor */
	
	public MovieDataBase(String title) {
		fileName = title + "_intro.txt";
		file = new File(fileName);	
		mdbMap = new HashMap<Integer, String>();  // Implement a movie map here
		fe = new FileEditor();
		movieDataBaseList = fe.fileToList("AllMovies.txt");
		movieDataBaseList.removeAll(Arrays.asList(null," ")); 
		int key = 1; 
		for(int i = 0; i < movieDataBaseList.size(); i++) {        		
    			mdbMap.put(key, movieDataBaseList.get(i));
        		key++;
    		}	
	}
	
	
	
	/*Read all the static movie infomation (infomation particular to a movie such as director but not showtimes) stored in corresponding files*/
	
	public void getMovieStatInfo() {fe.readFile(fileName);}   
	
	public void printMovieDataBase() {
		System.out.println("------------------");
		System.out.println("MovieId   Title");
		if (mdbMap==null) {
			System.out.println("------------------");
			return;
		}
		for (int key : mdbMap.keySet()) {
		    System.out.println(key + "         " + mdbMap.get(key));
		}
		System.out.println("------------------");
	}
	
	/* Check movie file existence*/
	
	public boolean fileExists() {
		return file.exists();
	}
	
	
	
	/* Add a movie file to the data base, check file existence first. Initialise the file with basic headings. */
	public boolean addFile() {
        boolean found = this.fileExists();
		if(found) {
			System.out.println("This entry already exists");
			return false;
		}
		
	    boolean success = fe.writeFile(fileName, "Title: ", false) && fe.writeFile(fileName, "Director: ", false) && fe.writeFile(fileName, "Casts: ", false) && fe.writeFile(fileName, "Type: ", false) && fe.writeFile(fileName, "Status: ", false) && fe.writeFile(fileName, "Synopsis: ", false) && fe.writeFile(fileName, "Content Rating: ", false) && fe.writeFile(fileName, "Movie Rating: ", false) && fe.writeFile(fileName, "Number of Votes: ", false) && fe.writeFile(fileName, "Reviews: ", false);
	    if(success) 
	    		System.out.println(fileName + " created");

	    else System.out.println("Failed to create " + fileName + " Please try again.");
	    return success;
	}
	
   
	
	
	/* Update movie static info */
	
	public boolean setTitle(String newTitle) {
		return fe.update(fileName, "Title:", "Title: " + newTitle, true);
	}

	
	public void setDirector(String newDirector) {
		if(fe.update(fileName, "Director:", "Director: " + newDirector, true))
			System.out.println("Director updated in data base.");
		else
			System.out.println("Update in data base failed");
	}
	
	
	public void setCasts(String newCasts) {
		if(fe.update(fileName, "Casts:", "Casts: " + newCasts, true))
			System.out.println("Casts updated in data base.");
		else
			System.out.println("Update in data base failed");
	}
	
	
	public void setStatus(String newStatus) {
		if(fe.update(fileName, "Status: ", "Status: " + newStatus, true))
			System.out.println("Status updated in data base.");
		else
			System.out.println("Update in data base failed");
	}
	
	
	public void setSynopsis(String newSynopsis) {
		if(fe.update(fileName, "Synopsis:", "Synopsis: " + newSynopsis, true))
			System.out.println("Synopsis updated in data base.");
		else
			System.out.println("Update in data base failed");
	}
	
	
	public void setType(String newType) {
		if(fe.update(fileName, "Type:", "Type: " + newType, true))
			System.out.println("Movie type updated in data base.");
		else
			System.out.println("Update in data base failed");
	}
	
	
	public void setContentRating(String newContentRating) {
		if(fe.update(fileName, "Content Rating:", "Content Rating: " + newContentRating, true))
			System.out.println("Content rating updated in data base.");
		else
			System.out.println("Update in data base failed");
	}


	public void setMovieRating(double newMovieRating) {                  // Use of this method is not encouraged
		if( fe.update(fileName, "Movie Rating", "Movie Rating: " + newMovieRating, true))
			System.out.println("Movie rating updated in data base.");
		else
			System.out.println("Update in data base failed");
	}
	
	
	public void setNumVotes(int newNumVotes) {
		if(fe.update(fileName, "Number of Votes", "Number of Votes: " + newNumVotes, true))
			System.out.println("Number of votes updated in data base.");
		else
			System.out.println("Update in data base failed");
	}
	
	
	
	
	/* Update the rating and number of votes concurrently*/
	
	public void updateMovieRating(double newInput) {
		double currRating = this.getMovieRating();
		int currNumVotes = this.getNumVotes();
		System.out.println("Current NumVotes: " + currNumVotes);
		double newRating = (currRating * (double)currNumVotes + newInput) / (double)(++currNumVotes);
		this.setNumVotes(++currNumVotes);
		this.setMovieRating(newRating);
	}
	
	
	/*Update review: append new review to the end of the data base file since review is the last item in the file */
	
	public void addReview(String newReview, String goerName) {
		boolean success = fe.writeFile(fileName, goerName + "'s review: " + newReview, false);
		if(success) System.out.println("Your review has been added. Thank you!");
		else {
			System.out.println("Unable to add review. Sorry.");
		}
		
	}
		


	/* Get movie information from data base files */
	public String getTitleFromAllMovies(int i) {
		return mdbMap.get(i);
	}
	
	public String getTitle() {
		return fe.extract(fileName, "Title: ", true);
	}
 
	
	public String getDirector() {
		return fe.extract(fileName, "Director: ", true);
	}
	
	
	public String getCasts() {
		return fe.extract(fileName, "Casts: ", true);
	}
	
	
	public String getSynopsis() {                          //Problem: Synopsis can only be a single paragraph
		return fe.extract(fileName, "Synopsis: ", true);
	}
	
	
	public String getType() {
		return fe.extract(fileName, "Type: ", true);
	}
	
	
	public String getStatus() {
		return fe.extract(fileName, "Status: ", true);
	}
	
	
	public String getContentRating() {
		return fe.extract(fileName, "Content Rating: ", true);
	}
	
	
	public double getMovieRating() {
		System.out.println(fe.extract(fileName, "Movie Rating: ", true));
		double rate = Double.parseDouble(fe.extract(fileName, "Movie Rating: ", true));
		return rate;
	}
	
	
	public int getNumVotes() {
		int num = Integer.parseInt(fe.extract(fileName, "Number of Votes: ", true));
		return  num;
	}
	
	
	public String getFilename() {
		return fileName;
	}
	
}