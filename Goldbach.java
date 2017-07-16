/*
 * Title:                             Goldbach Numbers
 *                                                             @author Pham.LongThanh
 *                                                            Date: December 13, 2013
 *                                                            
 * Class Description:
 *        This class reads a file of the first 2500 prime numbers, and calculates the 
 * multiplicity of each even integer bigger than 2 as of sums of two prime numbers. 
 * Such numbers are called Godlbach numbers because Godlbach's conjecture states that
 * every even integer bigger than 2 can be expressed as the sum of two prime numbers.
 * The gaps between two consecutive non-unique sums are also derived after making the 
 * list of multiplicity. This class also creates a window where a user can look at the
 * list of multiplicity and the list of gaps. The user has to click a button to look
 * at a list. Clicking another button displays the graph of multiplicity.
 *
 * Algorithm Description:
 *        The algorithm to find multiplicity of Godlbach numbers is the following:
 * use a double-nested loop to add two odd prime numbers from the file, and increment
 * the multiplicity of the sum by 1. Only odd prime numbers are used because Goldbach 
 * numbers are even, and they are either sums of two odd integers or sums of two even
 * integers. 2 is the only even prime number, so it is excluded from this
 * double-nested loop. However, 2 only appears in finding the multiplicity of 4,
 * which is 2+2. And we know that the multiplicity of 4 is 1. Hence, multiplicity of
 * all Godlbach numbers except 4 can be calculated using the algorithm with the
 * double-nested loop.
 */
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.util.Scanner;

public class Goldbach 
{
	JFrame frame;
	JPanel panel1;
	JPanel panel2;
	
	JFrame frame2;
	
	/*Graph to display multiplicity of Goldbach numbers*/
	GraphMultiplicity graph;
	
	JLabel label1;
	JLabel label2;
	
	JButton button1;
	JButton button2;
	JButton button3;
	
	JTextArea area1;
	JTextArea area2;
	
	/*GirdBagConsraints for GridBagLayout of JPanels*/
	private GridBagConstraints c;
	
	/*This array stores the first 2500 prime numbers.*/
	private int[] prime;
	
	/*This array stores multiplicity of Goldbach numbers from 4 to 2002.*/
	private int[] list;
	
	/*This ArrayList stores gaps between non-unique sums of Godlbach numbers*/
	private ArrayList<Integer> gap;
	
	/*
	 * Constructor of class Goldbach
	 */
	public Goldbach()
	{
		c = new GridBagConstraints();
		prime = new int[2500];
	}
	
	/*
	 * This method is executed in the beginning of the program, and creates
	 * an object of class Goldbach.
	 */
	public static void main(String[] args)
	{
		Goldbach gui = new Goldbach();
		gui.go();
	}
	
