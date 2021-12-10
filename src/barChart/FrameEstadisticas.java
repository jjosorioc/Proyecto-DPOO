package barChart;

import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class FrameEstadisticas extends JFrame
{
	private ArrayList<Integer> unidades;

	public FrameEstadisticas(ArrayList<Integer> unidades, String producto, String fechas)
	{
		this.unidades = unidades;
		((JComponent) this.getContentPane()).setBorder(new EmptyBorder (20,20,20,20));
		this.setSize(1000, 650);
		this.getContentPane().add(new PanelEstadisticas(this, this.unidades, producto,fechas));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		this.setVisible(true);
	}
}