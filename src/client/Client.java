package client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import View.Launcher;

/**
 * <b>Client</b> allows a user to send messages to the server.
 * @author lenaic
 *
 */
public class Client {
	
	private Socket socket = null;
	private DataInputStream  console = null;
	private DataInputStream  streamIn = null;
	private DataOutputStream streamOut = null;
	private String name;

	/**
	 * Constructor of the class.
	 * @param serverName : Host.
	 * @param serverPort : Port.
	 * @param name : Name of the user.
	 */
	public Client(String serverName, int serverPort, String name) {
		this.name = name;
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
		String console_in = "";
		while (!console_in.equals(".bye")) {  
			try {  
				console_in = console.readLine();
				streamOut.writeUTF(this.name +": " + console_in);
				streamOut.flush();
			}
			catch(IOException e)
			{  
				e.printStackTrace();
			}
			try {
				String server_out = streamIn.readUTF();
				System.out.println(server_out);
			}
			catch(IOException e) { }
		}
	}
	
	/**
	 * Start the communication with the server
	 * @throws IOException
	 */
	public void start() throws IOException {  
		console   = new DataInputStream(System.in);
		streamOut = new DataOutputStream(socket.getOutputStream());
		streamIn = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
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
	
	public String getName(){
		return name;
	}
}
