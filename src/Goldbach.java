/*                                                    
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

public class Goldbach {

	private GraphFrame graph = new GraphFrame();
	private AppFrame app = new AppFrame(graph);

	public static void main(String[] args) {
		Goldbach gui = new Goldbach();
		gui.activate();
	}

	public void activate() {
		try {
			app.readInPrime();
			app.activate();
		} catch (Exception ex) {
			System.out.println("Error occurred in reading in a file.");
		}
	}

}
