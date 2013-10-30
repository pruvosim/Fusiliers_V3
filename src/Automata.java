/**
 * 
 */

/**
 * @author verel
 *
 */
public class Automata {

	// diagramme espace-temps;
	int configs [][] ;

	// nombre d'Žtats
	int nbStates;

	// taille de l'automate
	int maxSize;

	// nombre maximum d'iterations
	int maxIteration;

	// notation pour certains Žtats
	int BORD;
	int FIRE;
	int GEN;
	int REPOS;


	/*********************************************
	 * constructor
	 *
	 * input : N : maximum size of the automata
	 *
	 *********************************************/  
	public Automata(int _maxSize) {
		nbStates = 5;	
		maxSize = _maxSize; 
		maxIteration = 2 * _maxSize - 2;
		BORD  = nbStates;	
		FIRE  = nbStates - 1;
		GEN   = 1;
		REPOS = 0;

		if ((maxIteration > 1) && (maxSize > 1))
			configs = new int [maxIteration + 1][maxSize + 2];
	}

	/*********************************************
	 * configuration initiale de l'automate
	 *
	 * input : N : size of the automata
	 *
	 *********************************************/
	public void initialConfiguration(int N) {
		// bord gauche
		for(int t = 0; t <= maxIteration; t++) 
			configs[t][0]   = BORD;

		// bord droit
		for(int t = 0; t <= maxIteration; t++) 
			configs[t][N+1] = BORD;

		// repos
		for(int i = 2; i <= N; i++)
			configs[0][i] = REPOS;

		// gŽnŽral
		configs[0][1] = GEN;
	}

	/*********************************************
	 * evolution of the automate from initial configuration
	 * to the first time of firing
	 *
	 * input : regles : rules of the automata
	 *         N : size of the automata
	 *         nbIter : maximum number of iterations
	 *	
	 * output : number of the firing after 2N-2 iteration
	 *          0 else
	 *********************************************/
	public int evol(int [] regles, int N) {
		int nbIter = 2 * N - 2;

		if (nbIter > maxIteration)
			nbIter = maxIteration;

		// initialise l'automate
		initialConfiguration(N);

		// nombre d'Žtats feu
		int nbFire = 0;

		// valeur de la regle locale
		int r;

		int i, t;

		t = 1;
		while (t <= nbIter && nbFire == 0) {
			for(i = 1; i <= N; i++) {
				r = regles[ configs[t-1][i-1] * 36 + configs[t-1][i] * 6 + configs[t-1][i+1] ];

				if (r == FIRE)
					nbFire++;

				configs[t][i] = r;
			}

			t++;
		}

		// nombre de fusiliers aprs 2N-2 itŽrations
		if (t == 2 * N - 2 + 1)
			return nbFire;
		else
			return 0;
	}

	/*********************************************
	 * compute objective function
	 *
	 * input : regles : rules of the automata
	 *         n : maximum of automata size
	 *
	 * output : the maximum size solved
	 *
	 *********************************************/
	public int f(int [] regles, int n) {
		int nbFireTot = evol(regles, 2);

		int k = 2;

		while (nbFireTot == k && k <= n) {
			k++;
			nbFireTot = evol(regles, k);
		}

		if (k == 2)
			return 0;
		else
			return k - 1;
	}

}
