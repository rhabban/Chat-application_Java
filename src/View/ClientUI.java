package View;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import client.*;
import utils.ImagePanel;

/**
 * <b>ClientUI</b> is the user interface. Each ClientUI create and observe a new Client. 
 * @author Corentin, Raphael
 */
public class ClientUI extends JFrame implements Observer{

    private JTextArea textArea;
    private JTextField inputTextField;
    private JButton sendButton;
    private Client client;
    private Box boxUsers;

    public ClientUI(Client client) throws IOException {
        this.client = client;
        // Client is observed by ClientUI
        client.addObserver(this);
        createUI();
    }

    /** Build user interface 
     * @throws IOException */
    private void createUI() throws IOException {
    	
    	JFrame map = new MapView(this.client);
    	ImagePanel mapPanel = (ImagePanel) map.getContentPane();
    	mapPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    	add(mapPanel,BorderLayout.NORTH);
    	
        textArea = new JTextArea(20, 50);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        Box box = Box.createHorizontalBox();
        boxUsers = Box.createVerticalBox();
        boxUsers.setBorder(BorderFactory.createLineBorder(Color.black));
        add(box, BorderLayout.SOUTH);
        add(new JScrollPane(boxUsers), BorderLayout.EAST);
        inputTextField = new JTextField();
        sendButton = new JButton("Send");
        sendButton.setBackground(new Color(59,89,182));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        inputTextField.setBackground(new Color(225,225,225));
        inputTextField.setForeground(Color.BLACK);
        box.add(inputTextField);
        box.add(sendButton);

        // Send text line message to client
        ActionListener sendListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String str = inputTextField.getText();
                if (str != null && str.trim().length() > 0)
                	client.send(str);
                inputTextField.selectAll();
                inputTextField.requestFocus();
                inputTextField.setText("");
            }
        };
        inputTextField.addActionListener(sendListener);
        sendButton.addActionListener(sendListener);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	client.close();
            }
        });
        
    }

    /** Updates the UI depending on the Object argument */
    public void update(Observable o, Object arg) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                textArea.append(arg.toString());
                textArea.append("\n");
                boxUsers.removeAll();
                JLabel titre = new JLabel("Liste des utilisateurs connectés : ");
                boxUsers.add(titre);
                ArrayList<Client> clientsList = client.getClientsData();
                for (Client c : clientsList) {
                	if(c != null){
                		JLabel userName = new JLabel(c.getName());
                		boxUsers.add(userName);
                	}
                }
                System.out.println("ClientUI update : " + clientsList);
            }
        });
    }
    
    public static void main(String[] args) throws IOException {
        Client client = new Client();

        JFrame frame = new ClientUI(client);
        frame.setTitle("Chat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        String server = "localhost";
        int port = 28000;
        try {  	
        	client.InitSocket(server,port);
        	
        } catch (IOException e) {
            System.out.println("Error while connecting to " + server + ":" + port);
            e.printStackTrace();
            System.exit(0);
        }
    }
}
