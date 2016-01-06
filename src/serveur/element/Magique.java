/**
 * 
 */
package serveur.element;

import java.util.HashMap;

import utilitaires.Constantes;

/**
 * @author kotoly
 *
 */
public class Magique extends Potion{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Magique(String nom, String groupe, HashMap<Caracteristique, Integer> caracts) {
		super(nom, groupe, caracts);
		// TODO Auto-generated constructor stub
		this.caracts.put(Caracteristique.INITIATIVE,Constantes.AUG_INITIATIVE);
		this.caracts.put(Caracteristique.VUE, Constantes.AUG_VUE);
		this.caracts.put(Caracteristique.VITESSE, Constantes.AUG_VITESSE);
		this.caracts.put(Caracteristique.ZONEATTACK, Constantes.AUG_ZONE);
	}

}
