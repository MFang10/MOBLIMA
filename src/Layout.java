package cathay;
import java.util.*;

public class Layout {

	private String layoutClass = null;
	private List<String> currentLayout = null;

	FileEditor fe = new FileEditor();

	public Layout (String cineClass) {	
		layoutClass = cineClass;
		currentLayout = new ArrayList<String>();
		
		if(cineClass == "Normal") currentLayout = fe.fileToList("NormalLayout.txt");
		else if (cineClass == "Platinum") currentLayout = fe.fileToList("PlatinumSuite.txt");
		else System.out.println("This cinema class does not exist. No layout available");
	}
	
	
	public void clearLayout() {currentLayout.clear();}
	
	
	public void initLayout() {
		if(layoutClass == "normal") currentLayout = fe.fileToList("NormalLayout.txt");
		else if (layoutClass == "platinum") currentLayout = fe.fileToList("PlatinumSuite.txt");
	}
	
	
	public void getLayout() {
        System.out.println("----------------------------------------");
        System.out.println("Current seating plan: ");
		for(int i = 0; i<currentLayout.size(); i++) {
			System.out.println(currentLayout.get(i));
		}
	}
	

	public boolean assignSeat(char rowChoice, int seatChoice) {
		boolean rowFound = false;
		//boolean seatFound = false;
		//boolean occupied = false;
		int rowIndex = -1;
		String targetRow = null;
		
		for (int i = 0; i < currentLayout.size(); i++) {
			if(currentLayout.get(i).charAt(0) == rowChoice) {
				System.out.println(currentLayout.get(i).charAt(0));
				rowFound = true;
				targetRow = currentLayout.get(i);
				rowIndex = i;
			}
		}
		
		if(!rowFound) {
			System.out.println("Please enter a valid row");
			return false;
		}
		
		String newRow = null;
		
		if(layoutClass == "Normal") {
			if((seatChoice < 1) || (seatChoice > 16)) {
				System.out.println("Please enter a valid seat.");
				return false;
			}
			for(int i = 0; i < targetRow.length(); i++) {
				
				if(seatChoice <=8) { 
					if (targetRow.charAt(seatChoice*3) == 'X') {
						System.out.println("This seat is occupied. Please choose another seat.");
						return false;
					}
					newRow = targetRow.substring(0, seatChoice * 3) + "X" + targetRow.substring(seatChoice*3+1);
				}
				
				else {
					if(targetRow.charAt(seatChoice*3 + 4) == 'X') {
						System.out.println("This seat is occupied. Please choose another seat.");
						return false;
					}
					newRow = targetRow.substring(0, seatChoice * 3 + 4) + "X" + targetRow.substring(seatChoice*3+5);	
				}	    
			}
		}
		
		
		else {
			if((seatChoice < 1) || (seatChoice > 6)) {
				System.out.println("Please enter a valid seat.");
				return false;
			}
			for(int i = 0; i < targetRow.length(); i++) {
				
				if(seatChoice <= 2) { 
					if (targetRow.charAt(seatChoice*3) == 'X') {
						System.out.println("This seat is occupied. Please choose another seat.");
						return false;
					}
					newRow = targetRow.substring(0, seatChoice * 3) + "X" + targetRow.substring(seatChoice*3+1);
				}
				
				else if (seatChoice <= 4) {
					if (targetRow.charAt(seatChoice*3 + 3) == 'X') {
						System.out.println("This seat is occupied. Please choose another seat.");
						return false;
					}
					newRow = targetRow.substring(0, seatChoice * 3 + 3) + "X" + targetRow.substring(seatChoice*3+4);
				}
				
				else {
					if(targetRow.charAt(seatChoice*3 + 6) == 'X') {
						System.out.println("This seat is occupied. Please choose another seat.");
						return false;
					}
					newRow = targetRow.substring(0, seatChoice * 3 + 6) + "X" + targetRow.substring(seatChoice*3+7);
				}	    
			}
		}
		
		currentLayout.remove(rowIndex);
		currentLayout.add(rowIndex, newRow);
		
		System.out.println("Seat assigned. New seating plan: ");
		this.getLayout();
		return true;
	}
	
	// can be used for cancellation
	public Boolean deassignSeat(char rowChoice, int seatChoice) {
		boolean rowFound = false;
		int rowIndex = -1;
		String targetRow = null;
		
		for (int i = 0; i < currentLayout.size(); i++) {
			if(currentLayout.get(i).charAt(0) == rowChoice) {
				rowFound = true;
				targetRow = currentLayout.get(i);
				rowIndex = i;
			}
		}
		
		if(!rowFound) {
			System.out.println("Please enter a valid row");
			return false;
		}
		
		String newRow = null;
		
		if(layoutClass == "Normal") {
			if((seatChoice < 1) || (seatChoice > 16)) {
				System.out.println("Please enter a valid seat.");
				return false;
			}
			for(int i = 0; i < targetRow.length(); i++) {
				
				if(seatChoice <=8) { 
					if (targetRow.charAt(seatChoice*3) == ' ') {
						System.out.println("This seat is already unoccupied. Please choose the right seat.");
						return false;
					}
					newRow = targetRow.substring(0, seatChoice * 3) + " " + targetRow.substring(seatChoice*3+1);
				}
				
				else {
					if(targetRow.charAt(seatChoice*3 + 4) == ' ') {
						System.out.println("This seat is already unoccupied. Please choose the right seat.");
						return false;
					}
					newRow = targetRow.substring(0, seatChoice * 3 + 4) + " " + targetRow.substring(seatChoice*3+5);	
				}	    
			}
		}
		
		
		else {
			if((seatChoice < 1) || (seatChoice > 6)) {
				System.out.println("Please enter a valid seat.");
				return false;
			}
			for(int i = 0; i < targetRow.length(); i++) {
				
				if(seatChoice <= 2) { 
					if (targetRow.charAt(seatChoice*3) == ' ') {
						System.out.println("This seat is already unoccupied. Please choose the right seat.");
						return false;
					}
					newRow = targetRow.substring(0, seatChoice * 3) + " " + targetRow.substring(seatChoice*3+1);
				}
				
				else if (seatChoice <= 4) {
					if (targetRow.charAt(seatChoice*3 + 3) == ' ') {
						System.out.println("This seat is already unoccupied. Please choose the right seat.");
						return false;
					}
					newRow = targetRow.substring(0, seatChoice * 3 + 3) + " " + targetRow.substring(seatChoice*3+4);
				}
				
				else {
					if(targetRow.charAt(seatChoice*3 + 6) == ' ') {
						System.out.println("This seat is already unoccupied. Please choose the right seat.");
						return false;
					}
					newRow = targetRow.substring(0, seatChoice * 3 + 6) + " " + targetRow.substring(seatChoice*3+7);
				}	    
			}
		}
		
		currentLayout.remove(rowIndex);
		currentLayout.add(rowIndex, newRow);
		
		System.out.println("Seat deassigned. New seating plan: ");
		this.getLayout();
		return true;
	}

}
