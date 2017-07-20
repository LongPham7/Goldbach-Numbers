import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.*;

/**
 * This class acts as a view in the MVC architecture, creating GUI components
 * for the main frame that pops up first. This frame has two buttons. One of
 * them users see a list of the multiplicities of even numbers from 4 to 2002.
 * The other button displays a graph of the multiplicities.
 */
public class AppFrame {

	// Number of prime numbers in the file
	private final int NUMBER_PRIME = 2500;

	// Array of the first 2500 prime numbers.
	private int[] prime = new int[NUMBER_PRIME];

	// Number of Godlbach numbers we consider
	private final int NUMBER_GOLDBACH = 1000;

	// Array of the multiplicity of even numbers from 4 to 2002.
	private int[] multiplicities = new int[NUMBER_GOLDBACH];

	private GraphFrame graph;

	private JFrame frame;
	private JPanel panel;
	private JLabel label;
	private JButton button1;
	private JButton button2;
	private JTextArea area;

	public AppFrame(GraphFrame graph) {
		this.graph = graph;
	}

	public void activate() {
		frame = new JFrame("Goldbach Numbers");
		panel = new JPanel();

		label = new JLabel("Goldbach numbers and their multiplicities");

		area = new JTextArea(15, 10);
		area.setText("Result: \n");
		area.setLineWrap(true);
		JScrollPane scroll1 = new JScrollPane(area);

		button1 = new JButton("See the numbers");
		button2 = new JButton("Draw a graph");

		frame.getContentPane().add(BorderLayout.NORTH, panel);
		panel.setLayout(new GridBagLayout());
		addComponent(label, panel, 0, 0, 1);
		addComponent(scroll1, panel, 0, 1, 1);
		addComponent(button1, panel, 0, 2, 1);
		addComponent(button2, panel, 0, 3, 1);

		button1.addActionListener(new button1Listener());
		button2.addActionListener(new button2Listener());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
		frame.setVisible(true);

		calculateMultiplicities();
	}

	// Adds an input component to a specified JPanel at a specified coordinate.
	private void addComponent(Component component, JPanel panel, int x, int y, int width) {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		panel.add(component, c);
	}

	// Reads in the file of the first 2500 prime numbers.
	public void readInPrime() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("AP-ComSci-A-Adv-Comp_2500Primes.txt"));
		int i = 0;
		while (scanner.hasNextInt()) {
			if (scanner.nextInt() == i + 1) {
				prime[i] = scanner.nextInt();
			}
			i++;
		}
		scanner.close();
	}

	// Calculates multiplicities of even numbers between 4 and 2002. It then fills
	// in
	// the array of multiplicities.
	private void calculateMultiplicities() {
		int first = 0;
		int second = 0;

		// Invariant: sum = first + second
		int sum = 0;

		// The outer loop starts with prime number 3 instead of 2 because 2 is only used
		// to add up to 4.
		for (int i = 1; i < 2500; i++) {
			first = prime[i];
			for (int j = i; j < 2500; j++) {
				second = prime[j];
				sum = first + second;

				// If the sum is no larger than 2002, increment the multiplicity by one.
				if (sum <= NUMBER_GOLDBACH * 2 + 2) {
					multiplicities[(sum - 4) / 2]++;
				} else {
					break;
				}
			}
		}
		multiplicities[0] = 1; // The multiplicity of 4 is 1
	}

	// Displays a list of multiplicity of even numbers between 4 and 2002.
	private void display() {
		area.setText("Result:\n");
		area.append("{sum, multiplicity}\n");
		for (int i = 0; i < NUMBER_GOLDBACH; i++) {
			area.append(" {" + (i * 2 + 4) + ": " + multiplicities[i] + "} \n");
		}
	}

	// Action listener for a button to display a list of multiplicities
	class button1Listener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				display();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Error in displaying multiplcities", "Error Message",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// Action listener for a button to display a graph of multiplicities
	class button2Listener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				graph.setData(multiplicities);
				graph.activate();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Error in displaying the gaps", "Error Message",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
