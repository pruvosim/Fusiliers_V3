import java.util.Arrays;
import java.util.Random;


public class Regles {
	
	public int n = 5 + 1;
	public int [] rules;
	public int fitness;
	
	Initialization init;
	
	public Regles()
	{
		rules = new int[n * n * n];
		fitness = -1;
	}
	
	public void init()
	{
		init = new Initialization();
		
		init.init(rules);
	}
	
	public int eval()
	{
		Automata auto = new Automata(25);
		fitness = auto.f(rules, 25);
		return fitness;
	}

	
	public Regles clone()
	{
		Regles result = new Regles();
		result.rules = copieRules(this.rules);
		result.fitness = this.fitness;
		return result;
	}
	
	public int[] copieRules(int[] rules_a_copier)
	{
		int[] result = new int[rules_a_copier.length];
		
		for (int i = 0; i < rules_a_copier.length; i++) {
			result[i] = rules_a_copier[i];
		}
		
		return result;
		
	}
	
	public int size(){
		return this.rules.length;
	}

	@Override
	public String toString() {		
		return "Regles [fitness="+ fitness + ". rules=" + Arrays.toString(rules) + " ]";
	}
	
	public int[] utile(){
		int[] regles = new int[216];
		
		int i;
		for(i = 0; i < 216; i++){
			regles[i] = 1;
		}
		
		for(i = 0; i < 36; i++){
			//on supprime les règles qui finissent par 4 comme par exemple 124
			regles[4 + (6 * i)] = 0;
			//on supprime les règles qui commencent par 4 comme par exemple 410
			regles[144 + i] = 0;
		}
		
		int j;
		for(i = 0; i < 6; i++){
			for(j = 0; j < 6; j++){
				//on supprime les règles qui sont de la forme ?4? comme par exemple 040
				regles[36 * i + 4 * 6 + j] = 0;
				//on supprime les règles qui sont de la forme ?5? comme par exemple 152
				regles[36 * i + 5 * 6 + j] = 0;
			}
		}
		
		//on supprime les règles "imposées"
		
		// les regles repos (obligatoires)
		regles[36 * 0 + 6 * 0 + 0] = 0;
		regles[36 * 5 + 6 * 0 + 0] = 0;
		regles[36 * 0 + 6 * 0 + 5] = 0;
				
		// les regles feu (trï¿½s conseillÅ½es)
		regles[36 * 1 + 6 * 1 + 1] = 0;
		regles[36 * 5 + 6 * 1 + 1] = 0;
		regles[36 * 1 + 6 * 1 + 5] = 0;
				
		// les regles a priori (signal de pÅ½riode 2 vers la droite)
		regles[36 * 1 + 6 * 0 + 0] = 0;
		regles[36 * 2 + 6 * 0 + 0] = 0;
		
		// a priori bord droit
		regles[36 * 1 + 6 * 0 + 5] = 0;
		regles[36 * 2 + 6 * 0 + 5] = 0;
				
		// a priori bord gauche (pour la taille 2)
		regles[36 * 5 + 6 * 1 + 0] = 0;
		
		int taille = 0;
		for(i = 0; i < 216; i++){
			if (regles[i] == 1){
				taille++;
			}
		}
		
		j = 0;
		int[] utile = new int[taille];
		for(i = 0; i < 216; i++){
			if (regles[i] == 1){
				utile[j] = i;
				j++;
			}
		}
		return utile;
	}

}
