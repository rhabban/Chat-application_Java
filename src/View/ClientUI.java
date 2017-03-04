package View;

import java.util.Observable;
import java.util.Observer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import client.*;

public class ClientUI extends JFrame implements Observer{

    private JTextArea textArea;
    private JTextField inputTextField;
    private JButton sendButton;
    private Client client;

    public ClientUI(Client client) {
        this.client = client;
        client.addObserver(this);
        createUI();
    }

    /** Build user interface */
    private void createUI() {
        textArea = new JTextArea(20, 50);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        Box box = Box.createHorizontalBox();
        add(box, BorderLayout.SOUTH);
        inputTextField = new JTextField();
        sendButton = new JButton("Send");
        box.add(inputTextField);
        box.add(sendButton);

        // Action for the inputTextField and the goButton
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
            }
        });
    }
    
    public static void main(String[] args) {
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
        	try {
        		// Une fois le client créé, on génère le view MapView avec ce client en paramètre
    			new MapView(client);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        	
        } catch (IOException e) {
            System.out.println("Error while connecting to " + server + ":" + port);
            e.printStackTrace();
            System.exit(0);
        }
    }
}
