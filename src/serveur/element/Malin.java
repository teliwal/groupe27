package serveur.element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import utilitaires.Constantes;

public   class Malin extends Personnage {
	protected Potion gardePotion ;
	public boolean estLance = false;
	
	public Malin(String nom, String groupe,
			HashMap<Caracteristique, Integer> caracts) {
		super(nom, groupe, caracts);
		// TODO Auto-generated constructor stub
	}

	public void addPotion(Potion p){
		gardePotion = p;
		estLance = false;
	}
	
	public void potionLance(){
		 estLance = true;
	}
	
	public boolean possedePotion(){
		return !estLance;
	}
	public Potion getPotion(){
		return gardePotion;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
