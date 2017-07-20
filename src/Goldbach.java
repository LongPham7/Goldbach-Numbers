/*
 * This class is an entry point to the application. 
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
