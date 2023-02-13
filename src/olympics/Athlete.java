package olympics;

import java.util.ArrayList;

public class Athlete extends Competitor {

	private int id;
	private Info info;
	
	private ArrayList<Pair> achivements;
	
	public Athlete(int id, String country) {
		super();
		this.id = id;
		this.country = country;
		achivements = new ArrayList<Pair>();
	}
	
	public void setInfo(Info i) { this.info = i; }
	
	public int getId() { return id; }
	public String getName() { return info.getName(); }
	public String getGender() {return info.getGender(); }
	public int getAge() {return info.getAge(); }
	public double getHeight() {return info.getHeight(); }
	public double getWeight() {return info.getWeight(); }
	public String getSport(int i) { return achivements.get(i).getEvent().getSport(); }
	public boolean getType(int i) { return achivements.get(i).getEvent().getType(); }
	
	public boolean competedInGivenYear(int year) {
		for (Pair p : achivements) {
			if (p.getEvent().getYear() == year) return true;
		}
		return false;
	}
	
	public boolean competedIndividualy() {
		for (Pair p : achivements) {
			if (p.getEvent().getType()) return true;
		}
		return false;
	}
	
	public boolean competedInTeam() {
		for (Pair p : achivements) {
			if (!p.getEvent().getType()) return true;
		}
		return false;
	}
	
	public boolean wonGivenMedal(Medal medal) {
		for (Pair p : achivements) {
			if (p.getMedal() == medal) return true;
		}
		return false;
	}

	@Override
	public void addAchivement(Event event, Medal medal) {
		achivements.add(new Pair(event, medal));
	}

	@Override
	public boolean isTeam() {
		return false;
	}
	
	public int achivementsSize() { return achivements.size(); }
	
	public ArrayList<Pair> getAchivements() {return achivements; }
	
	
	
	
	
}
