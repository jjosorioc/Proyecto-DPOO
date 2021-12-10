/**
 * 
 */
package modelo.promociones;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Descuento implements Promocion
{
	/*
	 * descuento regalo combo puntos
	 */
	private static String tipoPromocion = "descuento";

	private LocalDate fechaInicio = null;

	private LocalDate fechaFin = null;

	private HashMap<String, Integer> productosCantidad;

	private Double porcentajeDescuento = null;

	private Double precioPromocion;

	private Double precioSinDescuento = 0.0;
	/*
	 * Métodos
	 */

	private boolean esVigente;

	public Descuento(LocalDate inicio, LocalDate fin, String producto, String porcentaje)
	{
		this.fechaInicio = inicio;
		this.fechaFin = fin;

		this.esVigenteMethod(inicio, fin);

		String[] arrayString = producto.split(",");
		this.productosCantidad = new HashMap<>();
		for (int i = 0; i < arrayString.length; i++)
		{
			this.productosCantidad.put(arrayString[i], 1);
		}

		this.porcentajeDescuento = Double.parseDouble(porcentaje.replace("%", "")) / 100;

	}

	@Override
	public String getTipoPromocion()
	{
		return Descuento.tipoPromocion;
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
	public void esVigenteMethod(LocalDate inferior, LocalDate superior)
	{
		this.esVigente = (this.fechaInicio.isAfter(inferior) || this.fechaInicio.equals(inferior)) && (this.fechaFin.isBefore(superior) || this.fechaFin.equals(superior));
	}

	@Override
	public HashMap<String, Integer> getProductosCantidad()
	{

		return this.productosCantidad;
	}

	@Override
	public Double getDescuentoPorcentaje()
	{
		return this.porcentajeDescuento;
	}

	@Override
	public void setPrecioPromocion(Double precioConDescuento)
	{
		this.precioPromocion = precioConDescuento;// - (precioConDescuento * this.porcentajeDescuento);
	}

	@Override
	public Double getPrecioPromocion()
	{
		return this.precioPromocion;
	}

	/**
	 * @return the precioSinDescuento
	 */
	public Double getPrecioSinDescuento()
	{
		return precioSinDescuento;
	}

	/**
	 * @param precioSinDescuento the precioSinDescuento to set
	 */
	public void setPrecioSinDescuento(Double precioSinDescuento)
	{
		this.precioSinDescuento = precioSinDescuento;
	}

	/**
	 * @return the esVigente
	 */
	public boolean isVigente()
	{
		return esVigente;
	}

}
