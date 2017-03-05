package model;

import java.io.Serializable;
import java.util.ArrayList;

import client.Client;

/**
 * <b>Message</b>
 * @author Corentin
 */
public class Message implements Serializable {
	public final static int _NAME_ = 0;
	public final static int _TEXT_ = 1;
	public final static int _CLIENTS_ = 2;
	public final static int _DISCONNECT_ = 3;
	public final static int _POSITION_ = 4;
	public final static int _CONNECT_ = 5;
	
	public int type;
	public String text;
	public String clientName;
	public int posX;
	public int posY;
	
	public ArrayList<Client> clients;
	
	public Message(int type, String text, String clientName, int posX, int posY, ArrayList<Client> clients){
		this.type = type;
		this.text = text;
		this.clientName = clientName;
		
		this.posX = posX;
		this.posY = posY;
		this.clients = clients;
	}
	
	public Message(int type, ArrayList<Client> clients){
		this.type = type;
		this.clients = clients;
	}

	@Override
	public String toString() {
		return "Message [type=" + type + ", text=" + text + ", clientName=" + clientName + ", posX=" + posX + ", posY="
				+ posY + ", clients=" + clients + "]";
	}	
}