	/*
	 * This method reads the file of prime numbers, and stores it in the array "prime."
	 * After that, this method creates a new window to display the list of 
	 * multiplicity of Goldbach numbers and the list of gaps between non-unique sums. 
	 * 
	 * Precondition:
	 *  - A file of prime numbers has already been imported.
	 * Postcondition:
	 *  - The array "prime" is filled.
	 *  - A JFrame with specified components is displayed.
	 */
	public void go()
	{
		/*
		 * Traverse the file of prime numbers, and extract them from
		 * the file, storing them in an array.
		 */
		try
		{
			Scanner scanner = new Scanner
					(new File("AP-ComSci-A-Adv-Comp_2500Primes.txt"));
			
			/*This variable is the index of prime numbers in the array*/
			int i = 0;          
			
			while(scanner.hasNextInt())
			{
				/*
				 * If the number in the file is i+1, the next element is prime, so 
				 * insert it to a specified index in the array "prime."
				 */
				if(scanner.nextInt()==i+1)
				{
					prime[i] = scanner.nextInt();
				}
				i++;
			}
			scanner.close();
		}
		catch(Exception ex)
		{
			System.out.println("Error");
		}
		
		
		frame = new JFrame("Goldbach Numbers");
		panel1 = new JPanel();
		panel2 = new JPanel();
		
		label1 = new JLabel("Investigate gaps between multiplicities of Goldbac numbers");
		label2 = new JLabel("Below are Goldbach numbers.");
		
		area1 = new JTextArea(15,10);
		area1.setText("Result: \n");
		area1.setLineWrap(true);
		JScrollPane scroll1 = new JScrollPane(area1);
		
		area2 = new JTextArea(15,10);
		area2.setText("Gap: \n");
		area2.setLineWrap(true);
		JScrollPane scroll2 = new JScrollPane(area2);
		
		button1 = new JButton("See the numbers");
		button2 = new JButton("Draw a graph");
		button3 = new JButton("See gaps between multiplicities");
		
		frame.getContentPane().add(BorderLayout.NORTH, panel1);
		panel1.setLayout(new GridBagLayout());
		panelAddComponent(label1,panel1,0,0,2);
		panelAddComponent(label2,panel1,0,1,1);
		panelAddComponent(scroll1,panel1,0,2,1);
		panelAddComponent(button1, panel1,0,3,1);
		panelAddComponent(button2, panel1,0,4,1);
		panelAddComponent(scroll2,panel1,1,2,1);
		panelAddComponent(button3,panel1,1,3,1);
		
		button1.addActionListener(new button1Listener());
		button2.addActionListener(new button2Listener());
		button3.addActionListener(new button3Listener());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,500);
		frame.setVisible(true);
	}
	
	/*
	 * This method creates a window to display the graph of multiplicity of each
	 * Goldbach numbers.
	 * 
	 * Postcondition:
	 *  - A JFrame with a graph of multiplicity is displayed.
	 */
	public void go2()
	{
		frame2 = new JFrame("Graph of Multiplicity");
		graph = new GraphMultiplicity();
		
		frame2.getContentPane().add(BorderLayout.CENTER, graph);
		
		frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame2.setSize(700,700);
		frame2.setVisible(false);
	}
	
	/*
	 * This method adds a specified component to a specified JPanel in a specified
	 * position. 
	 * @param  component: component to be added
	 *         panel:     JPanel to locate the component
	 *         x:         abscissa-coordinate of the component
	 *         y:         ordinate-coordinate of the component
	 *         width:     width of the component
	 *         
	 * Precondition:
	 *  - Component, panel, abscissa-coordinate, ordinate-coordinate, and width are
	 *    specified.
	 * Postcondition:
	 *  - A specified component is added to a specified JPanel in a specified position.
	 */
    private void panelAddComponent(Component component,JPanel panel,int x, int y, int width)
    {
    	  c.fill = GridBagConstraints.BOTH;
      	  c.gridx = x;
      	  c.gridy = y;
      	  c.gridwidth = width;
      	  panel.add(component,c);
    }
    
    /*
     * This inner class implements an ActionListener of a button to display the
     * multiplicity of each Goldbach numbers.
     */
    class button1Listener implements ActionListener
    {
    	public void actionPerformed(ActionEvent event)
    	{
    		try
    		{
        		makeList();
        		display1();
    		}
    		catch(Exception ex)
    		{
    			JOptionPane.showMessageDialog(null,"There is an error.",
    					"Error Message", JOptionPane.ERROR_MESSAGE);
    		}
    	}
    }
    
    /*
     * This inner class implements an ActionListener of a button to display the
     * gaps between non-unique sums.
     */
    class button2Listener implements ActionListener
    {
    	public void actionPerformed(ActionEvent event)
    	{
    		try
    		{
        		go2();
        		/*If the list of multiplicity has not been created yet, make it.*/
        		if(list==null)
        		{
        			makeList();
        		}
        		graph.sample = list;
        		graph.repaint();
        		frame2.setVisible(true);
    		}
    		catch(Exception ex)
    		{
    			JOptionPane.showMessageDialog(null,"There is an error.",
    					"Error Message", JOptionPane.ERROR_MESSAGE);
    		}
    	}
    }
    
    /*
     * This inner class implements an ActionListener of a button to display the graph
     * of multiplicity of Goldbach numbers.
     */
    class button3Listener implements ActionListener
    {
    	public void actionPerformed(ActionEvent event)
    	{
    		try
    		{
    			/*If the list of multiplicity has not been created yet, make it.*/
        		if(list==null)
        		{
        			makeList();
        		}
        		countGap();
        		display2();
    		}
    		catch(Exception ex)
    		{
    			JOptionPane.showMessageDialog(null,"There is an error.",
    					"Error Message", JOptionPane.ERROR_MESSAGE);
    		}
    	}
    }
    
    /*
     * Using the list of prime numbers, this method creates an array that stores
     * multiplicity of each Goldbach number. 
     * 
     * Precondition:
     *  - The array "list" has already been created.
     *  - The array "prime" has already been filled.
     * Postcondition:
     *  - The array "list" is filled with multiplicity of each Godlbach number.
     */
    private void makeList()
    {
    	/*The list of multiplicity contains 1000 elements.*/
    	list = new int[1000];
    	
    	/*First prime number chosen from the list of prime numbers*/
    	int first = 0;
    	
    	/*Second prime number chosen from the list of prime numbers*/
    	int second = 0;
    	
    	/*Sum of the first and second prime numbers*/
    	int sum = 0;
    	
    	/*
    	 * This outer loop traverses the list of prime numbers, setting the variable
    	 * "first" to be each prime number. This loop starts from 3 instead of 2
    	 * because 2 appears  only in finding the multiplicity of 4.
    	 */
    	for(int i=1;i<2500;i++)
    	{
    		first = prime[i];
    		
    		/*
    		 * This inner loop traverse the rest of the list, setting the variable
    		 * "second" to be each prime number.
    		 */
    		for(int e=i;e<2500;e++)
    		{
    			second = prime[e];
    			sum = first+second;  /*Add two prime numbers: first and second*/
    			
    			/*
    			 * If the sum is not bigger than the maximum Goldbach number allowed to
    			 * be in the array, increment the multiplicity of the sum by 1.
    			 */
    			if(sum<=list.length*2+2)
    			{
    			    list[(sum-4)/2]++;
    			}
    		}
    	}
    	list[0] = 1;     /*The multiplicity of 4 is 1*/
    }
    
    /*
     * This method creates an ArrayList that stores gaps between two consecutive 
     * non-unique sums.
     * 
     * Precondition:
     *  - The ArrayList "gap" has already been created.
     *  - The array "list" has already been filled.
     * Postcondition:
     *  - The ArrayList "gap" is filled.
     */
    private void countGap()
    {
		gap = new ArrayList<Integer>();
    	int index = -1;      /*Starting index of a sequence of unique sums*/
    	
    	/*
    	 * This loop traverses the list of multiplicity, and count the length of gaps
    	 * between two consecutive non-unique sums.
    	 */
    	for(int i=0;i<list.length;i++)
    	{
    		/*
    		 * If the multiplicity is not 1, this is the end of the sequence of 
    		 * unique sums.
    		 */
    		if(list[i]!=1)
    		{
    			/*
    			 * If this is not the first non-unique sums, the length of gap can 
    			 * be found.
    			 */
    			if(index!=-1)
    			{
    				gap.add(i-index-1);
    			}
				index = i; /*The next starting index of */
    		}
    		
    	}
    }
    
    /*
     * This method displays the list of multiplicity of Godlbach numbers in a JtextArea.
     * 
     * Precondition:
     *  - The main JFrame has already been created.
     *  - The array "list" has already been filled.
     * Postcondition
     *  - Multiplicity of each Goldbach number is displayed on an JTextArea.
     */
    private void display1()
    {
    	area1.setText("Result:\n");
    	area1.append("{sum, multiplicity}\n");
    	for(int i=0;i<list.length;i++)
    	{
    		area1.append(" {"+(i*2+4)+": "+list[i]+"} \n");
    	}
    }
    
    /*
     * This method displays the list of gaps between two consecutive non-unique
     * sums in a JtextArea.
     * 
     * Precondition:
     *  - The main JFrame has already been created.
     *  - The ArrayList "gap" has been filled.
     * Postcondition:
     *  - A sequence of gap between non-unique sums is displayed on a JTextArea.
     */
    private void display2()
    {
    	area2.setText("Gap:\n");
    	area2.append("{");
    	for(int i=0;i<gap.size();i++)
    	{
    		area2.append(gap.get(i)+", ");
    		/*
    		 * If it is the last element to display, add a curly bracket in the end.
    		 */
    		if(i==gap.size()-1)
    		{
    			area2.append(gap.get(i)+"}");
    		}
    		
    	}
    }
}
