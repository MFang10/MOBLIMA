package cathay;
import java.util.*;

public class MovieManager {
	

    static HashMap<Integer, String> mMap = null;
    MovieDataBase mdb = null;
    FileEditor fe = null;
    Cinema cn = null;
    private static int key = 0; // used to assign key to new entries on the movie list

    /**
     * This is a Constructor.
     * A Hashmap is implemented in here.
     * Only movies that are in "Preview" or "Now Showing" status will be passed into the hashmap.
     */
    public MovieManager() {
    	mMap = new HashMap<Integer, String>();  // Implement a movie map here
    	fe = new FileEditor();
    	key = 1; 
    	this.loadToMap();              
    }
    
    /**
     *  This method is to load movie from data base to movieMap. 
     *  Only those in "Preview" or "Now Showing" status will be loaded 
     */
    public void loadToMap() {
   	List<String> allMovies = null;
    	System.out.println("Loading available movies...");
    	allMovies = fe.fileToList("AllMovies.txt");
    allMovies.removeAll(Arrays.asList(null," ")); 
    	for(int i = 0; i < allMovies.size(); i++) {
    		mdb = new MovieDataBase(allMovies.get(i));
    		if((mdb.getStatus()!=null)&&((mdb.getStatus().equals("Preview")) || (mdb.getStatus().equals("Now Showing")))) {
    			mMap.put(key, allMovies.get(i));
        		key++;
    		}	
    	}
    	
    }
    


	/**
	 * @param title the exact movie title that is to be added by the staff.
	 * This method adds movie from database to movieMap. 
	 * Movies with status "Coming soon" or "End of Showing" will not be added.
	 */
	public void addAvailableMovie(String title) {   //input of an exact title is very important here
		
		mdb = new MovieDataBase(title); 
		
		if(this.existenceInListing(title)) {
			System.out.println("This movie already exists in the listing.");
			
		}
		
		else if((mdb.getStatus().equals("Coming soon"))||(mdb.getStatus().equals("End of Showing"))) {
			System.out.println("This movie cannot be added as it is in neither preview nor showing status. Exiting...");
		}
		else {
			mMap.put(key, title);
			System.out.println("Movie added to the listing.");
		}
		
	}
	
	/**
	 * @param id	:	movieID
	 *  Remove a movie from listing. Check for its existence on the movie listing first
	 */
	public void removeFromListing(int id) {
		if(mMap.containsKey(id)) {

			System.out.println("Removing " + mMap.get(id));
			mMap.remove(id);
			System.out.println("id "+ id + " removed");
		}
		else
			System.out.println("This movie is not in the movie listing. Please check again.");
		
	}
	
    /**
     * @param title	:  Movie title to be searched about its existence in the listing
     * @return	boolean true if the title exists in movieListing, false if the title does not exist in the movieListing.
     */
    public boolean existenceInListing(String title) {
    	return mMap.containsValue(title);
    }
    


    /* Add a movie to database and update AllMovies.txt, Sales.txt, Ratings.txt. This method should be followed by initDBEntry*/
    
	public boolean addToDB(String title) {  //***************************************return type changed 16/11/17********************************
		boolean done = false;
		mdb = new MovieDataBase(title);
		done = mdb.addFile();
		if(done) System.out.println("Entry added to data base");
		else {
			System.out.println("Failed to add entry");
			return false;
		}
		fe.writeFile("AllMovies.txt", title, false);
		fe.writeFile("Sales.txt", title + ": " + 0.0 , false);   // update in ratings and sales
		fe.writeFile("Ratings.txt", title + ": " + 0.0 , false);
		return true;
	}
	
	
	
	/* Initialise a movie database file. */
	
	public void initDBEntry(String title, String director, String casts, String type, String status, String synopsis, String contentRating) {
		mdb = new MovieDataBase(title);
		if((status=="Preview") || (status=="Now Showing")) {  //update the movie listing as well
			
			mMap.put(key, title);
    		key++;
    		//fe.writeFile("Sales.txt", title + ": " + 0.0, false); // update in ratings and sales
    		//fe.writeFile("Ratings.txt", title + ": " + 0.0, false);//********************* changes: use 0.0 instead of 0 ****************************
		}
		
		mdb.setTitle(title);
		mdb.setDirector(director);
		mdb.setCasts(casts);
		mdb.setType(type);
		mdb.setStatus(status);
		mdb.setSynopsis(synopsis);
		mdb.setContentRating(contentRating);
		mdb.setMovieRating(0.0);
		mdb.setNumVotes(0);	
	}
		
	

