package serveur.interaction;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.logging.Level;

import serveur.Arene;
import serveur.element.Caracteristique;
import serveur.element.Malin;
import serveur.element.Potion;
import serveur.vuelement.VuePersonnage;
import serveur.vuelement.VuePotion;
import utilitaires.Constantes;

public class GarderPotion extends Interaction<VuePotion> {

	/**
	 * Cree une interaction de ramassage.
	 * @param arene arene
	 * @param ramasseur personnage ramassant la potion
	 * @param potion potion a ramasser
	 */
	public GarderPotion(Arene arene, VuePersonnage ramasseur, VuePotion potion) {
		super(arene, ramasseur, potion);
	}

	@Override
	public void interagit() {
		logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " essaye de garder " + 
				Constantes.nomRaccourciClient(defenseur));
		
		// si le personnage est vivant
		if(attaquant.getElement().estVivant()) {
			Potion valeursPotion = defenseur.getElement();
			
			Malin m = (Malin) attaquant.getElement();
			m.addPotion(valeursPotion);
			logs(Level.INFO, "Potion garde !");

			
		} else {
			logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " ou " + 
					Constantes.nomRaccourciClient(defenseur) + " est deja mort... Rien ne se passe");
		}
	}
}
