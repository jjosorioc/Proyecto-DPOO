package modelo;

import java.util.ArrayList;
import java.util.HashMap;

public class Compra
{
	/**
	 * @param cedula
	 */
	public Compra(String cedula)
	{
		this.cedula = cedula;
	}
	
	
	// Atributos

	HashMap<String, ArrayList<Double>> productoCantidad = new HashMap<>(); //Producto: [Cantidad, Peso]
	
	private String factura = "";
	
	private Double valorTotal = 0.0;
	
	public String cedula = null;
	
	public Integer puntos = 0;
	
	
	// Methods


	/**
	 * 
	 * @param inventario
	 * @return Factura completa
	 */
	public String getFactura(Inventario inventario)
	{
		
		this.factura += "\nFACTURA\n";
		for (String llave: this.productoCantidad.keySet())
		{
			this.factura += "\nProducto: " + llave + " Cantidad: " + this.productoCantidad.get(llave).get(0) + "\n";
			valorTotal += inventario.getPrecioProducto(llave, this.productoCantidad.get(llave).get(0), this.productoCantidad.get(llave).get(1));
		}
		
		this.factura += "\nValor Total de la Compra: " + valorTotal + "\n";
		
		if (this.cedula != null)
		{
			this.factura += "\nNúmero de cédula de usuario registrado: " + this.cedula + "\n";
			this.puntos = (int) (this.valorTotal / 1000);
			this.factura += "\nPuntos obtenidos: " + this.puntos;
		}
		
		return this.factura;
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
    