package client;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.HashMap;

import client.controle.Console;
import logger.LoggerProjet;
import serveur.IArene;
import serveur.element.Caracteristique;
import serveur.element.Element;
import serveur.element.Magique;
import serveur.element.Malin;
import serveur.element.PersonnageDjinnarou;
import serveur.element.Potion;
import serveur.element.TesterPotion;
import utilitaires.Calculs;
import utilitaires.Constantes;

public class StrategieDjinnarou extends Strategie {

	public StrategieDjinnarou(String ipArene, int port, String ipConsole, 
			String nom, String groupe, HashMap<Caracteristique, Integer> caracts,
			int nbTours, Point position, LoggerProjet logger) {
		
		logger.info("Lanceur", "Creation de la console...");
		
		try {
			console = new Console(ipArene, port, ipConsole, this, 
					new PersonnageDjinnarou(nom, groupe, caracts), 
					nbTours, position, logger);
			logger.info("Lanceur", "Creation de la console reussie");
			
		} catch (Exception e) {
			logger.info("Personnage", "Erreur lors de la creation de la console : \n" + e.toString());
			e.printStackTrace();
		}
	}
	
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
						// j'interagis directement
						if(elemPlusProche instanceof Potion) { // potion
							// ramassage
							TesterPotion teste = new TesterPotion();
							//Si potion est avantageuse je la prend
							if(teste.forceAvantageuse((Potion) elemPlusProche)){
								console.setPhrase("Je ramasse une potion");
								arene.ramassePotion(refRMI, refCible);
							}else if(teste.estPoison((Potion) elemPlusProche)){//si c'est poison, je la garde
								console.setPhrase("Je garde une potion");
								arene.garderPotion(refRMI, refCible);
							}

						} else { // personnage
							// duel
							console.setPhrase("Je fais un duel avec " + elemPlusProche.getNom());
							arene.lanceAttaque(refRMI, refCible);
						}
						
					} else { // si voisins, mais plus eloignes
						// je vais vers le plus proche
						if(elemPlusProche instanceof Potion)
						{
							console.setPhrase("Je me teleporte vers " + elemPlusProche.getNom());
							arene.seTeleporter(refRMI, refCible);
							//arene.deplace(refRMI, refCible);
						}
						else
						{
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
	
	public void deposerPotion(IArene arene, Point position,Potion p){
		try {
			arene.ajoutePotion(p, position);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
