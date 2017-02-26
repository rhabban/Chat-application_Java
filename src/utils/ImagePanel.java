package utils;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

/**
 * @author bastiensebire
 * Cette classe aide à créer une image de background pour la map.
 */
public class ImagePanel extends JComponent {
    private Image image;
    
    /**
     * @param image
     */
    public ImagePanel(Image image) {
        this.image = image;
    }
    @Override
    protected void paintComponent(Graphics g) {
    	
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }
}
