package exo2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SacDos {

	private ArrayList<objet> objets;
	private int capacite;

	public SacDos(ArrayList<objet> objets, int capacite) {
		super();
		this.objets = objets;
		this.capacite = capacite;
	}
	
	public Solution glouton1() {
		int size = objets.size();
		ArrayList<Boolean> instance = new ArrayList<Boolean>(Arrays.asList(new Boolean[size]));
		Collections.fill(instance, Boolean.FALSE);
		int valeur = 0;
		
		ArrayList<objet> copy_objets = (ArrayList<objet>) objets.clone();
		Collections.sort(copy_objets, Collections.reverseOrder());

		int i = 0;
		int capaciteDisponible = this.capacite;

		while (i<size && capaciteDisponible>0) {
			if(capaciteDisponible >= copy_objets.get(i).getPoids()) {
				
				valeur += copy_objets.get(i).getValeur();
				int v = objets.indexOf(copy_objets.get(i));
				instance.set(v, true);
				capaciteDisponible -= copy_objets.get(i).getPoids();
			}
			i++;
		}

		Solution solution = new Solution(instance, valeur);
		return solution;
	}
	
	public Solution hamming(Solution s, int i, int j) {
		Solution solution = s.clone();
		ArrayList<Boolean> nvInstance = solution.getInstance();
		int nvValeur = solution.getValeur();
		
		if(nvInstance.get(i)==true) {
			nvInstance.set(i,false);
			nvValeur -= this.objets.get(i).getValeur();
			}
		
		else if(nvInstance.get(i)==false && capacite > this.poidTotal(solution) + objets.get(i).getPoids()) {
			nvInstance.set(i,true);
			nvValeur += this.objets.get(i).getValeur();
			}
		
		if(nvInstance.get(j)==true) {
			nvInstance.set(j, false);
			nvValeur -= this.objets.get(j).getValeur();
			}
		
		else if(nvInstance.get(j)==false && capacite > this.poidTotal(solution) + objets.get(j).getPoids()) {
			nvInstance.set(j, true);
			nvValeur += this.objets.get(j).getValeur();
			}
		
		Solution nvSolution = new Solution();
		nvSolution.setInstances(nvInstance);
		nvSolution.setValeur(nvValeur);
		return nvSolution;
	}
	public Solution descente(Solution s0) {
		Solution sol = s0.clone();
		Set<Solution> listesVoisins;
		Solution VoisinMax;
		int initVal = sol.getValeur();
		int ValVoisinMax;
		
		while (true) {
			listesVoisins = this.generatesVosins(sol);
			VoisinMax = getVoisinAvecValMax(listesVoisins);
			ValVoisinMax = VoisinMax.getValeur();
            if (initVal<ValVoisinMax) {
                sol = VoisinMax;
                initVal = ValVoisinMax;
            } else {
                return sol;
            }
		}
	}
	
	private Solution getVoisinAvecValMax(Set<Solution> listesVoisins) {
		Solution solMax = new Solution();
		int max = Integer.MIN_VALUE;
		for (Solution s: listesVoisins) {
			if(s.getValeur() > max) {
				max = s.getValeur();
				solMax = s.clone();
			}
			
		}
		
		return solMax;
	}

	private Set<Solution> generatesVosins(Solution s) {
		Solution sol = s.clone();
		Set<Solution> solutions = new HashSet<Solution>();
		for (int i = 0; i < objets.size(); i++) {
			for(int j = i + 1; j < objets.size(); j++) {
				solutions.add(hamming(sol, i, j));
			}
		}
		return solutions;
	}

	public Solution recuitSimule(Solution s0) {
		
		Set<Solution> solutionsVisites = new HashSet<Solution>();
		double T=100000;
		Solution sol = s0.clone();
		solutionsVisites.add(sol);
		Set<Solution> listesVoisins;
		Solution VoisinMax;
        while (T>1) {
        	int l = 0;
            do {
                listesVoisins = generatesVosins(sol);//géneration des voisin
                VoisinMax = getVoisinAleatoires(listesVoisins);//choisir le meilleur voisin
                double r = Math.random();//;   //géneration de 0<r<1
                if (r < fonctionBoltZmann(sol, VoisinMax, T)) {
                	solutionsVisites.add(VoisinMax);
                	sol = VoisinMax;
                	break;
                }
            } while (l<100);
            T=T-100;//Modification de la températur
        }
        return this.getVoisinAvecValMax(solutionsVisites);
		
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
	
	private double fonctionBoltZmann(Solution sol, Solution voisinMax, double t) {
		
        return Math.exp((voisinMax.getValeur() - sol.getValeur()) / t);
        }
	
	public int getCapacite() {
		return capacite;
	}

	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}
	
	public int poidTotal(Solution s) {
		int poids = 0;
		for(int i=0; i<s.getInstance().size(); i++) {
			if(s.getInstance().get(i)==true)
				poids += objets.get(i).getPoids();
		}
		return poids;
	}

}
