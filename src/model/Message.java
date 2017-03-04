package model;

import java.io.Serializable;

import client.Client;

public class Message implements Serializable {
	public String text;
	public String clientName;
	
	public float posX;
	public float posY;
	
	public Message(String text, String clientName, float posX, float posY){
		this.text = text;
		this.clientName = clientName;
		
		this.posX = posX;
		this.posY = posY;
	}
	
}
