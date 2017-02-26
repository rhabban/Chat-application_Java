package model;

public class MapManager {

	private int size;
	// liste de clients

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
