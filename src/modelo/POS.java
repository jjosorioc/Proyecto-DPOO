package modelo;

import java.util.HashMap; // import the HashMap class

public class POS

{
	//Atributos 
	
	
	private HashMap<String, Integer> clientes = new HashMap<String, Integer>(); // Cédula y Puntos
	
	//Métodos 
	
	public HashMap<String, Integer> getClientes()
	{
		return this.clientes;
	}
}
