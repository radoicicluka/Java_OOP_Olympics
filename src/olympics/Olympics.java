package olympics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;


public class Olympics {

	private ArrayList<Games> games;
	/**
	 * Events is a list of all events held. An Event is defined as a discipline at one instance of the games,
	 * e.g. An Event would be 2016 Summer Barcelona Games -  Athletics Men's Hammer Throw.
	 */
	private ArrayList<Event> events;
	private TreeMap<Integer, Athlete> athletes;
	
	private int currYear;
	private String currSeason;
	
	public Olympics() {
		games = new ArrayList<Games>();
		events = new ArrayList<Event>();
		athletes = new TreeMap<Integer, Athlete>();
		currYear = 0;
		currSeason = "";
	}
	/**
	 * A function that parses the data from "events.txt" and "athletes.txt".
	 * @param directory - file path to "events.txt" and "athletes.txt"
	 */
	public void parse() {
		try {
			File file = new File("events.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			Stream<String> stream = reader.lines();
			
			stream.forEach(str -> {
				Pattern p = Pattern.compile("(\\d*) (Summer|Winter)!([^!]*)!([^!]*)!([^!]*)!([^!]*)!([^!]*)!([^!]*)!((Gold|Silver|Bronze)?)");
				Matcher m = p.matcher(str);
				
				Games game = null;
				Competitor newCompetitor = null;
				Event event = null;
				if (m.matches()) {
					int year = Integer.parseInt(m.group(1));
					String season = m.group(2);
					String city = m.group(3);
					String sport = m.group(4);
					String discipline = m.group(5);
					boolean isIndividual = (m.group(6).equals("Individual"));
					String country = m.group(7);
					String competitor = m.group(8);
					String medal = m.group(9);
					
					// Creating new instance of Games if either the year or the season had changed
					if (currYear != year || (currSeason.compareTo(season)!=0)) {
						currYear = year;
						currSeason = season;
						game = new Games(year, season, city);
						games.add(game);
					}
					else {
						game = games.get(games.size()-1);
					}
					
					

					// Creating new instance of Competitor if the list of events is empty
					if (events.isEmpty()) {
						event = new Event(discipline, isIndividual, game, sport);
						events.add(event);
					}
					// Creating new instance of Competitor if the discipline has changed
					else if(events.get(events.size() - 1).getDiscipline().compareTo(discipline) != 0) {
						event = new Event(discipline, isIndividual, game, sport);
						events.add(event);
					}
					else {
						event = events.get(events.size()-1);
					}
					
					// Creating new instance of Athlete if they have not been added to the
					// list of all athletes already
					if(isIndividual) {
						if (!(athletes.containsKey(Integer.parseInt(competitor)))) {
							newCompetitor = new Athlete(Integer.parseInt(competitor), country);
							athletes.put(Integer.parseInt(competitor), (Athlete)newCompetitor);
						}
						else {
							newCompetitor = athletes.get(Integer.parseInt(competitor));
						}
						
					}
					// Creating new instance of Team
					else {
						newCompetitor = new Team(country);
						
						// Reading the ID of all athletes in the team
						int i = 0;
						int id;
						boolean readingNumber = false;
						while(i != competitor.length()) {
							// Adding digit by digit to the empty string until the apostrophe is found
							String s = "";
							
							while(Character.isDigit(competitor.charAt(i))) {
								s += competitor.charAt(i);
								readingNumber = true;
								i++;
							}
							if (readingNumber) {
								i--;
								// Check if athlete already exists after the ID has been read 
								readingNumber = false;
								id = Integer.parseInt(s);
								if (!athletes.containsKey(id)) {
									Athlete newA = new Athlete(id, country);
									athletes.put(id, newA);
									((Team)newCompetitor).addAthlete(newA);
								}
								else {
									((Team)newCompetitor).addAthlete(athletes.get(id));
								}
								
							}
							i++;
						}
					}
					// Every competitor (either individual or team) is added to the list of competitors of the current games
					if (game != null && newCompetitor.isTeam()) game.addCompetitor((Team)newCompetitor);
					else if(game != null && (!newCompetitor.isTeam())) game.addCompetitor((Athlete)newCompetitor);
					Medal med;
					if (medal.compareTo("Gold") == 0) med = Medal.Gold;
					else if(medal.compareTo("Silver") == 0) med = Medal.Silver;
					else if (medal.compareTo("Bronze") == 0) med = Medal.Bronze;
					else med = Medal.NoMedal;
					
					if(newCompetitor != null)newCompetitor.addAchivement(event, med);
					
				}
				
			});
			// Reading input from "athletes.txt"
			File file2 = new File("athletes.txt");
			BufferedReader reader2 = new BufferedReader(new FileReader(file2));
			Stream<String> stream2 = reader2.lines();
			stream2.forEach(str -> {
				Pattern p = Pattern.compile("(\\d*)!([^!]*)!(M|F)!([^!]*)!([^!]*)!([^!]*)");
				Matcher m = p.matcher(str);
				
				if (m.matches()) {
					int id = Integer.parseInt(m.group(1));
					String name = m.group(2);
					String gender = m.group(3);
					// Age, height and weight are set to 0 if they are not available
					int age = (m.group(4).compareTo("NA") != 0)? Integer.parseInt(m.group(4)) : 0;
					double height = (m.group(5).compareTo("NA") != 0)? Double.parseDouble(m.group(5)) : 0;
					double weight = (m.group(6).compareTo("NA") != 0)? Double.parseDouble(m.group(6)) : 0;
					if (athletes.containsKey(id)) {
						Athlete a = athletes.get(id);
						a.setInfo(new Info(name, gender, age, height, weight));
					}
				}
			});
			
			reader.close();
			reader2.close();
			currYear = 0;
		}
		catch (FileNotFoundException e) {
			System.err.println("File not found");
		}
		catch(IOException ioe) {}
	}
	/**
	 * A function that parses the data from "events.txt" and "athletes.txt" for a given year only.
	 * @param directory - file path to "events.txt" and "athletes.txt"
	 * @param givenYear - a year with which the data is filtered
	 */
	public void parse(int givenYear) {
		try {
			File file = new File("events.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));
			Stream<String> stream = reader.lines();
			
			stream.forEach(str -> {
				Pattern p = Pattern.compile("(\\d*) (Summer|Winter)!([^!]*)!([^!]*)!([^!]*)!([^!]*)!([^!]*)!([^!]*)!((Gold|Silver|Bronze)?)");
				Matcher m = p.matcher(str);
				
				Games game = null;
				Competitor newCompetitor = null;
				Event event = null;
				if (m.matches()) {
					int year = Integer.parseInt(m.group(1));
					if (year == givenYear) {
						String season = m.group(2);
						String city = m.group(3);
						String sport = m.group(4);
						String discipline = m.group(5);
						boolean isIndividual = (m.group(6).equals("Individual"));
						String country = m.group(7);
						String competitor = m.group(8);
						String medal = m.group(9);
						
						// Creating new instance of Games if either the year or the season had changed
						if (currSeason.compareTo(season)!=0) {
							currSeason = season;
							game = new Games(year, season, city);
							games.add(game);
						}
						else {
							game = games.get(games.size()-1);
						}
						
						// Creating new instance of Competitor if the list of events is empty
						if (events.isEmpty()) {
							event = new Event(discipline, isIndividual, game, sport);
							events.add(event);
						}
						// Creating new instance of Competitor if the discipline has changed
						else if(events.get(events.size() - 1).getDiscipline().compareTo(discipline) != 0) {
							event = new Event(discipline, isIndividual, game, sport);
							events.add(event);
						}
						else event = events.get(events.size()-1);
						// Creating new instance of Athlete if they have not been added to the
						// list of all athletes already
						if(isIndividual) {
							if (!(athletes.containsKey(Integer.parseInt(competitor)))) {
								newCompetitor = new Athlete(Integer.parseInt(competitor), country);
								athletes.put(Integer.parseInt(competitor), (Athlete)newCompetitor);
							}
							else {
								newCompetitor = athletes.get(Integer.parseInt(competitor));
							}
						}
						// Creating new instance of Team
						else {
							newCompetitor = new Team(country);
							
							// Reading the ID of all athletes in the Team
							int i = 0;
							int id;
							boolean readingNumber = false;
							while(i != competitor.length()) {
								// Adding digit by digit to the empty string until the apostrophe is found
								String s = "";
								while(Character.isDigit(competitor.charAt(i))) {
									s += competitor.charAt(i);
									readingNumber = true;
									i++;
								}
								if (readingNumber) {
									i--;
									// Check if athlete already exists after the ID has been read 
									readingNumber = false;
									id = Integer.parseInt(s);
									if (!athletes.containsKey(id)) {
										Athlete newA = new Athlete(id, country);
										athletes.put(id, newA);
										((Team)newCompetitor).addAthlete(newA);
									}
									else {
										((Team)newCompetitor).addAthlete(athletes.get(id));
									}
								}
								i++;
							}
						}
						// Every competitor (either individual or team) is added to the list of competitors of the current games
						if (game != null && newCompetitor.isTeam()) game.addCompetitor((Team)newCompetitor);
						else if(game != null && (!newCompetitor.isTeam())) game.addCompetitor((Athlete)newCompetitor);
						Medal med;
						if (medal.compareTo("Gold") == 0) med = Medal.Gold;
						else if(medal.compareTo("Silver") == 0) med = Medal.Silver;
						else if (medal.compareTo("Bronze") == 0) med = Medal.Bronze;
						else med = Medal.NoMedal;
						if (newCompetitor != null)newCompetitor.addAchivement(event, med);
					}
				}
				
			});
			// Reading input from "athletes.txt"
			File file2 = new File("athletes.txt");
			BufferedReader reader2 = new BufferedReader(new FileReader(file2));
			Stream<String> stream2 = reader2.lines();
			
			stream2.forEach(str -> {
				Pattern p = Pattern.compile("(\\d*)!([^!]*)!(M|F)!([^!]*)!([^!]*)!([^!]*)");
				Matcher m = p.matcher(str);
				
				
				if (m.matches()) {
					int id = Integer.parseInt(m.group(1));
					String name = m.group(2);
					String gender = m.group(3);
					// Age, height and weight are set to 0 if they are not available
					int age = (m.group(4).compareTo("NA") != 0)? Integer.parseInt(m.group(4)) : 0;
					double height = (m.group(5).compareTo("NA") != 0)? Double.parseDouble(m.group(5)) : 0;
					double weight = (m.group(6).compareTo("NA") != 0)? Double.parseDouble(m.group(6)) : 0;
					if (athletes.containsKey(id)) {
						Athlete a = athletes.get(id);
						a.setInfo(new Info(name, gender, age, height, weight));
					}
				}
			});
			
			reader.close();
			reader2.close();
		}
		catch (FileNotFoundException e) {
			System.err.println("File not found");
		}
		catch(IOException ioe) {}
		
		
	}
	
	// filter
	/**
	 * Getter for the parametar games.
	 * @return  value is type ArrayList<Games> and represents an array of all olympic games held 
	 */
	public ArrayList<Games> getGames() {
		return games;
	}

	public ArrayList<Event> getEvents() {
		return events;
	}

	public TreeMap<Integer, Athlete> getAthletes() {
		return athletes;
	}
	
	
	/**
	 * Function which makes a copy of list of athletes and filters it, leaving only players of given sport in the list.
	 * @param ath - map of athletes and their IDs
	 * @param sport - given sport for which the list will be filtered
	 * @return - return filtered map of athletes and their IDs
	 */
	public TreeMap<Integer, Athlete> filterAthletesBySport(TreeMap<Integer, Athlete> ath, String sport) {
		TreeMap<Integer, Athlete> a = new TreeMap<>();
		
		for (Entry<Integer, Athlete> entry : ath.entrySet()) {
			for (int i = 0; i < entry.getValue().achivementsSize(); i++) {
				if (entry.getValue().getSport(i).compareTo(sport) == 0) {
					a.put(entry.getKey(), entry.getValue());
				}
			}
		}
		return a;
	}
	
	public TreeMap<Integer, Athlete> filterAthletesByYear(TreeMap<Integer, Athlete> ath, int year){
		TreeMap<Integer, Athlete> a = new TreeMap<>();
		for (Entry<Integer, Athlete> entry : ath.entrySet()) {
			if(entry.getValue().competedInGivenYear(year)) {
				a.put(entry.getKey(), entry.getValue());
			}
		}
		return a;
	}
	
	public TreeMap<Integer, Athlete> filterAthletesByType(TreeMap<Integer, Athlete> ath, String type){
		TreeMap<Integer, Athlete> a = new TreeMap<>();
		boolean isInd;
		if (type.compareTo("Individual") == 0) isInd = true;
		else isInd = false;
		
		for (Entry<Integer, Athlete> entry : ath.entrySet()) {
			if(entry.getValue().competedIndividualy() && isInd) {
				a.put(entry.getKey(), entry.getValue());
			}
			if (entry.getValue().competedInTeam() && !isInd) {
				a.put(entry.getKey(), entry.getValue());
			}
		}
		return a;
	}
	
	public TreeMap<Integer, Athlete> filterAthletesByMedal(TreeMap<Integer, Athlete> ath, String medal){
		TreeMap<Integer, Athlete> a = new TreeMap<>();
		Medal med;
		if (medal.compareTo("Gold") == 0) med = Medal.Gold;
		else if(medal.compareTo("Silver") == 0) med = Medal.Silver;
		else if (medal.compareTo("Bronze") == 0) med = Medal.Bronze;
		else med = Medal.NoMedal;
		
		for (Entry<Integer, Athlete> entry : ath.entrySet()) {
			if(entry.getValue().wonGivenMedal(med)) a.put(entry.getKey(), entry.getValue());
		}
		return a;
	}
	
	public ArrayList<String> countries(){
		ArrayList<String> c = new ArrayList<>();
		for (Entry<Integer, Athlete> entry : athletes.entrySet()) {
			if (!c.contains(entry.getValue().getCountry())) c.add(entry.getValue().getCountry());
		}
		return c;
	}
	
	public ArrayList<Event> filterEventsBySport(ArrayList<Event> evs, String sport){
		ArrayList<Event> output = new ArrayList<>();
		for (Event ev : evs) {
			if (ev.getSport().compareTo(sport) == 0) output.add(ev);
		}
		return output;
	}
	
	public ArrayList<Event> filterEventsByType(ArrayList<Event> evs, String type){
		ArrayList<Event> output = new ArrayList<>();
		boolean isInd;
		if (type.compareTo("Individual") == 0) isInd = true;
		else isInd = false;
		
		for (Event ev : evs) {
			if (ev.getType() && isInd) output.add(ev);
			if (!ev.getType() && !isInd) output.add(ev);
		}
		return output;
	}


	
}
