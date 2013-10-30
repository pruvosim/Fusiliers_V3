import java.util.Arrays;


public class Population {

	public Regles[] individuals;
	
	public Population(int taille){
		individuals = new Regles[taille];
	}
	
	public Regles bestSolution(){
		Regles s = individuals[0];
		int i;
		for(i = 0; i < individuals.length; i++){
			if(individuals[i].fitness > s.fitness){
				s = individuals[i];
			}
		}
		return s;
	}

	@Override
	public String toString() {
		return "Population: " + Arrays.toString(individuals);
	}
	
	void resize(int s){
		individuals = new Regles[s];
	}
	
}
