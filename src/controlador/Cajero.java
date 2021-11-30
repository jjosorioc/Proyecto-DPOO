package controlador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import modelo.Compra;
import modelo.Lote;
import modelo.POS;

public class Cajero
{
	public static void main(String[] args) throws Exception
	{
		Cajero objCajero = new Cajero();
		objCajero.readCSV(System.getProperty("user.dir") + "/data/clientes.csv"); // se cargan los clientes
		objCajero.pos.cargarInventario(); // Se carga inventario.csv
		objCajero.pos.cargarPromociones();
	}

	// Atributos

	public POS pos = new POS();

	public Compra compraActiva = null;

	// Métodos

	/**
	 * Método para obtener el input del usuario.
	 * 
	 * @param mensaje
	 * @return String o null
	 */
	public String input(String mensaje)
	{
		try
		{
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		} catch (IOException e)
		{
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Método para leer un csv con la infromación de los clientes.
	 * 
	 * @param pathCSV
	 * @throws IOException
	 */
	public void readCSV(String pathCSV) throws IOException // Opción 1
	{
		BufferedReader csvReader = new BufferedReader(new FileReader(pathCSV));

		/*
		 * Estructura CSV: Cedula Puntos PuntosEnero PuntosFebrero PuntosMarzo PuntosAbril PuntosMayo PuntosJunio PuntosJulio PuntosAgosto PuntosSeptiembre PuntosOctubre PuntosNoviembre
		 * PuntosDiciembre
		 */
		csvReader.readLine(); // Lee primera linea
		String row;
		while ((row = csvReader.readLine()) != null)
		{
			ArrayList<Integer> puntosMes = new ArrayList<Integer>();

			String[] elArray = row.split(","); // Main array

			String documento = elArray[0];

			Integer puntos = Integer.parseInt(elArray[1]);

			pos.getClientes().put(documento, puntos);

			Integer puntosEnero = Integer.parseInt(elArray[2]);

			puntosMes.add(puntosEnero);

			Integer puntosFebrero = Integer.parseInt(elArray[3]);

			puntosMes.add(puntosFebrero);

			Integer puntosMarzo = Integer.parseInt(elArray[4]);

			puntosMes.add(puntosMarzo);

			Integer puntosAbril = Integer.parseInt(elArray[5]);

			puntosMes.add(puntosAbril);

			Integer puntosMayo = Integer.parseInt(elArray[6]);

			puntosMes.add(puntosMayo);

			Integer puntosJunio = Integer.parseInt(elArray[7]);

			puntosMes.add(puntosJunio);

			Integer puntosJulio = Integer.parseInt(elArray[8]);

			puntosMes.add(puntosJulio);

			Integer puntosAgosto = Integer.parseInt(elArray[9]);

			puntosMes.add(puntosAgosto);

			Integer puntosSeptiembre = Integer.parseInt(elArray[10]);

			puntosMes.add(puntosSeptiembre);

			Integer puntosOctubre = Integer.parseInt(elArray[11]);

			puntosMes.add(puntosOctubre);

			Integer puntosNoviembre = Integer.parseInt(elArray[12]);

			puntosMes.add(puntosNoviembre);

			Integer puntosDiciembre = Integer.parseInt(elArray[13]);

			puntosMes.add(puntosDiciembre);

			pos.getPuntos().put(documento, puntosMes);

		}
		csvReader.close();
	}

	/**
	 * Se agrega un cliente al HashMap de POS
	 * 
	 * @param cedula
	 */
	public void crearCliente(String cedula)
	{
		this.pos.getClientes().put(cedula, 0);
	}

	/**
	 * 
	 * @param cedula
	 */
	public void inicarCompraCliente(String cedula)
	{
		this.compraActiva = new Compra(cedula);
		this.pos.updateUnidadesDuranteEjecucion();
	}

	/**
	 * 
	 * @param productoNombre
	 * @param cantidad
	 * @param peso
	 */
	public int agregarProducto(String productoNombre, Double cantidad, Double peso) // el producto debe estar en minúsculas
	{
		if (this.compraActiva != null)
		{
			if (disponibilidadProducto(productoNombre) >= cantidad && disponibilidadProducto(productoNombre) != -1 && this.pos.getUnidadesDuranteEjecucion().get(productoNombre) >= cantidad)
			{
				this.compraActiva.agregarProducto(productoNombre, cantidad, peso);
				Integer cantidadActual = this.pos.getUnidadesDuranteEjecucion().get(productoNombre);
				this.pos.getUnidadesDuranteEjecucion().replace(productoNombre, (int) (cantidadActual - cantidad));
				return (1);
			} else
			{
				// El producto no existe o la cantidad de unidades no alcanza.
				return (2);
			}
		} else
		{
			// No se agregó ningún producto... Inicie una compra.
			return (3);
		}
	}

	/**
	 * 
	 * @param nombreProducto
	 * @return Es -1 si no existe el producto
	 */
	public int disponibilidadProducto(String nombreProducto) // nombreProducto debe estar en minúsculas
	{
		int cantidadTotal = 0;

		// Array de lotes según nombreProducto

		boolean existeLote = this.pos.inventario.getLotes().containsKey(nombreProducto);
		if (!existeLote)
		{
			cantidadTotal = -1;
		}

		else
		{
			ArrayList<Lote> lotesDelProducto = this.pos.inventario.getLotes().get(nombreProducto);

			for (Lote i : lotesDelProducto)
			{
				cantidadTotal += i.getCantidadUnidades();
			}
		}

		return cantidadTotal;
	}

	/**
	 * Método para finalizar la compra del cliente
	 * 
	 * @return
	 */
	public String finalizarCompra()
	{
		String resultado = compraActiva.getFactura(this.pos.inventario);

		if (!compraActiva.cedula.equals(""))
		{

			this.pos.agregarPuntosCliente(compraActiva.cedula, compraActiva.puntos);
			this.pos.agregarPuntosClienteMes(compraActiva.cedula, compraActiva.puntos, compraActiva.mes);

		}
		this.compraActiva = null;
		this.pos.updateUnidadesDuranteEjecucion();
		return resultado;
	}

	/**
	 * Método para eliminar la compra del cliente activo.
	 */
	public void eliminarCompra()
	{
		this.compraActiva = null;
	}

	/**
	 * Método para guardar toda la información en sus csv respectivos. Luego cierra la aplicación.
	 * 
	 * @throws IOException
	 */
	public void guardarYcerrar() throws IOException
	{
		this.pos.guardarInventario();
		this.pos.guardarClientes();
		this.pos.guardarGananciasYPerdidas();
	}

}
