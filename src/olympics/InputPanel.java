package olympics;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class InputPanel extends JPanel {
	
	private Olympics olympics;
	private PieChartPanel pie;
	private XYGraphPanel xy;

	private String[] options = {"All years", "1896", "1900", "1904", "1906", "1908", "1912", 
			"1920", "1924", "1928", "1932", "1936", "1948", "1952", "1956", "1960", "1964", 
			"1968", "1972", "1976", "1980", "1984", "1988", "1992", "1994", "1996", "1998", 
			"2000", "2002", "2004", "2006", "2008", "2010", "2012", "2014", "2016"};
	
	private String[] yearOpt = {"1896", "1900", "1904", "1906", "1908", "1912", 
			"1920", "1924", "1928", "1932", "1936", "1948", "1952", "1956", "1960", "1964", 
			"1968", "1972", "1976", "1980", "1984", "1988", "1992", "1994", "1996", "1998", 
			"2000", "2002", "2004", "2006", "2008", "2010", "2012", "2014", "2016"};
	
	private String[] typeOpt = {"Individual", "Team"};
	
	private String[] medalOpt = {"Gold", "Silver", "Bronze", "No medal"};
	
	public InputPanel(Olympics ol) {
		this.olympics = ol;
		setLayout(null);
		
		int a = 10;
		
		JButton parseButton = new JButton("Parse");
		parseButton.setBounds(a, 10, 95, 20);
		JComboBox<String> drop = new JComboBox<>(options);
		drop.setBounds(a + 105, 10, 95, 20);
		

		JLabel actionLabel = new JLabel("ACTION", JLabel.CENTER);
		int x = 35;
		actionLabel.setBounds(a, x + 5, 200, 20);
		
		JRadioButton numButton = new JRadioButton("Number of contestants");
		numButton.setSelected(true);

		JRadioButton disButton = new JRadioButton("Number of disciplines");
		JRadioButton heightButton = new JRadioButton("Average height of contestants");
		JRadioButton weightButton = new JRadioButton("Average weight of contestants");
		
		numButton.setBounds(a, x + 30, 200, 20);
		disButton.setBounds(a, x + 55, 200, 20);
		heightButton.setBounds(a, x + 80, 200, 20);
		weightButton.setBounds(a, x + 105, 200, 20);

		ButtonGroup group = new ButtonGroup();
		group.add(numButton);
		group.add(disButton);
		group.add(heightButton);
		group.add(weightButton);
		
		JLabel filterLabel = new JLabel("FILTER", JLabel.CENTER);
		int y = 170;
		
		filterLabel.setBounds(a, y + 5, 200, 20);
		
		JCheckBox sportButton = new JCheckBox("By sport");
		JCheckBox yearButton = new JCheckBox("By year");
		JCheckBox typeButton = new JCheckBox("By type");
		JCheckBox medalButton = new JCheckBox("By medal");
		
		sportButton.setBounds(a, y + 30, 100, 20);
		yearButton.setBounds(a, y + 55, 100, 20);
		typeButton.setBounds(a, y + 80, 100, 20);
		medalButton.setBounds(a, y + 105, 100, 20);
		
		JTextField sportInput = new JTextField("Input sport");
		sportInput.setBounds(a + 100, y + 30, 100, 20);
		
		JComboBox<String> yearDrop = new JComboBox<>(yearOpt);
		yearDrop.setBounds(a + 100, y + 55, 100, 20);
		
		JComboBox<String> typeDrop = new JComboBox<>(typeOpt);
		typeDrop.setBounds(a + 100, y + 80, 100, 20);
		
		JComboBox<String> medalDrop = new JComboBox<>(medalOpt);
		medalDrop.setBounds(a + 100, y + 105, 100, 20);
		
		
		
		JButton applyButton = new JButton("Apply");
		applyButton.setBounds(a, 325, 200, 20);
		
		
		add(parseButton);
		add(drop);
		add(actionLabel);
		add(numButton);
		add(disButton);
		add(heightButton);
		add(weightButton);
		add(filterLabel);
		add(sportButton);
		add(yearButton);
		add(typeButton);
		add(medalButton);
		add(yearDrop);
		add(typeDrop);
		add(medalDrop);
		add(sportInput);
		add(applyButton);
		
		numButton.setEnabled(false);
		disButton.setEnabled(false);
		heightButton.setEnabled(false);
		weightButton.setEnabled(false);
		
		sportButton.setEnabled(false);
		yearButton.setEnabled(false);
		typeButton.setEnabled(false);
		medalButton.setEnabled(false);
		
		sportInput.setEnabled(false);
		yearDrop.setEnabled(false);
		typeDrop.setEnabled(false);
		medalDrop.setEnabled(false);

		applyButton.setEnabled(false);
		
		
		
		parseButton.addActionListener(ae -> {
			
			parseButton.setEnabled(false);
			drop.setEnabled(false);
			applyButton.setEnabled(true);
			
			numButton.setEnabled(true);
			disButton.setEnabled(true);
			heightButton.setEnabled(true);
			weightButton.setEnabled(true);
			
			sportButton.setEnabled(true);
			typeButton.setEnabled(true);
			medalButton.setEnabled(true);
			
			sportInput.setEnabled(true);
			typeDrop.setEnabled(true);
			medalDrop.setEnabled(true);
			
			
			if (((String) drop.getSelectedItem()).compareTo("All years") != 0) {
				yearButton.setEnabled(false);
				yearDrop.setEnabled(false);
				olympics.parse(Integer.parseInt((String)drop.getSelectedItem()));
			}
			else {
				yearButton.setEnabled(true);
				yearDrop.setEnabled(true);
				olympics.parse();
			}
		});
		
		numButton.addActionListener(ae ->{
			sportButton.setEnabled(true);
			typeButton.setEnabled(true);
			medalButton.setEnabled(true);
			
			sportInput.setEnabled(true);
			typeDrop.setEnabled(true);
			medalDrop.setEnabled(true);
			
			if (((String) drop.getSelectedItem()).compareTo("All years") != 0) {
				yearButton.setEnabled(false);
				yearDrop.setEnabled(false);
			}
			else {
				yearButton.setEnabled(true);
				yearDrop.setEnabled(true);
			}
			
			sportButton.setSelected(false);
			yearButton.setSelected(false);
			typeButton.setSelected(false);
			medalButton.setSelected(false);
		});
		
		disButton.addActionListener(ae ->{
			sportButton.setEnabled(true);
			yearButton.setEnabled(false);
			typeButton.setEnabled(true);
			medalButton.setEnabled(false);
			
			sportInput.setEnabled(true);
			yearDrop.setEnabled(false);
			typeDrop.setEnabled(true);
			medalDrop.setEnabled(false);

			sportButton.setSelected(false);
			yearButton.setSelected(false);
			typeButton.setSelected(false);
			medalButton.setSelected(false);
		});
		
		heightButton.addActionListener(ae ->{
			sportButton.setEnabled(true);
			yearButton.setEnabled(false);
			typeButton.setEnabled(true);
			medalButton.setEnabled(true);
			
			sportInput.setEnabled(true);
			yearDrop.setEnabled(false);
			typeDrop.setEnabled(true);
			medalDrop.setEnabled(true);

			sportButton.setSelected(false);
			yearButton.setSelected(false);
			typeButton.setSelected(false);
			medalButton.setSelected(false);
		});
		
		weightButton.addActionListener(ae ->{
			sportButton.setEnabled(true);
			yearButton.setEnabled(false);
			typeButton.setEnabled(true);
			medalButton.setEnabled(true);
			
			sportInput.setEnabled(true);
			yearDrop.setEnabled(false);
			typeDrop.setEnabled(true);
			medalDrop.setEnabled(true);
			
			sportButton.setSelected(false);
			yearButton.setSelected(false);
			typeButton.setSelected(false);
			medalButton.setSelected(false);
		});
		
		
		applyButton.addActionListener(ae -> {
			xy.setVisible(false);
			pie.setVisible(false);
			
			if (numButton.isSelected()) {
				TreeMap<Integer, Athlete> ath = (TreeMap<Integer, Athlete>) olympics.getAthletes().clone();
				
				if (sportButton.isSelected() && sportInput.getText().compareTo("Input sport") != 0) {
					ath = olympics.filterAthletesBySport(ath, sportInput.getText());
				}
				if (yearButton.isSelected()) {
					int year = Integer.parseInt((String)yearDrop.getSelectedItem());
					ath = olympics.filterAthletesByYear(ath, year);
				}
				
				if (typeButton.isSelected()) {
					String type = (String)typeDrop.getSelectedItem();
					ath = olympics.filterAthletesByType(ath, type);
				}
				
				if (medalButton.isSelected()) {
					String medal = (String)medalDrop.getSelectedItem();
					ath = olympics.filterAthletesByMedal(ath, medal);
				}
				pie = getPie();
				pie.setAth(ath);
				
				pie.repaint();
				paintComponent(getGraphics());
				pie.setVisible(true);
			}
			
			else if (disButton.isSelected()) {
				ArrayList<Event> evs = (ArrayList<Event>)olympics.getEvents().clone();
				
				if (sportButton.isSelected() && sportInput.getText().compareTo("Input sport") != 0) {
					evs = olympics.filterEventsBySport(evs, sportInput.getText());
				}
				if (typeButton.isSelected()) {
					String type = (String)typeDrop.getSelectedItem();
					evs = olympics.filterEventsByType(evs, type);
				}
				
				xy = getXY();
				xy.setEvents(evs);
				xy.setGraphEvents();
				xy.setEvents(evs);
				
				xy.repaint();
				paintComponent(getGraphics());
				xy.setVisible(true);
			}
			
			else if (heightButton.isSelected()) {
				TreeMap<Integer, Athlete> ath = (TreeMap<Integer, Athlete>) olympics.getAthletes().clone();
				if (sportButton.isSelected() && sportInput.getText().compareTo("Input sport") != 0) {
					ath = olympics.filterAthletesBySport(ath, sportInput.getText());
				}
				
				if (typeButton.isSelected()) {
					String type = (String)typeDrop.getSelectedItem();
					ath = olympics.filterAthletesByType(ath, type);
				}
				
				if (medalButton.isSelected()) {
					String medal = (String)medalDrop.getSelectedItem();
					ath = olympics.filterAthletesByMedal(ath, medal);
				}
				xy = getXY();
				xy.setAth(ath);
				xy.setGraphHeight();
				
				
				xy.repaint();
				paintComponent(getGraphics());
				xy.setVisible(true);
				
			}
			
			else if (weightButton.isSelected()) {
				TreeMap<Integer, Athlete> ath = (TreeMap<Integer, Athlete>) olympics.getAthletes().clone();
				if (sportButton.isSelected() && sportInput.getText().compareTo("Input sport") != 0) {
					ath = olympics.filterAthletesBySport(ath, sportInput.getText());
				}
				
				if (typeButton.isSelected()) {
					String type = (String)typeDrop.getSelectedItem();
					ath = olympics.filterAthletesByType(ath, type);
				}
				
				if (medalButton.isSelected()) {
					String medal = (String)medalDrop.getSelectedItem();
					ath = olympics.filterAthletesByMedal(ath, medal);
				}
				xy = getXY();
				xy.setGraphWeight();
				xy.setAth(ath);
				
				xy.repaint();
				paintComponent(getGraphics());
				xy.setVisible(true);
			}
			paintComponent(getGraphics());

		});
		
	}
	
	private XYGraphPanel getXY() {
		return xy;
	}

	public PieChartPanel getPie() {
		return pie;
	}

	public void setPie(PieChartPanel pie) {
		this.pie = pie;
	}
	

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public void setXY(XYGraphPanel xy) {
		this.xy = xy;
		
	}

	
}
