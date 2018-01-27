//Author: Chen Jing
package cathay;

import java.util.Arrays;
import java.util.List;

public class Platinum extends PriceScheme {

    public Platinum() {
    		/*
    		 * Initialize the map if empty.
    		 */
        super();
        if (!priceSchemeMap.containsKey("Platinum")) {
        	priceSchemeMap.put("Platinum", (List<Double>) Arrays.asList(20.00, 25.00));
    }
}

    public void printPrice(){
        List platinum = (List) priceSchemeMap.get("Platinum");
        System.out.println("-------------------(3)Platinum------------------- ");
        System.out.println("1. Monday to Thursday: " + platinum.get(0));
        System.out.println("2. Friday to Sunday, Eve of PH and PH: " + platinum.get(1));
    }

    public void setPrice() {
        this.printPrice();
        System.out.println("Press any other key to return.");
        System.out.println("Choose the price type you like to change: ");
        int choice2 = sc.nextInt();
        int[] choicePlatinum = {1,2};
        if (!(contains(choicePlatinum,choice2))) return;
        System.out.println("Enter the new price:");
        double price2 = sc.nextDouble();
        ((List<Double>) priceSchemeMap.get("Platinum")).set(choice2-1, price2);
        System.out.println("The price scheme after the edit:");
        this.printPrice();
        System.out.println();
    }

    public double getPrice(int i) {
        return (double) ((List) priceSchemeMap.get("Platinum")).get(i-1);
    }
}