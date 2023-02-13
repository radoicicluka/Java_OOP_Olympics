package olympics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class XYGraphPanel extends JPanel {
	
	private ArrayList<Point> summer = new ArrayList<>();
	private ArrayList<Point> winter = new ArrayList<>();
	
	private boolean graphEvents = false;
	private boolean graphHeight = false;
	private boolean graphWeight = false;
	
	private ArrayList<Event> evs;
	private TreeMap<Integer, Athlete> ath;
	
	public XYGraphPanel() {
		setLayout(null);
	}
	
	/**
	 * Function that draws a graph with years from 1890 to 2030 on the X-Axis and requested data on the Y-Axis.
	 * After drawing the axis', the function iterates trough list summer and winter to draw data points.
	 * @param g - Graphics parameter
	 * @param area - Surrounding area in which the graph will be drawn
	 */
	public void drawGraph(Graphics2D g, Rectangle area) {
		if(graphEvents) convertEventsToPoints();
		else if(graphHeight) convertAthletesToPointsH();
		else if (graphWeight) convertAthletesToPointsW();
				
		double maxY = 0;
		for (int i = 0; i < summer.size(); i++) {
			maxY = (maxY < summer.get(i).getY())? summer.get(i).getY() : maxY;
		}
		for (int i = 0; i < winter.size(); i++) {
			maxY = (maxY < winter.get(i).getY())? winter.get(i).getY() : maxY;
		}
		
		
		
		g.drawLine(280, 25, 280, 315);
		g.drawLine(280, 315, 570, 315);
		g.drawLine(280,  25, 570, 25);
		g.drawLine(570,  25,  570,  315);
		
		// Line tick 1900
		g.drawLine(300, 315, 300, 320);
		g.drawString("1900", 285, 335);
		
		// Line tick 1920
		g.drawLine(340, 315, 340, 320);
		g.drawString("1920", 325, 335);
		
		// Line tick 1940
		g.drawLine(380, 315, 380, 320);
		g.drawString("1940", 365, 335);
		
		// Line tick 1960
		g.drawLine(420, 315, 420, 320);
		g.drawString("1960", 405, 335);
		
		// Line tick 1980
		g.drawLine(460, 315, 460, 320);
		g.drawString("1980", 445, 335);
		
		// Line tick 2000
		g.drawLine(500, 315, 500, 320);
		g.drawString("2000", 485, 335);
		
		// Line tick 2020
		g.drawLine(540, 315, 540, 320);
		g.drawString("2020", 525, 335);
		
		
		g.drawLine(280,  315,  275,  315);
		g.drawString(Integer.toString(0), 260, 315);
		
		int i = 1;
		int num = 0;
		double numD = 0;
		if(maxY > 200) num = 50;
		else if(maxY > 100) num = 20;
		else if(maxY > 50) num = 10;
		else if(maxY > 5) num = 1;
		else numD = 0.5;
		
		// At any given time, only num or numD can have value greater than 0, 
		// so the other one that is equal to zero won't effect the sum
		while (i * (num + numD) <= maxY) {
			int j = 315 - (int)((i * (num + numD) / maxY * 280));
			g.drawLine(275,  j,  280,  j);
			double val = (num + numD) * i;
			if (num == 0) {
				g.drawString(Double.toString(val), 255, j + 5);
			}
			else g.drawString(Integer.toString((int)val), 255, j + 5);
			i++;
		}
		
		
		
		g.setColor(Color.BLUE);
		for (Point p : winter) {
			int x = ((p.getX() - 1890) * 2 + 280);
			int y = (int)(315 - (p.getY() / maxY) * 280);
			g.fillOval(x - 2, y - 2, 5, 5); 
			// x and y point to upper left corner, not the centre
		}
		g.setColor(Color.RED);
		for (Point p : summer) {
			int x = ((p.getX() - 1890) * 2 + 280);
			int y = (int)(315 - (p.getY() / maxY) * 280);
			g.fillOval(x - 2, y - 2, 5, 5); 
			// x and y point to upper left corner, not the centre
		}
		
		
		graphEvents = false;
		graphHeight = false;
		graphWeight = false;
	}
	
	


	/**
	 * Function that differentiates data based on season, 
	 * and sets up list summer and winter for drawing the graph.
	 */
	public void convertEventsToPoints() {
		summer.clear();
		winter.clear();
		for (Event ev : evs) {
			if (summerContains(ev.getYear()) && ev.getSeason().compareTo("Summer")==0) {
				int index = getIndex(ev.getYear(), summer);
				if (index > -1) {
					summer.get(index).inc();
				}
			}
			else if (winterConstains(ev.getYear()) && ev.getSeason().compareTo("Winter")==0) {
				int index = getIndex(ev.getYear(), winter);
				if (index > -1) {
					winter.get(index).inc();
				}
			}
			else if (ev.getSeason().compareTo("Summer") == 0) {
				summer.add(new Point(ev.getYear(), 1));
			}
			else {
				winter.add(new Point(ev.getYear(), 1));
			}
		}
	}
	
	public void convertAthletesToPointsH() {
		summer.clear();
		winter.clear();
		
		ArrayList<YearInfo> sum = new ArrayList<>();
		ArrayList<YearInfo> win = new ArrayList<>();
		ArrayList<Integer> knownYearsSum = new ArrayList<>();
		ArrayList<Integer> knownYearsWin = new ArrayList<>();
		for (Entry<Integer, Athlete> a : ath.entrySet()) {
			for (Pair p : a.getValue().getAchivements()) {
				Event ev = p.getEvent();
				if (knownYearsSum.contains(ev.getYear()) && ev.getSeason().compareTo("Summer")==0) {
					int index = getIndexYI(ev.getYear(), sum);
					if (index > -1 && a.getValue().getHeight() > 0) {
						sum.get(index).add(a.getValue().getHeight());
					}
				}
				else if (knownYearsWin.contains(ev.getYear()) && ev.getSeason().compareTo("Winter")==0) {
					int index = getIndexYI(ev.getYear(), win);
					if (index > -1 && a.getValue().getHeight() > 0) {
						win.get(index).add(a.getValue().getHeight());
					}					
				}
				else if (ev.getSeason().compareTo("Summer") == 0) {
					if (a.getValue().getHeight() > 0) {
						sum.add(new YearInfo(ev.getYear(), a.getValue().getHeight()));
						knownYearsSum.add(ev.getYear());
					}
				}
				else {
					if (a.getValue().getHeight() > 0)win.add(new YearInfo(ev.getYear(), a.getValue().getHeight()));
					knownYearsWin.add(ev.getYear());
				}
			}
		}
		for (YearInfo y : sum) {
			summer.add(new Point(y.getYear(), y.getTotal()/y.getNum()));
		}
		for (YearInfo y : win) {
			winter.add(new Point(y.getYear(), y.getTotal()/y.getNum()));
		}
	}
	
	private void convertAthletesToPointsW() {
		summer.clear();
		winter.clear();
		
		ArrayList<YearInfo> sum = new ArrayList<>();
		ArrayList<YearInfo> win = new ArrayList<>();
		ArrayList<Integer> knownYearsSum = new ArrayList<>();
		ArrayList<Integer> knownYearsWin = new ArrayList<>();
		for (Entry<Integer, Athlete> a : ath.entrySet()) {
			for (Pair p : a.getValue().getAchivements()) {
				Event ev = p.getEvent();
				if (knownYearsSum.contains(ev.getYear()) && ev.getSeason().compareTo("Summer")==0) {
					int index = getIndexYI(ev.getYear(), sum);
					if (index > -1 && a.getValue().getWeight() > 0) {
						sum.get(index).add(a.getValue().getWeight());
					}
				}
				else if (knownYearsWin.contains(ev.getYear()) && ev.getSeason().compareTo("Winter")==0) {
					int index = getIndexYI(ev.getYear(), win);
					if (index > -1 && a.getValue().getWeight() > 0) {
						win.get(index).add(a.getValue().getWeight());
					}					
				}
				else if (ev.getSeason().compareTo("Summer") == 0) {
					if (a.getValue().getWeight() > 0) {
						sum.add(new YearInfo(ev.getYear(), a.getValue().getWeight()));
						knownYearsSum.add(ev.getYear());
					}
				}
				else {
					if (a.getValue().getWeight() > 0)win.add(new YearInfo(ev.getYear(), a.getValue().getWeight()));
					knownYearsWin.add(ev.getYear());
				}
			}
		}
		for (YearInfo y : sum) {
			summer.add(new Point(y.getYear(), y.getTotal()/y.getNum()));
		}
		for (YearInfo y : win) {
			winter.add(new Point(y.getYear(), y.getTotal()/y.getNum()));
		}
	}
	
	public boolean summerContains(int year) {
		if (summer.isEmpty()) return false;
		for (Point p : summer) {
			if (p.getX() == year) return true;
		}
		return false;
	}
	
	public boolean winterConstains(int year) {
		if (winter.isEmpty()) return false;
		for (Point p : winter) {
			if (p.getX() == year) return true;
		}
		return false;
	}
	
	public int getIndex(int year, ArrayList<Point> season) {
		if (season.isEmpty()) return -1;
		for (int i = 0; i < season.size(); i++) {
			if (season.get(i).getX() == year) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Function looks for an index of given year in an input list of information for every year.
	 * @param year - a year for which the index was requested
	 * @param season - a list that will be iterated trough
	 * @return index the year was found and -1 instead
	 */
	public int getIndexYI(int year, ArrayList<YearInfo> season) {
		if (season.isEmpty()) return -1;
		for (int i = 0; i < season.size(); i++) {
			if (season.get(i).getYear() == year) {
				return i;
			}
		}
		return -1;
	}
	
	
	public void setEvents(ArrayList<Event> evs) {
//		evs.clear();
		this.evs = evs;
	}
	
	public void setAth(TreeMap<Integer, Athlete> ath) {
		this.ath = ath;
	}
	
	
	
	public void setGraphEvents() {
		this.graphEvents = true;
	}


	public void setGraphHeight() {
		this.graphHeight = true;
	}


	public void setGraphWeight() {
		this.graphWeight = true;
	}
	
	
	
	


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawGraph((Graphics2D)g, new Rectangle(270, 25, 300, 300));
	}

	
	
}
