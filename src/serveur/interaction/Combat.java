package serveur.interaction;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.logging.Level;

import serveur.Arene;
import serveur.element.Caracteristique;
import serveur.element.Personnage;
import serveur.vuelement.VuePersonnage;
import utilitaires.Calculs;
import utilitaires.Constantes;

public class Combat extends Interaction<VuePersonnage> {
	/**
	 * Cree une interaction de duel contre Jedi & Hulk
	 * @param arene arene
	 * @param attaquant attaquant
	 * @param defenseur defenseur
	 */
	public Combat(Arene arene, VuePersonnage attaquant, VuePersonnage defenseur) {
		super(arene, attaquant, defenseur);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void interagit() {
		// TODO Auto-generated method stub
		try {
			Personnage pAttaquant = attaquant.getElement();
			Personnage pDefenseur = defenseur.getElement();
			
			int forceAttaquant = pAttaquant.getCaract(Caracteristique.FORCE)*2;
			int forceDefenseur = pDefenseur.getCaract(Caracteristique.FORCE);
			int defenseDefenseur = pDefenseur.getCaract(Caracteristique.DEFENSE);
			int perteVie = forceAttaquant;
		
			Point positionEjection = positionEjection(defenseur.getPosition(), attaquant.getPosition(), forceAttaquant);
			int distance = Calculs.distanceChebyshev(defenseur.getPosition(),attaquant.getPosition());
			perteVie = forceAttaquant / distance;
			int perte = perteVie - (defenseDefenseur/5);
			
			// ejection du defenseur
			defenseur.setPosition(positionEjection);
			arene.incrementeCaractElement(defenseur, Caracteristique.DEFENSE, -perteVie);
			
				// degats
				if (perte > 0) {
					arene.incrementeCaractElement(defenseur, Caracteristique.VIE, -perte);
					arene.incrementeCaractElement(attaquant, Caracteristique.FORCE, forceDefenseur/10);
					arene.incrementeCaractElement(attaquant, Caracteristique.VIE, 10);
					logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " colle un marteau ("
							+ perte + " points de degats) a " + Constantes.nomRaccourciClient(defenseur));
				}else{
					logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " echec attaque a "
							 + Constantes.nomRaccourciClient(defenseur));
				}
				// initiative
				incrementeInitiative(defenseur);
				decrementeInitiative(attaquant);
			
		} catch (RemoteException e) {
			logs(Level.INFO, "\nErreur lors d'une attaque : " + e.toString());
		}
	}

	/**
	 * Incremente l'initiative du defenseur en cas de succes de l'attaque. 
	 * @param defenseur defenseur
	 * @throws RemoteException
	 */
	private void incrementeInitiative(VuePersonnage defenseur) throws RemoteException {
		arene.incrementeCaractElement(defenseur, Caracteristique.INITIATIVE, 
				Constantes.INCR_DECR_INITIATIVE_DUEL);
	}
	
	/**
	 * Decremente l'initiative de l'attaquant en cas de succes de l'attaque. 
	 * @param attaquant attaquant
	 * @throws RemoteException
	 */
	private void decrementeInitiative(VuePersonnage attaquant) throws RemoteException {
		arene.incrementeCaractElement(attaquant, Caracteristique.INITIATIVE, 
				-Constantes.INCR_DECR_INITIATIVE_DUEL);
	}

	
	/**
	 * Retourne la position ou le defenseur se retrouvera apres ejection.
	 * @param posDefenseur position d'origine du defenseur
	 * @param positionAtt position de l'attaquant
	 * @param forceAtt force de l'attaquant
	 * @return position d'ejection du personnage
	 */
	private Point positionEjection(Point posDefenseur, Point positionAtt, int forceAtt) {
		int distance = forceVersDistance(forceAtt);
		
		// abscisses 
		int dirX = posDefenseur.x - positionAtt.x;
		
		if (dirX > 0) {
			dirX = distance;
		}
		
		if (dirX < 0) {
			dirX = -distance;
		}
		
		// ordonnees
		int dirY = posDefenseur.y - positionAtt.y;
		
		if (dirY > 0) {
			dirY = distance;
		}
		
		if (dirY < 0) {
			dirY = -distance;
		}
		
		int x = posDefenseur.x + dirX;
		int y = posDefenseur.y + dirY;
		
		return Calculs.restreintPositionArene(new Point(x, y));
	}
	
	/**
	 * Calcule la distance a laquelle le defenseur est projete suite a un coup.
	 * @param forceAtt force de l'attaquant
	 * @return distance de projection
	 */
	private int forceVersDistance(int forceAtt) {
		int max = Caracteristique.FORCE.getMax();
		
		int quart = (int) (4 * ((float) (forceAtt - 1) / max)); // -1 pour le cas force = 100
		
		return Constantes.DISTANCE_PROJECTION[quart];
	}
}
