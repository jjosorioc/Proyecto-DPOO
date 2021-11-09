package cajero.paneles;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.GridBagConstraints;  
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridLayout;
import javax.imageio.ImageIO;
import javax.swing.*;

import cajeroAcciones.CajeroVentana;

@SuppressWarnings("serial")
public class BotonesPanel extends JPanel 
{
	private CajeroVentana padreCajero; // I am your father.
	
	private String pathImagenes = System.getProperty("user.dir") + "/images/cajero";
	
	
	
	public JButton agregarProducto;
	public JButton eliminarCompra;
	public JButton guardarYCerrar;
	public JButton finalizarCompra;
	
		
	
	public BotonesPanel(CajeroVentana padre) throws IOException
	{
		this.padreCajero = padre;
		
		GridLayout layout = new GridLayout(2, 2, 20, 20);
		this.setLayout(layout);
		
		
		BufferedImage cl = ImageIO.read(new File(pathImagenes + "/botonAgregarProducto.png"));
		ImageIcon clAsIcon = new ImageIcon(cl); 
		Image img = clAsIcon.getImage();
		Image newImg = img.getScaledInstance(400, 138, java.awt.Image.SCALE_SMOOTH);
		
		agregarProducto = new JButton(new ImageIcon(newImg));
		agregarProducto.setBorder(BorderFactory.createEmptyBorder());
		agregarProducto.setContentAreaFilled(false);
		
		this.add(agregarProducto);
		
		
		
		BufferedImage dp = ImageIO.read(new File(pathImagenes + "/botonEliminarCompra.png"));
		ImageIcon dpAsIcon = new ImageIcon(dp); 
		Image img2 = dpAsIcon.getImage();
		Image newImg2 = img2.getScaledInstance(400, 138, java.awt.Image.SCALE_SMOOTH);
		
		eliminarCompra = new JButton(new ImageIcon(newImg2));
		eliminarCompra.setBorder(BorderFactory.createEmptyBorder());
		eliminarCompra.setContentAreaFilled(false);
		
		this.add(eliminarCompra);
		
		
		BufferedImage gc = ImageIO.read(new File(pathImagenes + "/botonGuardaryCerrar2.png"));
		ImageIcon gcAsIcon = new ImageIcon(gc); 
		Image img3 = gcAsIcon.getImage();
		Image newImg3 = img3.getScaledInstance(400, 138, java.awt.Image.SCALE_SMOOTH);
		
		guardarYCerrar = new JButton(new ImageIcon(newImg3));
		guardarYCerrar.setBorder(BorderFactory.createEmptyBorder());
		guardarYCerrar.setContentAreaFilled(false);
		
		this.add(guardarYCerrar);
		
		
		BufferedImage fc = ImageIO.read(new File(pathImagenes + "/botonFinalizarCompra.png"));
		ImageIcon fcAsIcon = new ImageIcon(fc); 
		Image img4 = fcAsIcon.getImage();
		Image newImg4 = img4.getScaledInstance(400, 138, java.awt.Image.SCALE_SMOOTH);
		
		finalizarCompra = new JButton(new ImageIcon(newImg4));
		finalizarCompra.setBorder(BorderFactory.createEmptyBorder());
		finalizarCompra.setContentAreaFilled(false);
		
		this.add(finalizarCompra);
		
		
		
		
		
		/*
		 *  ACTION LISTENERS
		 */
		this.agregarProducto.addActionListener(padreCajero);
		this.eliminarCompra.addActionListener(padreCajero);
		this.guardarYCerrar.addActionListener(padreCajero);
		this.finalizarCompra.addActionListener(padreCajero);
		
	}
}