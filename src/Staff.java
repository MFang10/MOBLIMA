//Author: Tony Zeng
package cathay;
//import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Staff {
	private static String staffAccountPath = "staffAccount.txt";
	
	
	Scanner sc = new Scanner(System.in);
	private static Map<Object,Object> staffAccountMap = FileMapManager.read(staffAccountPath);
    
	public Staff() {
		/* 
		 * Initialization
		 */
	
		//int staffChoice = 0;
		/*
		 * Create the admin account.
		 */
		if (staffAccountMap.isEmpty()) {
			System.out.println("Map Empty");
			staffAccountMap.put("staffname", "password");
			staffAccountMap.put("admin", "root");
			staffAccountMap.put("prof", "admin");
			System.out.println("Generating...");

		    //System.out.println(staffAccountMap.get("admin"));

		}
	}

	
	
	/* 
	 * Configure the price scheme.
	 */

	public void configurePriceScheme() {
		int choiceConfigure = 0;
        PriceSelect p = new PriceSelect();
        TwoD twoD = new TwoD();
        ThreeD threeD = new ThreeD();
        Platinum platinum = new Platinum();
        PriceScheme priceScheme = new PriceScheme();
		/* 
	 * Loop terminates when 4 is entered.
	 */
	do {
		try {
            p.printAllPrice();
			System.out.println("Please enter choice (1-4)");
			choiceConfigure = Integer.parseInt(sc.nextLine());
			switch(choiceConfigure) {
				case 1:								
					twoD.setPrice();
					break;
				case 2:
					threeD.setPrice();
					break;
				case 3:
					platinum.setPrice();
					break;
				case 4:
					break;
			}
		}
		catch (NumberFormatException e) {
			System.out.println("Invalid Input. Only numbers between 1 to 4 are accepted.\n");
		}	
	} while (choiceConfigure != 4);

	priceScheme.updateFile();
}

	
	public void print() {
	   
	}
	
	/*
	 * Staff Log In.
	 */
	public boolean userLogIn() {
		System.out.println("Please enter your username:");
		String name = sc.nextLine();
		//System.out.println(name);
		System.out.println("Please enter your password:");
		String pwd = sc.nextLine();
		//System.out.println(pwd);
		//System.out.println(staffAccountMap.get(name));
		if (pwd.equals(staffAccountMap.get(name)))
			return true;

		else {
			int choice = 0;
			try {
				System.out.println("Invalid username or password.");
				System.out.println("1. I'm a staff.");
				System.out.println("2. I'm a customer.");
				System.out.println("Please enter your choice:");
				choice = sc.nextInt();
				String dummy = sc.nextLine();
			}
			catch (NumberFormatException e) {
				System.out.println("Invalid Input. Only numbers between 1 to 2 are accepted.\n");
			}
			if (choice == 2)
				return false;
			return userLogIn();
		}
	}
	
	public void updateFile() {
		FileMapManager.updateFile(staffAccountPath, staffAccountMap);
	}
	
}