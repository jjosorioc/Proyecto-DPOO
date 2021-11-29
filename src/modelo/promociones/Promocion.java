package modelo.promociones;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.DefaultListCellRenderer;

public interface Promocion
{
	/*
	 * descuento regalo combo puntos
	 */
	String tipoPromocion = "";

	LocalDate fechaInicio = null;

	LocalDate fechaFin = null;

	ArrayList<String> productoStrings = null;

	/*
	 * Atributos
	 */

	/**
	 * @return the tipoPromocion
	 */
	public String getTipoPromocion();

	/**
	 * @return the fechaInicio
	 */
	public LocalDate getFechaInicio();

	/**
	 * @return the fechaFin
	 */
	public LocalDate getFechaFin();

	/**
	 * @return the productoStrings
	 */
	public default ArrayList<String> getProductoStrings()
	{
		return this.productoStrings;
	}

	public boolean esVigente(LocalDate inferior, LocalDate superior);

	// return (this.fechaInicio.isAfter(inferior) || this.fechaInicio.equals(inferior)) && (this.fechaFin.isBefore(superior) || this.fechaFin.equals(superior));

}
