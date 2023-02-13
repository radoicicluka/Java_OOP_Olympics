package olympics;

public class Point{
	private int x;
	private double y;

	public Point(int x, double y) {
		super();
		this.x = x; // year
		this.y = y; // Y axis data
	}

	public int getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	public void inc() { y++; }
}
