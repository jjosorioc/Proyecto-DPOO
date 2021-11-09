/**
 * 
 */
package encargadoInventario.interfaz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;



import controlador.EncargadoInventario;
import encargadoInventario.paneles.ArribaPanel;
import encargadoInventario.paneles.BotonesPanel;

@SuppressWarnings({ "unused", "serial" })
public class VentanaPrincipalEncargado extends JFrame implements ActionListener
{
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException
	{
		
		VentanaPrincipalEncargado objEncargado = new VentanaPrincipalEncargado();

	}

	/*
	 * ###################### ATRIBUTOS #########################
	 */
	public static EncargadoInventario encargado = new EncargadoInventario();

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
			String pathAlCsvString = encargado.getCSVPath();
			
			try
			{
				encargado.readCSV(pathAlCsvString);
			} catch (IOException e1)
			{
				System.err.println("\n¡No se encontró el archivo!\n");
				e1.printStackTrace();
			}
		}
	}

}
