package View;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

import model.GridCase;
import model.MapManager;
import utils.ImagePanel;

/**
 * @author bastiensebire
 * Permet de générer la grille sur laquelle seront positionnés les utilisateurs.
 * Il s'agit d'un JPanel transparent constitué d'un ensemble de JLabel (correspondant aux cases)
 */
public class GridView extends JPanel {

	private MapManager mapManager;

	public GridView(MapManager mapManager) {
		super();
		this.mapManager = mapManager;
        
		// Image du marqueur
        ImageIcon pinIcon = new ImageIcon(getClass().getResource("/res/pin.png")); // load the image to a imageIcon
        Image pin = pinIcon.getImage(); // transform it 
        Image newPin = pin.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        pinIcon = new ImageIcon(newPin);  // transform it back
   
        // Listener permettant de gérer le drag n drop
        MouseListener ml = new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
            
            	GridCase jc = (GridCase)e.getSource();
                TransferHandler th = jc.getTransferHandler();
                
                // Permet d'éviter de pouvoir déplacer une case vide
                if(jc.getIcon() != null)
                	th.exportAsDrag(jc, e, TransferHandler.COPY);
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            	GridCase jc = (GridCase)e.getSource();
            	
            	// Évite de dupliquer les marqueurs
            	jc.setIcon(null);
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        };
        
        setLayout(new GridLayout(10, 10));
        setOpaque(false);
        
        // Création de cases vides
        for(int i = 0; i < mapManager.getSize() * mapManager.getSize(); i++)
        {
        	GridCase gridCase = new GridCase(i);
        	gridCase.setSize(200, 200);
        	gridCase.addMouseListener(ml);
        	gridCase.setHorizontalTextPosition(JLabel.CENTER);
        	gridCase.setTransferHandler(new TransferHandler("icon"));
        	add(gridCase);
        }
        
        
        // On ajoute un marqueur pour le client
        // @TODO placer le marqueur d'un utilisateur venant de se connecter aléatoirement sur la grille
        GridCase pinCase = new GridCase(3);
        pinCase.setIcon(pinIcon);
        
        pinCase.setSize(200, 200);
        pinCase.addMouseListener(ml);
        pinCase.setHorizontalTextPosition(JLabel.CENTER);
        pinCase.setTransferHandler(new TransferHandler("icon"));
        //pinCase.paintComponent(getGraphics());
        add(pinCase, 3);
        
	}
	public MapManager getMapManager() {
		return mapManager;
	}

	public void setMapManager(MapManager mapManager) {
		this.mapManager = mapManager;
	}

	@Override
	public String toString() {
		return "GridView [mapManager=" + mapManager + "]";
	}
	
	
}
