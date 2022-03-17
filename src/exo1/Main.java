package exo1;

import java.util.Date;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		int n = 300; //50, 100, 200, 300
		int[][] array = generateMatriceDistances(n);
		
		Afficher(array, n);
		
		TSP tsp = new TSP(n, array);
		Solution solution = tsp.TSPNearestNeighbour(1);
		
		for (int i : solution.getChemin()) {
			System.out.println(i);
		}
	
		System.out.println(solution.getFonctionObjectif());
		
		System.out.println("---------------------------------------------------------------------------------------");
		
		
		
		Date uDate1 = new Date (System.currentTimeMillis ()); //Le temps avant le debut du progamme (en milliseconde)
		
		Solution solution0 = tsp.descente(solution);
		
		Date duree1 = new Date (System.currentTimeMillis()); //Pour calculer la différence
		Date dateFin1 = new Date (System.currentTimeMillis());
		duree1.setTime (dateFin1.getTime () - uDate1.getTime ()); //Calcul de la différence
		long secondes1 = duree1.getTime () / 1000;
		long min1 = secondes1 / 60;
		long heures1 = min1 / 60;
		long mili1 = duree1.getTime () % 1000;
		secondes1 %= 60;
		System.out.println ("Temps passé durant le traitement : \nHeures : " + heures1 + " Minutes : " + min1 + " Secondes : " + secondes1 + " Milisecondes : " + mili1 + "");
		
		for (int i : solution0.getChemin()) {
			System.out.println(i);
		}
	
		System.out.println(solution0.getFonctionObjectif());
		
		System.out.println("---------------------------------------------------------------------------------------");
		
		Date uDate2 = new Date (System.currentTimeMillis ());
		
		Solution nvSolution = tsp.recuitSimule(solution);
		
		Date duree2 = new Date (System.currentTimeMillis()); //Pour calculer la différence
		Date dateFin2 = new Date (System.currentTimeMillis());
		duree2.setTime (dateFin2.getTime () - uDate2.getTime ()); //Calcul de la différence
		long secondes2 = duree2.getTime () / 1000;
		long min2 = secondes2 / 60;
		long heures2 = min2 / 60;
		long mili2 = duree2.getTime () % 1000;
		secondes2 %= 60;
		System.out.println ("Temps passé durant le traitement : \nHeures : " + heures2 + " Minutes : " + min2 + " Secondes : " + secondes2 + " Milisecondes : " + mili2 + "");
		
		for (int i : nvSolution.getChemin()) {
			System.out.println(i);
		}
		System.out.println(nvSolution.getFonctionObjectif());
	}
	private static void Afficher(int[][] array, int n) {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(array[i][j] + "      ");
			}
			System.out.println();
		}
	}
	private static int[][] generateMatriceDistances(int n) {
		Random rand = new Random();
		int[][] matriceDistances = new int[n][n];
		for (int k = 0; k < n; k++) {
			for (int j = k + 1; j < n; j++) {
				matriceDistances[k][j] = rand.nextInt(n*n) + 1;
				matriceDistances[j][k] = matriceDistances[k][j];
			}
		}
		return matriceDistances;
	}

}
