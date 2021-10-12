package modelo;

import java.util.ArrayList;

import java.util.HashMap; // import the HashMap class

public class Inventario
{
	// Atributos

	private HashMap<String, ArrayList<Lote>> lotes = new HashMap<String, ArrayList<Lote>>(); // TODO sort según fecha
	private HashMap<String, String> codigos = new HashMap<String, String>();
	private HashMap<String, Double> ganancias = new HashMap<String, Double>();
	private HashMap<String, Double> perdidas = new HashMap<String, Double>();

	// Métodos

	/**
	 * @return the lotes
	 */
	public HashMap<String, ArrayList<Lote>> getLotes()
	{
		return lotes;
	}
	
	public HashMap<String, String> getCodigos()
	{
		return codigos;
	}

	/**
	 * @return the ganancias
	 */
	public HashMap<String, Double> getGanancias()
	{
		return ganancias;
	}

	/**
	 * @return the perdidas
	 */
	public HashMap<String, Double> getPerdidas()
	{
		return perdidas;
	}

	public Double getPrecioProducto(String nombre, Double cantidad, Double pesoCliente)
	{
		Double costoTotal = 0.0;

		if (this.lotes.get(nombre).get(0).getEsEmpacado())
		{
			costoTotal = this.lotes.get(nombre).get(0).getPrecioPublico() * cantidad;
		} else
		{
			costoTotal = this.lotes.get(nombre).get(0).getPrecioPublico() * pesoCliente;
		}

		// TODO eliminar unidades

		this.ganancias.replace(nombre, costoTotal);
		return costoTotal;
	}

}
