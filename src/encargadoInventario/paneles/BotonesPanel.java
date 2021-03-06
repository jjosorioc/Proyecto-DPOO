package encargadoInventario.paneles;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import encargadoInventario.interfaz.VentanaPrincipalEncargado;

@SuppressWarnings("serial")
public class BotonesPanel extends JPanel
{
	private VentanaPrincipalEncargado padrEncargado; // I am your father.

	private String pathImagenes = System.getProperty("user.dir") + "/images/encInv";

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
	
	public JButton agregarImagen;	
	
	public JButton comportamientoProducto;

	public BotonesPanel(VentanaPrincipalEncargado padre) throws IOException
	{
		this.padrEncargado = padre;

		GridLayout layout = new GridLayout(3, 3, 20, 20);
		this.setLayout(layout);

		// Cargar lote
		BufferedImage cl = ImageIO.read(new File(pathImagenes + "/CargarLote.png"));
		ImageIcon clAsIcon = new ImageIcon(cl);
		Image img = clAsIcon.getImage();
		Image newImg = img.getScaledInstance(300, 102, java.awt.Image.SCALE_SMOOTH);

		cargarLote = new JButton(new ImageIcon(newImg));
		cargarLote.setBorder(BorderFactory.createEmptyBorder());
		cargarLote.setContentAreaFilled(false);

		this.add(cargarLote);

		// Disponibilidad producto

		BufferedImage dp = ImageIO.read(new File(pathImagenes + "/DisponibilidadProducto.png"));
		ImageIcon dpAsIcon = new ImageIcon(dp);
		Image img2 = dpAsIcon.getImage();
		Image newImg2 = img2.getScaledInstance(300, 102, java.awt.Image.SCALE_SMOOTH);

		disponibilidadProducto = new JButton(new ImageIcon(newImg2));
		disponibilidadProducto.setBorder(BorderFactory.createEmptyBorder());
		disponibilidadProducto.setContentAreaFilled(false);

		this.add(disponibilidadProducto);

		// Eliminar Lotes vencidos

		BufferedImage lv = ImageIO.read(new File(pathImagenes + "/LotesVencidos.png"));
		ImageIcon lvAsIcon = new ImageIcon(lv);
		Image img3 = lvAsIcon.getImage();
		Image newImg3 = img3.getScaledInstance(300, 102, java.awt.Image.SCALE_SMOOTH);

		eliminarLotesVencidos = new JButton(new ImageIcon(newImg3));
		eliminarLotesVencidos.setBorder(BorderFactory.createEmptyBorder());
		eliminarLotesVencidos.setContentAreaFilled(false);

		this.add(eliminarLotesVencidos);

		// Unidades en un lote

		BufferedImage ul = ImageIO.read(new File(pathImagenes + "/UnidadesLote.png"));
		ImageIcon ulAsIcon = new ImageIcon(ul);
		Image img4 = ulAsIcon.getImage();
		Image newImg4 = img4.getScaledInstance(300, 102, java.awt.Image.SCALE_SMOOTH);

		unidadesEnUnLote = new JButton(new ImageIcon(newImg4));
		unidadesEnUnLote.setBorder(BorderFactory.createEmptyBorder());
		unidadesEnUnLote.setContentAreaFilled(false);

		this.add(unidadesEnUnLote);

		// fecha de vencimiento de un lote

		BufferedImage fv = ImageIO.read(new File(pathImagenes + "/FechaVencimiento.png"));
		ImageIcon fvAsIcon = new ImageIcon(fv);
		Image img5 = fvAsIcon.getImage();
		Image newImg5 = img5.getScaledInstance(300, 102, java.awt.Image.SCALE_SMOOTH);

		fechaVencimientoLote = new JButton(new ImageIcon(newImg5));
		fechaVencimientoLote.setBorder(BorderFactory.createEmptyBorder());
		fechaVencimientoLote.setContentAreaFilled(false);

		this.add(fechaVencimientoLote);

		// Desempe??o financiero de un producto desempenhoFinancieroProducto

		BufferedImage df = ImageIO.read(new File(pathImagenes + "/DesempenoFinanciero.png"));
		ImageIcon dfAsIcon = new ImageIcon(df);
		Image img6 = dfAsIcon.getImage();
		Image newImg6 = img6.getScaledInstance(300, 102, java.awt.Image.SCALE_SMOOTH);

		desempenhoFinancieroProducto = new JButton(new ImageIcon(newImg6));
		desempenhoFinancieroProducto.setBorder(BorderFactory.createEmptyBorder());
		desempenhoFinancieroProducto.setContentAreaFilled(false);

		this.add(desempenhoFinancieroProducto);

		// Guardar y cerrar guardarYCerrar : JButton

		BufferedImage gc = ImageIO.read(new File(pathImagenes + "/GuardarYCerrar3.png"));
		ImageIcon gcAsIcon = new ImageIcon(gc);
		Image img7 = gcAsIcon.getImage();
		Image newImg7 = img7.getScaledInstance(300, 102, java.awt.Image.SCALE_SMOOTH);

		guardarYCerrar = new JButton(new ImageIcon(newImg7));
		guardarYCerrar.setBorder(BorderFactory.createEmptyBorder());
		guardarYCerrar.setContentAreaFilled(false);

		this.add(guardarYCerrar);

		// AGREGAR IMAGEN
		BufferedImage ai = ImageIO.read(new File(pathImagenes + "/botonAgregarImagen.png"));
		ImageIcon aiAsIcon = new ImageIcon(ai);
		Image img8 = aiAsIcon.getImage();
		Image newImg8 = img8.getScaledInstance(300, 102, java.awt.Image.SCALE_SMOOTH);

		agregarImagen = new JButton(new ImageIcon(newImg8));
		agregarImagen.setBorder(BorderFactory.createEmptyBorder());
		agregarImagen.setContentAreaFilled(false);

		this.add(agregarImagen);
		
		// COMPORTAMIENTO PRODUCTO
		BufferedImage bi = ImageIO.read(new File(pathImagenes + "/consultarComportamientoProducto.png"));
		ImageIcon biAsIcon = new ImageIcon(bi);
		Image img9 = biAsIcon.getImage();
		Image newImg9 = img9.getScaledInstance(300, 102, java.awt.Image.SCALE_SMOOTH);

		comportamientoProducto = new JButton(new ImageIcon(newImg9));
		comportamientoProducto.setBorder(BorderFactory.createEmptyBorder());
		comportamientoProducto.setContentAreaFilled(false);

		this.add(comportamientoProducto);

		/*
		 * ACTION LISTENERS
		 */
		this.cargarLote.addActionListener(padrEncargado);
		this.disponibilidadProducto.addActionListener(padrEncargado);
		this.eliminarLotesVencidos.addActionListener(padrEncargado);
		this.unidadesEnUnLote.addActionListener(padrEncargado);
		this.fechaVencimientoLote.addActionListener(padrEncargado);
		this.desempenhoFinancieroProducto.addActionListener(padre);
		this.guardarYCerrar.addActionListener(padre);
		this.agregarImagen.addActionListener(padre);
		this.comportamientoProducto.addActionListener(padre);

	}
}
