package serveur.interaction;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.logging.Level;

import serveur.Arene;
import serveur.element.Caracteristique;
import serveur.element.Personnage;
import serveur.vuelement.VuePersonnage;
import utilitaires.Calculs;

public class Donner extends Interaction<VuePersonnage> {

	/**
	 * Cree une interaction de don.
	 * @param arene arene
	 * @param attaquant attaquant
	 * @param defenseur defenseur
	 * le defenseur est projeté a une case aleatoire
	 */
	public Donner(Arene arene, VuePersonnage attaquant, VuePersonnage defenseur) {
		super(arene, attaquant, defenseur);
	}
	@Override
	public void interagit() {
		try {
			Personnage pAttaquant = attaquant.getElement();
			int vies = pAttaquant.getCaract(Caracteristique.DON);
			Point position = Calculs.positionAleatoireArene();
			if(vies>0)
			{
				int nbvie=vies>=10?10:vies;
				arene.incrementeCaractElement(defenseur, Caracteristique.VIE, nbvie);
				arene.incrementeCaractElement(attaquant, Caracteristique.DON, -nbvie);
			}
			defenseur.setPosition(position);
		} catch (RemoteException e) {
			logs(Level.INFO, "\nErreur lors d'une attaque : " + e.toString());
		}

	}
}