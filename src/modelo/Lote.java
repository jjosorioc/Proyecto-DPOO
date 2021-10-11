package modelo;

import java.time.LocalDate;


public class Lote
{	
	
	//Atributos
	/* Estructura CSV:
	 * Producto	
	 * Categoría	
	 * Vencimiento (D/M/A)	
	 * Ingreso (D/M/A)	
	 * Precio Proveedor	
	 * Precio Público	
	 * Unidades	
	 * Peso por una unidad (g)	
	 * Empacado 
	 * Unidad de medida
	 */

	/**
	 * @param nameProducto
	 * @param categoria
	 * @param fechaDeVencimiento
	 * @param fechaDeIngreso
	 * @param precioProveedor
	 * @param precioPublico
	 * @param cantidadUnidades
	 * @param peso
	 * @param esEmpacado
	 */
	public Lote(String nameProducto, String categoria, LocalDate fechaDeVencimiento, LocalDate fechaDeIngreso, Double precioProveedor, Double precioPublico, Integer cantidadUnidades, double peso,
			boolean esEmpacado, String unidadMedida)
	{
		this.nameProducto = nameProducto;
		this.categoria = categoria;
		this.fechaDeVencimiento = fechaDeVencimiento;
		this.fechaDeIngreso = fechaDeIngreso;
		this.precioProveedor = precioProveedor;
		this.precioPublico = precioPublico;
		this.cantidadUnidades = cantidadUnidades;
		this.peso = peso;
		this.esEmpacado = esEmpacado;
		this.unidadMedida = unidadMedida;
		
		// method para codigo de barras 
	}

	private String nameProducto;
	
	private String categoria;

	private LocalDate fechaDeVencimiento;
	
	private LocalDate fechaDeIngreso;
	
	private Double precioProveedor;
	
	private Double precioPublico;
	
	private Integer cantidadUnidades;
	
	private double peso;
	
	private boolean esEmpacado; // para el metodo de get precio
	
	private String unidadMedida;
	
	/*
	 * Fuera del constructor.
	 */
	
	private int codigoDeBarras; // TODO ver c[omo lo hacemos
	
		
	// Métodos	
		
	
	
	/**
	 * @return the nameProducto
	 */
	public String getNameProducto()
	{
		return nameProducto;
	}

	/**
	 * @return the categoria
	 */
	public String getCategoria()
	{
		return categoria;
	}

	public LocalDate getfechaDeVencimiento()
	{
		return this.fechaDeVencimiento;
	}
	
	public LocalDate getfechaDeIngreso()
	{
		return this.fechaDeIngreso;
	}
	
	public Double getPrecioProveedor()
	{
		return this.precioProveedor;
	}
	
	public Double getPrecioPublico()
	{
		return this.precioPublico;
	}
	
	public Integer getCantidadUnidades()
	{
		return this.cantidadUnidades;
	}
	
	public Boolean getEsEmpacado()
	{
		return this.esEmpacado;
	}
	public String getUnidadMedida()
	{
		return this.unidadMedida;
	}
	
	
	public Double getPeso()
	{
		return this.peso;
	}
	

	/**
	 * @param precioPublico the precioPublico to set
	 */
	public void setPrecioPublico(Double precioPublico)
	{
		this.precioPublico = precioPublico;
	}


}
