package cajeroPrincipal.paneles;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cajeroPrincipal.*;

import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class ArribaPanel extends JPanel
{
	@SuppressWarnings("unused")
	private VentanaPrincipal padre;
	
	
	
	public ArribaPanel(VentanaPrincipal padre) throws IOException
	{
		this.padre = padre;
		
		BufferedImage myPicture = ImageIO.read(new File(System.getProperty("user.dir") + "/images/cajero/logoCajero.png"));
		
		ImageIcon mpAsIcon = new ImageIcon(myPicture);
		Image img1 = mpAsIcon.getImage();
		Image newPictureImage = img1.getScaledInstance(672,378,java.awt.Image.SCALE_SMOOTH);
		
		
		JLabel picLabel = new JLabel(new ImageIcon(newPictureImage));
		this.add(picLabel);
	}
	
}
