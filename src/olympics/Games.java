package olympics;

import java.util.ArrayList;

public class Games {
	
	private int year;
	private String season;
	private String city;
	private ArrayList<Athlete> players;
	
	public Games(int year, String season, String city) {
		this.year = year;
		this.season = season;
		this.city = city;
		this.players = new ArrayList<Athlete>();
	}
	
	public String name() {
		String name = season;
		name.concat(" ").concat(Integer.toString(year));
		return name;
	}
	
	public int getYear() { return year; }
	public void addCompetitor(Athlete player) { players.add(player); }
	public void addCompetitor(Team team) {
		for (Athlete a : team.getAthletes()) {
			addCompetitor(a);
		}
	}
	public String getCity() { return city; }
	public String getSeason() { return season; }
}
