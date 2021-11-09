package encargadoInventario.paneles;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import encargadoInventario.interfaz.VentanaPrincipalEncargado;

@SuppressWarnings("serial")
public class ArribaPanel extends JPanel
{
	private VentanaPrincipalEncargado padre;

	public ArribaPanel(VentanaPrincipalEncargado padre) throws IOException
	{
		this.padre = padre;

		BufferedImage myPicture = ImageIO.read(new File(System.getProperty("user.dir") + "/images/encInv/logoEncargadoInventario.png"));

		ImageIcon mpAsIcon = new ImageIcon(myPicture);
		Image img1 = mpAsIcon.getImage();
		Image newPictureImage = img1.getScaledInstance(672, 350, java.awt.Image.SCALE_SMOOTH);

		JLabel picLabel = new JLabel(new ImageIcon(newPictureImage));
		this.add(picLabel);
	}

	public static void main(String[] args)
	{

	}
}
