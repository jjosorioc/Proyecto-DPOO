package modelo;

public class ProductoNoEmpacado implements Producto
{
	// Atributos

	private String categoria = "";
	
	private double precio = 0; // Precio por peso
	
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
	
	
	
	public double getPrecioSegunPeso(double pesoUsuario)
	{
		return (this.precio * pesoUsuario);
	}


}
