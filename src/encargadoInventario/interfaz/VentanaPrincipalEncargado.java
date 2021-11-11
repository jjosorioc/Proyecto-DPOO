/**
 * 
 */
package encargadoInventario.interfaz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.formdev.flatlaf.FlatLightLaf;

import controlador.EncargadoInventario;
import encargadoInventario.paneles.ArribaPanel;
import encargadoInventario.paneles.BotonesPanel;
import modelo.Lote;

@SuppressWarnings("serial")
public class VentanaPrincipalEncargado extends JFrame implements ActionListener
{
	/**
	 * @param args
	 * @throws IOException
	 */

	public static void main(String[] args) throws IOException
	{
		FlatLightLaf.setup();
		// FlatLightLaf.install();
		VentanaPrincipalEncargado objEncargado = new VentanaPrincipalEncargado();

		// Carga de datos
		objEncargado.ENCARGADO.cargarGananciasPerdidas();
		objEncargado.ENCARGADO.readCSV(System.getProperty("user.dir") + "/data/inventario.csv"); // Leer inventario.csv

	}

	/*
	 * ###################### ATRIBUTOS #########################
	 */
	public EncargadoInventario ENCARGADO;

	public ArribaPanel panelDeArriba;
	public BotonesPanel botonesPanel;

	/*
	 * ############## BOTONES #################
	 */

	// Primera fila
	public JButton cargarLote;

	public JButton disponibilidadProducto;

	public JButton eliminarLotesVencidos;

	// Segunda fila
	public JButton unidadesEnUnLote;

	public JButton fechaVencimientoLote;

	public JButton desempenhoFinancieroProducto;

	// Last
	public JButton guardarYCerrar;

