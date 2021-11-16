package cajero.paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import cajeroAcciones.CajeroVentana;

@SuppressWarnings("serial")
public class AbajoPanel extends JPanel
{
	@SuppressWarnings("unused")
	private CajeroVentana padre;
	
	public JLabel numeroCliente;
	
	public AbajoPanel(CajeroVentana cajeroVentana, String cedula) throws IOException
	{
		this.padre = cajeroVentana;
		
		FlowLayout layout = new FlowLayout();
		this.setLayout(layout);
		
		JLabel text = new JLabel("Cliente registrado: ");
		
		numeroCliente = new JLabel(cedula);
		
		Font f = new Font("TimesRoman",Font.BOLD,25);
	    text.setForeground(Color.BLUE);
	    text.setFont(f);
	    numeroCliente.setForeground(Color.BLUE);
	    numeroCliente.setFont(f);

		add(text, BorderLayout.EAST);
		add(numeroCliente, BorderLayout.EAST);
	}
}
