package olympics;

public class Pair {

	private Event event;
	private Medal medal;
	
	public Pair(Event event, Medal medal) {
		this.event = event;
		this.medal = medal;
	}
	
	public Event getEvent() { return event; }
	public void setEvent(Event event) { this.event = event; }
	public Medal getMedal() { return medal; }
	public void setMedal(Medal medal) { this.medal = medal; }
	
	
}
