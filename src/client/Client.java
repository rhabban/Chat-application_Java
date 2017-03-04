package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

import model.Message;


/**
 * <b>Client</b> allows a user to send messages to the server.
 * @author Corentin
 *
 */
public class Client extends Observable implements Serializable {
	
	private String name = "test";
	private int position_x;
	private int position_y;
	

	private ArrayList<Client> clientsData = new ArrayList<>();
	
	private Socket socket = null;
	private OutputStream outputStream;
	private ObjectOutputStream objectOutputStream;
	
	public Client(){
		super();
	}
	
	public Client(String name, int position_x, int position_y){
		this.name = name;
		this.position_x = position_x;
		this.position_y = position_y;
	}
		
	/** Create socket, and receiving thread */
    public void InitSocket(String server, int port) throws IOException {
        socket = new Socket(server, port);
        outputStream = socket.getOutputStream();
        objectOutputStream = new ObjectOutputStream(outputStream);
        
        Thread receivingThread = new Thread(new ReceivingThread(socket, this));
        receivingThread.start();
    }
    
    public void notifyObservers(Object arg) {
        super.setChanged();
        super.notifyObservers(arg);
    }
    
    /** Send a line of text */
    public void send(String text) {
        try {
        	Message message = new Message(Message._TEXT_, text, this.name, this.position_x, this.position_y);
        	objectOutputStream.writeObject(message);
        	//objectOutputStream.flush();
        } catch (IOException e) {
            notifyObservers(e);
        }
    }
    
    /** Close the socket */
    public void close() {
        try {
            socket.close();
        } catch (IOException ex) {
            notifyObservers(ex);
        }
    }
    
    /** Getter for the name */
	public String getName() {
		return name;
	}

	/** Setter for the name */
	public void setName(String name) {
		this.name = name;
	}

	/** Getter for the x position */
	public float getX() {
		return position_x;
	}

	/** Setter for the x position */
	public void setX(int position_x) {
		this.position_x = position_x;
	}

	/** Getter for the y position */
	public float getY() {
		return position_y;
	}

	/** Setter for the y position */
	public void setY(int position_y) {
		this.position_y = position_y;
	}
	
	public void setClientsData(ArrayList<Client> clients){
		this.clientsData = clients;
		System.out.println(clients);
	}
	
	public ArrayList<Client> getClientsData(){
		return clientsData;
	}

	@Override
	public String toString() {
		return "Client [name=" + name + ", position_x=" + position_x + ", position_y=" + position_y + "]";
	}
	
	

}
