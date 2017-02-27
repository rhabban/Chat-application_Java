package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import View.Launcher;

/**
 * <b>Client</b> allows a user to send messages to the server.
 * @author lenaic
 * @edited Raphael
 */
public class Client {
	
	private Socket socket = null;
	private DataInputStream  console = null;
	private DataOutputStream streamOut = null;
	private String name;
	private int x;
	private int y;

	/**
	 * Constructor of the class.
	 * @param serverName : Host.
	 * @param serverPort : Port.
	 * @param name : Name of the user.
	 * @param x : the position of the user in the x axe
	 * @param y : the position of the user in the y axe
	 */
	public Client(String serverName, int serverPort, String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
		System.out.println("Connexion en cours ...");
		try
		{  
			socket = new Socket(serverName, serverPort);
			System.out.println("Connecté: " + this.name);
			this.start();
		}
		catch(IOException e)
		{  
			e.printStackTrace();
		}
		String line = "";
		while (!line.equals(".bye")) {  
			try {  
				line = console.readLine();
				streamOut.writeUTF(this.name +": " + line);
				streamOut.flush();
			}
			catch(IOException e)
			{  
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Getter for the client name
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter for the x position
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/** 
	 * Setter for the x position
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * * Getter for the y position
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Setter for the y position
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Start the communication with the server
	 * @throws IOException
	 */
	public void start() throws IOException {  
		console   = new DataInputStream(System.in);
		streamOut = new DataOutputStream(socket.getOutputStream());
	}
	
	/**
	 * Stop the communication with the server
	 */
	public void stop() {  
		try{  
			if (console != null)  
				console.close();
			if (streamOut != null)  
				streamOut.close();
			if (socket != null)  
				socket.close();
		}
		catch(IOException e)
		{  
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {  
		new Launcher();
	}
}
