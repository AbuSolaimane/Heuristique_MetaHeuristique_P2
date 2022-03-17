package exo2;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Main {

	public static void main(String[] args) {
		
		ArrayList<objet> objets2 = generateobjets(20);
		
		Date uDate1 = new Date (System.currentTimeMillis ()); //Le temps avant le debut du progamme (en milliseconde)
		
		SacDos sacdos = new SacDos(objets2, 1000);
		
		Solution  solution = sacdos.glouton1();
		
		Date duree1 = new Date (System.currentTimeMillis()); //Pour calculer la différence
		Date dateFin1 = new Date (System.currentTimeMillis());
		duree1.setTime (dateFin1.getTime () - uDate1.getTime ()); //Calcul de la différence
		long secondes1 = duree1.getTime () / 1000;
		long min1 = secondes1 / 60;
		long heures1 = min1 / 60;
		long mili1 = duree1.getTime () % 1000;
		secondes1 %= 60;
		System.out.println ("Temps passé durant le Glouton  : \nHeures : " + heures1 + " Minutes : " + min1 + " Secondes : " + secondes1 + " Milisecondes : " + mili1 + "");
		System.out.println(solution.getValeur());
		System.out.println(solution.getInstance());
		
		System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
		
		Date uDate2 = new Date (System.currentTimeMillis ()); //Le temps avant le debut du progamme (en milliseconde)
		
		Solution  solution2 = sacdos.descente(solution);
		
		Date duree2 = new Date (System.currentTimeMillis()); //Pour calculer la différence
		Date dateFin2 = new Date (System.currentTimeMillis());
		duree2.setTime(dateFin2.getTime() - uDate2.getTime()); //Calcul de la différence
		long secondes2 = duree2.getTime () / 1000;
		long min2 = secondes2 / 60;
		long heures2 = min2 / 60;
		long mili2 = duree2.getTime () % 1000;
		secondes2 %= 60;
		
		System.out.println ("Temps passé durant le descente : \nHeures : " + heures2 + " Minutes : " + min2 + " Secondes : " + secondes2 + " Milisecondes : " + mili2 + "");
		System.out.println(solution2.getValeur());
		System.out.println(solution2.getInstance());
		
		
		System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------");
		
		Date uDate3 = new Date (System.currentTimeMillis ()); //Le temps avant le debut du progamme (en milliseconde)
		
		Solution  solution3 = sacdos.recuitSimule(solution);
		
		Date duree3 = new Date (System.currentTimeMillis()); //Pour calculer la différence
		Date dateFin3 = new Date (System.currentTimeMillis());
		duree3.setTime(dateFin3.getTime() - uDate3.getTime()); //Calcul de la différence
		long secondes3 = duree3.getTime () / 1000;
		long min3 = secondes3 / 60;
		long heures3 = min3 / 60;
		long mili3 = duree3.getTime () % 1000;
		secondes3 %= 60;
		
		System.out.println ("Temps passé durant le recuitSimule : \nHeures : " + heures3 + " Minutes : " + min3 + " Secondes : " + secondes3 + " Milisecondes : " + mili3 + "");
		System.out.println(solution3.getValeur());
		System.out.println(solution3.getInstance());
		
	}
	
	private static ArrayList<objet> generateobjets(int n) {
		
		ArrayList<objet> objets = new ArrayList<>();
		Random rand = new Random();
		for (int i = 0; i < n; i++) {
			int valeur =  rand.nextInt(400) + 1;
			int poid = rand.nextInt(400) + 1;
			objets.add(new objet(valeur, poid));
		}
		
		return objets;
	}
}