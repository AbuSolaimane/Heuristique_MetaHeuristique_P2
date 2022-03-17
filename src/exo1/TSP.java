package exo1;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TSP {

	private int nbrVilles;
	private int[][] distances;
	
	public TSP(int nbrVilles, int[][] distances) {
		super();
		this.nbrVilles = nbrVilles;
		this.distances = distances;
	}
	
	public Solution TSPNearestNeighbour(int premierVille) {
		Solution solution = new Solution();
		int n = nbrVilles + 1;
		int[] chemin = new int[n] ;
		initialiser(chemin, n);
		chemin[0] =   premierVille;
		chemin[nbrVilles] = premierVille;
		
		for(int k=1; k<n-1;k++) {
			int[] dis = distances[chemin[k-1] ];
			chemin[k] = min(dis, chemin);
		}
		
		int fonctionObjectif = distanceTravelled(chemin);
		
		solution.setChemin(chemin);
		solution.setFonctionObjectif(fonctionObjectif);
		
		return solution;
	}

	private int distanceTravelled(int[] chemin) {
		int fonctionObjectif = 0;
		int n = chemin.length;
		for(int k=1; k<n;k++) {
			fonctionObjectif += distances[chemin[k-1] ][chemin[k]];
		}
		return fonctionObjectif;
	}

	private void initialiser(int[] chemin, int n) {
		for(int i=0;i<n;i++) {
			chemin[i] = n;
		}
	}

	private int min(int[] dis, int[] chemin) {
		int min = Integer.MAX_VALUE;
		int min_dis = Integer.MAX_VALUE;
		int k = 0;
		for(int i : dis) {
			if(i<min_dis && !Exist(k, chemin)) {
				min_dis = i;
				min = k;
			}
			k++;
		}
		return min;
	}

	private boolean Exist(int k, int[] chemin) {
		for(int i : chemin) {
			if(i == k)
				return true;
		}
		return false;
	}
	
	
	
	public Solution descente(Solution s0) {
		Solution sol = s0.clone();
		Set<Solution> listesVoisins;
		Solution VoisinMin;
		int initCout = sol.getFonctionObjectif();
		int coutVoisinMin;
		
		while (true) {
			listesVoisins = this.generatesVosins(sol);
			VoisinMin = getVoisinAvecCoutMin(listesVoisins);
            coutVoisinMin = VoisinMin.getFonctionObjectif();
            if (coutVoisinMin<initCout) {
                sol = VoisinMin.clone();
                initCout = coutVoisinMin;
            } else {
                return sol;
            }
		}
	}
	
	private Solution getVoisinAvecCoutMin(Set<Solution> listesSolution) {
		Solution solMin = new Solution();
		int min = Integer.MAX_VALUE;
		for (Solution s: listesSolution) {
			if(s.getFonctionObjectif()<min) {
				min = s.getFonctionObjectif();
				solMin = s.clone();
			}
			
		}
		
		return solMin;
	}

	private Set<Solution> generatesVosins(Solution sol) {
		Set<Solution> voisins = new HashSet<Solution>();
		for(int i=0; i < nbrVilles; i++) {
			for (int j = i + 1; j < nbrVilles; j++) {
				voisins.add(this.TwoOpt(sol.clone(), i, j));
			}
		}
		return voisins;
	}
	
	public Solution recuitSimule(Solution s0) {
		Set<Solution> solutionsVisites = new HashSet<Solution>();
		double T = 100000+1;
		Solution sol = s0.clone();
		solutionsVisites.add(sol);
		Set<Solution> listesVoisins;
		Solution VoisinMin;
		
        while (T > 1) {
        	int l = 0;
        	do {
                listesVoisins = generatesVosins(sol);//géneration des voisin
                VoisinMin = getVoisinAleatoires(listesVoisins);//choisir un voisin aleatoire
                double r = Math.random();//géneration de 0<r<1
                if (r < this.fonctionBoltZmann(sol, VoisinMin, T)) {
                	sol = VoisinMin.clone();
                	solutionsVisites.add(VoisinMin);
                	break;
                }
           
            } while (l<100);
            T = T - 100;  //Modification de la températur
        }
        return getVoisinAvecCoutMin(solutionsVisites);
	}
	
	private Solution getVoisinAleatoires(Set<Solution> listesVoisins) {
		int size = listesVoisins.size();
		Random rand = new Random();
		int i = rand.nextInt(size);
		int j = 0;
		for (Solution solution : listesVoisins) {
			if(i==j) 
				return solution;
			j++;
		}
		return null;
	}

	private double fonctionBoltZmann(Solution sol, Solution voisinMin, double t) {
		
	        return Math.exp((sol.getFonctionObjectif() - voisinMin.getFonctionObjectif()) / t);
	}

	public Solution TwoOpt(Solution solution,  int city1,  int city2)
    {
		int [] start = solution.getChemin();
        int route [] = new int[nbrVilles + 1];
        System.arraycopy(start, 0, route, 0, route.length);
        int middle [] = new int[city2 - city1 + 1];
        int counter = 0;
        for (int i = city2; i >= city1; i--)
        {
            middle[counter] = route[i];
            counter++;
        }
        System.arraycopy(middle, 0, route, city1, middle.length);
        if(city1 == 0)  {
        	route [nbrVilles] = route [0];
        }
        int fonctionObjective = this.distanceTravelled(route);
        
        Solution nvSolution = new Solution();
        nvSolution.setChemin(route);
        nvSolution.setFonctionObjectif(fonctionObjective);
        return nvSolution;
    }
	
}
