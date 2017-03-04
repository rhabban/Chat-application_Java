package View;

import java.util.Observable;
import java.util.Observer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import client.*;
import utils.ImagePanel;

/**
 * <b>ClientThread</b>
 * @author Corentin
 */
public class ClientUI extends JFrame implements Observer{

    private JTextArea textArea;
    private JTextField inputTextField;
    private JButton sendButton;
    private Client client;
    private Box boxUsers;

    public ClientUI(Client client) throws IOException {
        this.client = client;
        client.addObserver(this);
        createUI();
    }

    /** Build user interface 
     * @throws IOException */
    private void createUI() throws IOException {
    	
    	// TODO :: supprimer la mapView de la Frame à part et l'intégrer à ClientUI
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
        for(int i=0; i<5;i++){
        	JLabel userName = new JLabel("user"+i);
        	boxUsers.add(userName);
        }
        //JTextArea usersList = new JTextArea("users List");
        sendButton = new JButton("Send");
        sendButton.setBackground(new Color(59,89,182));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        inputTextField.setBackground(new Color(225,225,225));
        inputTextField.setForeground(Color.BLACK);
        box.add(inputTextField);
        box.add(sendButton);
        //boxUsers.add(usersList);

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
            	//System.out.println(arg);
                textArea.append(arg.toString());
                textArea.append("\n");
            }
        });
        //System.out.println(arg);
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
