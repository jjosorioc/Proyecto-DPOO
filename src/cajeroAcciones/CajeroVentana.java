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
import javax.swing.JOptionPane;

import cajero.paneles.ArribaPanel;
import cajero.paneles.BotonesPanel;
import cajero.paneles.AbajoPanel;
import controlador.Cajero;
import controlador.EncargadoInventario;
import modelo.POS;


public class CajeroVentana extends JFrame implements ActionListener
{

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException
	{
		new CajeroVentana("");

	}
	
	public ArribaPanel panelDeArriba;
	public BotonesPanel botonesPanel;
	public AbajoPanel panelDeAbajo;
	
	public JButton agregarProducto;
	public JButton eliminarCompra;
	public JButton guardarYCerrar;
	public JButton finalizarCompra;
	
	public Cajero CAJERO;

	
	public CajeroVentana(String cedula) throws IOException
	{
		
		CAJERO = new Cajero();
		
		CAJERO.inicarCompraCliente(cedula);
		
		CAJERO.crearCliente(cedula);
		
		CAJERO.readCSV(System.getProperty("user.dir") + "/data/clientes.csv"); // se cargan los clientes
		CAJERO.pos.cargarInventario(); // Se carga inventario.csv
		
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
		
		panelDeAbajo = new AbajoPanel(this,cedula);
		this.add(panelDeAbajo, BorderLayout.SOUTH);

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
		
		if (e.getSource() == agregarProducto)
		{
			if (CAJERO.compraActiva != null)
			{
			String codigoProducto = JOptionPane.showInputDialog(this, "Ingrese el codigo de barras del producto:");		
			String nombreProducto = CAJERO.pos.inventario.getCodigos().get(codigoProducto);

			
			if (nombreProducto == null) // Si el producto no se encontró
			{
				String mensaje = "¡No se encontró el producto que ingresó!" ;
				JOptionPane.showMessageDialog(this, mensaje);
			}
			else 
			{
				boolean esEmpacado = CAJERO.pos.inventario.getLotes().get(nombreProducto).get(0).getEsEmpacado();
				
				if (esEmpacado) // Si es empacado
				{
					String cantidadString = JOptionPane.showInputDialog(this, "\nPor favor ingrese la cantidad de "+ nombreProducto + " que desea comprar el cliente", JOptionPane.PLAIN_MESSAGE);
					double cantidad = Double.parseDouble(cantidadString);
					double peso = -1.0;
					CAJERO.agregarProducto(nombreProducto, cantidad, peso);
				}
				else // Si es no empacado
				{
					String pesoString = JOptionPane.showInputDialog(this, "\nPor favor ingrese el peso de "+ nombreProducto + " que desea comprar el cliente");
					System.out.println(nombreProducto);
					double peso = Double.parseDouble(pesoString);
					System.out.println(peso);
					double cantidad = Math.ceil(peso / CAJERO.pos.inventario.getLotes().get(nombreProducto).get(0).getPeso());
					System.out.println(cantidad);
					CAJERO.agregarProducto(nombreProducto, cantidad, peso);
				}
			}
			}
					
		}
		
		if (e.getSource() == finalizarCompra)
		{
			if (CAJERO.compraActiva != null)
			{
				CAJERO.finalizarCompra();
				JOptionPane.showMessageDialog(this, "Compra finalizada!");

			}
			else
			{
				JOptionPane.showMessageDialog(this, "INICIE UNA COMPRA!");
			}
		}
		
		if (e.getSource() == eliminarCompra)
		{
			CAJERO.eliminarCompra();
		}
		
		if (e.getSource() == guardarYCerrar)
		{
			try
			{
				this.CAJERO.guardarYcerrar();
			} catch (IOException e1)
			{
				System.err.println("¡No se logró guardar y cerrar!");
				e1.printStackTrace();
			}
		}
		
	}

}