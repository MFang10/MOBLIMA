package cathay;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;


public class MoblimaBooking {
	Scanner sc = new Scanner(System.in);
	ComplexManager cm = new ComplexManager();   // of 3 complexes
	MovieManager mm = new MovieManager();
	Staff staff = new Staff();
	Booking bk = null;
	HolidayScheme hs = null;
	boolean staff1 = false;	// For running staff section.
	boolean customer =!staff1;	// For running customer section.
	boolean flag1 = true;
	boolean flag2 = true;
	boolean flag3 = true;
	boolean flag4 = true;
	String dummy;	// use to clear buffer after sc.nextInt()
	MoblimaBooking(){}
	public void run() {
		for(int i = 0; i<3;i++) {
			cm.cp[i].initComplex("Normal");	// cp = 3 cinemas
		}

		int initChoice = 0;	// initialization
		
		while (initChoice!=3) {	
			System.out.println("================================================");
			System.out.println(" Welcome to Cathay MOBLIMA");
			System.out.println("================================================");
			System.out.println("Options: ");
			System.out.println("1. Log in as a staff.");
			System.out.println("2. Browse as a customer.");
			System.out.println("3. Quit.");
			System.out.println(">>>> Enter your choice (1-3): ");
			
			/* Get a valid input*/
			/* 1. Log in as a staff; 2. Browse as a customer; 3. Quit.*/
			initChoice = getValidChoice(initChoice,1,3);
			
			if(initChoice == 1) 
				staff1 = staff.userLogIn(); //	verify staff username & password.
			else
				staff1 = false;	// fail to enter the staff section.
			
			customer =!staff1;	//	used to enter the customer section.

			if(staff1) {
				flag1 =true;
				while(flag1) {
					System.out.println();
					System.out.println();
					System.out.println("================================================");
					System.out.println("Welcome, admin!");
					System.out.println("================================================");
					System.out.println("Configuration options: ");
					System.out.println("1. Configure Movie Listing");
					System.out.println("2. Configure cinema settings"); 
					System.out.println("3. Configure Prices");
					System.out.println("4. Configure Holidays");
					System.out.println("5. Quit");

					System.out.println(">>>> Enter your choice (1-5): ");
					
					int configChoice = 0;
					// get a valid input.
					configChoice = getValidChoice(configChoice,1,5);
	
					switch(configChoice){
						case 1: System.out.println("Entering movie listing configuration...");
						        flag2 = true;
						        while(flag2) {
						        	System.out.println("================================================");
						        	System.out.println("Movie Listing configuration options: ");
									System.out.println("1. Show Movie Listing");
									System.out.println("2. Remove item from Movie Listing"); // always print the updated listing
									System.out.println("3. Add item to Movie Listing");
									System.out.println("4. Edit details of a Movie"); // check existence in the listing first
									System.out.println("5. Show movie database");// add and init																		
									System.out.println("6. Add a movie to the database");// add and init									
									System.out.println("7. List the top 5 movies");
									System.out.println("8. Back");
	                 
									System.out.println(">>>> Enter your choice (1-8): ");

									int mListConfigChoice = 0;
									mListConfigChoice = getValidChoice(mListConfigChoice,1,8);								
									
									switch(mListConfigChoice) {
									// 1. Show Movie Listing				
									 
									case 1: mm.printListing();
									        flag2 = true;
									        break;
									        
									/* 2. remove from listing, set status as "End of Showing, 
									 * change cinema lists, reload new listing and print        
									 */
									case 2: 		    
									        mm.printListing();
									        int idRemove = 0;
									        
									        //	scan a valid choice.
									        do {
									        		System.out.println(">>>> Enter the corresponding movie ID:");
											    try {
											    		idRemove = sc.nextInt();
											    } catch (InputMismatchException e) {
											    }
											    if (idRemove <1) {
											    	System.out.println("Invalid choice, please try again.");
											    }
											    dummy = sc.nextLine(); // clear buffer
									        } while (idRemove<1);
									        	
									        String title = MovieManager.mMap.get(idRemove);
									        if(!mm.existenceInListing(title)) System.out.println("This movie does not exist in the listing");
									        
									        else 
									        {											    
											    System.out.println("Setting new status");
											    mm.setStatus(title, "End of Showing");

											    for(int i = 0; i<3;i++) {
													for(int j =0; j< 3; j++) 
													{
														//System.out.println("the movieList size"+cm.cp[i].cn[j].movieList.size());
														for(int k =0; k< cm.cp[i].cn[j].movieList.size(); k++)
															if(cm.cp[i].cn[j].movieList.get(k).getTitle() == title) {
																cm.cp[i].cn[j].movieList.remove(k);
														}
													}
											    }
											    mm.removeFromListing(idRemove);
											    //mm.loadToMap();
											    System.out.println("The new movie listing: ");
											    mm.printListing();
									        }

										    flag2 = true;
										    break;
										    	
									case 3: 
											mm.mdb.printMovieDataBase();
											System.out.println(">>>> Enter the corresponding MovieId of the movie:");
											int movieChoice = sc.nextInt();
																					   		   
									        String movieToAdd = mm.mdb.getTitleFromAllMovies(movieChoice);
									        if(!mm.fe.checkExistence(movieToAdd + "_intro.txt")) {
									        	System.out.println("This movie does not exist in the data base. Please create the data base entry");
									        }
									        else{
										        mm.addAvailableMovie(movieToAdd);
												System.out.println("The new movie listing: ");
												mm.printListing();
									        }	
									        
										    flag2 = true;
										    break;
										    
									case 4: //print all movies first
										
										
											mm.mdb.printMovieDataBase();
											System.out.println("Please enter the MovieId of the movie:");
											int movieEdit = sc.nextInt();																					   		   
										    String editTitle = mm.mdb.getTitleFromAllMovies(movieEdit);							        

									        if(!mm.fe.checkExistence(editTitle + "_intro.txt")) {
									        	System.out.println("This movie does not exist in the data base. Please create the data base entry");
									        }else {
									        	flag3 = true;
										        while(flag3) {
											        System.out.println("================================================");
										        	System.out.println("Update options for the movie: " + editTitle);
											        System.out.println("1. Update director");
											        System.out.println("2. Update casts");
											        System.out.println("3. Update status");
											        System.out.println("4. Update synopsis");
											        System.out.println("5. Update type");
											        System.out.println("6. Update content rating");
											        System.out.println("7. Back");
											        System.out.println(">>>> Enter your choice (1-7)");
											           
											        int editChoice = 0;
											        editChoice = getValidChoice(editChoice,1,7);
											        
											        switch(editChoice) {
											        case 1: System.out.println("Enter the new director name: "); 
											        	    String newDirector = sc.nextLine();
											        	    mm.mdb = new MovieDataBase(editTitle);
											        	    mm.mdb.setDirector(newDirector);
											        	    flag3 = true;
											                break;
											                
											        case 2: System.out.println("Enter all the casts name: ");
											        
									        	            String newCasts = sc.nextLine();
									        	            mm.mdb = new MovieDataBase(editTitle);
									        	            mm.mdb.setDirector(newCasts);
									        	            flag3 = true;
											        	    break;
											        	    
											        case 3: System.out.println("Choose a new status: ");
									        	            	   System.out.println("1. Coming soon");
									                        System.out.println("2. Preview");
									                        System.out.println("3. Now Showing");
									                        System.out.println("4. End of Showing");
				
									                        System.out.println(">>>> Enter the new Status (1-4): "); 
															
															int newStatus = 0;
															newStatus = getValidChoice(newStatus,1,4);															
															        	 					        	                            
					        	                            switch(newStatus) {
					        	                            case 1: mm.setStatus(editTitle, "Coming Soon"); 
					        	                            
														            for(int i = 0; i<3;i++) {
																        for(int j =0; j< 3; j++) {
																	        for(int k =0; k< cm.cp[i].cn[j].movieList.size(); k++)
																	           if(cm.cp[i].cn[j].movieList.get(k).getTitle() == editTitle) {
																		        cm.cp[i].cn[j].movieList.remove(k);
																	           }
																        }
														             }
														            
														             
					        	                                     if(mm.existenceInListing(editTitle)) {
					        	                                    	 //mm.printListing();
					        	                                    	 System.out.println("Please enter exact movie code for updating the movie listing: ");
					        	                                    	 int updateId = sc.nextInt();
					        	                                    	 mm.removeFromListing(updateId);
					        	                                     }
					        	                                    	 
														            
					        	                                    break;
					        	                                    
					        	                            case 2: mm.setStatus(editTitle, "Preview");
			        	                                            break;
			        	                                            
					        	                            case 3: mm.setStatus(editTitle, "Now Showing"); 
			        	                                            break;
			        	                                            
					        	                            case 4: mm.setStatus(editTitle, "End of Showing"); 

					        	                                    for(int i = 0; i<3;i++) {
														                for(int j =0; j< 3; j++) {
															                for(int k =0; k< cm.cp[i].cn[j].movieList.size(); k++)
															                   if(cm.cp[i].cn[j].movieList.get(k).getTitle().equals(editTitle)) {
															                	   
																                   cm.cp[i].cn[j].movieList.remove(k);
															                   }
														                 }
												                     }
					        	                                    if(mm.existenceInListing(editTitle)) {
					        	                                    	 //mm.printListing();
					        	                                    	 System.out.println("Please enter exact movie code for updating the movie listing: ");
					        	                                    	 int updateId = sc.nextInt();
					        	                                    	 
					        	                                    	 dummy=sc.nextLine();
					        	                                    	
					        	                                    	 mm.removeFromListing(updateId);
					        	                                     }
					        	                                    break;
					        	                                    
			        	                                    default: System.out.println("Invalid input. Please try again");
			        	                                             break;
					        	                            }
											     
					        	                            flag3 = true;
							        	                    break;
							        	                    
											        case 4: System.out.println("Enter the new synopsis: "); 
														    String newSyn = sc.nextLine();
						        	                             mm.mdb = new MovieDataBase(editTitle);
						        	                             mm.mdb.setSynopsis(newSyn);
												        	    flag3 = true;
						        	                             break;															        	                            
					        	                            
											        case 5: String newType = getMovieType();			        	                                    
				        	                                    mm.mdb = new MovieDataBase(editTitle);
				        	                                    mm.mdb.setType(newType);
										        	            flag3 = true;
				        	                                    break;												        	
			        	                                    
											        case 6: 	String contentRating = getContentRating();												        		
				        	                                     mm.mdb = new MovieDataBase(editTitle);
				        	                                     mm.mdb.setContentRating(contentRating);
										        	            flag3 = true;
				        	                                     break;															        											        			
			        	                                    
											        case 7: flag3 = false;
											                break;
											                
											        default: System.out.println("Invalid choice. Please try again");
											                 flag3 = true;
											                 break;
											        }
											  
										        }
									        }

										    flag2 = true;
										    break;
										    
									case 5:	mm.mdb.printMovieDataBase();
							        			flag2 = true;
							        			break;
										    
									case 6: System.out.println("Creation of new files in the data base");         
									        System.out.println("Please enter the exact title of the movie");
									        String newDBEntry = sc.nextLine();
									        
									        if(mm.addToDB(newDBEntry)){
										        System.out.println("================================================");
										        System.out.println("Starting initialization..");
										        System.out.println("Enter NA when there is no data for an entry");
										        System.out.println("Enter the director: ");
										        String director = sc.nextLine();
										        System.out.println("Enter the casts: ");
										        String casts = sc.nextLine();
										        
												// until a valid input is obtained.
										        System.out.println("Choose a new status: ");
						        	            		System.out.println("1. Coming Soon");
						        	            		System.out.println("2. Preview");
						                        System.out.println("3. Now Showing");
						                        System.out.println("4. End of Showing");							                        				
						                        System.out.println(">>>> Enter the new status (1-4): ");
						                        
						                        int newStatus = 0; // new content rating
						                        newStatus = getValidChoice(newStatus,1,4);																									
								        	   
												String status = null;		
												switch(newStatus) {
												case 1:	
													status = "Coming Soon";
													break;
												case 2:
													status = "Preview";
													break;
												case 3:
													status = "Now Showing";
													break;
												case 4:
													status = "End of Showing";
													break;																									
												}
											
										        System.out.println("Enter the synopsis: ");
										        String synopsis = sc.nextLine();
										        String type = getMovieType();	
										        String contentRating = getContentRating();
											
										        mm.initDBEntry(newDBEntry, director, casts, type, status, synopsis, contentRating);
										        if((status.equals("Preview")) || (status.equals("Now Showing"))) mm.setStatus(newDBEntry, status);
									        }

										    flag2 = true;
										    break;
										    
									case 7:
									        listTopMovies();
										    flag2 = true;
									        break;
									        
									case 8: flag2 = false;
									        break;
									        
									default: System.out.println("Please enter a valid choice.");
							                 flag2 = true;
								             break;
									}
						        	
						        }

							    break;
						case 2: System.out.println("Entering cinema configuration...");
						        while(true) {
						        	
						        	System.out.println("Which complex (1-3) would you like to configure? Enter 0 to exit");
						        	int cpChoice = sc.nextInt();
						        	if(cpChoice == 0) break;
						        	else if ((cpChoice>3) || (cpChoice<1)) {
						        		System.out.println("Invalid input. Please try again.");
						        		break;
						        	}
						        	System.out.println("Which cinema ("+ (cpChoice*3-2) + "-" + cpChoice*3 + ")" + "would you like to configure? Enter 0 to exit" );
						        	int cineChoice = sc.nextInt();
						        	if(cineChoice == 0 ) break;
						        	else if((cineChoice < cpChoice*3 -2 ) || (cineChoice > cpChoice*3)) {
						        		System.out.println("Invalid input. Please try again.");
						        		break;
						        	}
						        	
						        	int cpIndex = cpChoice - 1;
						        	int cineIndex = (cineChoice-1) % 3;

						        	flag4 = true;
						        	while(flag4) {
							        	System.out.println("--------------------------------");
							        	System.out.println("Entering Cinema " + cineChoice);
							        	System.out.println("--------------------------------");
							        	System.out.println("Configuration Options: ");
							        	System.out.println("1. Show all movies in this cinema");
							        	System.out.println("2. Add a movie to the cinema");
							        	System.out.println("3. Remove a movie from this cinema");
							        	System.out.println("4. Retrive information of a movie"); // show layout as well
							        	System.out.println("5. Configure the show time of a movie");
							        	System.out.println("6. Back");
							        	System.out.println("Enter your choice: ");
							        	int choice = 0;
							        	choice = getValidChoice(choice,1,6);
							        	
							        	switch(choice) {
							        	case 1: cm.cp[cpIndex].cn[cineIndex].printAllMovieInfo();
							        	        break;
							        	        
							        	case 2: //System.out.println("----------------------------------------");
							        		    mm.printListing();
							        	        System.out.println("Please enter the index of the movie to be added: ");
							        	        int index = sc.nextInt();
							        	        if(MovieManager.mMap.containsKey(index)) {
							        	        	String title = MovieManager.mMap.get(index);
							                        System.out.println(">>>> " + title);
							        	        	System.out.println("Enter show date in YYYYMMDD format: ");
							        	        	int date = sc.nextInt();
							        	        	System.out.println("Enter show time in HHMM format: ");
							        	        	int time = sc.nextInt();
							        	        	cm.cp[cpIndex].cn[cineIndex].addMovie(title, date, time);

							        	        }else {
							        	        	System.out.println("Invalid Choice. This movie is not in the movie listing");
							        	        }
							        	        break;
							        	       
							        	case 3: System.out.println("----------------------------------------");
					        		            cm.cp[cpIndex].cn[cineIndex].printAllMovieInfo();
					        		            if (cm.cp[cpIndex].cn[cineIndex].movieList.size()==0)
					        		            		break;
					        		            System.out.println("Enter the index of the movie to be removed, enter 0 to exit: ");
					        		            index = sc.nextInt();
					        		    
					        		            if (index == 0) break;
					        		            else if((index < 1) ||(index > cm.cp[cpIndex].cn[cineIndex].movieList.size())){
					        		    	        System.out.println("Invalid Choice. Please try again.");
					        		    	        break;
					        		            }
					        		    
					        		            String title = cm.cp[cpIndex].cn[cineIndex].movieList.get(index - 1).getTitle();
					                            System.out.println(">>>> " + title);
					        	        	    cm.cp[cpIndex].cn[cineIndex].deleteMovie(title);
					        	        	    //System.out.println(title + " has been removed");
					        	              
							        		    break;
							        	
							        	case 4: System.out.println("----------------------------------------");
							        		    cm.cp[cpIndex].cn[cineIndex].printAllMovieInfo();
							        		    System.out.println(">>>> Enter the index of the movie to learn more, enter 0 to exit: ");
							        		    index = sc.nextInt();
							        		    
							        		    if (index == 0) break;
							        		    else if((index < 1) ||(index > cm.cp[cpIndex].cn[cineIndex].movieList.size())){
							        		    	System.out.println("Invalid Choice. Please try again.");
							        		    	break;
							        		    }
							        		    
							        		    title = cm.cp[cpIndex].cn[cineIndex].movieList.get(index - 1).getTitle();
							        		    cm.cp[cpIndex].cn[cineIndex].printOneMovieInfo(title);
							        		    System.out.println("----------------------------------------");
							        		    System.out.println("Current seating plan: ");
							        		    cm.cp[cpIndex].cn[cineIndex].layoutList.get(index-1).getLayout();  // solve the cinema index problem
							        		    break;
							        	
							        	case 5: System.out.println("----------------------------------------");
					        		            cm.cp[cpIndex].cn[cineIndex].printAllMovieInfo();
					        		            System.out.println(">>>> Enter the index of the movie for time configuration, enter 0 to exit: ");
					        		            index = sc.nextInt();
					        		    
					        		            if (index == 0) break;
					        		            else if((index < 1) ||(index > cm.cp[cpIndex].cn[cineIndex].movieList.size())){
					        		    	    System.out.println("Invalid Choice. Please try again.");
					        		    	    break;
					        		            }
					        		            title = cm.cp[cpIndex].cn[cineIndex].movieList.get(index - 1).getTitle();
							        		    System.out.println("Configuration for the movie " + title);
							        		    System.out.println("1. Set show date: ");
							        		    System.out.println("2. Set show time: ");
							        		    System.out.println("3. Back ");
							        		    System.out.println(">>>> Enter your choice: ");
							        		    int setTime = sc.nextInt();
							        		    
							        		    if(setTime == 1) {
							        		    	System.out.println(">>>> Enter the new date: ");
							        		    	int newDate = sc.nextInt();
							        		    	cm.cp[cpIndex].cn[cineIndex].movieList.get(index - 1).setDate(newDate);
							        		   
							        		    }
							        		    else if(setTime == 2) {
							        		    	System.out.println(">>>> Enter the new time: ");
							        		    	int newTime = sc.nextInt();
							        		    	cm.cp[cpIndex].cn[cineIndex].movieList.get(index - 1).setTime(newTime);
							        		   
							        		    }
							        		    else if(setTime == 3) break;
							        		    else System.out.println("Invalid choice. Please try again.");
							        		    
					        		            break;
					        		            
							        	case 6: flag4 = false;
							        	        break;
							        	default: System.out.println("Invalid choice. Please try again.");
							        	         break;
							        	}
						        	}
		
						        }

						        break;
						        
						case 3: System.out.println("----------------------------------------");
							    System.out.println("Entering price configuration...");
						        System.out.println();
							    staff.configurePriceScheme();
							    break;
							

						case 4: hs = new HolidayScheme();
						        hs.configureHolidayScheme();
						        hs.updateFile();     
							    break;
							    
						case 5: flag1 = false;
						        System.out.println("Exiting admin page...");
						        break;
						        
						default: System.out.println("Please enter a valid choice.");
							     break;
					}
					
				}
		

			}
			
			else if (initChoice==3)
				break;
			
			else {
				
				flag1 = true;
				flag2 = true;
				flag3 = true;
				flag4 = true;
				while(flag1) {
					System.out.println();
					System.out.println();
					System.out.println("================================================");
					System.out.println("Dear customer, welcome to Cathay!");
					System.out.println("================================================");
					System.out.println("Movie options: ");
					System.out.println("1. Checkout our movies");
					System.out.println("2. Check out our cinemas and book a seat! ");
					System.out.println("3. Booking histories");
					System.out.println("4. Quit");
					
					System.out.println(">>>> Enter your choice (1-4): ");
					int cusChoice = 0;
					cusChoice = getValidChoice(cusChoice,1,4);
					
					switch(cusChoice){
					
					case 1: System.out.println("Welcome to Cathay Movies!");
					        flag2 = true;
					        while(flag2) {
			        	         System.out.println("Options: ");
			        	         System.out.println("1. Show all movies available");
			        	         System.out.println("2. Retrive information of a movie");
			        	         System.out.println("3. Review a movie");
			        	         System.out.println("4. View our top 5 movies");
			        	         System.out.println("5. Back");
			        	         System.out.println(">>>> Enter your choice (1-5): ");
			        	         int choice = 0;
			        	         choice = getValidChoice(choice,1,5);
			        	
			        	         switch(choice) {
			        	         case 1: mm.printListing();
			        	                 break;
			        	         case 2: mm.printListing();
			        	        	     System.out.println("Please enter the corresponding movie ID:");
							             int id = sc.nextInt();
							             String title = null;
							             
							             if(MovieManager.mMap.containsKey(id)) {
							            	 title = MovieManager.mMap.get(id);
							             }
							             else {
							            	 System.out.println("This movie does not exist in the listing. Please try again.");
	                                    	 break;
							             }
							             
							             mm.getStatInfo(title);
	                                     
							             System.out.println();
										 System.out.println("=================== Available At ========================");
									     for(int i = 0; i<3;i++) {
											 for(int j =0; j<3; j++) {
												 System.out.println("Showtime in Cinema " + cm.cp[i].cn[j].getCinemaId() + ": ");
												 if(cm.cp[i].cn[j].movieList.size()==0) {
													 System.out.println(">>>> Not available");
													 System.out.println();
												 }
												 else {
													 
													 for(int k =0; k< cm.cp[i].cn[j].movieList.size(); k++) {
														 if(cm.cp[i].cn[j].movieList.get(k).getTitle() == title) {
															 System.out.print(cm.cp[i].cn[j].movieList.get(k).getDate());
															 System.out.println( ">>>> at " + cm.cp[i].cn[j].movieList.get(k).getTime());
														 }
														 else System.out.println(">>>> Not available");
														 System.out.println();
													 } 

												 }

											 }
									     }
										 System.out.println("=========================================================");

			        	        	     break;
			        	        	     
			        	          case 3: 
			        	        	    System.out.println("--------------------------------");
								        System.out.println("Welcome to Movie Review: ");
							            mm.printListing();
			        	        	    System.out.println("Please enter the corresponding movie ID:");
							            id = sc.nextInt();
							            title = null;
							             
							            if(MovieManager.mMap.containsKey(id)) {
							            	title = MovieManager.mMap.get(id);
							            }
							            else {
							            	System.out.println("This movie does not exist in the listing. Please try again.");
		                               	    break;
							            }
							            
							            flag3 = true;
							            while(flag3) {
								            System.out.println("Options: ");
								            System.out.println("1. Rate the movie");
								            System.out.println("2. Add review");
								            System.out.println("3. Back");
								            System.out.println(">>>> Enter your choice (1-3): ");
								            
								            choice = 0;
								            choice = getValidChoice(choice,1,3);
								            
								            switch(choice) {
								            										        	 
								        	   
								            case 1: 
										            Double newRate = null;
										        	   String newRating = null;	
										        	   NumberFormat formatter = new DecimalFormat("#0.0");     
		
								                    do {
								                    		System.out.println(">>>> Enter your rating on a scale of 1.0-5.0: ");												        
								                    		newRate = sc.nextDouble();
												        if (newRate<0||newRate>5)
												        	System.out.println("Invalid entry. Please try again");
											        } while (newRate<0||newRate>5);
								                    newRating = formatter.format(newRate);
								                    newRate = Double.parseDouble(newRating);									                    
								                    mm.updateRating(id, newRate);
		                                            break;
								            case 2: System.out.println(">>>> Enter your name: ");								                    
								                    String name = sc.nextLine();
								                    System.out.println(">>>> Enter your review: ");
								                    String input = sc.nextLine();
								                    mm.addReview(id, input, name);
								                    break;
								            case 3: flag3 = false;
								                    break;
								            default: System.out.println("Invalid choice. Please try again.");
								                     break;
								            }
							            }
							            break;
							            
			        	          case 4: 
			        	        	  	listTopMovies();			        	        	 
							    break;
			        	        	  
			        	          case 5: flag2 = false; 
			        	        	      break;
			        	        	      
			        	          default: System.out.println("Invalid Choice. Please try again");
			        	                   break;
			        	         }
					        }
					        break;
						    
					case 2:  System.out.println("Entering Cathay cinemas...");
			                 while(true) {
			                	 
			        	         System.out.println("Which complex (1-3) would you like to visit? Enter 0 to exit");
			        	         int cpChoice = 0;
			        	         cpChoice = getValidChoice(cpChoice,0,3);
			        	         if(cpChoice == 0) break;
			        	       
			        	         System.out.println("Which cinema ("+ (cpChoice*3-2) + "-" + cpChoice*3 + ")" + "would you like to visit? Enter 0 to exit" );
			        	         int cineChoice = sc.nextInt();
			        	         if(cineChoice == 0 ) break;
			        	         else if((cineChoice < cpChoice*3 -2 ) || (cineChoice > cpChoice*3)) {
			        		         System.out.println("Invalid input. Please try again.");
			        		        break;
			        	         }
			        	
			        	         int cpIndex = cpChoice - 1;
			        	         int cineIndex = (cineChoice-1)%3;
			        	         
			        	         flag4 = true;
			        	         while(flag4) {
				        	         System.out.println("--------------------------------");
				        	         System.out.println("Entering Cinema " + cineChoice);
				        	         System.out.println("--------------------------------");
				        	         System.out.println("Options: ");
				        	         System.out.println("1. Show all movies in this cinema");
				        	         System.out.println("2. View a movie and book seats");				        	   
				        	         System.out.println("3. Back");
				        	         System.out.println("Enter your choice: ");
				        	         int choice = sc.nextInt();
				        	
				        	         switch(choice) {
				        	         case 1: cm.cp[cpIndex].cn[cineIndex].printAllMovieInfo();
					        	             break;
					        	             
				        	         case 2: System.out.println("----------------------------------------");
					        		         cm.cp[cpIndex].cn[cineIndex].printAllMovieInfo();
					        		         System.out.println(">>>> Enter the index of the movie to learn more, enter 0 to exit: ");
					        		         int index = sc.nextInt();
					        		    
					        		         if (index == 0) break;
					        		         else if((index < 1) ||(index > cm.cp[cpIndex].cn[cineIndex].movieList.size())){
					        		    	     System.out.println("Invalid Choice. Please try again.");
					        		    	     break;
					        		         }
					        		    
					        		         String title = cm.cp[cpIndex].cn[cineIndex].movieList.get(index - 1).getTitle();
					        		         cm.cp[cpIndex].cn[cineIndex].printOneMovieInfo(title);
					        		         cm.cp[cpIndex].cn[cineIndex].layoutList.get(index-1).getLayout(); 
					        		         
					        		         String type = cm.cp[cpIndex].cn[cineIndex].movieList.get(index - 1).getType();
					        		         int time = cm.cp[cpIndex].cn[cineIndex].movieList.get(index - 1).getTime();
					        		         int date = cm.cp[cpIndex].cn[cineIndex].movieList.get(index - 1).getDate();
					        		         
					        		         System.out.println("Book a seat?");
					        		         System.out.println("1. Yes");
					        		         System.out.println("2. No");
					        		         index = sc.nextInt();
					        		         
					        		         if(index == 1) {
					        		        	 System.out.println("Welcome to the booking page!");
					        	                 bk = new Booking();		        	           
					        	                 
					        	                 hs = new HolidayScheme();
					        	                 int dateCopy = date;
					        	                 String dateStr = Integer.toString(dateCopy);
					        	                 String reformDate = dateStr.substring(6) + "/" + dateStr.substring(4, 6) + "/" + dateStr.substring(0, 4);
					        	                 boolean holiday = hs.verifyPublicHoliday(reformDate);
					        	                 boolean freeDay = false;
					        	                 int day = hs.getDayFromDate(reformDate);
					        	                 if(day > 4) freeDay = true;
					        	                 
					        	                 String cineClass = ((cineIndex-cpIndex)==2)? "Platinum" : "Normal";
					        	                 
					        	                 //System.out.println(time+date*10000);
											     bk.bookingInit(title, type, date, time, !holiday||!freeDay, cineChoice, cineClass);
											     dummy = sc.nextLine();
											     
											     boolean success = false;
											     while(!success) {
												     cm.cp[cpIndex].cn[cineIndex].layoutList.get(index-1).getLayout(); 
												     System.out.println("Please select a seat. Enter A2, for example. ");
												     String seatC = sc.nextLine();
												     
												     char row = seatC.charAt(0);
												     int seat = Integer.parseInt(seatC.substring(1));
												     success = cm.cp[cpIndex].cn[cineIndex].layoutList.get(index-1).assignSeat(row, seat); 
											     }

											     
											     bk.payment(title);
											     
					        		         }
					        		         else if (index == 2) {
					        		        	 System.out.println("Thank you for your interest!");
					        		         }
					        		         else {
					        		        	 System.out.println("Invalid inputs. Please try again. ");
					        		         }
					        		         
					        		         break;
					        		        
				        	         case 3: flag4 = false;
				        	                 break;
				        	         default: break;
				        	         
				        	         }
			        	          }
			                 }
						    break;
						    
					case 3: System.out.println(">>>> Check your booking histories!");                         
							bk = new Booking();	
							bk.printHistory();				
						    break;
					case 4: flag1 = false;
					        System.out.println("Thank you for visiting Cathay! See you next time!");
					        break;
					default: break;
					        	
			        }
				}
			}
	}
	System.out.println("Exiting...");
	staff.updateFile();
	sc.close();

		

}
	
