package cathay;
import java.util.Scanner;

public class PriceSelect {
	
      PriceScheme ps= null;
	  TwoD twod = null;
	  ThreeD threed = null;
	  Platinum platinum = null;
      Scanner sc = null;
      double price = 0.0;
      
      
      
      /* Constructor */
      
      public PriceSelect() {
    	  ps = new PriceScheme();
    	  twod = new TwoD();
    	  threed = new ThreeD();
    	  platinum = new Platinum();
    	  sc = new Scanner(System.in);  
      }
      
      
      
      /* Print the entire price scheme */
      
      public void printAllPrice() {
		  twod.printPrice();
		  threed.printPrice();
		  platinum.printPrice();
		  ps.printPrice();
		  System.out.println();
      }

      
      
      /* output the corresponding price based on user inputs and other conditions */
      
      public double selectPrice(String type, boolean workDay, boolean senior, boolean student, String cineClass) {
    	  if(cineClass.equals("Platinum")) {
    		  if(workDay) {price = platinum.getPrice(1);}
    		  else { price = platinum.getPrice(2); }
    	  }
    	  else if(type.equals("2D")) {
    		  if(workDay) {
    			  if(senior) { price = twod.getPrice(1); }
    			  else if (student) { price = twod.getPrice(2);}
    			  else { price = twod.getPrice(3); }
    		  }
    		  else { price = twod.getPrice(4); }
    		  
    	  }
    	  
    	  else if (type.equals("2DBlockbuster")) { price = twod.getPrice(5);}
    	  
    	  else if (type.equals("3D")) {
    		  if(workDay) {
    			  if(student) { price = threed.getPrice(1); }
    			  else { price = threed.getPrice(2); }
    		  }
    		  else {price = threed.getPrice(3);}
    	  }
    	  
    	  else if (type.equals("3DBlockbuster")) { price = threed.getPrice(4);}
    	  
    	  else {
    		  System.out.println("The type of the movie is not defined yet. Please wait for further updates. Sorry for the inconvenience.");
    		  price = -1.0 ;

    	  }
		  return price;


      }
      
}