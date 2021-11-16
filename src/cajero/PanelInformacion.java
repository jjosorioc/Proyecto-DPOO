package cajero;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PanelInformacion  extends JPanel{
	
	public JLabel nombre ;
	public JTextField cuadro ;
	
	
	public PanelInformacion (String solicitud)
	{
		this.setLayout(new BorderLayout());
		
		Font f = new Font("TimesRoman",Font.BOLD,15);
		
		nombre = new JLabel(solicitud);
		nombre.setVerticalAlignment(JLabel.CENTER);
		nombre.setHorizontalAlignment(JLabel.CENTER);
		nombre.setFont(f);
		nombre.setForeground(new java.awt.Color(0,0,128));
		
		cuadro = new JTextField();
		
		
		this.add(nombre, BorderLayout.NORTH);
		this.add(cuadro, BorderLayout.CENTER);
	}

}
