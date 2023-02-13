package olympics;

import java.util.ArrayList;

public class Team extends Competitor {

	private ArrayList<Athlete> athletes = new ArrayList<>();
	
	public Team(String country) { 
		super();
		this.country = country; 
	}
	public void addAthlete(Athlete ath) { athletes.add(ath); }
	public int size() { return athletes.size(); }
	public ArrayList<Athlete> getAthletes() { return athletes; }
	
	@Override
	public void addAchivement(Event event, Medal medal) {
		for (Athlete a : athletes) {
			a.addAchivement(event, medal);
		}
	}
	@Override
	public boolean isTeam() {
		return true;
	}
	
	
}
