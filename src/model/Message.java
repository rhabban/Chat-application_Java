package model;

import java.io.Serializable;
import java.util.ArrayList;

import client.Client;

/**
 * <b>ClientThread</b>
 * @author Corentin
 */
public class Message implements Serializable {
	public final static int _NAME_ = 0;
	public final static int _TEXT_ = 1;
	public final static int _CLIENTS_ = 2;
	public final static int _DISCONNECT_ = 3;
	public final static int _POSITION_ = 4;
	
	public int type;
	public String text;
	public String clientName;
	public int posX;
	public int posY;
	
	public ArrayList<Client> clients;
	
	public Message(int type, String text, String clientName, int posX, int posY){
		this.type = type;
		this.text = text;
		this.clientName = clientName;
		
		this.posX = posX;
		this.posY = posY;
	}

	public String getClientName() {
		return clientName;
	}
	
	
	public Message(int type, String text, String clientName, int posX, int posY, ArrayList<Client> clients){
		this(type, text, clientName, posX, posY);
		this.clients = clients;
	}
	
	@Override
	public String toString() {
		return "Message [text=" + text + ", clientName=" + clientName + ", type=" + type + ", posX=" + posX + ", posY="
				+ posY + "]";
	}
	
}
