package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import model.Message;

/**
 * <b>ReceivingThread</b>
 * @author Corentin
 */
public class ReceivingThread implements Runnable{
	
	private Socket socket;
	private Client client;
	
	public ReceivingThread(Socket socket, Client client){
		this.socket = socket;
		this.client = client;
	}
	
	public void run()
    {
		try {
        	InputStream is = socket.getInputStream();
        	ObjectInputStream streamIn = new ObjectInputStream(is);
        	Message msg = null;
        	while(true){
				msg = (Message)streamIn.readObject();
				if(msg.type == Message._NAME_){
					this.client.setName(msg.clientName);
					msg.clientName = "BOT";
				}
				this.client.setClientsData(msg.clients);
				this.client.notifyObservers("<"+msg.clientName+">"+msg.text);
				System.out.println("ReceivingThread.run Message :" + msg);
        	}

        } catch (IOException | ClassNotFoundException e) {
            this.client.notifyObservers(e);
        }
    }
}
