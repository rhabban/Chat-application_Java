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
	
	private DataInputStream streamIn = null;
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
			ObjectInputStream ois = new ObjectInputStream(is);
			streamOut = new PrintStream(clientSocket.getOutputStream());
			
			streamOut.println("Entrer votre nom :");
			Message msgName = (Message)ois.readObject();
			name = msgName.text;
			
						
				for(ClientThread thread : threads){
					if(thread != this){
						thread.streamOut.println(name + " s'est connecté");
					} else {
						thread.streamOut.println("Bonjour " + name + " et bienvenue dans le chat. Pour communiquer avec les utilisateurs, il est nécessaire de se positionner à leur portée");
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
				Message msg = (Message)ois.readObject();
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
			thread.streamOut.println("<" + message.clientName + "> " + message);
		}
	}
	
	public synchronized void disconnect(){
		try{
			for(ClientThread thread : threads){
				thread.streamOut.println(name + " s'est déconnecté");
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
