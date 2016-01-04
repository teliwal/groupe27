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
		caracts.put(Caracteristique.INITIATIVE,Constantes.AUG_INITIATIVE);
	}

}
