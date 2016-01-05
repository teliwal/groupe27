/**
 * 
 */
package serveur.element;

import java.util.HashMap;

/**
 * @author kotoly
 *
 */
public class PersonnageFou extends Personnage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PersonnageFou(String groupe, HashMap<Caracteristique, Integer> caracts) {
		super("Fou", groupe, caracts);
		// TODO Auto-generated constructor stub
		this.caracts.put(Caracteristique.VUE, 10);
		this.caracts.put(Caracteristique.VIE,20);
		this.caracts.put(Caracteristique.INITIATIVE, 10);
	}
	
}
