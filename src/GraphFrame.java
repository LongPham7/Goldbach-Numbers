import java.awt.BorderLayout;

import javax.swing.JFrame;

public class GraphFrame {
	
    private JFrame frame = new JFrame("Graph of Multiplicity");
	private GraphMultiplicity graph = new GraphMultiplicity();
	
	public void activate() {
		frame.getContentPane().add(BorderLayout.CENTER, graph);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(700, 700);
		frame.setVisible(true);
	}
	
	public void setData(int[] data) {
		graph.sample = data;
	}
}
