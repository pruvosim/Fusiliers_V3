import java.io.IOException;
import java.util.Random;


public class IteratedLocalSearch {

	//Déclaration de l'automate et des règles
	int n = 5 + 1;
	int [] rules = new int[n * n * n];
	Initialization init;
	Automata automate;
	Random generator;

	public IteratedLocalSearch()
	{
		//Initialisation de l'automate et des règles
		generator = new Random();
		init = new Initialization();
		automate = new Automata(20);

		init.init(rules);

	}

	public void iterer(int nb_iterations, int iterations_avant_perturbation) throws IOException
	{

		int compteur = 0;
		
		//Tableau de règles qui contiendra celles de la solution précédent si le voisin est moins bon
		int [] meilleures_regles = new int[216];

		//Indice de la règle à changer
		int regle_a_changer = 0;

		int fit_actuel = automate.f(rules, 20);
		int fit_ancien = -1;

		Regles r = new Regles();
		int[] utile = r.utile();
		for (int i = 1; i < nb_iterations; i++) {
			
			if(compteur == iterations_avant_perturbation)
			{
				perturbations();
				init.ajoutReglesBase(meilleures_regles);
				//System.out.println("Perturbations");
				compteur = 0;
			}
			
			generator = new Random();
			if(fit_actuel >= fit_ancien)
			{
				meilleures_regles = copier_tableau(rules);

				//On stocke les règles pour pouvoir les recopier en cas de moins bonne performance
				if(fit_actuel > fit_ancien)
				{
					System.out.println("Meilleure perf : " + fit_actuel);
					compteur = 0;
				}
				else
				{
					compteur ++;
				}
				
				fit_ancien = fit_actuel;
				
			}
			else
			{
				rules = copier_tableau(meilleures_regles);
				compteur ++;
			}


			//On change j regles au lieu d'une seule comme dans l'exemple du OneMax
			for (int j = 0; j < 5; j++) {
				
				regle_a_changer = generator.nextInt(utile.length);
				rules[utile[regle_a_changer]] = generator.nextInt(4);
				
			}
			
			init.ajoutReglesBase(rules);
			
			//System.out.println("Fitness : " + fit_actuel);
			
			fit_actuel = automate.f(rules, 20);
		}
		System.out.println("Fin");

	}

	//Fonction qui permet de recopier un tableau en entrée
	public int[] copier_tableau(int[] tableau_a_copier)
	{
		int[] result = new int[tableau_a_copier.length];

		for (int i = 0; i < tableau_a_copier.length; i++) {
			result[i] = tableau_a_copier[i];	
			//System.out.print(result[i] + " ");
		}

		return result;
	}
	
	public void perturbations()
	{
		//perturbation_uniforme();
		perturbation_bit_flip();
	}
	
	public void perturbation_uniforme()
	{
		Random generator = new Random();
		
		for (int i = 0; i < rules.length; i++) {
			if(generator.nextBoolean())
			{
				int new_val = generator.nextInt(4);
				while(new_val == rules[i]) new_val = generator.nextInt(4);
				rules[i] = new_val;
			}
			
		}
	}
	
	public void perturbation_bit_flip()
	{
		Random generator = new Random();
		
		for (int i = 0; i < rules.length; i++) {
			if(generator.nextInt(216) == i)
			{
				int new_val = generator.nextInt(4);
				while(new_val == rules[i]) new_val = generator.nextInt(4);
				rules[i] = new_val;
			}
			
		}
	}

}
