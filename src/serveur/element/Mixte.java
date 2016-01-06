package serveur.element;

import java.util.HashMap;

import utilitaires.Calculs;

public class Mixte extends Potion{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Mixte(String nom, String groupe, HashMap<Caracteristique, Integer> caracts) {
		super(nom, groupe, caracts);
		// TODO Auto-generated constructor stub
		
		this.caracts.put(Caracteristique.VUE, Calculs.nombreAleatoire(0, 5));
		this.caracts.put(Caracteristique.FORCE, Calculs.nombreAleatoire(-10, 10));
		this.caracts.put(Caracteristique.VITESSE, Calculs.nombreAleatoire(-2, 3));
		this.caracts.put(Caracteristique.ZONEATTACK, Calculs.nombreAleatoire(-1, 2));
		this.caracts.put(Caracteristique.VIE, Calculs.nombreAleatoire(-10, 10));
		this.caracts.put(Caracteristique.INITIATIVE, Calculs.nombreAleatoire(-10, 10));
		
	}

}
