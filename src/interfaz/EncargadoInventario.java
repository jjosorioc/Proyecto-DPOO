package interfaz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.io.InputStreamReader;

import javax.sound.sampled.Port;
import javax.swing.JFileChooser;
import javax.swing.RowFilter;

import modelo.Inventario;
import modelo.Lote;

public class EncargadoInventario
{
	public static void main(String[] args) throws IOException
	{
		EncargadoInventario objEncargadoInventario = new EncargadoInventario();
		objEncargadoInventario.readCSV(System.getProperty("user.dir") + "/data/inventario.csv"); // Leer inventario.csv
		
		objEncargadoInventario.eliminarLotesVacios();
		objEncargadoInventario.guardarYcerrar();

		// objEncargadoInventario.ejecutarOpcion();

	}

	// Atributos
	private Inventario inventario = new Inventario();

	/**
	 * @return the inventario
	 */
	public Inventario getInventario()
	{
		return inventario;
	}

	public void mostrarMenu()
	{
		System.out.println("\n******************** MENÚ PRINCIPAL ********************\n");
		System.out.println("\nBienvenido a la consola para el encargado de inventario");
		System.out.println("\n0. Cargar lote.");
		System.out.println("\n1. Consultar disponibilidad de un producto.");
		System.out.println("\n2. Eliminar lotes vacíos.");
		System.out.println("\n3. Consultar unidades disponibles en un lote.");
		System.out.println("\n4. Consultar fecha de vencimiento de un lote.\n");
		System.out.println("\n5. Consultar desempeño financiero de un producto.\n"); // TODO

		System.out.println("\n6. GUARDAR y CERRAR (Si no selecciona esta opción sus cambios no serán guardados).\n");
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

	/**
	 * 
	 * @return el path del archivo o null si no es valido
	 */
	private String getCSVPath()
	{
		// Tomado de https://mail.codejava.net/java-se/swing/show-simple-open-file-dialog-using-jfilechooser
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(fileChooser);

		if (result == JFileChooser.APPROVE_OPTION)
		{
			File selectedFile = fileChooser.getSelectedFile();

			return (selectedFile.getAbsolutePath());
		}

		return ("null");
	}

	private void readCSV(String pathCSV) throws IOException // Opción 1
	{
		BufferedReader csvReader = new BufferedReader(new FileReader(pathCSV));

		/*
		 * Estructura CSV: Producto Categoría Vencimiento (D/M/A) Ingreso (D/M/A) Precio Proveedor Precio Público Unidades Peso (g) Empacado
		 */
		csvReader.readLine(); // Lee primera linea
		String row;
		while ((row = csvReader.readLine()) != null)
		{
			String[] elArray = row.split(","); // Main array

			String nombreProducto = elArray[0].toLowerCase();

			String categoria = elArray[1];

			String[] vencimientoString = (elArray[2]).split("-");
			LocalDate vencimiento = LocalDate.of(Integer.parseInt(vencimientoString[0]), Integer.parseInt(vencimientoString[1]), Integer.parseInt(vencimientoString[2]));

			String[] ingresoString = (elArray[3]).split("-");
			LocalDate ingreso = LocalDate.of(Integer.parseInt(ingresoString[0]), Integer.parseInt(ingresoString[1]), Integer.parseInt(ingresoString[2]));

			double proveedor = Double.parseDouble(elArray[4]);

			double publico = Double.parseDouble(elArray[5]);

			int unidades = Integer.parseInt(elArray[6]);

			double peso = Double.parseDouble(elArray[7]);

			boolean empacado = Boolean.parseBoolean(elArray[8]);

			String unidad = elArray[9];

			Lote newLote = new Lote(nombreProducto, categoria, vencimiento, ingreso, proveedor, publico, unidades, peso, empacado, unidad);

			// Poner lo de abajo en Inventario + camiar el precio
			if (this.inventario.getLotes().containsKey(nombreProducto))
			{
				ArrayList<Lote> arrayDelHash = this.inventario.getLotes().get(nombreProducto);
				arrayDelHash.add(newLote);

				// Ajustar el nuevo precio

				for (Lote i : arrayDelHash)
				{
					i.setPrecioPublico(publico);
				}
			} else
			{
				ArrayList<Lote> arrayDelHash = new ArrayList<Lote>();
				arrayDelHash.add(newLote);
				this.inventario.getLotes().put(nombreProducto, arrayDelHash);
			}
		}
		csvReader.close();

	}

	/**
	 * 
	 * @param nombreProducto
	 * @return Es 0 si no existe el producto
	 */
	private int disponibilidadProducto(String nombreProducto) // nombreProducto debe estar en minúsculas
	{
		int cantidadTotal = 0;

		// Array de lotes según nombreProducto

		boolean existeLote = this.inventario.getLotes().containsKey(nombreProducto);
		if (!existeLote)
		{
			return cantidadTotal;
		}

		else
		{
			ArrayList<Lote> lotesDelProducto = this.inventario.getLotes().get(nombreProducto);

			for (Lote i : lotesDelProducto)
			{
				cantidadTotal += i.getCantidadUnidades();
			}
		}

		return cantidadTotal;
	}

	/**
	 * 
	 * @param nombreProducto
	 * @return -1 si no existe.
	 */
	private int unidadesDisponiblesLote(String nombreProducto)
	{
		int cantidadTotal = 0;
		boolean existeLote = this.inventario.getLotes().containsKey(nombreProducto);
		if (!existeLote)
		{
			cantidadTotal = -1; // No existe
		} else
		{

			HashMap<String, Lote> opcionesLoteHashMap = new HashMap<>();
			ArrayList<Lote> lotesDelProducto = this.inventario.getLotes().get(nombreProducto);

			int numOpcion = 0;
			System.out.println("Seleccione una opción");
			for (Lote i : lotesDelProducto)
			{
				System.out.println(numOpcion + " - Fecha de ingreso: " + i.getfechaDeIngreso() + " Fecha de vencimiento: " + i.getfechaDeVencimiento());
				opcionesLoteHashMap.put(String.valueOf(numOpcion), i);
				numOpcion++;
			}

			String opcion_seleccionada = input("~ ");

			cantidadTotal = opcionesLoteHashMap.get(opcion_seleccionada).getCantidadUnidades();
		}

		return cantidadTotal;
	}

	/**
	 * 
	 * @param nombreProducto
	 * @return null si no existe
	 */
	private LocalDate fechaVencimientoLote(String nombreProducto)
	{
		LocalDate fechaVencimiento = null;
		boolean existeLote = this.inventario.getLotes().containsKey(nombreProducto);
		if (!existeLote)
		{
			fechaVencimiento = null; // No existe
		} else
		{

			HashMap<String, Lote> opcionesLoteHashMap = new HashMap<>();
			ArrayList<Lote> lotesDelProducto = this.inventario.getLotes().get(nombreProducto);

			int numOpcion = 0;
			System.out.println("Seleccione una opción");
			for (Lote i : lotesDelProducto)
			{
				System.out.println(numOpcion + " - Fecha de ingreso: " + i.getfechaDeIngreso() + " Cantidad de unidades: " + i.getCantidadUnidades());
				opcionesLoteHashMap.put(String.valueOf(numOpcion), i);
				numOpcion++;
			}

			String opcion_seleccionada = input("~ ");

			fechaVencimiento = opcionesLoteHashMap.get(opcion_seleccionada).getfechaDeVencimiento();
		}

		return fechaVencimiento;

	}

	private void eliminarLotesVacios()
	{
		Set<String> llaves = inventario.getLotes().keySet();
		
		ArrayList<Lote> porBorrar = new ArrayList<>(); 

		for (String llave : llaves)
		{
			ArrayList<Lote> contenido = inventario.getLotes().get(llave);


			for (Lote i : contenido)
			{
				if (i.getCantidadUnidades() <= 0)
				{
					porBorrar.add(i);
				}

			}
			
			for (Lote i: porBorrar)
			{
				contenido.remove(i);
			}
		}

	}

	private void guardarYcerrar() throws IOException
	{
		String dataDirectory = System.getProperty("user.dir") + "/data";
		File csvfile = new File(dataDirectory + "/inventario.csv");
		csvfile.createNewFile();

		FileWriter writeCSV = new FileWriter(csvfile);

		String primeraLineaString = "Producto,Categor�a,Vencimiento (YYYY-MM-DD),Ingreso (YYYY-MM-DD),Precio Proveedor,Precio P�blico,Unidades,Peso por una unidad (g),Empacado,Unidad";

		writeCSV.write(primeraLineaString + "\n"); // Se agrega la primera linea

		// se va a agregar el contenido del Mapa de inventario

		Set<String> llaves = inventario.getLotes().keySet();

		for (String llave : llaves)
		{
			ArrayList<Lote> contenido = inventario.getLotes().get(llave);

			for (Lote i : contenido)
			{
				String producto = i.getNameProducto();
				String categoria = i.getCategoria();

				String vencimiento = i.getfechaDeVencimiento().toString();
				String ingreso = i.getfechaDeIngreso().toString();

				String proveedor = i.getPrecioProveedor().toString();
				String publico = i.getPrecioPublico().toString();

				String unidades = i.getCantidadUnidades().toString();
				String peso = i.getPeso().toString();

				String empacado = i.getEsEmpacado().toString();

				String unidadPeso = i.getUnidadMedida();

				// Nueva linea
				String nuevaLinea = producto + "," + categoria + "," + vencimiento + "," + ingreso + "," + proveedor + "," + publico + "," + unidades + "," + peso + "," + empacado + "," + unidadPeso;
				writeCSV.write(nuevaLinea + "\n");
			}
		}
		writeCSV.close();
		System.exit(0);// Successful
	}

}
