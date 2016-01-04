package client;

import java.awt.Point;
import java.rmi.RemoteException;
import java.util.HashMap;

import client.controle.Console;

public abstract class Strategie {
	/**
	 * Console permettant d'ajouter une phrase et de recuperer le serveur 
	 * (l'arene).
	 */
	protected Console console;
	public abstract void executeStrategie(HashMap<Integer, Point> voisins) throws RemoteException;
}
