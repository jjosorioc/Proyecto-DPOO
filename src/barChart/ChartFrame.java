package barChart;

import java.util.ArrayList;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ChartFrame extends JFrame
{
	private ArrayList<Integer> lista;

	public ChartFrame(ArrayList<Integer> lista, String cedulaString)
	{
		this.lista = lista;
		this.setSize(1000, 400);
		this.getContentPane().add(new ChartPanel(this, this.lista, cedulaString));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setAlwaysOnTop(true);
		this.setVisible(true);
	}
}
