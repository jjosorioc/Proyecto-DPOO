package modelo;

import java.util.ArrayList;

import java.util.HashMap; // import the HashMap class

public class Inventario
{
	// Atributos

	private HashMap<String, ArrayList<Lote>> lotes = new HashMap<String, ArrayList<Lote>>();
	
	private HashMap<String, String> codigos = new HashMap<String, String>(); // Codigo - NombreProducto
	
	private HashMap<String, Double> ganancias = new HashMap<String, Double>(); //todo guardar en CSV
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

	public Double getPrecioProducto(String nombre, Double cantidad)
	{
		Double costoTotal = 0.0;

		costoTotal = this.lotes.get(nombre).get(0).getPrecioPublico() * cantidad;
		
		// Se eliminan las unidades del lote más viejo
		
		ArrayList<Lote> lotesDelProducto = this.lotes.get(nombre);
		
		for (Lote l: lotesDelProducto)
		{
			if (l.getCantidadUnidades() >= cantidad && l.getCantidadUnidades() > 0) // Ocurre en el primer lote con una cantidad válida
			{
				l.restarCantidadUnidades(cantidad);
				break; // No repetir en los demás lotes
			}
		}

		this.ganancias.replace(nombre, costoTotal);
		return costoTotal;
	}

}
