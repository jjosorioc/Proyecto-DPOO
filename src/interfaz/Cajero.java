package interfaz;

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
	public static void main(String[] args) throws IOException
	{
		Cajero objCajero = new Cajero();
		objCajero.readCSV(System.getProperty("user.dir") + "/data/clientes.csv"); // se cargan los clientes
		objCajero.pos.cargarInventario(); // Se carga inventario.csv
		objCajero.ejecutarOpcion();
	}

	// Atributos
	
	private POS pos = new POS();
	
	private Compra compraActiva = null;
	
	// Métodos
	private void mostrarMenu()
	{
		System.out.println("\n******************** MENÚ PRINCIPAL ********************\n");
		System.out.println("\nBienvenido a la consola para el cajero");
		System.out.println("\n0. Agregar un nuevo cliente al sistema.");
		System.out.println("\n1. Iniciar una compra de un cliente.");
		System.out.println("\n2. Agregar un producto a la compra del cliente.");
		System.out.println("\n3. Finalizar compra cliente.");
		System.out.println("\n4. Eliminar compra cliente.");
		System.out.println("\n5. GUARDAR y CERRAR (Si no selecciona esta opción sus cambios no serán guardados).\n");
		System.out.println("*********************************************************\n");
	}

	
	private void ejecutarOpcion()
	{
		System.out.println("Iniciando programa...");

		boolean continuar = true;

		while (continuar)
		{
			try
			{
				mostrarMenu();
				int opcion_seleccionada = Integer.parseInt(input("Por favor seleccione una opción"));
			} catch (NumberFormatException e)
			{
				System.out.println("Debe seleccionar uno de los números de las opciones.");

			}

		}
	}

	
	// Método para poder usar input()
	private String input(String mensaje)
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

	
	private void readCSV(String pathCSV) throws IOException // Opción 1
	{
		BufferedReader csvReader = new BufferedReader(new FileReader(pathCSV));

		/*
		 * Estructura CSV: Nombre cliente Numero de cedula Puntos totales Edad Sexo Estado civil Situacion laboral
		 */
		csvReader.readLine(); // Lee primera linea
		String row;
		while ((row = csvReader.readLine()) != null)
		{
			String[] elArray = row.split(";"); // Main array

			String documento = elArray[1];

			Integer puntos = Integer.parseInt(elArray[2]);

			pos.getClientes().put(documento, puntos);

		}
		csvReader.close();
	}
	
	
	/**
	 * Se agrega un cliente al HashMap de POS
	 * @param cedula
	 */
	private void crearCliente(String cedula)
	{
		this.pos.getClientes().put(cedula, 0);
	}
	
	
	
	/**
	 * 
	 * @param cedula
	 */
	private void inicarCompraCliente(String cedula)
	{
		this.compraActiva = new Compra();
	}
	
	
	/**
	 * 
	 * @param productoNombre
	 * @param cantidad
	 * @param peso
	 */
	private void agregarProducto(String productoNombre, Double cantidad, Double peso) // el producto debe estar en minúsculas
	{
		if (this.compraActiva != null) 
		{
			if (disponibilidadProducto(productoNombre) >= cantidad && disponibilidadProducto(productoNombre) != -1)
			{
				this.compraActiva.agregarProducto(productoNombre, cantidad, peso);
			}
			else
			{
				System.out.println("\nEl producto no existe o la cantidad de unidades no alcanza.\n");
			}
		}
		else {
			System.out.println("\nNo se agregó ningún producto... Inicie una compra.\n");
		}
	}
	
	
	
	/**
	 * 
	 * @param nombreProducto
	 * @return Es -1 si no existe el producto
	 */
	private int disponibilidadProducto(String nombreProducto) // nombreProducto debe estar en minúsculas
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
	

}
