package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * 
 * @author Corentin
 */


// For every client's connection we call this class
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

	public void run() {
		ArrayList<ClientThread> threads = this.threads;

		try {
			streamIn = new DataInputStream(clientSocket.getInputStream());
			streamOut = new PrintStream(clientSocket.getOutputStream());
			

			streamOut.println("Entrer votre nom :");
			name = streamIn.readLine().trim();

			streamOut.println("Bonjour " + name + " et bienvenue dans le chat. Pour communiquer avec les utilisateurs, il est nécessaire de se positionner à leur portée");
			synchronized (this) {
				// Vérifier si c'est nécessaire
				/*for(ClientThread thread : threads){
					if(thread == this){
						name = name;
					}
				}*/
				
				for(ClientThread thread : threads){
					if(thread != this){
						thread.streamOut.println("/* Un nouvel utilisateur s'est connecté : " + name+ "*/");
					} else {
						thread.streamOut.println("message posté");
					}			
				}
			}
			
			/* Start the conversation. */
			while (true) {
				String line = streamIn.readLine();
				if (line.startsWith("/quit")) {
					break;
				}
				
				/* The message is public, broadcast it to all other clients. */
				synchronized (this) {
					for(ClientThread thread : threads){
						thread.streamOut.println("<" + name + "> " + line);
					}
				}

            }
			
			/*synchronized (this) {
				for (int i = 0; i < maxClientsCount; i++) {
					if (threads[i] != null && threads[i] != this
							&& threads[i].clientName != null) {
						threads[i].os.println("*** The user " + name
                + " is leaving the chat room !!! ***");
					}
				}
			}*/
			streamOut.println("/* Bye " + name + " */");

	      /*
	       * Clean up. Set the current thread variable to null so that a new client
	       * could be accepted by the server.
	       */
			synchronized (this) {
				for(ClientThread thread : threads){
					if(thread == this)
						thread = null;
				}
			}
			/*
			 * Close the output stream, close the input stream, close the socket.
			 */
			streamIn.close();	
			streamOut.close();
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace(); 
		}
  }
}
