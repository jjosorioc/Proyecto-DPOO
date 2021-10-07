package modelo;

import java.util.ArrayList;

import java.util.HashMap; // import the HashMap class

public class Inventario
{
	//Atributos
	
	private Lote lote;
	
	private HashMap<String, ArrayList<Integer>> gananciasPerdidas = new HashMap <String, ArrayList<Integer>>();
	
	private HashMap<String, ArrayList<Producto>> categorias = new HashMap <String, ArrayList<Producto>>();
	
	// Métodos
	
	public Lote getLote()
	{
		return this.lote;
	}
	
	public HashMap<String, ArrayList<Integer>> gananciasPerdidas()
	{
		return this.gananciasPerdidas;
	}
	
	public HashMap<String, ArrayList<Producto>> categorias()
	{
		return this.categorias;
	}
	
}
