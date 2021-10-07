package modelo;

import java.util.ArrayList;

public class Compra
{
	// Atributos
	
	private ArrayList<Producto> productos = new ArrayList<Producto>();
	
	private String factura = "";
	
	private double precio = 0;
	
	// Methods

	/**
	 * @return the productos
	 */
	public ArrayList<Producto> getProductos()
	{
		return productos;
	}

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
}
    