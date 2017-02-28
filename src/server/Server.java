package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * <b>Server</b> allow the communication between many clients. 
 * @author lenaic
 *
 */
public class Server implements Runnable {  
	private ServerSocket server = null;
	private Thread thread = null;	
	private ArrayList<ServerThread> clients = new ArrayList<ServerThread>();

	/**
	 * Constructor of the class.
	 * @param port : Port.
	 */
	public Server(int port) {  
		try
		{  
			System.out.println("Connexion au port " + port + ", veuillez patienter  ...");
			server = new ServerSocket(port);  
			System.out.println("Serveur lancé: " + server);
			start();
		}
		catch(IOException e)
		{  
			e.printStackTrace();
		}
	}
	
	/**
	 * Waiting for client.
	 */
	public void run() {  
		while (thread != null) {  
			try {  
				System.out.println("En attente de clients ..."); 
				this.addThread(server.accept());
			}
			catch(IOException e) {  
				e.printStackTrace(); 
			}
		}
	}
	
	/**
	 * Add new client.
	 * @param socket : socket.
	 */
	public void addThread(Socket socket) {  
		System.out.println("Client accepté: " + socket);
		ServerThread serverThread = new ServerThread(this, socket);
		try {  
			serverThread.open();
			serverThread.start();
			clients.add(serverThread);
		}
		catch(IOException e)
		{  
			e.printStackTrace();
		}
	}
	
	/**
	 * Start a thread for a client.
	 */
	public void start() { 
		if (thread == null) {  
			thread = new Thread(this); 
			thread.start();
		}
	}
	
	/**
	 * Stop a thread for a client.
	 */
	public void stop() {
		if (thread != null) {  
			thread.stop(); 
			thread = null;
		}
	}
	
	/**
	 * Transmit input message event to all serverThreads
	 * @author CCH 
	 */
	public void transmitMessage(String message){
		System.out.println("________________________");
		System.out.println(message);
		
		for(ServerThread serverThread : clients){
			try {  
				serverThread.sendMessage(serverThread.getId()+message);
			}
			catch(IOException e)
			{  
				e.printStackTrace();
			}
			System.out.println(serverThread.getId());
		}
	}
	
	public static void main(String args[]){ 
		Server server = null;
		server = new Server(28000);
	}
	
}
