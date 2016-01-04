package client;

import java.awt.Point;
import java.util.HashMap;

import logger.LoggerProjet;
import serveur.element.Caracteristique;

public class StrategieHulk extends StrategiePersonnage {

	public StrategieHulk(String ipArene, int port, String ipConsole, String nom, String groupe,
			HashMap<Caracteristique, Integer> caracts, int nbTours, Point position, LoggerProjet logger) {
		super(ipArene, port, ipConsole, nom, groupe, caracts, nbTours, position, logger);
		
	}

}