	public VentanaPrincipalEncargado() throws IOException
	{
		ENCARGADO = new EncargadoInventario();
		// Nombre de la ventana
		this.setTitle("Encargado del Inventario");

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setSize(1000, 800);
		this.setLayout(new BorderLayout());

		/*
		 * Panel de arriba
		 */
		panelDeArriba = new ArribaPanel(this);
		this.add(panelDeArriba, BorderLayout.NORTH);

		/*
		 * Panel centro (El de los botones)
		 */
		botonesPanel = new BotonesPanel(this);
		this.add(botonesPanel, BorderLayout.CENTER);

		/*
		 * Guardar y cerrar si se cierra la ventana
		 */
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				try
				{
					ENCARGADO.guardarYcerrar();
				} catch (IOException e1)
				{
					//TODO: Exception
				}
			}
		});

		this.setLocationRelativeTo(null);
		this.setVisible(true);
		// this.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		this.cargarLote = botonesPanel.cargarLote;
		this.disponibilidadProducto = botonesPanel.disponibilidadProducto;
		this.eliminarLotesVencidos = botonesPanel.eliminarLotesVencidos;
		this.unidadesEnUnLote = botonesPanel.unidadesEnUnLote;
		this.fechaVencimientoLote = botonesPanel.fechaVencimientoLote;
		this.desempenhoFinancieroProducto = botonesPanel.desempenhoFinancieroProducto;
		this.guardarYCerrar = botonesPanel.guardarYCerrar;

		/*
		 * Cargar un lote
		 */
		if (e.getSource() == this.cargarLote)
		{
			String pathAlCsvString = ENCARGADO.getCSVPath();

			try
			{
				ENCARGADO.readCSV(pathAlCsvString);
				JOptionPane.showMessageDialog(this, "¡Se cargó el Lote al sistema!", "Nice", JOptionPane.PLAIN_MESSAGE);
			} catch (IOException e1)
			{
				//TODO: Exception
			}
		}

		/*
		 * DISPONIBILIDAD DE UN PRODUCTO
		 */
		if (e.getSource() == this.disponibilidadProducto)
		{
			String nombreProducto = JOptionPane.showInputDialog(this, "Ingrese el nombre del producto:");

			Integer cantidadDelProductoInteger = this.ENCARGADO.disponibilidadProducto(nombreProducto); // Cantidad de unidades de ese producto

			String mensaje = "";
			if (cantidadDelProductoInteger == -1) // Si no se encontraron
			{
				mensaje = "¡No se encontró el producto que ingresó!: " + nombreProducto;
				JOptionPane.showMessageDialog(this, mensaje, "Disponibilidad de un Producto", JOptionPane.PLAIN_MESSAGE);
			} else
			{
				mensaje = "Nombre del producto: " + nombreProducto + "\n- Cantidad: " + cantidadDelProductoInteger;
				JOptionPane.showMessageDialog(this, mensaje, "Disponibilidad de un Producto", JOptionPane.PLAIN_MESSAGE);
			}

		}

		/*
		 * ELIMINAR LOTES VENCIDOS
		 */
		if (e.getSource() == this.eliminarLotesVencidos)
		{
			this.ENCARGADO.eliminarLotesVencidos();
			JOptionPane.showMessageDialog(this, "¡Los lotes vencidos fueron eliminados con éxito!", "Lotes Vencidos", JOptionPane.PLAIN_MESSAGE);
		}

		/*
		 * UNIDADES EN UN LOTE
		 */
		if (e.getSource() == this.unidadesEnUnLote)
		{
			String nombreProducto = JOptionPane.showInputDialog(this, "Ingrese el nombre del producto:", "Unidades en un Lote", JOptionPane.PLAIN_MESSAGE);

			String mensaje = "";
			if (!this.ENCARGADO.existeElLote(nombreProducto)) // No existen lotes que contienen ese producto
			{
				mensaje = "¡No hay lotes con ese producto!";
				JOptionPane.showMessageDialog(this, mensaje, "Unidades en un Lote", JOptionPane.PLAIN_MESSAGE);
			} else
			{
				ArrayList<Lote> arregloDeLotes = this.ENCARGADO.lotesDeUnProducto(nombreProducto); // Arreglo con los lotes del producto.

				String[] lotesComoStrings = this.ENCARGADO.lotesDeUnProductoIngresoVencimientoStrings(arregloDeLotes); // Lotes como strings

				// Opción que el usuario selecciona en el menú
				try
				{
					String loteStringEscogido = (String) JOptionPane.showInputDialog(this, "Escoja un Lote según su información:", "Unidades en un Lote", JOptionPane.QUESTION_MESSAGE, null,
							lotesComoStrings, lotesComoStrings[0]);

					// Cantidad de unidades en el lote que seleccionó el usuario.
					int cantidadUnidadesLote = this.ENCARGADO.unidadesDisponibles(loteStringEscogido, lotesComoStrings, arregloDeLotes);

					mensaje = "Info del lote: " + loteStringEscogido + "\n- Cantidad de Unidades en el lote: " + cantidadUnidadesLote;
					JOptionPane.showMessageDialog(this, mensaje, "Unidades en un Lote", JOptionPane.PLAIN_MESSAGE);
				} catch (IndexOutOfBoundsException e2)
				{
					JOptionPane.showMessageDialog(this, "El producto no tiene Lotes disponibles", "Unidades en un Lote", JOptionPane.PLAIN_MESSAGE, null);
				}

			}
		}

		/*
		 * FECHA DE VENCIMIENTO DE UN LOTE
		 */
		if (e.getSource() == this.fechaVencimientoLote)
		{
			String nombreProducto = JOptionPane.showInputDialog(this, "Ingrese el nombre del producto:", "Fecha de Vencimiento de un Lote", JOptionPane.PLAIN_MESSAGE);

			String mensaje = "";
			if (!this.ENCARGADO.existeElLote(nombreProducto)) // No existen lotes que contienen ese producto
			{
				mensaje = "¡No hay lotes con ese producto!";
				JOptionPane.showMessageDialog(this, mensaje, "Fecha de Vencimiento de un Lote", JOptionPane.PLAIN_MESSAGE);
			} else
			{
				ArrayList<Lote> arregloDeLotes = this.ENCARGADO.lotesDeUnProducto(nombreProducto); // Arreglo con los lotes del producto.

				String[] lotesComoStrings = this.ENCARGADO.lotesDeUnProductoIngresoCantidad(arregloDeLotes);
				try
				{
					// Opción que el usuario selecciona en el menú
					String loteStringEscogido = (String) JOptionPane.showInputDialog(this, "Escoja un Lote según su información:", "Fecha de Vencimiento de un Lote", JOptionPane.QUESTION_MESSAGE,
							null, lotesComoStrings, lotesComoStrings[0]);

					LocalDate fechaDeVencimientoLote = this.ENCARGADO.fechaVencimientoLote(loteStringEscogido, lotesComoStrings, arregloDeLotes);

					mensaje = "Info del lote: " + loteStringEscogido + "\n- Fecha de Vencimiento del Lote (YYYY-MM-DD): " + fechaDeVencimientoLote;
					JOptionPane.showMessageDialog(this, mensaje, "Fecha de Vencimiento de un Lote", JOptionPane.PLAIN_MESSAGE);
				} catch (ArrayIndexOutOfBoundsException e2)
				{
					JOptionPane.showMessageDialog(this, "El producto no tiene Lotes disponibles", "Fecha de Vencimiento de un Lote", JOptionPane.PLAIN_MESSAGE, null);
				}

			}
		}

		/*
		 * DESEMPEÑO FINANCIERO
		 */
		if (e.getSource() == this.desempenhoFinancieroProducto)
		{
			String nombreProducto = JOptionPane.showInputDialog(this, "Ingrese el nombre del producto:");
			ArrayList<Double> gananciasPerdidas = this.ENCARGADO.consultarDesempenoFinanciero(nombreProducto); // [ganancias, perdidas]

			Double ganancias = gananciasPerdidas.get(0);
			Double perdidas = gananciasPerdidas.get(1);

			String mensaje;
			if (ganancias != null && perdidas != null)
			{
				mensaje = "Nombre del producto: " + nombreProducto + "\n-Ganancias: $" + ganancias + "\n-Pérdidas: $" + perdidas;

				JOptionPane.showMessageDialog(this, mensaje, "Desempeño Financiero", JOptionPane.PLAIN_MESSAGE);
			} else
			{
				mensaje = "¡No se encontró el producto que ingresó!";
				JOptionPane.showMessageDialog(this, mensaje, "Desempeño Financiero", JOptionPane.PLAIN_MESSAGE);
			}

		}

		/*
		 * Guardar y Cerrar
		 */
		if (e.getSource() == this.guardarYCerrar)
		{
			try
			{
				this.ENCARGADO.guardarYcerrar();
			} catch (IOException e1)
			{//TODO: Exception
			
			}
		}
	}

}
