package modelo;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import modelo.promociones.Combo;
import modelo.promociones.Promocion;

public class Compra
{
	/**
	 * @param cedula
	 */
	public Compra(String cedula)
	{
		this.cedula = cedula;
		this.combos = new ArrayList<>();
	}

	// Atributos

	HashMap<String, ArrayList<Double>> productoCantidad = new HashMap<>(); // Producto: [Cantidad, Peso]

	ArrayList<Promocion> promociones = new ArrayList<>(); // Sin los Combos

	ArrayList<Combo> combos;

	private String factura = "";

	public Double valorTotal = 0.0;

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

	private int puntosMultiplicados = 1;

	private Double restaDeDescuentosYCombosDouble = 0.0;

	// Methods

	/**
	 * 
	 * @param inventario
	 * @return Factura completa
	 */
	public String getFactura(Inventario inventario, Integer puntosActuales)
	{

		this.factura += "\nFACTURA\n";
		for (String llave : this.productoCantidad.keySet())
		{
			this.factura += "\nProducto: " + llave + " Cantidad: " + this.productoCantidad.get(llave).get(0) + "\n";
			valorTotal += inventario.getPrecioProducto(llave, this.productoCantidad.get(llave).get(0));
		}

		if (this.combos.size() > 0)
		{
			this.factura += "\nCOMBOS:";

			for (Combo c : combos)
			{
				this.factura += "\n-Nombre: " + c.toString() + " | Precio: " + c.getPrecioPromocion() + " | Descuento: " + (c.getDescuentoPorcentaje() * 100) + "%\n";

				valorTotal += c.getPrecioPromocion();
			}
		}

		if (this.promociones.size() > 0)
		{
			this.factura += "\nPROMOCIONES:";
			
			for (Promocion p: promociones)
			{
				this.factura += "\n-Tipo: " + p.getTipoPromocion() + " | Ahorro: " + (p.getPrecioSinDescuento() - p.getPrecioPromocion());
			}
		}

			this.factura += "\nVALOR TOTAL DE LA COMPRA: " + (valorTotal - this.restaDeDescuentosYCombosDouble) + "\n";

		if (this.cedula != null)
		{

			this.factura += "\nNúmero de cédula de usuario registrado: " + this.cedula + "\n";
			this.puntos = (int) (this.valorTotal / 1000) * this.puntosMultiplicados;
			this.factura += "\nPuntos acumulados antes de la compra: " + puntosActuales + "\n";
			this.factura += "\nPuntos redimidos: El cliente no quiso redimir puntos\n";
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
	 * Retorna la factura en la cual se han redimido puntos
	 * 
	 * @param inventario
	 * @param puntosRedimidos
	 * @param puntosActuales
	 * @return
	 */
	public String getFacturaPuntos(Inventario inventario, Integer puntosRedimidos, Integer puntosActuales)
	{
		this.factura += "\nFACTURA\n";
		for (String llave : this.productoCantidad.keySet())
		{
			this.factura += "\nProducto: " + llave + " Cantidad: " + this.productoCantidad.get(llave).get(0) + "\n";
			valorTotal += inventario.getPrecioProducto(llave, this.productoCantidad.get(llave).get(0));
		}

		if (this.combos.size() > 0)
		{
			this.factura += "\nCOMBOS:";

			for (Combo c : combos)
			{
				this.factura += "\n-Nombre: " + c.toString() + " | Precio: " + c.getPrecioPromocion() + " | Descuento: " + (c.getDescuentoPorcentaje() * 100) + "%\n";

				valorTotal += c.getPrecioPromocion();
			}
		}

		this.factura += "\nVALOR TOTAL DE LA COMPRA: " + valorTotal + "\n";

		if (this.cedula != null)
		{

			this.factura += "\nNúmero de cédula de usuario registrado: " + this.cedula + "\n";

			double valor = this.valorTotal;

			if (puntosRedimidos * 15 > valor)
			{
				puntosRedimidos = (int) (valor / 15);
			}

			Double valorConPuntos = (valorTotal - (puntosRedimidos * 15));

			this.puntos = (int) (valorConPuntos / 1000);

			this.factura += "\nSECCIÓN DE PUNTOS: " + "\n";

			this.factura += "\nPuntos acumulados antes de la compra: " + puntosActuales + "\n";

			this.factura += "\nPuntos redimidos: " + puntosRedimidos + "\n";

			this.factura += "\nPuntos obtenidos: " + this.puntos + "\n";

			Integer puntosFinales = (puntosActuales - puntosRedimidos) + this.puntos;

			this.factura += "\nPuntos finales: " + puntosFinales + "\n";

			this.factura += "\nVALOR TOTAL DE LA COMPRA CON PUNTOS REDIMIDOS: " + (valorConPuntos) + "\n";

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
	 * Se agrega un producto a la compra con una cantidad.
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
		} else
		{

			Double nuevaCantidad = productoCantidad.get(nombreProducto).get(0) + cantidad;
			porIngesar.add(nuevaCantidad);
			porIngesar.add(pesoFinal);
		}

		this.productoCantidad.put(nombreProducto, porIngesar);
	}

	/**
	 * 
	 * @param c
	 */
	public void agregarCombo(Combo c)
	{
		this.combos.add(c);
	}

	/**
	 * Se buscan las promociones que aplican a la compra
	 * 
	 * @param inventario
	 */
	private void buscarPromociones(Inventario inventario)
	{
		/*
		 * restaDeDescuentosYCombosDouble+= SinDescuento - ConDescuento
		 */

		ArrayList<Promocion> promocionesInventario = inventario.getPromociones();

		for (Promocion p : promocionesInventario)
		{
			HashMap<String, Integer> productosDeLaPromocion = p.getProductosCantidad();

			if (this.tieneTodosLosProductos(productosDeLaPromocion))
			{
				this.promociones.add(p);

				if (p.getTipoPromocion().equals("descuento"))
				{
					this.restaDeDescuentosYCombosDouble += p.getPrecioSinDescuento() - p.getPrecioPromocion();
				}
			}
		}

	}

	/**
	 * 
	 * @param inventario
	 * @param productos
	 * @return
	 */
	private boolean tieneTodosLosProductos(HashMap<String, Integer> productosDePromocion)
	{
		boolean tieneTodos = true;

		// HashMap<String, Integer> productosDePromocion = p.getProductosCantidad();

		Set<String> nombreProductoPromocion = productosDePromocion.keySet();
		for (String nombreProducto : nombreProductoPromocion)
		{
			Integer cantidadPromocion = productosDePromocion.get(nombreProducto);

			if ((double) cantidadPromocion != this.productoCantidad.get(nombreProducto).get(0))
			{
				tieneTodos = false;
				break;
			}
		}

		return tieneTodos;
	}
}
