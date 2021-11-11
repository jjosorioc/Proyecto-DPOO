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
	public POS pos;
	
	public CajeroVentana(String cedula) throws IOException
	{
		
		CAJERO = new Cajero();
		
		pos = new POS();
		
		CAJERO.inicarCompraCliente(cedula);
		
		CAJERO.crearCliente(cedula);
		
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
//			String codigoProducto = JOptionPane.showInputDialog(this, "Ingrese el nombre del producto:", "Fecha de Vencimiento de un Lote", JOptionPane.PLAIN_MESSAGE);
//			
//			String nombreProducto = pos.inventario.getCodigos().get(codigoProducto);
//			
//			System.out.println(nombreProducto);
//			
//			boolean esEmpacado = pos.inventario.getLotes().get(nombreProducto).get(0).getEsEmpacado();
//			
//			if (esEmpacado) //Para agregar producto se debe confirmar si el producto es empacado o no, para saber que información pedirle al cajero.
//			{
//				String cantidadString = JOptionPane.showInputDialog(this, "\nPor favor ingrese la cantidad de "+ nombreProducto + " que desea comprar el cliente", JOptionPane.PLAIN_MESSAGE);
//				double cantidad = Double.parseDouble(cantidadString);
//				Double peso = -1.0;
//				CAJERO.agregarProducto(nombreProducto, cantidad, peso);
//			}
//			
//			else
//			{
//				String pesoString = JOptionPane.showInputDialog(this, "\nPor favor ingrese el peso de "+ nombreProducto + " que desea comprar el cliente", JOptionPane.PLAIN_MESSAGE);
//				double peso = Double.parseDouble(pesoString);
//				Double cantidad = Math.ceil(peso / CAJERO.pos.inventario.getLotes().get(nombreProducto).get(0).getPeso());
//				System.out.println("\nLa cantidad de " + nombreProducto + " según el peso ingresado es: " + cantidad);
//				CAJERO.agregarProducto(nombreProducto, cantidad, peso);
//			}
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