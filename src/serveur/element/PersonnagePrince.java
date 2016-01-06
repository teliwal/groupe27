package serveur.element;

import java.util.HashMap;

public class PersonnagePrince extends Personnage{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PersonnagePrince(String nom,
			HashMap<Caracteristique, Integer> caracts) {
		super(nom, "Prince", caracts);
		this.caracts.put(Caracteristique.VUE, Caracteristique.VUE.getMax());
		this.caracts.put(Caracteristique.VITESSE, Caracteristique.VITESSE.getMax());
	}

}

