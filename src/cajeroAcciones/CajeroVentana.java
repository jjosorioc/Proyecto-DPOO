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
import modelo.promociones.Combo;

@SuppressWarnings("serial")
public class CajeroVentana extends JFrame implements ActionListener
{

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
		CAJERO.pos.cargarPromociones(); // Se cargan las promociones

		CAJERO.inicarCompraCliente(cedula);

		if (cedula.length() == 0) // Si el cliente no está registrado
		{
			cedula = null;
		} else if (cedula != null)
		{
			ArrayList<Integer> listaMeses = this.CAJERO.pos.puntos.get(cedula);
			if (listaMeses != null)
			{
				new ChartFrame(listaMeses, cedula);
			}
		}

		if (cedula != null)
		{
			if (!CAJERO.pos.clientes.containsKey(cedula))
			{
				CAJERO.crearCliente(cedula);
			}

		}

		this.setTitle("Cajero");

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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

				/*
				 * SI ES UN COMBO
				 */
				if (codigoProducto.startsWith("00"))
				{
					Combo comboEscogido = CAJERO.pos.inventario.getComboBasedOnQR(codigoProducto);

					if (comboEscogido == null) // No existe el combo
					{
						String mensaje = "¡No se encontró el combo que ingresó!";
						JOptionPane.showMessageDialog(this, mensaje, null, JOptionPane.ERROR_MESSAGE);
					} else
					{
						try
						{
							this.CAJERO.agregarCombo(comboEscogido);
						} catch (Exception e1)
						{
							String mensaje = "¡No es posible agregar el Combo, faltan productos!";
							JOptionPane.showMessageDialog(this, mensaje, null, JOptionPane.ERROR_MESSAGE);
						}
					}
				}

				/*
				 * SI ES UN PRODUCTO
				 */
				else
				{
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

							String cantidadString;

							ImageIcon icono = null;

							try // Si hay imagen del producto
							{
								icono = new ImageIcon(new ImageIcon("./images/cajero/productos/" + nombreProducto + ".png").getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));

							} catch (Exception e1) // Si NO hay imagen del producto
							{
								cantidadString = (String) JOptionPane.showInputDialog(this, "\nPor favor ingrese la cantidad de " + nombreProducto + " que desea comprar el cliente", null,
										JOptionPane.INFORMATION_MESSAGE, null, null, null);
							} // Se guarda y se cierra la compra realizada.

							cantidadString = (String) JOptionPane.showInputDialog(this, "\nPor favor ingrese la cantidad de " + nombreProducto + " que desea comprar el cliente", null,
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

							String pesoString;

							ImageIcon icono = null;

							try // Si hay imagen del producto
							{
								icono = new ImageIcon(new ImageIcon("./images/cajero/productos/" + nombreProducto + ".png").getImage().getScaledInstance(70, 70, Image.SCALE_DEFAULT));
								pesoString = (String) JOptionPane.showInputDialog(this, "\nPor favor ingrese el peso de " + nombreProducto + " que desea comprar el cliente", null,
										JOptionPane.INFORMATION_MESSAGE, icono, null, null);
							} catch (Exception e2) // Si NO hay imagen del producto
							{
								pesoString = (String) JOptionPane.showInputDialog(this, "\nPor favor ingrese el peso de " + nombreProducto + " que desea comprar el cliente", null,
										JOptionPane.INFORMATION_MESSAGE, null, null, null);
							} // Se guarda y se cierra la compra realizada.

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

			}
			this.dispose();
		}

		if (e.getSource() == guardarYCerrar)
		{

			try
			{
				this.CAJERO.guardarYcerrar();
				JOptionPane.showMessageDialog(this, "La compra se ha guardado con éxito!", "Compra guardada", JOptionPane.PLAIN_MESSAGE);
				new VentanaPrincipal(); // Se abre de nuevo la ventana principal.

				this.dispose(); // Se cierra la ventana actual.
			} catch (IOException e1)
			{

			} // Se guarda y se cierra la compra realizada.

		}

	}

}