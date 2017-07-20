import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

/*
 *  This class implements a panel where a graph of multiplicities is
 *  shown. 
 */
public class GraphMultiplicity extends JPanel {

	// Default serial version UID
	private static final long serialVersionUID = 1L;

	/* Array of data points to plot */
	private int[] sample;

	// Updates/initialises the data points.
	public void setData(int[] data) {
		sample = data;
	}

	@Override
	public void paintComponent(Graphics g1) {
		super.paintComponent(g1);

		Graphics2D g = (Graphics2D) g1;

		// Optimal distance from the left edge of a window to the left edge of a graph
		final int WIDTH_HORIZONTAL = 100 + 5 * (Integer.toString(findYInterval()).length());

		// Interval of an abscissa
		int intervalX = findXInterval();
		// Interval of an ordinate
		int intervalY = findYInterval();

		// Draw the abscissa and ordinate.
		g.setColor(Color.black);
		g.drawLine(WIDTH_HORIZONTAL, 550, WIDTH_HORIZONTAL + 500, 550);
		g.drawLine(WIDTH_HORIZONTAL, 550, WIDTH_HORIZONTAL, 50);

		// Draw scale bars for the abscissa.
		for (int i = 1; i != 11; i++) {
			g.drawLine(WIDTH_HORIZONTAL + 50 * i, 550, WIDTH_HORIZONTAL + 50 * i, 555);
		}

		// Draw scale bar for the ordinate.
		for (int i = 1; i != 11; i++) {
			g.drawLine(WIDTH_HORIZONTAL - 5, 550 - 50 * i, WIDTH_HORIZONTAL, 550 - 50 * i);
		}

		// Label numbers for the abscissa.
		for (int i = 0; i != 11; i++) {
			String n = Integer.toString(intervalX * i);
			g.drawString(n, WIDTH_HORIZONTAL - 5 + 50 * i, 575);
		}
		// Label numbers for the ordinate.
		for (int i = 0; i != 11; i++) {
			String n = Integer.toString(intervalY * i);
			g.drawString(n, 75, 555 - 50 * i);
		}

		// Plot data points from the array and connect them by lines.
		for (int i = 0; i != sample.length; i++) {
			g.setColor(Color.blue);
			int x = WIDTH_HORIZONTAL + (50 * (2 * i + 4) / intervalX);
			int y = 550 - (int) Math.round(sample[i] * 50 / intervalY);
			g.fillOval(x - 3, y - 3, 4, 4);
		}

		g.setColor(Color.black);
		g.setFont(new Font("Serif", Font.ITALIC, 13));
		g.drawString("Goldbach Number", 225 + WIDTH_HORIZONTAL, 600);
		g.drawString("Multiplicity", 10, 300);
	}

	// Calculates an optimal abscissa-interval.
	public int findXInterval() {
		return (2 * sample.length + 2) / 10 + 1;
	}

	// Calculates an optimal ordinate-interval.
	public int findYInterval() {
		int result = 1;
		int max = 1; // Maximum data value
		for (int i = 0; i != sample.length; i++) {
			max = Math.max(sample[i], max);
		}
		result = (int) Math.ceil(max / 10.0);

		// If the ordinate-interval is larger than 100, take the two most significant
		// digits.
		if (result > 100) {
			int sd = Integer.parseInt(Integer.toString(result).substring(0, 2));
			for (int i = 0; i != Integer.toString(result).length() - 2; i++) {
				sd = sd * 10;
			}
			result = sd;
		}
		return result;
	}
}