    /* update status: update movielist and movie objects; movies objects will be updated in the higher level module */
	/* if the movie is "Preview" or "Now Showing", update the movie listing as well */
	
	public void setStatus(String title, String status) {

		mdb = new MovieDataBase(title);
		mdb.setStatus(status); //update in the data base
		if((status.equals("Preview")) || (status.equals("Now Showing"))) {  // update movie listing
			if(!this.existenceInListing(title)) {
				//mMap.put(key, title);                         //****************************changes made 16/11/17 ***********************************8
				//key++;
				mMap.clear();
				key =1;
				System.out.println("New Listing: ");
				this.loadToMap();
				this.printListing();
			}

		}
	}
	
	
	
	/*update rating: update number of votes as well as rating.txt*/
	
	public void updateRating(int id, double input) {
		String title = mMap.get(id);
		mdb = new MovieDataBase(title);
		mdb.updateMovieRating(input);
		double newRating = mdb.getMovieRating();
		fe.update("Ratings.txt", title, title + ": " + newRating, true);
	}
	
	
	
	/* Add review into the data base */
	
	public void addReview(int id, String input, String goerName) {
		String title = mMap.get(id);
		mdb = new MovieDataBase(title);
		mdb.addReview(input, goerName);
	}
	
	
	
	/* show movielisting: show movieMap */ 

	public void printListing() {
		System.out.println("------------------");
		System.out.println("MovieId   Title");
		
		for (Map.Entry<Integer, String> entry : mMap.entrySet()) {
			Integer id = entry.getKey();
			String mv = entry.getValue();
		    System.out.println(id + "         " + mv);
		}
		System.out.println("------------------");
	}

	
	/*getting movie info from database. this method might not be necessary*/
	
	public void getStatInfo(String title) {
		mdb = new MovieDataBase(title);
		mdb.getMovieStatInfo();
	}
	
	/* Sort all movies by sale using insertion sort */
	
	public void sortBySale() {
		List<String> saleList = new LinkedList<String>();
		saleList = fe.fileToList("Sales.txt");
		saleList.removeAll(Arrays.asList(null," "));  

		for(int i = 0; i<saleList.size();i++) {
            if(!saleList.get(i).contains(": ")) saleList.remove(i);
			for(int j =i; j>0;j--) {
				String line = saleList.get(j);
				double sale = Double.parseDouble(line.substring(line.indexOf(": ")+2));
				String line2 = saleList.get(j-1);
				double sale2 = Double.parseDouble(line2.substring(line2.indexOf(": ")+2));
				
				if(sale>sale2) {
					saleList.remove(line);
					saleList.add(j-1, line); 
				}
			}
			
		}
		
		System.out.println("Top 5 movies by Sale:");
		if(saleList.size()>=5) {
			for(int j = 0;j < 5; j++) {
				System.out.println(saleList.get(j));
			}
		}
		else {
			for(int j = 0;j < saleList.size(); j++) {
				System.out.println(saleList.get(j));
			}
		}
	}
	
	
	
	/* Sort all movies by rating using insertion sort*/
	
	public void sortByRating() {
		List<String> rateList = new LinkedList<String>();
		rateList = fe.fileToList("Ratings.txt");
		rateList.removeAll(Arrays.asList(null," ")); 
		for(int i = 0; i<rateList.size();i++) {
			if(!rateList.get(i).contains(": ")) rateList.remove(i);
			for(int j =i; j>0;j--) {
				String line = rateList.get(j);
				double rate = Double.parseDouble(line.substring(line.indexOf(": ")+2));
				String line2 = rateList.get(j-1);
				double rate2 = Double.parseDouble(line2.substring(line2.indexOf(": ")+2));

				if(rate>rate2) {
					rateList.remove(line);
					rateList.add(j-1, line); 
				}
			}
			
		}
		
		System.out.println("Top 5 movies by Rating:");
		if(rateList.size()>=5) {
			for(int j = 0;j < 5; j++) {
				System.out.println(rateList.get(j));
			}
		}
		else {
			for(int j = 0;j < rateList.size(); j++) {
				System.out.println(rateList.get(j));
			}
		}
	}
}

