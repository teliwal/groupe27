package client;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.*;

import logger.LoggerProjet;
import serveur.IArene;
import serveur.element.Caracteristique;
import serveur.element.Element;
import serveur.element.Malin;
import serveur.element.PersonnageHulk;
import serveur.element.PersonnagePrince;
import serveur.element.Poison;
import serveur.element.Potion;
import serveur.element.TesterPotion;
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
			Element elem = null;
			/*Iterator<Integer> it = voisins.keySet().iterator();
			Element elem = null;
			while (!trouve && it.hasNext()) {
				refRmiVoisin = it.next();
				elem = arene.elementFromRef(refRmiVoisin);
				trouve = elem instanceof PersonnageHulk ; 
				console.setPhrase("Je cherche le Hulk");
			}*/
			int refHulk=0;
			for(int refVoisin : voisins.keySet()) {
				if(arene.elementFromRef(refVoisin) instanceof PersonnageHulk){
					elemPlusProche = arene.elementFromRef(refVoisin);
					refHulk = refVoisin;
					trouve = true;
				}
			}
			Point myPos = position;
			if(trouve && voisins.size() > 1){
				console.setPhrase("J evite Hulk");
				Point posHulk = arene.getPosition(refHulk);  //voisins.get(refRmiVoisin);
				
				eviteUnElement(arene, refRMI, myPos, posHulk );
			}else{
				
				//if(voisins.size() > 1 && voisins.)


				int testDistance = arene.elementFromRef(refRMI).getCaract(Caracteristique.ZONEATTACK);
				//diminution de la zone d'attaque
				if(distPlusProche <= testDistance) { // si suffisamment proches
					// j'interagis directement
					if(elemPlusProche instanceof Potion) { // potion
						//si Poison -> evite
						if(elemPlusProche instanceof Poison){
							console.setPhrase("Je evite le Poison");
						}else{
							TesterPotion teste = new TesterPotion();
							//Si potion est avantageuse je la prend
							if(teste.potionAvantageuse((Potion) elemPlusProche)){
								console.setPhrase("Je ramasse une potion");
								arene.ramassePotion(refRMI, refCible);
							}else if(teste.estPoison((Potion) elemPlusProche)){//si c'est poison, je la garde
								console.setPhrase("Je garde une potion");
								arene.garderPotion(refRMI, refCible);
							}
						}
					} else { // personnage
						// duel
						Element me = arene.elementFromRef(refRMI);
						int myForce = me.getCaract(Caracteristique.FORCE);
						int myLive = me.getCaract(Caracteristique.VIE);
						
						/*parcourir les voisins pour choisir le plus faible*/
						
						int minForce = 100;
						int minVie = 100;
						int refFailee = 0;
						for(int refVoisin : voisins.keySet()) {
							elem = arene.elementFromRef(refVoisin);
							refRmiVoisin = refVoisin;
							int elemVie = elem.getCaract(Caracteristique.VIE);
							int elemForce = elem.getCaract(Caracteristique.FORCE);
							if((elemVie <= minVie || elemForce <= minForce) && (elemVie+elemForce <= minVie+minForce)){
								minVie = elemVie;
								minForce = elemForce;
								refFailee = refRmiVoisin;
							}
						}
						/*it = voisins.keySet().iterator();
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
						}*/
						refCible = refFailee;
						if((voisins.size() <= 1 || myForce > minForce)/*  && myLive > 10*/){
							console.setPhrase("Je fais un duel avec " + elemPlusProche.getNom());
							arene.lanceAttaque(refRMI, refCible);
						}
					}

				} else { // si voisins, mais plus eloignes
					Malin el = (Malin) arene.elementFromRef(refRMI);
					if(!el.getList().isEmpty()){
						for(int i = 0;i < el.getList().size();i++){
							if(el.getPotion(i) != null){
								deposerPotion(arene, arene.getPosition(refCible),el.getPotion(i));
								i = el.getList().size();
							}
						}
						console.setPhrase("Je depose une potion vers mon voisin " + elemPlusProche.getNom());
						
					}else{
						console.setPhrase("Je vais vers mon voisin " + elemPlusProche.getNom());
						arene.deplace(refRMI, refCible);
					}
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
	public void deposerPotion(IArene arene, Point position,Potion p){
		try {
			arene.ajoutePotion(p, position);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

