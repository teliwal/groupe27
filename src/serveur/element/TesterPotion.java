/**
 * 
 */
package serveur.element;

import java.util.HashMap;


/**
 * @author 21207221
 *
 */

public class TesterPotion{

	/**
	 * @param potion : la potion à ramasser
	 * prend la potion si ce n'est pas un poison
	 * */
	public boolean estPoison(Potion potion) {
		return (potion instanceof Poison);
	}
	
	/**
	 *@param potion : la potion à ramasser
	 *verifie si la potion  mixte peut bouster sa vie 
	 */
	public boolean vieAvantageuse(Potion potion) {
			return (potion.getCaract(Caracteristique.VIE) > 0);
	}
	
	/**
	 *@param potion : la potion à ramasser
	 *verifie si la potion mixte peut augmenter sa force
	*/
	public boolean forceAvantageuse(Potion potion) {
			return (potion.getCaract(Caracteristique.FORCE) > 0);
	}
	
	/**
	 *@param potion : la potion à ramasser
	 *verifie si la potion augmente sa vitesse et sa zone d'attaque
	 */
	public boolean forceEtVieAvantage(Potion potion) {
			return (potion.getCaract(Caracteristique.VITESSE) > 0) && (potion.getCaract(Caracteristique.ZONEATTACK) > 0);
	}
	
	public boolean potionAvantageuse(Potion potion) {
		return (potion.getCaract(Caracteristique.VUE) > 0) && 
				(potion.getCaract(Caracteristique.DEFENSE) > 0) && 
				(potion.getCaract(Caracteristique.INITIATIVE) > 0) &&
				(potion.getCaract(Caracteristique.VIE) >0);
	}
	
	/**
	 *@param potion : la potion à ramasser
	 *verifie si un personnage à atteind la force maximale, si oui on augmente sa vision
	 */
	
	public boolean forceEtvitesse(Potion potion) {
		return (potion.getCaract(Caracteristique.FORCE) > 0) && 
				(potion.getCaract(Caracteristique.VITESSE) > 0);
	}
	
	
	/**
	 *@param potion : la potion à ramasser
	 *verifie si un personnage à atteind la force maximale, si oui on augmente sa vision
	 */
	public boolean atteinteForceMaxi(Personnage personnage) {
		return personnage.getCaract(Caracteristique.FORCE) == 100 ? true : false;
	}	
}
