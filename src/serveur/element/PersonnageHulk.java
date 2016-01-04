package serveur.element;

import java.util.HashMap;

public class PersonnageHulk extends Personnage{
	/**
	 * Hulk : il est le plus fort,toujours prêt pour le combat
	 * mais il n'est pas assez intelligent 
	 * il lui arrive meme de se suicider
	 * il peut attaquer a distance
	 */
	private static final long serialVersionUID = 1L;
	
	public PersonnageHulk(String nom, HashMap<Caracteristique, Integer> caracts) {
		super(nom, "Hulk", caracts);
		caracts.put(Caracteristique.FORCE, Caracteristique.FORCE.getMax());
	}

}
