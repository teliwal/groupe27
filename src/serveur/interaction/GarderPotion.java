package serveur.interaction;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.logging.Level;

import serveur.Arene;
import serveur.element.Caracteristique;
import serveur.element.Malin;
import serveur.element.Personnage;
import serveur.element.PersonnageDjinnarou;
import serveur.element.PersonnagePrince;
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
			logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " est vivant ");
			Potion valeursPotion = (Potion) defenseur.getElement();
			logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " cast potion ");
			/*if(attaquant.getElement() instanceof PersonnagePrince){
				PersonnagePrince m = (PersonnagePrince) attaquant.getElement();
				m.addPotion(valeursPotion);
			}else{
				PersonnageDjinnarou m = (PersonnageDjinnarou) attaquant.getElement();
				m.addPotion(valeursPotion);
			}*/
			Malin m = (Malin) attaquant.getElement();
			logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " va garder la potion ");
			m.addPotion(valeursPotion);
			logs(Level.INFO, "Potion garde !");
			arene.ejectePotion(defenseur.getRefRMI());
			
		} else {
			logs(Level.INFO, Constantes.nomRaccourciClient(attaquant) + " ou " + 
					Constantes.nomRaccourciClient(defenseur) + " est deja mort... Rien ne se passe");
		}
	}
}
