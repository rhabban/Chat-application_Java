package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MapView extends JFrame
{
	
	private Container container;
	
	
	public static void main(String[] args) 
	{
		try {
			MapView frame = new MapView();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param sea
	 * @throws IOException 
	 */
	public MapView() throws IOException 
	{	
		
		this.setTitle("My Super Chat");
		this.setSize(1000, 650);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		
		//ImageIcon imageIcon = new ImageIcon(this.getResource("res/carte.png"));
		
		/*this.setLayout(new GridLayout(50, 50));
		
		for(int i = 0; i < 50 * 50; i++)
		{
			this.add(new JButton("Button 1"));
		}
		this.pack();*/
		this.setVisible(true);
		
	}
}
