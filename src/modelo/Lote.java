package modelo;

import java.time.LocalDate;


public class Lote
{	
	
	//Atributos

	private String tipo;

	private LocalDate fechaDeVencimiento;
	
	private LocalDate fechaDeIngreso;
	
	private Double precioProveedor;
	
	private Double precioPublico;
	
	private Integer cantidadUnidades;
	
	// Métodos	
		
	public String getTipo()
	{
		return this.tipo;
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


}
