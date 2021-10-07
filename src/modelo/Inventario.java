package modelo;

import java.util.ArrayList;

import java.util.HashMap; // import the HashMap class

public class Inventario
{
	//Atributos
	
	private ArrayList<Lote> lotes = new ArrayList<Lote>();
	
	private HashMap<String, ArrayList<Integer>> gananciasPerdidas = new HashMap <String, ArrayList<Integer>>();
	
	private HashMap<String, ArrayList<Producto>> categorias = new HashMap <String, ArrayList<Producto>>();
	
	
	// Métodos
	
	
	/**
	 * @return the lotes
	 */
	public ArrayList<Lote> getLotes()
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

	/**
	 * @return the categorias
	 */
	public HashMap<String, ArrayList<Producto>> getCategorias()
	{
		return categorias;
	}
	
}
