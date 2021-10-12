package modelo;

import java.util.ArrayList;
import java.util.HashMap;

public class Compra
{
	// Atributos
	
	
	HashMap<String, ArrayList<Double>> productoCantidad = new HashMap<>(); //Producto: [Cantidad, Peso]
	private String factura = "";
	
	private double precio = 0;
	
	
	// Methods


	/**
	 * @return the factura
	 */
	public String getFactura()
	{
		return factura;
	}

	
	/**
	 * @return the precio
	 */
	public double getPrecio()
	{
		return precio;
	}
	
	
	/**
	 * 
	 * @param nombreProducto
	 * @param cantidad
	 * @param peso
	 */
	public void agregarProducto(String nombreProducto, Double cantidad, Double peso)
	{
		Double pesoFinal = -1.0;
		if (peso != -1)
		{
			pesoFinal = peso;
		}
		
		ArrayList<Double> porIngesar = new ArrayList<>();
		porIngesar.add(cantidad);
		porIngesar.add(pesoFinal);
		
		this.productoCantidad.put(nombreProducto, porIngesar);
	}
}
    