	public int getValidChoice(int initChoice, int min, int max) {
		do {						
		    try {		    		
		        initChoice = sc.nextInt();
		    }
		    catch (InputMismatchException e){} //handle exceptions;
		    
		    // quit looping when the choice is within the required range.
		    if (initChoice>=min & initChoice<=max) { 
		    		dummy = sc.nextLine(); // clear buffer.
		    		break;
		    }
		    dummy = sc.nextLine();
		    System.out.println("Invalid choice, please try again:");		    				    		    
		} while (true);
		return initChoice;
	}
	
	public String getMovieType() {
		System.out.println("Choose a new type: ");
		System.out.println("1. 2D");
		System.out.println("2. 2DBlockBuster");
		System.out.println("3. 3D");
		System.out.println("4. 3DBlockBuster");								                        				
		System.out.println(">>>> Enter the new content rating (1-4): "); 
		
		int newTypeChoice = 0;
		newTypeChoice = getValidChoice(newTypeChoice,1,4);

	    switch(newTypeChoice) {
	    case 1:
	    		return "2D";

	    case 2:
	    		return "2DBlockBuster";
	    		
	    case 3:
	    		return "3D";
	    		
	    default:
	    		return "3DBlockBuster";	    				        	                                    	
	    }
	}
	
	public String getContentRating() {
		System.out.println("Choose a new content rating: ");
		System.out.println("1. G");
		System.out.println("2. PG");
		System.out.println("3. PG-13");
		System.out.println("4. R");
		System.out.println("5. NC-17");					
		System.out.println(">>>> Enter the new content rating (1-5): "); 
		
		int newCR = 0; // new content rating	
		newCR = getValidChoice(newCR,1,5);									                		
		
		switch(newCR) {
		case 1:	
			return "G";
			
		case 2:
			return "PG";
			
		case 3:
			return "PG-13";
			
		case 4:
			return "R";
			
		default:
			return "NC-17";															
		}
	}
	
	public void listTopMovies() {
		System.out.println("List the top 5 movies by:");
        System.out.println("1. Ticket sales");
        System.out.println("2. Ratings");
        System.out.println("3. Back");
        System.out.println("Enter your choice");
        int top5Choice = 0;
        top5Choice = getValidChoice(top5Choice,1,3);
        
	    	if(top5Choice == 1) {
	    		System.out.println("Entering..");
	    		mm.sortBySale();
	    		
	    	}
	    	else if (top5Choice == 2) {
	    		mm.sortByRating();
	    		
	    	}

		}
		
													        				        	   
		    

}
