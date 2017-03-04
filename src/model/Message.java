package model;

import java.io.Serializable;

public class Message implements Serializable {
	public final static int _NAME_ = 0;
	public final static int _TEXT_ = 1;
	public final static int _POSITION_ = 2;
	public final static int _DISCONNECT_ = 3;

	public String text;
	
	public String clientName;
	public int type;
	
	public float posX;
	public float posY;
	
	public Message(int type, String text, String clientName, float posX, float posY){
		this.type = type;
		this.text = text;
		this.clientName = clientName;
		
		this.posX = posX;
		this.posY = posY;
	}
	
	@Override
	public String toString() {
		return "Message [text=" + text + ", clientName=" + clientName + ", type=" + type + ", posX=" + posX + ", posY="
				+ posY + "]";
	}
	
}
