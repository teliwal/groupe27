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
		this.caracts.put(Caracteristique.VIE,Constantes.DIM_VIE);
		this.caracts.put(Caracteristique.FORCE,Constantes.DIM_FORCE);
		this.caracts.put(Caracteristique.INITIATIVE,Constantes.DIM_INITIATIVE);
		this.caracts.put(Caracteristique.ZONEATTACK,Constantes.DIM_ZONE);
		this.caracts.put(Caracteristique.VUE,Constantes.DIM_VUE);
		this.caracts.put(Caracteristique.DEFENSE,Constantes.DIM_VUE);
	}
}

