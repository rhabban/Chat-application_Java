package View;

import javax.imageio.ImageIO;
import javax.swing.*;

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


public class TestView extends JFrame 
{
    private Container container;
    public TestView() throws IOException
    {
        super("La dinde");
        
        setLayout(null);
        setSize(1000,1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        BufferedImage myImage = ImageIO.read(new File("/Users/bastiensebire/Documents/Work/devoir-java/res/carte.png"));
        setContentPane(new ImagePanel(myImage));
        
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
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 10));
        panel.setOpaque(false);
        
        for(int i = 0; i < 100; i++)
        {
        	JLabel gridCase = new JLabel();
        	gridCase.setSize(200, 200);
        	gridCase.addMouseListener(ml);
        	gridCase.setTransferHandler(new TransferHandler("icon"));
        	panel.add(gridCase);
        }
        
        JLabel pinCase = new JLabel();
        pinCase.setIcon(pinIcon);
        
        pinCase.setSize(200, 200);
        pinCase.addMouseListener(ml);
        pinCase.setTransferHandler(new TransferHandler("icon"));
        panel.add(pinCase, 3);
        
        container = this.getContentPane();
		container.setLayout(new BorderLayout());
		container.add(panel,BorderLayout.CENTER);
		container.repaint();
        
		setVisible(true);
    }

                   
       
  public static void main(String[] args) throws IOException{
  
      new TestView();
  }
}
