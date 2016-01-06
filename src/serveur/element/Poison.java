package serveur.element;

import java.util.HashMap;

import utilitaires.Constantes;

public class Poison extends Potion{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Poison(String nom, String groupe, HashMap<Caracteristique, Integer> caracts) {
		super(nom, groupe, caracts);
		caracts.put(Caracteristique.VIE,Constantes.DIM_VIE);
		caracts.put(Caracteristique.FORCE,Constantes.DIM_FORCE);
		caracts.put(Caracteristique.INITIATIVE,Constantes.DIM_INITIATIVE);
		caracts.put(Caracteristique.ZONEATTACK,Constantes.DIM_ZONE);
	}

}
