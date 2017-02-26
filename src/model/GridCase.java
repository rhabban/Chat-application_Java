package model;

import javax.swing.JLabel;

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
