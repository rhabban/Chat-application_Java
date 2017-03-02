package server;


import java.net.*;
import java.io.*;

/**
 * <b>ServerThread</b> is the communication between client and server.
 * @author lenaic
 *
 */
public class ServerThread extends Thread {  
	private Socket socket = null;
	private Server server = null;
	private int id = -1;
	private DataInputStream streamIn = null;
	private DataOutputStream streamOut = null;

	/**
	 * Constructor of the class.
	 * @param server : server.
	 * @param socket : socket.
	 */
	public ServerThread(Server server, Socket socket) {  
		this.server = server;  
		this.socket = socket;  
		this.id = socket.getPort();
	}
	
	/**
	 * Inform that a new user is connected.
	 */
	public void run() {  
		System.out.println("Client " + id + " connecté.");
		while (true)
		{  
			try {
				String input = streamIn.readUTF();
				if(input != ""){
					//server.transmitMessage(input);
				}
			}
			catch(IOException e) { }
		}
	}
	
	/**
	 * Open the discussion channel.
	 * @throws IOException
	 */
	public void open() throws IOException {  
		streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
		streamOut = new DataOutputStream(socket.getOutputStream());
	}
	
	/**
	 * Close the discussion channel.
	 * @throws IOException
	 */
	public void close() throws IOException {  
		if (socket != null)   
			socket.close();
		if (streamIn != null)  
			streamIn.close();
		if (streamOut != null)  
			streamOut.close();
	}
	
	/**
	 * Send message to client
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException {
		streamOut.writeUTF(message);
		streamOut.flush();
	}
}
