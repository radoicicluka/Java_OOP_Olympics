package olympics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class PieChartPanel extends JPanel {
	
	private ArrayList<Slice> slices = new ArrayList<>();
	private ArrayList<CountryString> names = new ArrayList<>();
	
	private TreeMap<Integer, Athlete> ath;

	private Color[] colors = {Color.blue, Color.cyan, Color.green, 
			Color.yellow, Color.orange, Color.pink, Color.red, 
			Color.magenta, Color.gray, Color.darkGray};
	
	
	public PieChartPanel() {
		setLayout(null);
		
	}
	
	
	public void setAth(TreeMap<Integer, Athlete> ath) {
		this.ath = ath;
	}

	private void drawPie(Graphics2D g, Rectangle area) {
		ArrayList<PairCountry> p = filterAndSortByCountry(ath);
		ArrayList<String> countries = new ArrayList<>();
		emptySlices();
		int total = 0;
		for (int i = 0; i < p.size(); i++) {
			total += p.get(i).getNumber();
		}
		
		int j = 0;
		int curr = 0;
		for (int i = p.size() - 1; i > p.size() - ((p.size()>9)?8:p.size()-1); i--) {
			slices.add(new Slice(p.get(i).getNumber(), colors[j]));
			countries.add(p.get(i).getCountry());
			curr += p.get(i).getNumber();
			j++;
		}

		if (total - curr > 0) {
		countries.add("Other");
		slices.add(new Slice(total - curr, colors[j]));
		}
		double lastVal = 0;
		int startAngle = 0;
		int arcAngle;
		for (int i = 0; i < slices.size(); i++) {
			startAngle = (int)(lastVal * 360 / total);
			arcAngle = (int)(slices.get(i).val * 360 / total);
			g.setColor(slices.get(i).color);
			g.fillArc(area.x - 180, area.y + 25, area.width - 100, area.height - 100, startAngle, arcAngle + 1);

			lastVal += slices.get(i).val;
			
			int nameX = area.x - 10 + (int)((area.width - 100)/2) + (int)(Math.cos((startAngle + arcAngle / 2) * 2 * Math.PI / 360) * (160));
			int nameY = area.y + (int)((area.height - 100)/2) + (int)(Math.sin((startAngle + arcAngle / 2) * 2 * Math.PI / 360) * (-160));

			
			g.setColor(Color.black);

			g.drawString(countries.get(i), nameX - 180, nameY + 25);

		}
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		drawPie((Graphics2D)g, new Rectangle(225, 0, 400, 400));
	}
	/**
	 * Function which takes in a map of athletes and their IDs and returns a sorted list of pairs (Country, Number of contestants).
	 * This functions use is to gather and sort data that is going to be presented in a pie chart.
	 * @param ath - map of athletes and their IDs as keys
	 * @return list of pairs (Country, Number of contestants)
	 */
	public ArrayList<PairCountry> filterAndSortByCountry(TreeMap<Integer, Athlete> ath){
		ArrayList<PairCountry> a = new ArrayList<>();
		for (Entry<Integer, Athlete> entry : ath.entrySet()) {
			if (countryIndex(a, entry.getValue().getCountry()) != -1){
				a.get(countryIndex(a, entry.getValue().getCountry())).inc();
			}
			else {
				a.add(new PairCountry(entry.getValue().getCountry()));
			}
		}
		
		a.sort(null);
		
		return a;
	}
	
	public int countryIndex(ArrayList<PairCountry> list, String c) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getCountry().compareTo(c) == 0) return i;
		}
		return -1;
	}
	
	public void emptySlices() {
		slices.clear();
	}
	
	public void emptyNames() {
		names.clear();
	}
	
	
}
