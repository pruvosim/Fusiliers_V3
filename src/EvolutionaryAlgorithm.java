import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class EvolutionaryAlgorithm {

	public Random rand;
	public int mu;
	public int lambda;
	public Initialization reorganise;
	public HillClimber HC;
	
	public EvolutionaryAlgorithm(Random rand, int mu, int lambda){
		this.rand = rand;
		this.mu = mu;
		this.lambda = lambda;
	}
	
	public void initialisation(Population p) throws IOException{
		int i;
		for(i = 0; i < p.individuals.length; i++){
			p.individuals[i] = new Regles();
			p.individuals[i].init();
			HC = new HillClimber(p.individuals[i]);
			p.individuals[i] = HC.iterer_Regles(1000000);
		}
		reorganise = new Initialization();
	}
	
	public Regles evalPop(Population parents){
		
		int i;
		Regles s = new Regles();
		for(i = 0; i < parents.individuals.length; i++){
			parents.individuals[i].fitness = parents.individuals[i].eval();
			if(parents.individuals[i].fitness > s.fitness){
				s = parents.individuals[i].clone();
			}
		}
		return s;
		
	}
	
	public Population selection(Population p){
		
		Population geniteurs = new Population(lambda);
		int a, b;
		for(int i = 0; i < lambda; i ++){
			a = rand.nextInt(mu);
			b = rand.nextInt(mu);
			if(p.individuals[a].fitness > p.individuals[b].fitness){
				geniteurs.individuals[i] = p.individuals[a].clone();
			}
			else{
				geniteurs.individuals[i] = p.individuals[b].clone();
			}
		}
		return geniteurs;
		
	}
	
	public void croisement(Population enfants, Population geniteurs){
		int a, b, chance;
		int j;
		Regles u = new Regles();
		int[] utile = u.utile();
		
		for(int i = 0; i < lambda; i++){
			a = rand.nextInt(lambda);
			b = rand.nextInt(lambda);
			enfants.individuals[i] = new Regles();
			enfants.individuals[i] = geniteurs.individuals[i].clone();
			
			for(j = 0; j < utile.length; j++){
				chance = rand.nextInt(2);
				if(chance == 0){
					enfants.individuals[i].rules[utile[j]] = geniteurs.individuals[a].rules[utile[j]];
				}
				else{
					enfants.individuals[i].rules[utile[j]] = geniteurs.individuals[b].rules[utile[j]];
				}
			}
		}
	}
	
	public void mutation_bitFlip(Population enfants){
		float proba = 1000/enfants.individuals[0].size(), chance;
		proba = proba / 1000;
		Regles u = new Regles();
		int[] utile = u.utile();
		for(int i = 0; i < enfants.individuals.length; i++){
			for(int j = 0; j < utile.length; j++){
				chance = rand.nextInt(1000);
				chance = chance / 1000;
				if(chance <= proba){
					enfants.individuals[i].rules[utile[j]] = rand.nextInt(4);
				}
			}
			reorganise.ajoutReglesBase(enfants.individuals[i].rules);
		}
	}
	
	public Population variations(Population geniteurs) throws IOException{
		
		Population enfants = new Population(lambda);
		croisement(enfants, geniteurs);
		mutation_bitFlip(enfants);
		for(int i = 0; i < enfants.individuals.length; i++){
			HC = new HillClimber(enfants.individuals[i]);
			enfants.individuals[i] = HC.iterer_Regles(100000);
		}
		Regles s = evalPop(enfants);
		return enfants;
		
	}
	
	public int[] meilleursSol(Population p, int taille){
		
		int[] indice = new int[taille];
		int ind = 0;
		int fit = p.individuals[0].size();
		
		while(ind < taille){
			for(int j = 0; j < p.individuals.length; j++){
				if(p.individuals[j].fitness == fit && ind < taille){
					indice[ind] = j;
					ind++;
				}
			}
			fit--;
		}
		return indice;
		
	}
	
	public void replacement(Population parents, Population enfants){
		Population meilleur = new Population(parents.individuals.length + enfants.individuals.length);
		int i;
		for(i = 0; i < parents.individuals.length; i++){
			meilleur.individuals[i] = parents.individuals[i].clone();
		}
		for(i = 0; i < enfants.individuals.length; i++){
			meilleur.individuals[i + parents.individuals.length] = enfants.individuals[i].clone();
		}
		Regles s = evalPop(meilleur);
		int[] solution = meilleursSol(meilleur,mu);
		for( i = 0; i < parents.individuals.length; i++){
			parents.individuals[i] = meilleur.individuals[solution[i]];
		}
		parents = meilleur;
	}
	
	
	public Regles run(int genmax) throws IOException{
		
		String outName = "C:/Documents and Settings/spruvost/Mes documents/Resultats_HC/EA_";
		String date = new SimpleDateFormat("dd_MM_yy-HH_mm_ss").format(new Date());
		outName += date + ".dat";
		
		PrintWriter ecrivain;
		
		ecrivain =  new PrintWriter(new BufferedWriter(new FileWriter(outName)));
		
		Population parents = new Population(mu);
		initialisation(parents);
		Regles s = evalPop(parents);
		System.out.println("Parents");
		System.out.println(parents);
		
		Population geniteurs, enfants;
		int gen = 0;
		int fit = -1;
		
		while(gen < genmax){
			geniteurs = selection(parents);		//choisi les meilleurs
			//System.out.println("Geniteurs");
			//System.out.println(geniteurs);
			enfants = variations(geniteurs);	//croisement uniformes + bit-flip
			//System.out.println("Enfants");
			//System.out.println(enfants);
			//System.out.println();
			replacement(parents, enfants);	//on prend les mu meilleurs pour les mettres dans les parents
			//System.out.println("Parents");
			//System.out.println(parents);
			s = evalPop(parents);
			if(s.fitness > fit){
				fit = s.fitness;
				printToFile(s.fitness, s.rules, ecrivain);
				System.out.println(s);
			}
			gen++;
			System.out.println("Génération " + gen + " terminée.");
		}
		System.out.println("HillClimbing de fin en cours ...");
		for(int i = 0; i < parents.individuals.length; i++){
			HC = new HillClimber(parents.individuals[i]);
			parents.individuals[i] = HC.iterer_Regles(100000);
		}
		s = evalPop(parents);
		if(s.fitness > fit){
			fit = s.fitness;
			printToFile(s.fitness, s.rules, ecrivain);
		}
		
		System.out.println("Fin. Best Fitness : " + s.fitness + ". Fichier : " + outName);
		ecrivain.close();
		
		return s;
	}
	
	public static void printToFile(int fitness, int [] rules, PrintWriter ecrivain) {
		ecrivain.print(fitness);
		for(int i = 0; i < 216; i++) {
			ecrivain.print(" ");
			ecrivain.print(rules[i]);
		}
		ecrivain.println();
	}

}
