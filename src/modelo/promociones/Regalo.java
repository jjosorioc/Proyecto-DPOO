package modelo.promociones;

import java.time.LocalDate;
import java.util.ArrayList;

public class Regalo implements Promocion
{
	/*
	 * descuento regalo combo puntos
	 */
	private static String tipoPromocion = "regalo";

	private LocalDate fechaInicio = null;

	private LocalDate fechaFin = null;
	
	private ArrayList<String> productoStrings = null;
	
	private Integer cantidadUnidades;
	
	/*
	 * Atributos
	 */
	
	public Regalo(LocalDate inicio, LocalDate fin, String producto, Integer unidades)
	{
		this.fechaInicio = inicio;
		this.fechaFin = fin;

		String[] arrayString = producto.split(",");
		this.productoStrings = new ArrayList<>();
		for (int i = 0; i < arrayString.length; i++)
		{
			this.productoStrings.add(arrayString[i]);
		}

		this.cantidadUnidades = unidades;

	}

	@Override
	public String getTipoPromocion()
	{
		return Regalo.tipoPromocion;
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
