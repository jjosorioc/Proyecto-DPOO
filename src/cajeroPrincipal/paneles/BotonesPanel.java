package cajeroPrincipal.paneles;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.GridBagConstraints;  
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridLayout;
import javax.imageio.ImageIO;
import javax.swing.*;

import cajeroPrincipal.VentanaPrincipal;

@SuppressWarnings("serial")
public class BotonesPanel extends JPanel 
{
	private VentanaPrincipal padreCajero; // I am your father.
	
	private String pathImagenes = System.getProperty("user.dir") + "/images/cajero";
	
	
	
	// Primera fila
	public JButton nuevoCliente;
	
	public JButton iniciarCompra;
	
		
	
	public BotonesPanel(VentanaPrincipal padre) throws IOException
	{
		this.padreCajero = padre;
		
		GridLayout layout = new GridLayout(1, 1, 20, 20);
		this.setLayout(layout);
		
		
		BufferedImage cl = ImageIO.read(new File(pathImagenes + "/botonAgregarCliente.png"));
		ImageIcon clAsIcon = new ImageIcon(cl); 
		Image img = clAsIcon.getImage();
		Image newImg = img.getScaledInstance(400, 138, java.awt.Image.SCALE_SMOOTH);
		
		nuevoCliente = new JButton(new ImageIcon(newImg));
		nuevoCliente.setBorder(BorderFactory.createEmptyBorder());
		nuevoCliente.setContentAreaFilled(false);
		
		this.add(nuevoCliente);
		
		
		
		BufferedImage dp = ImageIO.read(new File(pathImagenes + "/botonIniciarCompra.png"));
		ImageIcon dpAsIcon = new ImageIcon(dp); 
		Image img2 = dpAsIcon.getImage();
		Image newImg2 = img2.getScaledInstance(400, 138, java.awt.Image.SCALE_SMOOTH);
		
		iniciarCompra = new JButton(new ImageIcon(newImg2));
		iniciarCompra.setBorder(BorderFactory.createEmptyBorder());
		iniciarCompra.setContentAreaFilled(false);
		
		this.add(iniciarCompra);
		
		
		/*
		 *  ACTION LISTENERS
		 */
		this.nuevoCliente.addActionListener(padreCajero);
		this.iniciarCompra.addActionListener(padreCajero);
		
	}
}