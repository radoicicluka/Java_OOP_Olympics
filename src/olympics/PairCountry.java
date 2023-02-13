package olympics;

public class PairCountry implements Comparable<PairCountry>{
	private String country;
	private int number;
	public PairCountry(String country) {
		super();
		this.country = country;
		this.number = 1;
	}
	public String getCountry() {
		return country;
	}
	public int getNumber() {
		return number;
	}
	public void inc() {
		number++;
	}
	@Override
	public int compareTo(PairCountry p) {
		if (number == p.getNumber()) return 0;
		if (number < p.getNumber()) return -1;
		return 1;
	}
}
