//Author: Chen Jing
package cathay;

import java.util.Arrays;
import java.util.List;

public class ThreeD extends PriceScheme {


    public ThreeD() {
    		/*
    		 * Initialize the map if empty.
    		 */
        super();
        if (!priceSchemeMap.containsKey("3D")) {
            priceSchemeMap.put("3D", (List<Double>) Arrays.asList(9.00, 11.00, 13.00, 15.00));

    }
}

    public void printPrice(){
        List threeD = (List) priceSchemeMap.get("3D");
        System.out.println("-------------------(2)3D Movies------------------- ");
        System.out.println("1. Student (Mon-Thur, before 6pm): " + threeD.get(0));
        System.out.println("2. Monday to Thursday: " + threeD.get(1));
        System.out.println("3. Friday to Sunday, Eve of PH and PH: " + threeD.get(2));
        System.out.println("4. Blockbusters: " + threeD.get(3));
    }

    public void setPrice() {
        this.printPrice();
        System.out.println("Press any other key to return.");
        System.out.println("Choose the price type you like to change: ");
        int choice1 = sc.nextInt();
        int[] choiceThreeD = {1,2,3,4};
        if (!(contains(choiceThreeD,choice1))) return;
        System.out.println("Enter the new price:");
        double price1 = sc.nextDouble();
        ((List<Double>) priceSchemeMap.get("3D")).set(choice1-1, price1);
        System.out.println("The price scheme after the edit:");
        this.printPrice();
        System.out.println();
    }

    public double getPrice(int i) {
        return (double) ((List) priceSchemeMap.get("3D")).get(i-1);
    }

}
