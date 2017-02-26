package model;

/**
 * @author bastiensebire
 * Cette classe va nous permettre de gérer l'affichage de la map.
 * Il serait bien de pouvoir récupérer la liste des clients connectés
 */
public class MapManager {

	private int size;
	// liste de clients
	
	/**
	 * @param size
	 * Taille d'un côté de la grille
	 */
	public MapManager(int size) {
		super();
		this.size = size;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "MapManager [size=" + size + "]";
	}
	
	
	
}
