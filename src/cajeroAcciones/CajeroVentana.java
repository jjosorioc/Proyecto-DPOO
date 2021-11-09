/**
 * 
 */
package cajeroAcciones;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

import cajero.paneles.ArribaPanel;
import cajero.paneles.BotonesPanel;
import controlador.Cajero;
import controlador.EncargadoInventario;


public class CajeroVentana extends JFrame implements ActionListener
{

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException
	{
		CajeroVentana subVentanaCajero = new CajeroVentana();

	}
	
	public ArribaPanel panelDeArriba;
	public BotonesPanel botonesPanel;
	
	public JButton agregarProducto;
	public JButton eliminarCompra;
	public JButton guardarYCerrar;
	public JButton finalizarCompra;
	
	public CajeroVentana() throws IOException
	{
		this.setTitle("Cajero");

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setSize(1000, 800);
		this.setLayout(new BorderLayout());


		panelDeArriba = new ArribaPanel(this);
		this.add(panelDeArriba, BorderLayout.NORTH);

		/*
		 * Panel centro
		 */
		botonesPanel = new BotonesPanel(this);
		this.add(botonesPanel, BorderLayout.CENTER);

		this.setLocationRelativeTo(null);
		this.setVisible(true);
		// this.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		this.agregarProducto = botonesPanel.agregarProducto;
		this.eliminarCompra = botonesPanel.eliminarCompra;
		this.guardarYCerrar = botonesPanel.guardarYCerrar;
		this.finalizarCompra = botonesPanel.finalizarCompra;
		
	}

}