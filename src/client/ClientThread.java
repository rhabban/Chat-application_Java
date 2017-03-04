package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

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
			streamIn = new DataInputStream(clientSocket.getInputStream());
			streamOut = new PrintStream(clientSocket.getOutputStream());
			
			streamOut.println("Entrez votre nom :");
			name = streamIn.readLine();
			
							
				for(ClientThread thread : threads){
					if(thread != this){
						thread.streamOut.println(name + " s'est connect�");
					} else {
						thread.streamOut.println("Bonjour " + name + " et bienvenue dans le chat. Pour communiquer avec les utilisateurs, il est n�cessaire de se positionner � leur port�e");
					}			
				}
			
			
			/* Start the conversation. */
			while (true) {
				String line = streamIn.readLine();
				if (line.startsWith("/bye")) {
					disconnect();
				} else {
					sendMessage(line);
				}
            }
		} catch (IOException e) {
			e.printStackTrace(); 
		}
  }
	
	public synchronized void sendMessage(String message){
		for(ClientThread thread : threads){
			thread.streamOut.println("<" + name + "> " + message);
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
