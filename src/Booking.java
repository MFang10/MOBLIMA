package cathay;
import java.io.*;
import java.util.Scanner;



public class Booking {

    Scanner sc = new Scanner(System.in);
    PriceSelect  psel = new PriceSelect();
    FileEditor fe = new FileEditor();
    
    private String goerName, phoneNumber, email;
    private int age;
    private String movieType, movieTitle;
    private int date, time;
    private boolean seniorCitizen = false, student = false, workDay = false;
    private String transactionID = null;
    private String cineClass;

    /* constructor */
    public Booking() {}


    /* Initialize the attributes of the class */

    public void bookingInit(String title, String movieType, int date, int time, boolean workDay, int cineId, String cineClass) {

        this.movieTitle = title;
        this.movieType = movieType;
        this.time = time;
        this.date = date;
        this.workDay = workDay;
        this.transactionID = Character.toString((char)(cineId + 64) )+ String.valueOf(date)+String.valueOf(time);
        this.cineClass = cineClass;

        System.out.println("Enter your name: ");
        goerName = sc.nextLine();
        System.out.println("Enter your age: ");
        age = Integer.parseInt(sc.nextLine());
        if(age>65) seniorCitizen = true;

        System.out.println("Enter your phone number: ");
        phoneNumber = sc.nextLine();
        System.out.println("Enter your email: ");
        email = sc.nextLine();
        System.out.println("Are you a student? 1. Yes 2.No ");
        int choice  = sc.nextInt();
        if(choice == 1) { student = true; }

    }



    /*Print the price list and output the ticket price based on user inputs and selections. If payment is made, record the transaction */

    public void payment(String title) {
        psel.printAllPrice();
        double price = psel.selectPrice(movieType, workDay, seniorCitizen, student, cineClass);
        if (price == -1) {
            System.out.println("Price not available. Sorry.");
            return;
        }
        else System.out.println("The price of the ticket is " + price);
        System.out.println("Would you like to proceed to payment? 1. Yes 2.No");
        String dummy = sc.nextLine();
        int choice = Integer.parseInt(sc.nextLine());
        if(choice == 1) {
            System.out.println("Payment successful, thank you!");
            System.out.println("Your transaction ID is " + transactionID);
            this.bookingHistory();
            //update movie ticket sales
            String currSaleStr = fe.extract("Sales.txt", title + ": ", true);
            double currSale = Double.parseDouble(currSaleStr);
            double newSale = currSale + price;
            fe.update("Sales.txt", title, title+": "+newSale, true);
        }
        else {
            return;
        }

    }



    /* Add booking history to the file under the current user. If file is not found, create new file */

    public void bookingHistory(){
        try {
            File historyFile = new File(phoneNumber);
            FileWriter write = new FileWriter(historyFile, true);
            BufferedWriter bw = new BufferedWriter(write);

            if (historyFile.exists()) {
                //append to the end of file
                addHistoryEntry(phoneNumber);
                bw.close();

            } else {
                // create new file with attributes
                historyFile.createNewFile();

                bw.write("Customer name: " + goerName);
                bw.newLine();
                bw.write("Age: " + age);
                bw.newLine();
                bw.write("Phone number : " + phoneNumber);
                bw.newLine();
                bw.write("Email: " + email);

                // add the booking history
                addHistoryEntry(phoneNumber);

                bw.close();
            }
        }catch(IOException e) {
            System.out.println("Trouble handling file.");
            return;
        }

    }



    /* add a new entry into the user file */
    public void addHistoryEntry(String phone) throws IOException {

        FileWriter write = new FileWriter(phone, true);
        BufferedWriter bw = new BufferedWriter(write);

        bw.write("Transaction ID: " + transactionID);
        bw.newLine();
        bw.write("Movie title: " + movieTitle);
        bw.newLine();
        bw.write("Movie date: " + date);
        bw.newLine();
        bw.write("Movie time: " + time);
        bw.newLine();
        bw.write("===============================================");
        bw.newLine();

        bw.close();
    }



    /* this method can be called when printing receipt */

    public void printHistory() { // pass in phone number
        System.out.println("Please enter your phone number: ");
        String phone = sc.nextLine();
        System.out.println("Please enter your transaction ID: ");
        String transID = sc.nextLine();

        try {
            //prints booking history
            FileReader fr = new FileReader(phone);
            BufferedReader br = new BufferedReader(fr);
            String line = null;

            //search for line containing bookingID
            while((line = br.readLine())!= null) {
            	if(line.contains(transID)) {
            		System.out.println(line);
            		for(int i = 0;i<3;i++) {
            			System.out.println(br.readLine());
            		}
            		System.out.println("End of booking history");
            		break;
            	}
            }
            
            if(line == null) {
            	System.out.println("No relevant history found.");
            }


                fr.close();
                br.close();
            
        }catch(IOException e) {
            System.out.println("Trouble handling file.");
            return;
        }

    }
}