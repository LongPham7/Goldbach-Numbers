/*
 * Title:                Graph of Gaps between Non-unique Sums
 *                                                              @author Pham.LongThanh
 *                                                             Date: December 13, 2013
 * Class Description:
 *        This class is a subclass of JPanel that overrides method paintCOmponent()
 * in order to draw a specified graph of multiplicity of Godlbach numbers. It also
 * contains abscissa and ordinate with appropriate intervals.
 */
import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GraphMultiplicity extends JPanel
{
	/*Arrays of data points to be plotted*/
	public int[] sample;
   
	@Override
	/*
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 * 
	 * Precondition:
	 *  - Array "sample" has been filled.
	 */
    public void paintComponent(Graphics g1)
    {
		super.paintComponent(g1);
		
    	Graphics2D g = (Graphics2D) g1;
    	
    	/*
    	 * An optimized distance from the left edge of a window to the left
    	 * edge of the graph
    	 */
    	final int  WIDTH_HORIZONTAL = 100+5*
    			(Integer.toString(findYInterval()).length());
    	/*Interval of abscissa*/
    	int intervalX = findXInterval();
    	/*Interval of ordinate*/
    	int intervalY = findYInterval();
 	    
	    /*Draw abscissa and ordinate*/
 	    g.setColor(Color.black);
 	    g.drawLine(WIDTH_HORIZONTAL,550,WIDTH_HORIZONTAL+500,550);
 	    g.drawLine(WIDTH_HORIZONTAL,550,WIDTH_HORIZONTAL,50);
 	    
 	    /*raw scale bars for abscissa.*/
 	    for (int i=1;i<11;i++)
 	    {
 	    	g.drawLine(WIDTH_HORIZONTAL+50*i,550,WIDTH_HORIZONTAL+50*i,555);
 	    }
 	    
 	    /*Draw scale bar for ordinate.*/
 	    for(int i=1;i<11;i++)
 	    {
 	    	g.drawLine(WIDTH_HORIZONTAL-5,550-50*i,WIDTH_HORIZONTAL,550-50*i);
 	    }
 	    
 	    /*Label numbers for abscissa.*/
 	    for(int i = 0;i<11;i++)
 	    {
 	    	String n = Integer.toString(intervalX*i);
 	    	g.drawString(n,WIDTH_HORIZONTAL-5+50*i,575);
 	    }
 	    /*Label numbers for ordinate.*/
 	    for(int i = 0;i<11;i++)
 	    {
 	    	String n = Integer.toString(intervalY*i);
 	    	g.drawString(n,75,555-50*i);
 	    }
 	    
 	    /*Plot data points from the array, and connect them by lines.*/
 	    for(int i=0;i<sample.length;i++)
 	    {
 	    	g.setColor(Color.blue);
 	    	/*Find x-coordinate.*/
 	    	int x = WIDTH_HORIZONTAL+(50*(2*i+4)/intervalX);
 	    	/*Find y-coordinate.*/
 	    	int y = 550-(int)Math.round(sample[i]*50/intervalY);

 	    	/*Plot a point (a circle with radius of 5) on this point.*/
 	    	g.fillOval(x-3,y-3,4,4);
 	    }
 	    
 	    g.setColor(Color.black);
 	    /*Set a font for axis labels.*/
 	    g.setFont(new Font("Serif", Font.ITALIC, 13));
 	    /*Label abscissa titles.*/
 	    g.drawString("Goldbach Number", 225+WIDTH_HORIZONTAL,600);
 	    /*Label ordinate titles.*/
 	    g.drawString("Multiplicity", 10,300);
 	    
 	 }
    
    /*
     * This method finds an optimized abscissa-interval.
     * @return  optimized abscissa-interval
     * 
     * Precondition:
     *  - Array "sample" has been filled.
     */
    public int findXInterval()
    {
    	return (2*sample.length+2)/10+1;
    }
    
    /*
     * This method finds an optimized ordinate-interval. 
     * @return  optimized ordinate-interval
     * 
     * Precondition:
     *  - Array "sample" has been filled.
     */
    public int findYInterval()
    {
    	int result = 1;         /*Ordinate interval*/
    	int max = 1;        /*Maximum population.*/
    	for(int i=0;i<sample.length;i++)
    	{
	    	max = Math.max(sample[i], max);
    	}
    	result = (int)Math.ceil(((max)/10.0));
    	
    	/*If ordinate-interval is larger than 100, take only 2 significant digits.*/
    	if (result>100)
    	{
    		int sd = Integer.parseInt(Integer.toString(result).substring(0,2));
    		for(int i=0;i<Integer.toString(result).length()-2;i++)
    		{
    			sd = sd*10;
    		}
    		result = sd;
    	}
    	return result;
    }
}
