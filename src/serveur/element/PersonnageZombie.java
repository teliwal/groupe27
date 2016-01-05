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
	private Integer vieInitiale;
	private Integer forceInitiale;
	private boolean tue = false;

	public int getVies() {
		return vies;
	}
	public void setVies(int vies) {
		this.vies = vies;
	}
	public Integer getVieInitiale() {
		return vieInitiale;
	}
	public void setVieInitiale(Integer vieInitiale) {
		this.vieInitiale = vieInitiale;
	}
	public Integer getForceInitiale() {
		return forceInitiale;
	}
	public void setForceInitiale(Integer forceInitiale) {
		this.forceInitiale = forceInitiale;
	}

	public PersonnageZombie(String nom,	HashMap<Caracteristique, Integer> caracts) {
		super(nom, "Zombie", caracts);
		Integer initVie = caracts.get(Caracteristique.VIE);
		Integer initForce = caracts.get(Caracteristique.VIE);
		setVieInitiale(initVie);
		setForceInitiale(initForce);
	}
	
	/**
	 * Quand il meurt,il quitte pas l'arene,on lui donne des caracteristiques faibles
	 */
	public void tue() {
		this.setVies(this.getVies() - 1);
		tue = true;
		if( this.getVies() == 1){
			caracts.put(Caracteristique.VIE, 20);
			caracts.put(Caracteristique.FORCE, 20);
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
		if(vie == null || vie < 1){
			this.setVies(this.getVies() - 1);
		}
		if( this.getVies() == 1){
			if(tue == false){
				caracts.put(Caracteristique.VIE, 20);
				caracts.put(Caracteristique.FORCE, 20);
				tue = true;
			}
			return true;
		}else if ( this.getVies() < 1){ 
			return vie != null && vie > 0 ;
		}else{
			return true;
		}
	}
	
	/**
	 * il peut tuer le petit prince a tous les coups et il redevient normal
	 */
	public void tuerPrince() {
		setVies(2);
		caracts.put(Caracteristique.VIE, (int)getVieInitiale());
		caracts.put(Caracteristique.FORCE, (int)getForceInitiale());
		tue = false;
	}

}
