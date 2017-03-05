package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import model.Message;
import model.MessageValidator;

/**
 * <b>ClientThread</b> is the thread that communicates with Server and Client. It receives Message from Client and sends Message to Client.
 * @author Corentin
 */
public class ClientThread extends Thread{	
	// Client Data (Name, posX, posY) saved in thread, not real Client instanced in ClientUI.
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
		// Each ClientThread knows its ClientThread's pair.
		ArrayList<ClientThread> threads = this.threads;

		try {
			InputStream is = clientSocket.getInputStream();
			ObjectInputStream streamIn = new ObjectInputStream(is);
			streamOut = new ObjectOutputStream(clientSocket.getOutputStream());
			
			// Ask to Client it's username and save him into clientData
			streamOut.writeObject(new Message(Message._TEXT_,"Quel est votre nom ?", "BOT", 0, 0, getClients()));
			Message msgName = (Message)streamIn.readObject();
			String clientName = msgName.text;
			
			this.clientData = new Client(clientName, 0, 0);
			
			for(ClientThread thread : threads){
				if(thread != this){
					streamOut.writeObject(new Message(Message._TEXT_," s'est connecté !", clientName, 0, 0, getClients()));
				} else {
					streamOut.writeObject(new Message( Message._NAME_, "Bonjour " + clientName + " et bienvenue dans le chat. Pour communiquer avec les utilisateurs, il est nécessaire de se positionner à leur portée", clientName, 0, 0, getClients()));
				}
				ArrayList<Client> clients = getClients();
			}				

			/* Start the conversation. */
			while (true) {
				ArrayList<Message> messages = new ArrayList<>();
				Message msg = (Message)streamIn.readObject();
				messages.add(msg);
				if(msg != null){
					switch(msg.type){
						case Message._TEXT_:
							sendMessages(messages);
							break;
							
						case Message._DISCONNECT_:
							disconnect();
							break;
							
						case Message._POSITION_:
							refreshClientData(msg.clientName, msg.posX, msg.posY);
							break;
							
						default:
							System.out.println("test default");
							break;
					}
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace(); 
		}
	}
	
	/** This method allows threads to send Messages to every Client **/
	public synchronized void sendMessages(ArrayList<Message> messages){
		ArrayList<Client> clients = getClients();

		for(ClientThread thread : threads){
			for(Message message : messages){
				try {
					// All clients connected to server are saved into Message to allow Client to know them each other.
					message.clients = clients;
					/*MessageValidator val = new MessageValidator(this.clientData, thread.clientData);
					if(this == thread || val.isClientsNear() == true)
						thread.streamOut.writeObject(message);
					else
						System.out.println("Hors de portée");*/
					thread.streamOut.writeObject(message);					
					System.out.println("ClientThread.sendMessages :" + message);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public synchronized void sendMessage(Message message){
		ArrayList<Message> messages = new ArrayList<>();
		messages.add(message);
		sendMessages(messages);
	}
	
	/** Save Client new position **/
	public synchronized void refreshClientData(String name, int posX, int posY){
		this.clientData.setName(name);
		this.clientData.setX(posX);
		this.clientData.setY(posY);
		
		System.out.println(name+" position updated" + clientData);
	}
	
	public synchronized Message createClientsMessage(){
		ArrayList<Client> clients = getClients();
		return (new Message(Message._CLIENTS_, clients));
	}
	
	/** Return the list of Thread's Clients **/
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
				thread.streamOut.writeObject(new Message(Message._TEXT_, clientData.getName() + "s'est déconnecté !", "", 0, 0, getClients()));
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
