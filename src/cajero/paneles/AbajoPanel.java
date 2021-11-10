package cajero.paneles;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cajeroAcciones.CajeroVentana;
import cajeroPrincipal.*;

import java.awt.image.BufferedImage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

public class AbajoPanel extends JPanel
{
	private CajeroVentana padre;
	
	public JLabel numeroCliente;
	
	public AbajoPanel(CajeroVentana cajeroVentana, String cedula) throws IOException
	{
		this.padre = cajeroVentana;
		
		FlowLayout layout = new FlowLayout();
		this.setLayout(layout);
		
		JLabel text = new JLabel("Cliente registrado: "); //TODO si el cliente no está registrado, esto no debe salir.
		
		numeroCliente = new JLabel(cedula); //TODO dependiendo de lo que entre por parámetro debe cambiar :)
		
		Font f = new Font("TimesRoman",Font.BOLD,25);
	    text.setForeground(Color.BLUE);
	    text.setFont(f);
	    numeroCliente.setForeground(Color.BLUE);
	    numeroCliente.setFont(f);

		add(text, BorderLayout.EAST);
		add(numeroCliente, BorderLayout.EAST);
	}
}
