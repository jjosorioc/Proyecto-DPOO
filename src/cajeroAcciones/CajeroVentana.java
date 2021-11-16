/**
 * 
 */
package cajeroAcciones;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import barChart.ChartFrame;
import cajero.paneles.AbajoPanel;
import cajero.paneles.ArribaPanel;
import cajero.paneles.BotonesPanel;
import cajeroPrincipal.VentanaPrincipal;
import controlador.Cajero;

@SuppressWarnings("serial")
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
	// public POS pos = new POS();

	public CajeroVentana(String cedula) throws IOException
	{

		CAJERO = new Cajero();

		CAJERO.readCSV(System.getProperty("user.dir") + "/data/clientes.csv"); // se cargan los clientes
		CAJERO.pos.cargarInventario(); // Se carga inventario.csv

		CAJERO.inicarCompraCliente(cedula);

		if (cedula.length() == 0) // Si el cliente no está registrado
		{
			cedula = null;
		} else
		{
			ArrayList<Integer> listaMeses = this.CAJERO.pos.puntos.get(cedula);

			new ChartFrame(listaMeses, cedula);
		}

		if (cedula != null)
		{
			if (!CAJERO.pos.clientes.containsKey(cedula))
			{
				CAJERO.crearCliente(cedula);
			}

		}

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

		panelDeAbajo = new AbajoPanel(this, cedula);
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

				ImageIcon iconoLupa = new ImageIcon(new ImageIcon("./images/cajero/productos/lupa.png").getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
				String codigoProducto = (String) JOptionPane.showInputDialog(this, "Ingrese el codigo de barras del producto:", null, JOptionPane.INFORMATION_MESSAGE, iconoLupa, null, null);
				String nombreProducto = CAJERO.pos.inventario.getCodigos().get(codigoProducto);
				int resultado;

				if (nombreProducto == null) // Si el producto no se encontró
				{
					String mensaje = "¡No se encontró el producto que ingresó!";
					JOptionPane.showMessageDialog(this, mensaje, null, JOptionPane.ERROR_MESSAGE);
				} else
				{
					boolean esEmpacado = CAJERO.pos.inventario.getLotes().get(nombreProducto).get(0).getEsEmpacado();

					if (esEmpacado) // Si es empacado
					{

						ImageIcon icono = new ImageIcon(new ImageIcon("./images/cajero/productos/" + nombreProducto + ".png").getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
						String cantidadString = (String) JOptionPane.showInputDialog(this, "\nPor favor ingrese la cantidad de " + nombreProducto + " que desea comprar el cliente", null,
								JOptionPane.INFORMATION_MESSAGE, icono, null, null);

						if (cantidadString != null)
						{
							double cantidad = Double.parseDouble(cantidadString);
							double peso = -1.0;
							resultado = CAJERO.agregarProducto(nombreProducto, cantidad, peso);
							if (resultado == 1) // Se logró agregar el producto
							{
								JOptionPane.showMessageDialog(this, "El producto se agregó con éxito.", "Producto Agregado", JOptionPane.PLAIN_MESSAGE, icono);
							} else if (resultado == 2) // No hay suficientes unidades del producto
							{
								JOptionPane.showMessageDialog(this, "El producto no existe o la cantidad de unidades no alcanza.", "Producto Agregado", JOptionPane.ERROR_MESSAGE);
							} else if (resultado == 3) // El producto no se pudo agregar
							{
								JOptionPane.showMessageDialog(this, "No se agregó ningún producto... Inicie una compra.", "Producto Agregado", JOptionPane.ERROR_MESSAGE);
							}
						}

					} else // Si es no empacado
					{
						ImageIcon icono = new ImageIcon(new ImageIcon("./images/cajero/productos/" + nombreProducto + ".png").getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
						String pesoString = (String) JOptionPane.showInputDialog(this, "\nPor favor ingrese el peso de " + nombreProducto + " que desea comprar el cliente", null,
								JOptionPane.INFORMATION_MESSAGE, icono, null, null);

						if (pesoString != null)
						{
							double peso = Double.parseDouble(pesoString);
							double cantidad = Math.ceil(peso / CAJERO.pos.inventario.getLotes().get(nombreProducto).get(0).getPeso());
							resultado = CAJERO.agregarProducto(nombreProducto, cantidad, peso);
							if (resultado == 1) // Se logró agregar el producto
							{
								JOptionPane.showMessageDialog(this, "El producto se agregó con éxito.", "Producto Agregado", JOptionPane.PLAIN_MESSAGE, icono);
							} else if (resultado == 2) // No hay suficientes unidades del producto
							{
								JOptionPane.showMessageDialog(this, "El producto no existe o la cantidad de unidades no alcanza.", "Producto Agregado", JOptionPane.PLAIN_MESSAGE);
							} else if (resultado == 3) // El producto no se pudo agregar
							{
								JOptionPane.showMessageDialog(this, "No se agregó ningún producto... Inicie una compra.", "Producto Agregado", JOptionPane.PLAIN_MESSAGE);
							}
						}
					}

				}
			}

		}

		if (e.getSource() == finalizarCompra)
		{

			if (CAJERO.compraActiva != null)
			{

				JOptionPane.showMessageDialog(this, CAJERO.finalizarCompra(), "Compra finalizada!", JOptionPane.PLAIN_MESSAGE);

			} else
			{
				JOptionPane.showMessageDialog(this, "INICIE UNA COMPRA!");
			}
		}

		if (e.getSource() == eliminarCompra)
		{
			CAJERO.eliminarCompra();
			JOptionPane.showMessageDialog(this, "La compra se ha eliminado con éxtio!", "Compra eliminada", JOptionPane.PLAIN_MESSAGE);
			try
			{
				new VentanaPrincipal(); // Se abre la ventana principal
			} catch (IOException e1)
			{
				e1.printStackTrace(); // Se cierra la ventana actual
			}
			this.dispose();
		}

		if (e.getSource() == guardarYCerrar)
		{

			try
			{
				this.CAJERO.guardarYcerrar(); // Se guarda y se cierra la compra realizada.
			} catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try
			{
				JOptionPane.showMessageDialog(this, "La compra se ha guardado con éxito!", "Compra guardada", JOptionPane.PLAIN_MESSAGE);
				new VentanaPrincipal(); // Se abre de nuevo la ventana principal.
			} catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			this.dispose(); // Se cierra la ventana actual.

		}

	}

}