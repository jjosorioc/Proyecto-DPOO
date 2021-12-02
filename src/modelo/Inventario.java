package modelo;

import java.util.ArrayList;
import java.util.HashMap; // import the HashMap class
import java.util.Set;

import modelo.promociones.Combo;
import modelo.promociones.Promocion;

public class Inventario
{
	// Atributos

	private HashMap<String, ArrayList<Lote>> lotes = new HashMap<String, ArrayList<Lote>>();

	private HashMap<String, String> codigos = new HashMap<String, String>(); // Codigo - NombreProducto

	private HashMap<String, Double> ganancias = new HashMap<String, Double>(); // todo guardar en CSV
	private HashMap<String, Double> perdidas = new HashMap<String, Double>();

	private ArrayList<Promocion> promociones = new ArrayList<Promocion>(); // Excepto Combos

	private ArrayList<Combo> combos = new ArrayList<Combo>();

	// Métodos

	/**
	 * @return the combos
	 */
	public ArrayList<Combo> getCombos()
	{
		return combos;
	}

	/**
	 * @return the lotes
	 */
	public HashMap<String, ArrayList<Lote>> getLotes()
	{
		return lotes;
	}

	public HashMap<String, String> getCodigos()
	{
		return codigos;
	}

	/**
	 * @return the ganancias
	 */
	public HashMap<String, Double> getGanancias()
	{
		return ganancias;
	}

	/**
	 * @return the perdidas
	 */
	public HashMap<String, Double> getPerdidas()
	{
		return perdidas;
	}

	public Double getPrecioProducto(String nombre, Double cantidad) // TODO: Cambiar nombre del método
	{
		Double costoTotal = 0.0;

		costoTotal = this.lotes.get(nombre).get(0).getPrecioPublico() * cantidad;

		// Se eliminan las unidades del lote más viejo

		ArrayList<Lote> lotesDelProducto = this.lotes.get(nombre);

		for (Lote l : lotesDelProducto)
		{
			if (l.getCantidadUnidades() > 0) // Ocurre en el primer lote con una cantidad válida
			{
				if (l.getCantidadUnidades() >= cantidad)
				{
					l.restarCantidadUnidades(cantidad);
					break; // No repetir en los demás lotes
				} else
				{
					Integer cantidadDisponible = l.getCantidadUnidades();

					l.restarCantidadUnidades((double) cantidadDisponible);
					cantidad = cantidad - cantidadDisponible;
				}

				if (cantidad == 0)
				{
					break;
				}

			}
		}

		this.ganancias.replace(nombre, costoTotal);
		return costoTotal;
	} // TODO: getPrecioPromocion()

	/**
	 * @return the promociones
	 */
	public ArrayList<Promocion> getPromociones()
	{
		return promociones;
	}

	/**
	 * 
	 * @param p
	 */
	public void addPromocion(Promocion p)
	{
		this.promociones.add(p);
	}

	/**
	 * Agregar un Combo al ArrayList
	 * 
	 * @param c
	 * @throws Exception
	 */
	public void addCombo(Combo c)
	{
		Double precioTotalSinDescuento = 0.0;

		Set<String> llaveSet = c.getProductosCantidad().keySet();

		for (String nombreProducto : llaveSet)
		{
			if (this.lotes.containsKey(nombreProducto))
			{
				Double precioProductoDouble = this.lotes.get(nombreProducto).get(0).getPrecioPublico();

				// Ej. 6 unidades de Chocolates == precio * 6 unidades
				Double precioProductoPorCantidaDouble = precioProductoDouble * c.getProductosCantidad().get(nombreProducto);

				precioTotalSinDescuento += precioProductoPorCantidaDouble;
			} else // Si el prudcto no existe
			{
				//throw new Exception("No existe el Producto. Ya debería existir");
			}

		}

		Double precioConDescuento = precioTotalSinDescuento - (precioTotalSinDescuento * c.getDescuentoPorcentaje());
		c.setPrecioDelCombo(precioConDescuento); // Precio con el combo incluido
		this.combos.add(c);
	}

	/**
	 * Retorna el Combo según su QR && null si no existe.
	 * 
	 * @param QRcode
	 * @return
	 */
	public Combo getComboBasedOnQR(String QRcode)
	{
		Combo retornoCombo = null;
		for (Combo c : this.combos)
		{
			if (c.getCodigoQR().equals(QRcode))
			{
				retornoCombo = c;
			}
		}

		return retornoCombo;
	}

}
