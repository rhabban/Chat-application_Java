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

import model.MapManager;
import utils.ImagePanel;

public class GridView extends JPanel {

	private MapManager mapManager;

	public GridView(MapManager mapManager) {
		super();
		this.mapManager = mapManager;
        
        ImageIcon pinIcon = new ImageIcon("/Users/bastiensebire/Documents/Work/devoir-java/res/pin.png"); // load the image to a imageIcon
        Image pin = pinIcon.getImage(); // transform it 
        Image newPin = pin.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
        pinIcon = new ImageIcon(newPin);  // transform it back
   
        MouseListener ml = new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
            
                JComponent jc = (JComponent)e.getSource();
                TransferHandler th = jc.getTransferHandler();
                th.exportAsDrag(jc, e, TransferHandler.COPY);
            }

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        };
        
        setLayout(new GridLayout(10, 10));
        setOpaque(false);
        
        for(int i = 0; i < mapManager.getSize() * mapManager.getSize(); i++)
        {
        	JLabel gridCase = new JLabel();
        	gridCase.setSize(200, 200);
        	gridCase.addMouseListener(ml);
        	gridCase.setTransferHandler(new TransferHandler("icon"));
        	add(gridCase);
        }
        
        JLabel pinCase = new JLabel();
        pinCase.setIcon(pinIcon);
        
        pinCase.setSize(200, 200);
        pinCase.addMouseListener(ml);
        pinCase.setTransferHandler(new TransferHandler("icon"));
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
