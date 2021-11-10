package cajero;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cajeroAcciones.CajeroVentana;
import cajeroPrincipal.paneles.ArribaPanel;

public class VentanaAgregarCliente extends JFrame implements ActionListener{
	
	private String pathImagenes = System.getProperty("user.dir") + "/images/cajero";
	
	public JPanel panel ;
	public PanelMedio panelMedio;
	JButton guardarYCerrar = new JButton();
	
	private String numeroCedula;
	
	public VentanaAgregarCliente() throws IOException
	{
		this.setTitle("Agregar Cliente");

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setSize(900, 700);
		this.setLayout(new BorderLayout());
		
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		//IMAGEN ARRIBA
		
		BufferedImage myPicture = ImageIO.read(new File(System.getProperty("user.dir") + "/images/cajero/logoCajero.png"));
		
		ImageIcon mpAsIcon = new ImageIcon(myPicture);
		Image img1 = mpAsIcon.getImage();
		Image newPictureImage = img1.getScaledInstance(572,278,java.awt.Image.SCALE_SMOOTH);
		
		
		JLabel picLabel = new JLabel(new ImageIcon(newPictureImage));
		panel.add(picLabel, BorderLayout.NORTH);
		
		//
		
		
		// PANEL MEDIO CON INFORMACION
		
		panelMedio = new PanelMedio(this);
		panel.add(panelMedio,BorderLayout.CENTER);
		
		//
		
		// BOTON GUARDAR Y CERRAR
		
		BufferedImage gc = ImageIO.read(new File(pathImagenes + "/botonGuardaryCerrar2.png"));
		ImageIcon gcAsIcon = new ImageIcon(gc); 
		Image img3 = gcAsIcon.getImage();
		Image newImg3 = img3.getScaledInstance(200, 69, java.awt.Image.SCALE_SMOOTH);
		
		guardarYCerrar.setIcon(new ImageIcon(newImg3));
		guardarYCerrar.setBorder(BorderFactory.createEmptyBorder());
		guardarYCerrar.setContentAreaFilled(false);
		
		guardarYCerrar.addActionListener(this);
	
		
		panel.add(guardarYCerrar,BorderLayout.SOUTH);
		
		//
		 
		panel.setBorder(new EmptyBorder(0,0,40,0));
		
		this.add(panel,BorderLayout.CENTER);
		
		
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) throws IOException
	{
		new VentanaAgregarCliente();

	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == guardarYCerrar)
		{
			numeroCedula = panelMedio.cedula.cuadro.getText();
			try {
				new CajeroVentana(numeroCedula);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.dispose();
		}
		
	}

}
