package modelo.promociones;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Regalo implements Promocion
{
	/*
	 * descuento regalo combo puntos
	 */
	private static String tipoPromocion = "regalo";

	private LocalDate fechaInicio = null;

	private LocalDate fechaFin = null;

	private HashMap<String, Integer> productosCantidad; // Para eso está pague y lleve

	// 4,5
	private Integer pague;

	private Integer lleve;

	private Double precioPromocion;

	private boolean esVigente;

	/*
	 * Métodos
	 */

	public Regalo(LocalDate inicio, LocalDate fin, String producto, String pagueLleve)
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

		String[] arrayPagarLlevar = pagueLleve.split(",");

		this.pague = Integer.parseInt(arrayPagarLlevar[0]);

		this.lleve = Integer.parseInt(arrayPagarLlevar[1]);

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
		// TODO Auto-generated method stub
		return null; // TODO:Producto de regalo???
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
}
