package modelo;

import java.time.LocalDate;

public class Lote
{

	// Atributos
	/*
	 * Estructura CSV: Producto Categoría Vencimiento (D/M/A) Ingreso (D/M/A) Precio Proveedor Precio Público Unidades Peso por una unidad (g) Empacado Unidad de medida
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
	 * @param unidadMedida
	 * @param codigoBarras
	 */
	public Lote(String nameProducto, String categoria, LocalDate fechaDeVencimiento, LocalDate fechaDeIngreso, Double precioProveedor, Double precioPublico, Integer cantidadUnidades, double peso,
			boolean esEmpacado, String unidadMedida, String codigoBarras)
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
		this.codigoBarras = codigoBarras;

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

	private boolean esEmpacado; // para saber cómo se debe obtener el precio

	private String unidadMedida;
	
	private String codigoBarras;

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

	public Double getPeso()
	{
		return this.peso;
	}

	public String getUnidadMedida()
	{
		return this.unidadMedida;
	}
	
	public String getCodigoBarras()
	{
		return this.codigoBarras;
	}

	/**
	 * @param precioPublico the precioPublico to set
	 */
	public void setPrecioPublico(Double precioPublico)
	{
		this.precioPublico = precioPublico;
	}

}
