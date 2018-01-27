//Author: Chen Jing
package cathay;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PriceScheme {
	public static String priceSchemePath = "priceScheme.txt";
    Scanner sc = new Scanner(System.in);
    public static Map <Object,Object> priceSchemeMap =FileMapManager.read(priceSchemePath);
    public PriceScheme() {
		}
    
    @SuppressWarnings("rawtypes")
	public void printPrice() {
    				System.out.println("-------------------(4)Exit-------------------");
    	}

    public void setPrice(){}
    public void getPrice(){};

    /*
     * Check the choice of the price type of the movie type being edited.
     */
    public static boolean contains(int[] choiceMovie, Integer item) {
        return Arrays.stream(choiceMovie).anyMatch(item::equals);
     }
    
    public void updateFile() {
		FileMapManager.updateFile(priceSchemePath, priceSchemeMap);
	}
}