package olympics;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class Main {

	private Olympics olympics = new Olympics();	
	
	private JFrame frame;
	private InputPanel in;
	
	public Main() {
		frame = new JFrame("The Olympic Games");
		frame.setSize(675, 400);
		
		in = new InputPanel(olympics);
		in.setBounds(0, 0, 225, 400);
		frame.add(in);
		
		PieChartPanel pie = new PieChartPanel();
		pie.setBounds(225, 0, 400, 400);
		pie.setVisible(false);
		
		XYGraphPanel xy = new XYGraphPanel();
		xy.setBounds(270, 25, 300, 300);
		xy.setVisible(false);
		
		in.setPie(pie);
		in.setXY(xy);
		frame.add(pie);
		frame.add(xy);
		
		frame.setVisible(true);
		frame.setResizable(false);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int yes = JOptionPane.showConfirmDialog(null,  "Are you sure you want to exit the programme?", "Exit Programme Message Box", JOptionPane.YES_NO_OPTION);
				if (yes == JOptionPane.YES_OPTION) {
					frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				}
				else if (yes == JOptionPane.NO_OPTION) {
					frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
				}
			}
		});
	}
	
	public static void main(String[] args) {
		new Main();
	}
	
	
	
	
}
