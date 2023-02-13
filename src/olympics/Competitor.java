package olympics;


public abstract class Competitor {

	protected String country;
	
	protected Competitor() {
		
	}
	
	public String getCountry() { return country; }
	
	public abstract void addAchivement(Event event, Medal medal);
	
	public abstract boolean isTeam();
	
}
