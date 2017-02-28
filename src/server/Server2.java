package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import client.ClientThread;

/**
 * <b>Server</b> allow the communication between many clients. 
 * @author Corentin
 *
 */
public class Server2 {  
	private static ServerSocket serverSocket = null;
	private static Socket clientSocket = null;
	
	private static int port = 28000;
	private static ArrayList<ClientThread> threads = new ArrayList<ClientThread>();

	public static void main(String args[]) {

	    try {
	    	serverSocket = new ServerSocket(port);
	    	System.out.println("Server started at localhost:"+port);
	    } catch (IOException e) {
	    	System.out.println(e);
	    }

	    /*
	     * Create a new client socket when a client is connecting
	     */
	    while (true) {
	      try {
	        clientSocket = serverSocket.accept();
	        ClientThread thread = new ClientThread(clientSocket, threads);
	        thread.start();
	        threads.add(thread);
	      } catch (IOException e) {
	        System.out.println(e);
	      }
	    }
	}
	
}
