package cajeroPrincipal;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

import cajeroPrincipal.paneles.ArribaPanel;
import cajeroPrincipal.paneles.BotonesPanel;
import controlador.Cajero;
import controlador.EncargadoInventario;


public class VentanaPrincipal extends JFrame implements ActionListener
{

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException
	{
		VentanaPrincipal ventanaCajero = new VentanaPrincipal();

	}
	
	public static Cajero cajero = new Cajero();
	
	public ArribaPanel panelDeArriba;
	public BotonesPanel botonesPanel;
	
	public JButton nuevoCliente;
	public JButton iniciarCompra;
	
	public VentanaPrincipal() throws IOException
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
		this.nuevoCliente = botonesPanel.nuevoCliente;
		this.iniciarCompra = botonesPanel.iniciarCompra;
		
	}

}
