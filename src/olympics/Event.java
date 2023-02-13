package olympics;

public class Event {

	private String discipline;
	private boolean isIndividual;
	private Games games;
	private String sport;
	
	
	public Event(String discipline, boolean isIndividual, Games games, String sport) {
		this.discipline = discipline;
		this.isIndividual = isIndividual;
		this.games = games;
		this.sport = sport;
	}
	
	
	
	public String getDiscipline() { return discipline; }
	public String getSport() { return sport; }
	public boolean getType() { return isIndividual; }
	public int getYear() { return games.getYear(); }
	public String getSeason() {return games.getSeason(); }
	
}
