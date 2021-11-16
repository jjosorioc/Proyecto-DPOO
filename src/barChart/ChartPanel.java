package barChart;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ChartPanel extends JPanel
{
	@SuppressWarnings("unused")
	private JFrame padre;

	private Integer[] valoresEnOrden;

	private String[] meses = { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" };

	private String title = "Cliente: ";

	public ChartPanel(JFrame padreFrame, ArrayList<Integer> listaCliente, String cedula)
	{
		this.padre = padreFrame;
		this.title = this.title + cedula;
		this.valoresEnOrden = listaCliente.toArray(new Integer[0]);

		this.setVisible(true);
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		int minValue = 0;
		int maxValue = 0;

		for (int i = 0; i < this.valoresEnOrden.length; i++)
		{
			if (minValue > this.valoresEnOrden[i])
				minValue = this.valoresEnOrden[i];
			if (maxValue < this.valoresEnOrden[i])
				maxValue = this.valoresEnOrden[i];
		}

		Dimension d = this.getSize();

		int clientWidth = d.width;
		int clientHeight = d.height;
		int barWidth = clientWidth / this.valoresEnOrden.length;

		Font titleFont = new Font("SansSerif", Font.BOLD, 20);
		FontMetrics titleFontMetrics = g.getFontMetrics(titleFont);
		Font labelFont = new Font("SansSerif", Font.PLAIN, 10);
		FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);

		int titleWidth = titleFontMetrics.stringWidth(this.title);
		int y = titleFontMetrics.getAscent();
		int x = (clientWidth - titleWidth) / 2;
		g.setFont(titleFont);
		g.drawString(title, x, y);

		int top = titleFontMetrics.getHeight();
		int bottom = labelFontMetrics.getHeight();
		if (maxValue == minValue)
			return;
		double scale = (clientHeight - top - bottom) / (maxValue - minValue);
		y = clientHeight - labelFontMetrics.getDescent();
		g.setFont(labelFont);

		for (int i = 0; i < this.valoresEnOrden.length; i++)
		{
			int valueX = i * barWidth + 1;
			int valueY = top;
			int height = (int) (this.valoresEnOrden[i] * scale);
			if (this.valoresEnOrden[i] >= 0)
				valueY += (int) ((maxValue - this.valoresEnOrden[i]) * scale);
			else
			{
				valueY += (int) (maxValue * scale);
				height = -height;
			}

			g.setColor(Color.CYAN);
			g.fillRect(valueX, valueY, barWidth - 2, height);
			g.setColor(Color.black);
			g.drawRect(valueX, valueY, barWidth - 2, height);
			int labelWidth = labelFontMetrics.stringWidth(this.meses[i]);
			x = i * barWidth + (barWidth - labelWidth) / 2;
			g.drawString(this.meses[i], x, y);
		}

	}

}
