package exo1;

public class Solution {
	
	private int[] chemin;
	private int fonctionObjectif;
	
	public int[] getChemin() {
		return chemin;
	}
	public void setChemin(int[] chemin) {
		this.chemin = chemin;
	}
	public int getFonctionObjectif() {
		return fonctionObjectif;
	}
	public void setFonctionObjectif(int fonctionObjectif) {
		this.fonctionObjectif = fonctionObjectif;
	}
	
	public Solution clone() {
		
		Solution solution = new Solution();
		int[] chemin2 = this.chemin.clone();
		solution.setChemin(chemin2);
		solution.setFonctionObjectif(this.fonctionObjectif);
		return solution;
		
	}

}
