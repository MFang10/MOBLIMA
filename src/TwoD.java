//Author: Chen Jing
package cathay;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TwoD extends PriceScheme {

    public TwoD() {
    		/*
    		 * Initialize the map if empty.
    		 */
        super();
        if (!priceSchemeMap.containsKey("2D")) {
            priceSchemeMap.put("2D", (List<Double>) Arrays.asList(4.00, 7.00, 8.50, 11.00, 13.00));
        }
    }

    public void printPrice(){
        List twoD = (List) priceSchemeMap.get("2D");
        System.out.println("-------------------(1)2D Movies------------------- ");
        System.out.println("1. Senior citizen (Mon-Thur, before 6pm): " + twoD.get(0));
        System.out.println("2. Student (Mon-Thur, before 6pm): " + twoD.get(1));
        System.out.println("3. Monday to Thursday: " + twoD.get(2));
        System.out.println("4. Friday to Sunday, Eve of PH and PH: " + twoD.get(3));
        System.out.println("5. Blockbusters: " + twoD.get(4));
    }

    public void setPrice() {// Change to setPrice

        this.printPrice();
        System.out.println("Press any other key to return.");
        System.out.println("Choose the price type you like to change: ");
        int choice = sc.nextInt();
        int[] choiceTwoD = {1,2,3,4,5};
        if (!(contains(choiceTwoD,choice))) return;
        System.out.println("Enter the new price:");
        double price = sc.nextDouble();
        ((List<Double>) priceSchemeMap.get("2D")).set(choice-1, price);
        System.out.println("The price scheme after the edit:");
        this.printPrice();
        System.out.println();
    }

    public double getPrice(int i) {
        return (double) ((List) priceSchemeMap.get("2D")).get(i-1);
    }
}