package cajero;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class PanelMedio extends JPanel{
	
	@SuppressWarnings("unused")
	private VentanaAgregarCliente padre;
	
	public PanelInformacion cedula ;
	public PanelInformacion nombreCliente  ;
	public PanelInformacion edad ;
	public PanelInformacion genero ;
	public PanelInformacion estCivil ;
	
	public PanelMedio(VentanaAgregarCliente padre)
	{
		this.padre = padre;
		
		this.setLayout(new BorderLayout());
		
		
		// PARTE MEDIO INFORMACIÓN
		
		JPanel informacion = new JPanel();
		
		informacion.setLayout(new GridLayout(4,3,20,20));
		
		cedula = new PanelInformacion("Numero de cédula: ");
		nombreCliente = new PanelInformacion("Nombre del cliente: ");
		edad = new PanelInformacion("Edad: ");
		genero = new PanelInformacion("Género: ");
		estCivil = new PanelInformacion("Estado Civil: ");
		
		informacion.add(new JLabel(" "));
		informacion.add(cedula);
		informacion.add(new JLabel(" "));
		informacion.add(nombreCliente);
		informacion.add(new JLabel(" "));
		informacion.add(edad);
		informacion.add(genero);
		informacion.add(new JLabel(" "));
		informacion.add(estCivil);
		informacion.add(new JLabel(" "));
		informacion.add(new JLabel(" "));
		informacion.add(new JLabel(" "));
		
		this.setBorder(new EmptyBorder(0, 100, 0, 100));
		this.add(informacion,BorderLayout.CENTER);
		
		
		
		
	}

}
