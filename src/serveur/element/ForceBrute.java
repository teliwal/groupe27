package serveur.element;

import java.util.HashMap;

import utilitaires.Constantes;

public class ForceBrute extends Potion{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ForceBrute(String nom, String groupe, HashMap<Caracteristique, Integer> caracts) {
		super(nom, groupe, caracts);
		// TODO Auto-generated constructor stub
		this.caracts.put(Caracteristique.VUE, Constantes.DIM_VUE);
		this.caracts.put(Caracteristique.FORCE, Constantes.AUG_FORCE);
	}
}
