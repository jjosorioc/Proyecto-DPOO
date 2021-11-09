/**
 * 
 */
package encargadoInventario.interfaz;

import javax.swing.JFrame;

import encargadoInventario.paneles.ArribaPanel;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

@SuppressWarnings({ "unused", "serial" })
public class VentanaPrincipalEncargado extends JFrame implements ActionListener
{
	public ArribaPanel panelDeArriba;
	
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
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		//this.pack();
	}
	
	
	
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException
	{
		new VentanaPrincipalEncargado();

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		
	}

}
