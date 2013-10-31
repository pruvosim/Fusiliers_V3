import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;


public class MainILS {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		IteratedLocalSearch ils = new IteratedLocalSearch();
		ils.iterer(1000000, 10000);
		
	}
}
