package modelo.promociones;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public interface Promocion
{


	/*
	 * Métodos
	 */

	/**
	 * @return the tipoPromocion
	 */
	public String getTipoPromocion();

	/**
	 * @return the fechaInicio
	 */
	public LocalDate getFechaInicio();

	/**
	 * @return the fechaFin
	 */
	public LocalDate getFechaFin();

	/**
	 * @return the productoStrings
	 */
	public HashMap<String, Integer> getProductosCantidad();


	public boolean esVigente(LocalDate inferior, LocalDate superior);

	// return (this.fechaInicio.isAfter(inferior) || this.fechaInicio.equals(inferior)) && (this.fechaFin.isBefore(superior) || this.fechaFin.equals(superior));

}
