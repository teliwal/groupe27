package client;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.HashMap;

import client.controle.Console;
import logger.LoggerProjet;
import serveur.IArene;
import serveur.element.Caracteristique;
import serveur.element.Element;
import serveur.element.Jedi;
import serveur.element.PersonnageDjinnarou;
import serveur.element.PersonnageHulk;
import serveur.element.PersonnageZombie;
import utilitaires.Calculs;
import utilitaires.Constantes;

public class StrategieJedi extends Strategie {
	/**
	 * constructeurs
	 * @param ipArene
	 * @param port
	 * @param ipConsole
	 * @param nom
	 * @param groupe
	 * @param caracts
	 * @param nbTours
	 * @param position
	 * @param logger
	 */
	public StrategieJedi(String ipArene, int port, String ipConsole, String groupe,
			HashMap<Caracteristique, Integer> caracts, int nbTours, Point position, LoggerProjet logger) {
		logger.info("Lanceur", "Creation de la console...");
		try {
			caracts.put(Caracteristique.FORCE, Caracteristique.FORCE.getMax());
			console = new Console(ipArene, port, ipConsole, this, 
					new Jedi(groupe), 
					nbTours, position, logger);
			logger.info("Lanceur", "Creation de la console reussie");
			
		} catch (Exception e) {
			logger.info("Personnage", "Erreur lors de la creation de la console : \n" + e.toString());
			e.printStackTrace();
		}
	}
	/**
	 * il se bat contre Hulk et Zombie et donne des vies aux autres personnages,
	 * il prend pas les potions 
	 */
		@Override
	public void executeStrategie(HashMap<Integer, Point> voisins) throws RemoteException {
			// arene
			IArene arene = console.getArene();
			// reference RMI de l'element courant
			int refRMI = 0;
			// position de l'element courant
			Point position = null;
			try {
				refRMI = console.getRefRMI();
				position = arene.getPosition(refRMI);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			if (voisins.isEmpty()) { // je n'ai pas de voisins, j'erre
				console.setPhrase("J'erre...");
				arene.deplace(refRMI, 0); 	
			} else {
				int refCible = Calculs.chercheElementProche(arene,refRMI,position, voisins);
				int distPlusProche = Calculs.distanceChebyshev(position, arene.getPosition(refCible));
				Element elemPlusProche = arene.elementFromRef(refCible);
				int testDistance = arene.elementFromRef(refRMI).getCaract(Caracteristique.ZONEATTACK);
				//diminution de la zone d'attaque
				if(distPlusProche <= testDistance) { 
					if(elemPlusProche instanceof PersonnageHulk || elemPlusProche instanceof PersonnageZombie || elemPlusProche instanceof PersonnageDjinnarou) { 
						// duel
						console.setPhrase("Je fais un duel avec " + elemPlusProche.getNom());
						arene.lanceAttaque(refRMI, refCible);
					} else { // il  donne une vie
						console.setPhrase("Je nourris mes enfants");
						arene.donnerVie(refRMI, refCible);
					}
					
				} else { // si voisins, mais plus eloignes
					// je vais vers le plus proche
					console.setPhrase("Je vais vers mon voisin " + elemPlusProche.getNom());
					arene.deplace(refRMI, refCible);
				}

			}
		}
}