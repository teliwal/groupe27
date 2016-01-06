package serveur.element;

import utilitaires.Calculs;

public class Jedi extends Personnage{

	private static int num=1;
	
	public Jedi(String groupe) {
		super("Jedi_"+num++, groupe);
		this.caracts.put(Caracteristique.DON, 70);
		this.caracts.put(Caracteristique.VIE, Calculs.valeurCaracAleatoire(Caracteristique.VIE));
		this.caracts.put(Caracteristique.INITIATIVE, Calculs.valeurCaracAleatoire(Caracteristique.INITIATIVE));
		this.caracts.put(Caracteristique.FORCE, Calculs.valeurCaracAleatoire(Caracteristique.FORCE));
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}