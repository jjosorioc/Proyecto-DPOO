package modelo.promociones;

import java.time.LocalDate;
import java.util.ArrayList;

public class Combo implements Promocion
{
	/*
	 * descuento regalo combo puntos
	 */
	private static String tipoPromocion = "combo";

	private LocalDate fechaInicio = null;

	private LocalDate fechaFin = null;
	
	private ArrayList<String> productoStrings = null;

	@Override
	public String getTipoPromocion()
	{
		return Combo.tipoPromocion;
	}

	@Override
	public LocalDate getFechaInicio()
	{
		return this.fechaInicio;
	}

	@Override
	public LocalDate getFechaFin()
	{
		return this.fechaFin;
	}

	@Override
	public ArrayList<String> getProductoStrings()
	{
		return this.productoStrings;
	}

	@Override
	public boolean esVigente(LocalDate inferior, LocalDate superior)
	{
		return (this.fechaInicio.isAfter(inferior) || this.fechaInicio.equals(inferior)) && (this.fechaFin.isBefore(superior) || this.fechaFin.equals(superior));
	}
}
