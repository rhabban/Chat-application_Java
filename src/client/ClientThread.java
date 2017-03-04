package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
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
	private PrintStream streamOut = null;
	
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
			streamOut = new PrintStream(clientSocket.getOutputStream());
			streamOut.println("Entrez votre nom :");
			Message msgName = (Message)streamIn.readObject();
			name = msgName.text;
			//streamIn.close();	
						
				for(ClientThread thread : threads){
					if(thread != this){
						thread.streamOut.println(name + " s'est connect�");
					} else {
						thread.streamOut.println("Bonjour " + name + " et bienvenue dans le chat. Pour communiquer avec les utilisateurs, il est n�cessaire de se positionner � leur port�e");
					}			
				}			
				
			/* Start the conversation. */
			while (true) {
				//String line = streamIn.readLine();
				/*if (line.startsWith("/bye")) {
					disconnect();
				} else {
					sendMessage(line);
				}*/
				Message msg = (Message)streamIn.readObject();
				if(msg.text == "/bye")
					disconnect();
				else 
					sendMessage(msg);
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace(); 
		}
  }
	
	public synchronized void sendMessage(Message message){
		for(ClientThread thread : threads){
			thread.streamOut.println("<" + message.clientName + "> " + message.text);
		}
	}
	
	public synchronized void disconnect(){
		try{
			for(ClientThread thread : threads){
				thread.streamOut.println(name + " s'est d�connect�");
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
