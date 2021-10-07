package modelo;

public class ProductoEmpacado implements Producto
{
	// Atributos
	
	private String categoria = "";
	
	private double precio = 0;
	
	private double precioPorUnidad = 0;
	
	private int codigoDeBarras = 0;
	
	// Date fechaDeIngreso
	
	// Date fechaDeVencimiento
	
	private String nombre = "";
	
	private double peso = 0;
	
	
	// Methods
	
	@Override
	public void cambiarPrecio(double nuevoPrecio)
	{
		this.precio = nuevoPrecio;
		this.precioPorUnidad = (nuevoPrecio / this.peso);
	}


	/**
	 * @return the categoria
	 */
	public String getCategoria()
	{
		return categoria;
	}


	/**
	 * @return the precio
	 */
	public double getPrecio()
	{
		return precio;
	}


	/**
	 * @return the precioPorUnidad
	 */
	public double getPrecioPorUnidad()
	{
		return precioPorUnidad;
	}


	/**
	 * @return the codigoDeBarras
	 */
	public int getCodigoDeBarras()
	{
		return codigoDeBarras;
	}


	/**
	 * @return the nombre
	 */
	public String getNombre()
	{
		return nombre;
	}


	/**
	 * @return the peso
	 */
	public double getPeso()
	{
		return peso;
	}
	
	
	

}
