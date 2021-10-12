package interfaz;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import modelo.POS;

public class Cajero
{
	public static void main(String[] args) throws IOException
	{
		Cajero objCajero = new Cajero();
		objCajero.readCSV(System.getProperty("user.dir") + "/data/clientes.csv"); // se cargan los clientes

		objCajero.ejecutarOpcion();
	}

	// Atributos
	private POS pos = new POS();

	
	// Métodos
	public void mostrarMenu()
	{
		System.out.println("\n******************** MENÚ PRINCIPAL ********************\n");
		System.out.println("\nBienvenido a la consola para el cajero");
		System.out.println("\n0. Agregar un nuevo cliente al sistema.");
		System.out.println("\n1. Iniciar una compra de un cliente.");
		System.out.println("\n2. Agregar producto a compra cliente.");
		System.out.println("\n3. Finalizar compra cliente.");
		System.out.println("\n4. Eliminar compra cliente.");
		System.out.println("\n5. GUARDAR y CERRAR (Si no selecciona esta opción sus cambios no serán guardados).\n");
		System.out.println("*********************************************************\n");
	}

	
	public void ejecutarOpcion()
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

}
