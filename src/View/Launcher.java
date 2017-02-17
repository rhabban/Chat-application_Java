package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.Client;

/**
 * <b>Launcher</b> of the application using Swing.
 * @author lenaic
 *
 */
public class Launcher extends JDialog implements ActionListener{

	JButton valid = new JButton("Valider");
	JButton quit = new JButton("Quitter");
	JTextField name = new JTextField(10);
	
	/**
	 * Constructor of the class.
	 */
	public Launcher() {
		JPanel pan ;
		Box box = Box.createVerticalBox();
		setModal(true);
		setTitle("Chat");
		pan = new JPanel();
		pan.add(new JLabel("Indiquez votre nom : "));
		pan.add(name);
		box.add(pan);

		pan = new JPanel();
		pan.add(valid);
		pan.add(quit);
		box.add(pan);

		add(box);

		valid.addActionListener(this);
		quit.addActionListener(this);
		this.setLocation(600, 400);
		this.setSize( 300 , 200 );
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == valid) {
			this.dispose();
			new Client("localhost", 28000,name.getText());
		}
		if (e.getSource() == quit) {
			this.dispose();
		}
	}

}
