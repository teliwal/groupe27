package serveur.element;

import java.util.HashMap;

import utilitaires.Constantes;

public class Soigneur extends Potion {

	public Soigneur(String nom, String groupe, HashMap<Caracteristique, Integer> caracts) {
		super(nom, groupe, caracts);
		caracts.put(Caracteristique.VIE,Constantes.SOIGN_VIE);
		caracts.put(Caracteristique.FORCE,Constantes.SOIGN_FORCE);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
