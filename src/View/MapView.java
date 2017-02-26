package View;

import javax.imageio.ImageIO;
import javax.swing.*;

import client.Client;
import model.MapManager;
import server.Server;
import utils.ImagePanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class MapView extends JFrame 
{
    private Container container;
    private MapManager mapManager;
    private GridView gridView;
    private Client currentClient;
    public MapView() throws IOException
    {
        super("La dinde");
        
        setLayout(null);
        setSize(745,735);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        BufferedImage myImage = ImageIO.read(new File("/Users/bastiensebire/Documents/Work/devoir-java/res/carte.png"));
        setContentPane(new ImagePanel(myImage));
        
        mapManager = new MapManager(10);
        gridView = new GridView(mapManager);
        
        container = this.getContentPane();
		container.setLayout(new BorderLayout());
		container.add(gridView,BorderLayout.CENTER);
		container.repaint();
        
		setVisible(true);
    }
    
    public static void main(String[] args) throws IOException
    {
    	MapView frame = new MapView();
    }
}
