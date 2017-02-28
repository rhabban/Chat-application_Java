package client;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Observable;

import View.Launcher;

/**
 * <b>Client</b> allows a user to send messages to the server.
 * @author Corentin
 *
 */
public class Client2 extends Observable {
	private String name;
	private float position_x;
	private float position_y;
	
	private Socket socket = null;
	private OutputStream outputStream;
	
	@Override
    public void notifyObservers(Object arg) {
        super.setChanged();
        super.notifyObservers(arg);
    }
	
	/** Create socket, and receiving thread */
    public void InitSocket(String server, int port) throws IOException {
        socket = new Socket(server, port);
        outputStream = socket.getOutputStream();

        Thread receivingThread = new Thread() {
            @Override
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
    
    /** Send a line of text */
    public void send(String text) {
        try {
            outputStream.write((text + "\r\n").getBytes());
            outputStream.flush();
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
}
