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
	private String name = null;
	
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
			name = msgName.text;
			
			//System.out.println(name);
						
			for(ClientThread thread : threads){
				if(thread != this){
					streamOut.writeObject(new Message(Message._TEXT_," s'est connecté !", name, 0, 0));
				} else {
					streamOut.writeObject(new Message( Message._TEXT_, "Bonjour " + name + " et bienvenue dans le chat. Pour communiquer avec les utilisateurs, il est nécessaire de se positionner à leur portée", name, 0, 0));
				}
				//streamOut.flush();
			}				
				
			/* Start the conversation. */
			while (true) {
				Message msg = (Message)streamIn.readObject();
				if(msg != null){
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
		for(ClientThread thread : threads){
			try {
				thread.streamOut.writeObject(message);
				//System.out.println(message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void disconnect(){
		try{
			for(ClientThread thread : threads){
				thread.streamOut.writeObject(new Message(Message._TEXT_, name + "s'est déconnecté !", "", 0, 0));
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
