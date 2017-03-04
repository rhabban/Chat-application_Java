package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

import model.Message;

/**
 * <b>ClientThread</b>
 * @author Corentin
 */

public class ClientThread extends Thread{
	private String clientName = null;
	private int clientPosX = 0;
	private int clientPosY = 0;
	
	private Client clientData;
	
	private ObjectInputStream streamIn = null;
	private ObjectOutputStream streamOut = null;
	
	private Socket clientSocket = null;
	private ArrayList<ClientThread> threads = new ArrayList<ClientThread>();

	public ClientThread(Socket clientSocket, ArrayList<ClientThread> threads) {
		this.clientSocket = clientSocket;
		this.threads = threads;
    }

	public synchronized void run() {
		ArrayList<ClientThread> threads = this.threads;

		try {
			InputStream is = clientSocket.getInputStream();
			ObjectInputStream streamIn = new ObjectInputStream(is);
			streamOut = new ObjectOutputStream(clientSocket.getOutputStream());
			
			streamOut.writeObject(new Message(Message._TEXT_,"Quel est votre nom ?", "", 0, 0));
			streamOut.flush();
			Message msgName = (Message)streamIn.readObject();
			clientName = msgName.text;
			
			this.clientData = new Client(clientName, 0, 0);
									
			for(ClientThread thread : threads){
				if(thread != this){
					streamOut.writeObject(new Message(Message._TEXT_," s'est connecté !", clientName, 0, 0));
				} else {
					streamOut.writeObject(new Message( Message._NAME_, "Bonjour " + clientName + " et bienvenue dans le chat. Pour communiquer avec les utilisateurs, il est nécessaire de se positionner à leur portée", clientName, 0, 0));
				}
				//streamOut.flush();
			}				
				
			/* Start the conversation. */
			while (true) {
				Message msg = (Message)streamIn.readObject();
				if(msg != null){
					refreshClientData(msg.clientName, msg.posX, msg.posY);
					switch(msg.type){
						case Message._TEXT_:
							sendMessage(msg);
							break;
							
						case Message._DISCONNECT_:
							disconnect();
							break;
							
						default:
							System.out.println("test default");
							break;
					}
				}
				/*if(msg.type == 0){
					System.out.println(msg.text);
					if(msg.text == "/bye"){
						disconnect();
					} else {
						sendMessage(msg);
					}
				}*/
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace(); 
		}
	}
	
	public synchronized void sendMessage(Message message){
		Message clientsMsg = new Message(Message._CLIENTS_, "", "", 0, 0);
		clientsMsg.clients = getClients();
		//System.out.println(clientsMsg.clients);
		for(ClientThread thread : threads){
			try {
				thread.streamOut.writeObject(clientsMsg);
				thread.streamOut.writeObject(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void refreshClientData(String name, int posX, int posY){
		clientName = name;
		clientPosX = posX;
		clientPosY = posY;
		this.clientData.setName(name);
		this.clientData.setX(posX);
		this.clientData.setY(posY);
		
		System.out.println(name+" is refreshing position");
	}
	
	public ArrayList<Client> getClients(){
		ArrayList<Client> clients = new ArrayList<>();
		for(ClientThread thread : threads){
			clients.add(thread.clientData);
		}
		return clients;
	}
	
	public synchronized void disconnect(){
		try{
			for(ClientThread thread : threads){
				thread.streamOut.writeObject(new Message(Message._TEXT_, clientName + "s'est déconnecté !", "", 0, 0));
				if(thread == this)
					thread = null;
			}
			
			streamIn.close();	
			streamOut.close();
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace(); 
		}
	}
}
