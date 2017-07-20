import java.awt.BorderLayout;

import javax.swing.JFrame;

/** 
 * This class corresponds to a view in the MVC architecture. It creates a GUI
 * to display a graph of multiplicities.  
 * */
public class GraphFrame {
	
    private JFrame frame = new JFrame("Graph of Multiplicity");
	private GraphMultiplicity graph = new GraphMultiplicity();
	
	public void activate() {
		frame.getContentPane().add(BorderLayout.CENTER, graph);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(700, 700);
		frame.setVisible(true);
	}
	
	// Updates/initialises the data points.
	public void setData(int[] data) {
		graph.setData(data);
	}
}
