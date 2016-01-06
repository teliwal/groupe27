package client;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.*;



import logger.LoggerProjet;
import serveur.IArene;
import serveur.element.Caracteristique;
import serveur.element.Element;
import serveur.element.PersonnageHulk;
import serveur.element.PersonnagePrince;
import serveur.element.Poison;
import serveur.element.Potion;
import utilitaires.Calculs;
import utilitaires.Constantes;
import client.controle.Console;

public class StrategiePrince extends Strategie{

	/**
	 * Cree un personnage, la console associe et sa strategie.
	 * @param ipArene ip de communication avec l'arene
	 * @param port port de communication avec l'arene
	 * @param ipConsole ip de la console du personnage
	 * @param nom nom du personnage
	 * @param groupe groupe d'etudiants du personnage
	 * @param nbTours nombre de tours pour ce personnage (si negatif, illimite)
	 * @param position position initiale du personnage dans l'arene
	 * @param logger gestionnaire de log
	 */
	public StrategiePrince(String ipArene, int port, String ipConsole, 
			String nom, String groupe, HashMap<Caracteristique, Integer> caracts,
			int nbTours, Point position, LoggerProjet logger) {

		logger.info("Lanceur", "Creation de la console...");

		try {
			caracts.put(Caracteristique.VUE, Caracteristique.VUE.getMax());
			console = new Console(ipArene, port, ipConsole, this, 
					new PersonnagePrince(nom, caracts), 
					nbTours, position, logger);
			logger.info("Lanceur", "Creation de la console reussie");

		} catch (Exception e) {
			logger.info("Personnage", "Erreur lors de la creation de la console : \n" + e.toString());
			e.printStackTrace();
		}
	}

	// TODO etablir une strategie afin d'evoluer dans l'arene de combat
	// une proposition de strategie (simple) est donnee ci-dessous
	/** 
	 * Decrit la strategie.
	 * Les methodes pour evoluer dans le jeu doivent etre les methodes RMI
	 * de Arene et de ConsolePersonnage. 
	 * @param voisins element voisins de cet element (elements qu'il voit)
	 * @throws RemoteException
	 */
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

			/*Si Hul*/
			Integer refRmiVoisin = 0;
			boolean trouve = false;	
			Iterator<Integer> it = voisins.keySet().iterator();
			Element elem = null;
			while (!trouve && it.hasNext()) {
				refRmiVoisin = it.next();
				elem = arene.elementFromRef(refRmiVoisin);
				trouve = elem instanceof PersonnageHulk ; 
			}
			Point myPos = position;
			if(trouve && voisins.size() > 1){
				console.setPhrase("J evite Hulk");
				Point posHulk = voisins.get(refRmiVoisin);
				
				eviteUnElement(arene, refRMI, myPos, posHulk );
			}else{
				
				//if(voisins.size() > 1 && voisins.)


				int testDistance = Constantes.DISTANCE_MIN_INTERACTION;
				if(testDistance <= arene.elementFromRef(refRMI).getCaract(Caracteristique.ZONEATTACK)){
					testDistance = 1;
				}else{
					testDistance -= arene.elementFromRef(refRMI).getCaract(Caracteristique.ZONEATTACK);
				}
				//diminution de la zone d'attaque
				if(distPlusProche <= testDistance) { // si suffisamment proches
					// j'interagis directement
					if(elemPlusProche instanceof Potion) { // potion
						//si Poison -> evite
						if(elemPlusProche instanceof Poison){
							console.setPhrase("Je evite le Poison");
							eviteUnElement(arene, refRMI, myPos, arene.getPosition(refCible));
						}else{
							//sinon ramassage
							console.setPhrase("Je ramasse une potion");
							arene.ramassePotion(refRMI, refCible);
						}
					} else { // personnage
						// duel
						Element me = arene.elementFromRef(refRMI);
						int myForce = me.getCaract(Caracteristique.FORCE);
						int myLive = me.getCaract(Caracteristique.VIE);
						
						/*parcourir les voisins pour choisir le plus faible*/
						
						int minForce = 100;
						int minVie = 100;
						
						it = voisins.keySet().iterator();
						int refFailee = 0;
						while (it.hasNext()) {
							refRmiVoisin = it.next();
							elem = arene.elementFromRef(refRmiVoisin);
							int elemVie = elem.getCaract(Caracteristique.VIE);
							int elemForce = elem.getCaract(Caracteristique.FORCE);
							if((elemVie <= minVie || elemForce <= minForce) && (elemVie+elemForce <= minVie+minForce)){
								minVie = elemVie;
								minForce = elemForce;
								refFailee = refRmiVoisin;
							}
						}
						refCible = refFailee;
						if((voisins.size() <= 1 || myForce > minForce) && myLive > 10){
							console.setPhrase("Je fais un duel avec " + elemPlusProche.getNom());
							arene.lanceAttaque(refRMI, refCible);
						}
					}

				} else { // si voisins, mais plus eloignes
					// je vais vers le plus proche
					console.setPhrase("Je vais vers mon voisin " + elemPlusProche.getNom());
					arene.deplace(refRMI, refCible);
				}
			}
		}
	}
	
	public void eviteUnElement(IArene arene, int refRMI, Point myPos, Point posElem ){
		Point dir = new Point(myPos.x-posElem.x,myPos.y-posElem.y);
		try {
			arene.deplace(refRMI, new Point(myPos.x+2*dir.x,myPos.y+2*dir.y));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}

