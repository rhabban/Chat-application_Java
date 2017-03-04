package model;

import java.awt.Graphics;

import javax.swing.JLabel;

/**
 * @author bastiensebire
 * Objet de type JLabel. Permet de pouvoir récupérer la position d'un JLabel sur la grille.
 */
public class GridCase extends JLabel{
	
	private int position;

	public GridCase(int position) {
		super();
		this.position = position;
	}

	public GridCase() {
		super();
	}

	@Override
	public String toString() {
		return "GridCase [position=" + position + "]";
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	

}
