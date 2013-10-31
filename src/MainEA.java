import java.io.IOException;
import java.util.Random;

public class MainEA {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		Random rand = new Random();
		
		EvolutionaryAlgorithm EA = new EvolutionaryAlgorithm(rand,500, 200);
		EA.run(100);
		
	}

	public void AffichageUtile(){
		Regles r = new Regles();
		int[] u = r.utile();
		int g,c,d;
		for(int i = 0; i < u.length; i++){
			System.out.print(u[i] + " ");
			
			g = u[i];
			do{
				g  = g / 36;
			}while(g >= 6);
			
			c = u[i] - g * 36;
			do{
				c  = c / 6;
			}while(c >= 6);
			
			d = u[i] - g * 36 - c * 6;
			
			System.out.println(g + "" + c + "" + d);
		}
	}
}
