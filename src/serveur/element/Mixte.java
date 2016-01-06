package serveur.element;

import java.util.HashMap;

import utilitaires.Constantes;

public class Mixte extends Potion{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Mixte(String nom, String groupe, HashMap<Caracteristique, Integer> caracts) {
		super(nom, groupe, caracts);
		// TODO Auto-generated constructor stub
		this.caracts.put(Caracteristique.VUE, Constantes.DIM_VUE);
		this.caracts.put(Caracteristique.FORCE, Constantes.AUG_FORCE);
	}

}
