package modelo.promociones;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Combo implements Promocion
{
	/*
	 * descuento regalo combo puntos
	 */
	private static String tipoPromocion = "combo";

	private String nombreCombo = null;

	private LocalDate fechaInicio = null;

	private LocalDate fechaFin = null;

	private Double descuentoPorcentaje;

	private Double precioDelCombo;

	private HashMap<String, Integer> productosCantidad; // Bananos-3,Manzanas-5,Limon-6

	private String codigoQR;

	/*
	 * Métodos
	 */
	public Combo(String nombreParam, LocalDate inicio, LocalDate fin, String producto, String porcentaje, String codigoQR)
	{
		this.nombreCombo = nombreParam;
		this.fechaInicio = inicio;
		this.fechaFin = fin;

		String[] arrayString = producto.split(",");

		/*
		 * Se inicializan la estructura de datos
		 */

		this.productosCantidad = new HashMap<>();
		for (int i = 0; i < arrayString.length; i++)
		{
			String[] separado = (arrayString[i]).split("-");
			String nombre = separado[0];
			Integer cantidad = Integer.parseInt(separado[1]);

			this.productosCantidad.put(nombre, cantidad); // Se agrega al hashmap
		}

		this.descuentoPorcentaje = Double.parseDouble(porcentaje.replace("%", "")) / 100;
		this.precioDelCombo = null;
		this.codigoQR = codigoQR;

	}

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

	/**
	 * @return the descuentoPorcentaje
	 */
	public Double getDescuentoPorcentaje()
	{
		return descuentoPorcentaje;
	}

	/**
	 * @return the precioDelCombo
	 */
	public Double getPrecioDelCombo()
	{
		return precioDelCombo;
	}

	/**
	 * @param precioDelCombo the precioDelCombo to set
	 */
	public void setPrecioDelCombo(Double precioDelCombo)
	{
		this.precioDelCombo = precioDelCombo;
	}

	/**
	 * @return the productosCantidad
	 */
	public HashMap<String, Integer> getProductosCantidad()
	{
		return productosCantidad;
	}

	/**
	 * @return the codigoQR
	 */
	public String getCodigoQR()
	{
		return codigoQR;
	}

	@Override
	public boolean esVigente(LocalDate inferior, LocalDate superior)
	{
		return (this.fechaInicio.isAfter(inferior) || this.fechaInicio.equals(inferior)) && (this.fechaFin.isBefore(superior) || this.fechaFin.equals(superior));
	}

	@Override
	public String toString()
	{
		return this.nombreCombo;
	}

}
