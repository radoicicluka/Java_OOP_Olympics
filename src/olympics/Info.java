package olympics;

public class Info {

	private String name;
	private String gender;
	private int age;
	private double height, weight;
	
	public Info(String n, String g, int a, double h, double w) {
		this.name = n;
		this.gender = g;
		this.age = a;
		this.height = h;
		this.weight = w;
	}
	
	public String getName() { return name; }
	public String getGender() { return gender; }
	public int getAge() { return age; }
	public double getHeight() { return height; }
	public double getWeight() { return weight; }
	
}
