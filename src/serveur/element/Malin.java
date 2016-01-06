package serveur.element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public   class Malin extends Personnage {
	protected List<Potion> lstPotion = new ArrayList<Potion>();
	public Malin(String nom, String groupe,
			HashMap<Caracteristique, Integer> caracts) {
		super(nom, groupe, caracts);
		// TODO Auto-generated constructor stub
	}

	public void addPotion(Potion p){
		lstPotion.add(p);
	}

	public Potion getPotion(int i){
		return lstPotion.get(i);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
