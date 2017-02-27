package model;

import client.Client;

/**
 * @author Raphael
 *
 * Check if two clients can communicate
 */
public class MessageValidator {

	private Client client1;
	private Client client2;
	
	/**
	 * Constructor of the class
	 * @param c1 : the first client
	 * @param c2 : the second client
	 */
	public MessageValidator(Client c1, Client c2) {
		this.client1 = c1;
		this.client1 = c2;
	}
	
	/**
	 * Check if the two client is near or not. They are near when they are 
	 * within 4 case of each other.
	 * @return true if the two client can communicate or false if they can't
	 */
	public boolean isClientsNear() {
		int x1 = this.client1.getX();
		int y1 = this.client1.getY();
		int x2 = this.client2.getX();
		int y2 = this.client2.getY();
		// Portée de communication entre deux clients
		int scope = 4;
		
		if(x1 <= x2) {
			// Si le client 2 n'est plus à portée du client 1 sur l'axe x
			if((x2-x1) > scope) {
				return false;
			}
		}
		if(x2 <= x1) {
			// Si le client 1 n'est plus à portée du client 2 sur l'axe x
			if((x1-x2) > scope) {
				return false;
			}
		}
		if(y1 <= y2) {
			// Si le client 2 n'est plus à portée du client 1 sur l'axe y
			if((y2-y1) > scope) {
				return false;
			}
		}
		if(y2 <= y1) {
			// Si le client 1 n'est plus à portée du client 2 sur l'axe y
			if((y1-y2) > scope) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * toSring method
	 */
	public String toString() {
		String s = "";
		if(this.isClientsNear()) {
			s += "Les clients" + this.client1.getName() + " et " + this.client2.getName() + " sont à portée de communication";
		}
		else {
			s += "Les clients" + this.client1.getName() + " et " + this.client2.getName() + " ne sont pas à portée de communication";
		}
		return s;
	}

	/**
	 * Test class
	 * @param args
	 */
	public static void main(String[] args) {
		Client c1 = new Client("localhost", 28000,"client1",1,1);
		Client c2 = new Client("localhost", 28000,"client2",1,2);
		Client c3 = new Client("localhost", 28000,"client3",1,6);
		MessageValidator m1 = new MessageValidator(c1,c2);
		MessageValidator m2 = new MessageValidator(c1,c3);
		System.out.println("Test de proximité :");
		System.out.println(m1.toString());
		System.out.println(m2.toString());
	}
}