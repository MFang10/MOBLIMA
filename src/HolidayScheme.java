//Author: Tony Zeng
package cathay;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class HolidayScheme {
    public static String holidaySchemePath = "HolidayScheme.txt";
    static Scanner sc = new Scanner(System.in);
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static Map<Object, Object> holidaySchemeMap = (Map) FileMapManager.read(holidaySchemePath);

    HolidayScheme(){
		/*
		 * Create the admin account.
		 */
        if (holidaySchemeMap.isEmpty()) {
            holidaySchemeMap.put("01/01/2017","New Year's Day");
            holidaySchemeMap.put("28/01/2017","Chinese New Year");
            holidaySchemeMap.put("14/04/2017","Good Friday");
            holidaySchemeMap.put("01/05/2017","Labour Day");
            holidaySchemeMap.put("25/06/2017","Hari Raya Puasa");
            holidaySchemeMap.put("09/08/2017","National Day");
            holidaySchemeMap.put("01/09/2017","Hari Raya Haji");
            holidaySchemeMap.put("18/10/2017","Deepavali");
            holidaySchemeMap.put("25/12/2017","Christmas Day");
        }
    }

    public void printHolidayScheme() {
        Set<Object> keysHolidayScheme = holidaySchemeMap.keySet();  //get all keys
        for(Object i: keysHolidayScheme)
        {
            System.out.println("Date: "+i+" Holiday Name: "+holidaySchemeMap.get(i));
        }
    }

    public int getDayFromDate(String date) {
        LocalDate ld = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        return ld.getDayOfWeek().getValue();
    }

    public boolean verifyPublicHoliday(String date) {
        if (holidaySchemeMap.get(date)!=null)
            return true;
        else
            return false;
    }

    public String getHolidayDate(String name) {
        Set<Object> keysHolidayScheme = holidaySchemeMap.keySet();  //get all keys
        for(Object i: keysHolidayScheme)
        {
            if (name.equals(holidaySchemeMap.get(i)))
            {
                return (String) i;
            }
        }
        return null;
    }

    public void removeHolidayDate (String date) {
        if (holidaySchemeMap.get(date)!=null) {
            holidaySchemeMap.remove(date);
            System.out.println("The remove is successful!");
        }
        System.out.println("The date is not in the HolidayScheme! Please re-check your input!");

    }

    public String getHolidayName(String date) {
        if (holidaySchemeMap.get(date)!=null)
            return (String) holidaySchemeMap.get(date);
        return null;
    }

    public void setHolidayScheme(String date, String name) {
        holidaySchemeMap.put(date, name);
    }

    public void updateFile() {
        FileMapManager.updateFile(holidaySchemePath, holidaySchemeMap);
    }

    public void configureHolidayScheme() {
        int returnInt = 0;

        do {
            try {
                printHolidayScheme();
                System.out.println();
                System.out.println("Enter 1 to update the holiday record.");
                System.out.println("Enter 2 to delete the holiday record.");
                System.out.println("Enter 3 to return.");
                System.out.println("Please enter choice (1-3)");
                returnInt = Integer.parseInt(sc.nextLine());
                String date;
                String name;
                switch(returnInt) {
                    case 1:
                        System.out.println("Please enter the holiday date you want to update(dd/mm/yyyy):");
                        date = sc.nextLine();
						/*
						 ***************
						 *  CHANGES*****************/
                        do {
                            Pattern patternDate = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");
                            Matcher matcher = patternDate.matcher(date);
                            if (matcher.matches()) {
                                break;
                            }
                            else
                            {
                                System.out.println("date must be in the format of dd/mm/yyyy (Eg.01/09/2017)");
                                System.out.println("Please re-enter the holiday date you want to update(dd/mm/yyyy):");
                                date = sc.nextLine();
                            }
                        } while (!date.equals("Exit"));
                        if (date.equals("Exit"))
                            break;
						/*==========END OF CHANGE********/

                        System.out.println("Please enter the holiday name:");
                        name = sc.nextLine();
                        setHolidayScheme(date, name);
                        System.out.println();
                        System.out.println("The Holiday Scheme after the edit is:");
                        break;
                    case 2:
                        System.out.println("Please enter the holiday date you want to delete(dd/mm/yyyy):");
                        date = sc.nextLine();
						/*
						 ***************
						 *  CHANGES*****************/
                        do {
                            Pattern patternDate = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");
                            Matcher matcher = patternDate.matcher(date);
                            if (matcher.matches()) {
                                break;
                            }
                            else
                            {
                                System.out.println("date must be in the format of dd/mm/yyyy (Eg.01/09/2017)");
                                System.out.println("Enter \"Exit\" to return.");
                                System.out.println("Please re-enter the holiday date you want to delete(dd/mm/yyyy):");
                                date = sc.nextLine();
                            }
                        } while (!date.equals("Exit"));
                        if (date.equals("Exit"))
                            break;
                        /**********==========END OF CHANGE********/
                        removeHolidayDate(date);
                        System.out.println();
                        System.out.println("The Holiday Scheme after the edit is:");
                        break;
                    case 3:
                        break;
                }

            }
            catch (NumberFormatException e) {
                System.out.println("Invalid Input. Only numbers between 1 to 4 are accepted.\n");
            }

        } while (returnInt != 3);

    }
}