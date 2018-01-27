package cathay;

public class ComplexManager {

	Complex[] cp = null; 
	
	/* constructor creates an array of complexes */

	public ComplexManager() {
		cp = new Complex[3];
		for (int i = 0; i<3; i++) {
			cp[i] = new Complex(i+1);
		}
	}
}