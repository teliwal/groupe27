package serveur.element;

import java.util.HashMap;

public class PersonnageZombie extends Personnage{
  /**
   * le Zombie
   * quand il meurt,il quitte pas l'arene,on lui donne des caracteristiques faibles 
   * il peut tuer le petit prince a tous les coups et il redevient normal
   */
	private static final long serialVersionUID = 1L;
	private int vies = 2;
	private int vieInitiale;
	private int forceInitiale;


	public PersonnageZombie(String nom,	HashMap<Caracteristique, Integer> caracts) {
		super(nom, "Zombie", caracts);
		setVieInitiale(caracts.get(Caracteristique.VIE));
		setForceInitiale(caracts.get(Caracteristique.FORCE));
	}
	
	/**
	 * Quand il meurt,il quitte pas l'arene,on lui donne des caracteristiques faibles
	 */
	public void tue() {
		this.setVies(this.getVies() - 1);
		if( this.getVies() == 0){
			caracts.put(Caracteristique.VIE, (int)getVieInitiale()/4);
			caracts.put(Caracteristique.FORCE, (int)getForceInitiale()/4);
		}else{
			caracts.put(Caracteristique.VIE, 0);
		}	
	}

	/**
	 * Teste si le personnage est vivant, i.e., son nombre de points de vie
	 * est strictement superieur a 0.
	 * @return vrai si le personnage est vivant, faux sinon
	 */
	public boolean estVivant() {
		Integer vie = caracts.get(Caracteristique.VIE);
		return vie != null && vie > 0 && vies != 0;
	}
	
	/**
	 * il peut tuer le petit prince a tous les coups et il redevient normal
	 */
	public void tuerPrince() {
		setVies(2);
		caracts.put(Caracteristique.VIE, getVieInitiale());
		caracts.put(Caracteristique.FORCE, getForceInitiale());
	}
	
	public int getVies() {
		return vies;
	}
	public void setVies(int vies) {
		this.vies = vies;
	}
	public int getVieInitiale() {
		return vieInitiale;
	}
	public void setVieInitiale(int vieInitiale) {
		this.vieInitiale = vieInitiale;
	}
	public int getForceInitiale() {
		return forceInitiale;
	}
	public void setForceInitiale(int forceInitiale) {
		this.forceInitiale = forceInitiale;
	}
}
