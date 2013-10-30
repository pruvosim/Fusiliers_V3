import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;


public class MainHC {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		//Automata automate = new Automata(20);

		//int [] rules = initRules();
		//int[] rules = new int[216];
		
		//Initialization init = new Initialization();
		
		//init.init(rules);

		//int fit = automate.f(rules, 20);
		//System.out.println("Fit : " +fit);

		//int ancien_fit = 0;
		
		//HillClimber HC = new HillClimber();
		//HC.iterer(10000000);
		
		HillClimber test = new HillClimber();
		test.runForStatistics(10, 10000000);
		System.out.println("FIN!!");
	}
}
