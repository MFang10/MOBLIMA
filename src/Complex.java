package cathay;

public class Complex {
	private int cineplexId = 0;
	Cinema[] cn = null;
	private int firstCineId = 0;

	public Complex(int complexId) {
		cineplexId = complexId;
		firstCineId = complexId*3 -2;
		cn = new Cinema[3];
	}

	public void initComplex(String cineClass) {
		int j = 0;
		for(int i = firstCineId; i < (firstCineId + 3); i++) {
			if(i == firstCineId+1) {cineClass = "Platinum";}
			cn[j] = new Cinema(i,cineplexId, cineClass);
			j++;
		}
	}

	public int getCineplexId() {return cineplexId;}

	// try not to use this one
	public Cinema getCinema(int cineId) {
		return cn[cineId];
	}

}