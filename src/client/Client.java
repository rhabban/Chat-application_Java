package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Observable;

import model.Message;


/**
 * <b>Client</b> allows a user to send messages to the server.
 * @author Corentin
 *
 */
public class Client extends Observable {
	
	private String name;
	private float position_x;
	private float position_y;
	
	private Socket socket = null;
	private OutputStream outputStream;
	private ObjectOutputStream objectOutputStream;
	
	/** Create socket, and receiving thread */
    public void InitSocket(String server, int port) throws IOException {
        socket = new Socket(server, port);
        outputStream = socket.getOutputStream();
        objectOutputStream = new ObjectOutputStream(outputStream);

        Thread receivingThread = new Thread() {
            public void run() {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line = null;
                    while ((line = reader.readLine()) != null){
                        notifyObservers(line);
                    }
                } catch (IOException e) {
                    notifyObservers(e);
                }
            }
        };
        receivingThread.start();
    }
    
    public void notifyObservers(Object arg) {
        super.setChanged();
        super.notifyObservers(arg);
    }
    
    /** Send a line of text */
    public void send(String text) {
        try {
            //outputStream.write((text + "\r\n").getBytes());
            //outputStream.flush();
        	Message message = new Message(text, this.name, this.position_x, this.position_y);
        	objectOutputStream.writeObject(message);
        	objectOutputStream.flush();
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
	public void setX(float position_x) {
		this.position_x = position_x;
	}

	/** Getter for the y position */
	public float getY() {
		return position_y;
	}

	/** Setter for the y position */
	public void setY(float position_y) {
		this.position_y = position_y;
	}

	@Override
	public String toString() {
		return "Client [name=" + name + ", position_x=" + position_x + ", position_y=" + position_y + "]";
	}
	
	

}
