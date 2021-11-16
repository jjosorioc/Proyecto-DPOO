package modelo;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
	
	public Integer puntosEnero = 0;
	
	public Integer puntosFebrero = 0;
	
	public Integer puntosMarzo = 0;
	
	public Integer puntosAbril = 0;
	
	public Integer puntosMayo = 0;
	
	public Integer puntosJunio = 0;
	
	public Integer puntosJulio = 0;
	
	public Integer puntosAgosto = 0;
	
	public Integer puntosSeptiembre = 0;
	
	public Integer puntosOctubre = 0;
	
	public Integer puntosNoviembre = 0;
	
	public Integer puntosDiciembre = 0;
	
	public Integer mes = 0;
	
	
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
			valorTotal += inventario.getPrecioProducto(llave, this.productoCantidad.get(llave).get(0));
		}
		
		this.factura += "\nValor Total de la Compra: " + valorTotal + "\n";
		
		if (this.cedula != null)
		{
			
			this.factura += "\nNúmero de cédula de usuario registrado: " + this.cedula + "\n";
			this.puntos = (int) (this.valorTotal / 1000);
			this.factura += "\nPuntos obtenidos: " + this.puntos;
			this.mes = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue();

			if (this.mes == 0)
			{
				puntosEnero = this.puntos;
			}
			
			else if (this.mes == 1)
			{
				puntosFebrero = this.puntos;
			}
			
			else if (this.mes == 2)
			{
				puntosMarzo = this.puntos;
			}
			
			else if (this.mes == 3)
			{
				puntosAbril = this.puntos;
			}
			
			else if (this.mes == 4)
			{
				puntosMayo = this.puntos;
			}
			
			else if (this.mes == 5)
			{
				puntosJunio = this.puntos;
			}
			
			else if (this.mes == 6)
			{
				puntosJulio = this.puntos;
			}
			
			else if (this.mes == 7)
			{
				puntosAgosto = this.puntos;
			}
			
			else if (this.mes == 8)
			{
				puntosSeptiembre = this.puntos;
			}
			
			else if (this.mes == 9)
			{
				puntosOctubre = this.puntos;
			}
			
			else if (this.mes == 10)
			{
				puntosNoviembre = this.puntos;
			}
			
			else if (this.mes == 11)
			{
				puntosDiciembre = this.puntos;
			}
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
		if (!productoCantidad.containsKey(nombreProducto))
		{
			porIngesar.add(cantidad);
			porIngesar.add(pesoFinal);
		}
		else {
			
			Double nuevaCantidad = productoCantidad.get(nombreProducto).get(0) + cantidad;
			porIngesar.add(nuevaCantidad);
			porIngesar.add(pesoFinal);
		}
		
		
		this.productoCantidad.put(nombreProducto, porIngesar);
	}
}
    