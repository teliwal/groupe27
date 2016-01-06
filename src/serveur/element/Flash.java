package serveur.element;

import java.util.HashMap;

import utilitaires.Constantes;

public class Flash extends Potion{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Flash(String nom, String groupe, HashMap<Caracteristique, Integer> caracts) {
		super(nom, groupe, caracts);
		// TODO Auto-generated constructor stub
		this.caracts.put(Caracteristique.VITESSE, Constantes.AUG_VITESSE);
		this.caracts.put(Caracteristique.FORCE, Constantes.DIM_FORCE);
	}
}
