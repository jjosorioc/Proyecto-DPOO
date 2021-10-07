package modelo;

import java.util.ArrayList;

import java.util.HashMap; // import the HashMap class

public class Inventario
{
	//Atributos
	
	private HashMap<String, ArrayList<Lote>> lotes = new HashMap<String, ArrayList<Lote>>();
	
	private HashMap<String, ArrayList<Integer>> gananciasPerdidas = new HashMap <String, ArrayList<Integer>>();
	
	
	
	// Métodos
	
	
	/**
	 * @return the lotes
	 */
	public HashMap<String, ArrayList<Lote>> getLotes()
	{
		return lotes;
	}

	/**
	 * @return the gananciasPerdidas
	 */
	public HashMap<String, ArrayList<Integer>> getGananciasPerdidas()
	{
		return gananciasPerdidas;
	}

	
	
}
