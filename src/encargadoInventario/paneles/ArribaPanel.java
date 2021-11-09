package encargadoInventario.paneles;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import encargadoInventario.interfaz.*;

@SuppressWarnings("serial")
public class ArribaPanel extends JPanel
{
	private VentanaPrincipalEncargado padre;
	
	
	
	public ArribaPanel(VentanaPrincipalEncargado padre) throws IOException
	{
		this.padre = padre;
		
		BufferedImage myPicture = ImageIO.read(new File(System.getProperty("user.dir") + "/images/encInv/encargadoInventario2.png"));
		
		JLabel picLabel = new JLabel(new ImageIcon(myPicture));
		this.add(picLabel);
	}
	
	
	public static void main(String[] args)
	{
		
	}
}
