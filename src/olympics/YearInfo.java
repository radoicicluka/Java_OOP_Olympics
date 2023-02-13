package olympics;

public class YearInfo {
	private int year;
	private double total;
	private int num;
	public YearInfo(int year, double total) {
		super();
		this.year = year;
		this.total = total;
		this.num = 1;
	}
	public int getYear() {
		return year;
	}
	public double getTotal() {
		return total;
	}
	public int getNum() {
		return num;
	}
	
	public void add(double d) {
		total += d;
		num++;
	}
	
	
}
