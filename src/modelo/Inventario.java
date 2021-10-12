package modelo;

import java.util.ArrayList;

import java.util.HashMap; // import the HashMap class

public class Inventario
{
	//Atributos
	
	private HashMap<String, ArrayList<Lote>> lotes = new HashMap<String, ArrayList<Lote>>(); // TODO sort según fecha
	
	private HashMap<String, Double> ganancias = new HashMap <String, Double>();
	private HashMap<String, Double> perdidas = new HashMap <String, Double>();
	
	
	
	// Métodos
	
	
	/**
	 * @return the lotes
	 */
	public HashMap<String, ArrayList<Lote>> getLotes()
	{
		return lotes;
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

	

	
	
}
