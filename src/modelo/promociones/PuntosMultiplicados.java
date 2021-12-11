package modelo.promociones;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class PuntosMultiplicados implements Promocion
{
	/*
	 * descuento regalo combo puntos
	 */
	private static String tipoPromocion = "puntos";

	private LocalDate fechaInicio = null;

	private LocalDate fechaFin = null;

	private HashMap<String, Integer> productosCantidad;

	private Integer puntosMultiplicados;
	private Double precioPromocion;

	private boolean esVigente;

	/*
	 * Métodos
	 */
	public PuntosMultiplicados(LocalDate inicio, LocalDate fin, String producto, Integer puntos)
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

		this.puntosMultiplicados = puntos;

	}

	@Override
	public String getTipoPromocion()
	{
		return PuntosMultiplicados.tipoPromocion;
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
	public void setPrecioPromocion(Double precioConDescuento)
	{
		this.precioPromocion = precioConDescuento;
	}

	@Override
	public Double getPrecioPromocion()
	{
		return this.precioPromocion;
	}

	@Override
	public Double getDescuentoPorcentaje()
	{
		return null;
	}

	@Override
	public void setPrecioSinDescuento(Double precio)
	{
	}

	/**
	 * @return the esVigente
	 */
	public boolean isVigente()
	{
		return esVigente;
	}

	@Override
	public Double getPrecioSinDescuento()
	{
		return null;
	}

	/**
	 * @return the puntosMultiplicados
	 */
	public Integer getPuntosMultiplicados()
	{
		return puntosMultiplicados;
	}
}
