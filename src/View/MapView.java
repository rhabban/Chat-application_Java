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


/**
 * @author bastiensebire
 * Cette classe permet d'afficher la map et les utilisateurs connectés.
 * Peut-être devrait-on ajouter un paneau latéral qui permettrait de communiquer avec les autres utilisateurs
 */
public class MapView extends JFrame 
{
    private Container container;
    private MapManager mapManager;
    private GridView gridView;
    private Client currentClient;
    public MapView(Client currentClient) throws IOException
    {
        super("La dinde");
        this.currentClient = currentClient;
        
        // Configuration de la JFrame
        setLayout(null);
        setSize(745,735);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // On met l'image de la carte en backbground
        BufferedImage myImage = ImageIO.read(getClass().getResourceAsStream("/res/carte.png"));
        setContentPane(new ImagePanel(myImage));
        
        // Déclaration du MapManager et génération de la grille
        mapManager = new MapManager(10);
        gridView = new GridView(mapManager, currentClient);
        
        container = this.getContentPane();
		container.setLayout(new BorderLayout());
		container.add(gridView,BorderLayout.CENTER);
		container.repaint();
        
		setVisible(false);
    }
}
