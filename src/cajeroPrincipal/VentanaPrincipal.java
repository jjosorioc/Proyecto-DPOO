package cajeroPrincipal;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import cajero.VentanaAgregarCliente;
import cajeroAcciones.CajeroVentana;
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
		
		if (e.getSource() == nuevoCliente) //Si se selecciona el botón de nuevo cliente
		{
			try {
				new VentanaAgregarCliente(); //Se abre la ventana de agregar un nuevo cliente
			} catch (IOException e1) {
				e1.printStackTrace(); //Se cierra la ventana actual
			}
			this.dispose();
		}
		
		if (e.getSource() == iniciarCompra) //Si se selecciona el botón de iniciar compra
		{
			try {
				//Se obtiene la cedula del cliente
				String cedulaCliente = JOptionPane.showInputDialog(this, "Ingrese el número de cédula del cliente registrado:");
				new CajeroVentana(cedulaCliente); //Se abre la ventana de agregar productos (con el numero de cedula incluido)
			} catch (IOException e1) {
				e1.printStackTrace(); //Se cierra la ventana actual
			}
			this.dispose();
		}
		
		
	}

}
