package modelo;

public class Lote
{
	
	//Atributos

	private String tipo;

	private Date fechaVencimiento;
	
	private Date fechaIngreso;
	
	private Double precioProveedor;
	
	private Double precioPublico;
	
	private Integer cantidadUnidades;
	
	// Métodos	
		
	public String getTipo()
	{
		return this.tipo;
	}
	
	public Date getFechaVencimiento()
	{
		return this.fechaVencimiento;
	}
	
	public Date getFechaIngreso()
	{
		return this.fechaIngreso;
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
