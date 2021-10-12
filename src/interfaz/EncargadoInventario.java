package interfaz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JFileChooser;

import modelo.Inventario;
import modelo.Lote;

public class EncargadoInventario
{
	public static void main(String[] args) throws IOException
	{
		EncargadoInventario objEncargadoInventario = new EncargadoInventario();
		objEncargadoInventario.cargarGananciasPerdidas(); // Cargar ganancias y pérdidas
		objEncargadoInventario.readCSV(System.getProperty("user.dir") + "/data/inventario.csv"); // Leer inventario.csv
		
		
		objEncargadoInventario.ejecutarOpcion();

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

	private void mostrarMenu()
	{
		System.out.println("\n******************** MENÚ PRINCIPAL ********************\n");
		System.out.println("\nBienvenido a la consola para el encargado de inventario");
		System.out.println("\n0. Cargar lote.");
		System.out.println("\n1. Consultar disponibilidad de un producto.");
		System.out.println("\n2. Eliminar lotes vencidos.");
		System.out.println("\n3. Consultar unidades disponibles en un lote.");
		System.out.println("\n4. Consultar fecha de vencimiento de un lote.");
		System.out.println("\n5. Consultar desempeño financiero de un producto."); // TODO

		System.out.println("\n6. GUARDAR y CERRAR (Si no selecciona esta opción sus cambios no serán guardados).\n");
		System.out.println("*********************************************************\n");
	}

	public void ejecutarOpcion() throws IOException
	{
		System.out.println("Iniciando programa...");

		boolean continuar = true;

		while (continuar)
		{
			mostrarMenu();
			int opcion_seleccionada = Integer.parseInt(input("Por favor seleccione una opción"));
			
			if (opcion_seleccionada == 0)
			{
				String path = getCSVPath();
				readCSV(path);
				System.out.println("\nSe ha cargado el lote con éxito.\n");
			}
			else if (opcion_seleccionada == 1)
			{
				String nombre = input("\nIngrese el nombre del producto del cuál desea consultar su disponibilidad");
				Integer resultado = disponibilidadProducto(nombre);
				if (resultado == -1)
				{
					System.out.println("\nEl producto " +  nombre + " no forma parte de nuestro inventario.\n");
				}
				else 
				{
					System.out.println("\nExisten un total de " + resultado + " unidades de " + nombre + ".\n");
				}
			}
				
			else if (opcion_seleccionada == 2)
			{
				Integer year = Integer.parseInt(input("\nIngrese el año actual (Ejemplo: 2021)"));
				Integer month = Integer.parseInt(input("\nIngrese el mes actual (Ejemplo: 10)"));
				Integer day = Integer.parseInt(input("\nIngrese el día actual (Ejemplo: 12)"));
				
				eliminarLotesVencidos(year, month, day);
				
				System.out.println("\nSolicitud recibida con éxito. Asegúrese de seleccionar la opción 6) para completar la solicitud.\n");
			}
			
			else if (opcion_seleccionada == 3)
			{
				String nombre = input("\nIngrese el nombre del producto del cuál desea consultar su disponibilidad en TODOS los lotes");
				Integer resultado = unidadesDisponiblesLote(nombre);
				if (resultado == -1)
				{
					System.out.println("\nEl producto " +  nombre + " no forma parte de nuestro inventario.\n");
				}
				else 
				{
					System.out.println("\nExisten un total de  " + resultado + " unidades de " + nombre + ".\n");
				}
			}
			
			else if (opcion_seleccionada == 4)
			{
				String nombre = input("\nIngrese el nombre de un producto para encontrar el lote que desea consultar");
				LocalDate fecha = fechaVencimientoLote(nombre);
				System.out.println("\nLa fecha de vencimiento del lote seleccionado es el día " + fecha.getDayOfMonth() + " del mes " + fecha.getMonthValue() + " año " + fecha.getYear() + ".\n");
				
			}
			
			else if (opcion_seleccionada == 5)
			{
				//TODO 
			}
			
			else if  (opcion_seleccionada == 6)
			{
				guardarYcerrar();
				System.out.println("\nCambios de inventario guardados con éxito!\n");
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
			
			String codigoBarras = elArray[10];

			Lote newLote = new Lote(nombreProducto, categoria, vencimiento, ingreso, proveedor, publico, unidades, peso, empacado, unidad, codigoBarras);

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
				
				// Agregar a Ganancias/Pérdidas
				this.inventario.getGanancias().put(newLote.getNameProducto(), 0.0);
				this.inventario.getPerdidas().put(newLote.getNameProducto(),0.0);
				
				//Mapa para obtener el nombre del producto con el código de barras.
				this.inventario.getCodigos().put(codigoBarras, nombreProducto);
			}
		}
		csvReader.close();

	}
	
	
	private void cargarGananciasPerdidas() throws IOException
	{
		String dataDirectory = System.getProperty("user.dir") + "/data/gananciasYperdidas.csv";
		BufferedReader csvReader = new BufferedReader(new FileReader(dataDirectory));
		
		// NombreProducto, Ganancias, Perdidas
		
		csvReader.readLine();
		String row;
		while ((row = csvReader.readLine()) != null)
		{
			String[] separada = row.split(",");
			
			String nombreProducto = separada[0];
			Double gananciasDouble = Double.parseDouble(separada[1]);
			Double perdidasDouble = Double.parseDouble(separada[2]);
			
			this.inventario.getGanancias().put(nombreProducto, gananciasDouble);
			this.inventario.getPerdidas().put(nombreProducto, perdidasDouble);
		}
		csvReader.close();
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

		boolean existeLote = this.inventario.getLotes().containsKey(nombreProducto);
		if (!existeLote)
		{
			cantidadTotal = -1;
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

	
	/**
	 * Se ingresa la fecha actual.
	 * @param year
	 * @param month
	 * @param day
	 */
	private void eliminarLotesVencidos(int year, int month, int day) // YYYY-MM-DD
	{
		LocalDate fechaDate = LocalDate.of(year, month, day);
		Set<String> llaves = inventario.getLotes().keySet();
		
		ArrayList<Lote> porBorrar = new ArrayList<>(); 

		for (String llave : llaves)
		{
			ArrayList<Lote> contenido = inventario.getLotes().get(llave);


			for (Lote i : contenido)
			{
				if (i.getfechaDeVencimiento().isBefore(fechaDate))
				{
					porBorrar.add(i);
				}

			}
			
			for (Lote i: porBorrar)
			{
				contenido.remove(i);
				
				// Se agregan las pérdidas
				double perdidas = (i.getPrecioProveedor() * i.getCantidadUnidades());
				this.inventario.getPerdidas().replace(i.getNameProducto(), perdidas);
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
				
				String codigoBarras = i.getCodigoBarras();

				// Nueva linea
				String nuevaLinea = producto + "," + categoria + "," + vencimiento + "," + ingreso + "," + proveedor + "," + publico + "," + unidades + "," + peso + "," + empacado + "," + unidadPeso + "," + codigoBarras;
				writeCSV.write(nuevaLinea + "\n");
			}
		}
		writeCSV.close();
		guardarGananciasYPerdidas();
		System.exit(0);// Successful
	}
	
	/**
	 * Guarda las ganancias y perdidas en gananciasYperdidas.csv
	 * @throws IOException
	 */
	private void guardarGananciasYPerdidas() throws IOException
	{
		String dataDirectory = System.getProperty("user.dir") + "/data";
		File csvfile = new File(dataDirectory + "/gananciasYperdidas.csv");
		csvfile.createNewFile();

		FileWriter writeCSV = new FileWriter(csvfile);
		
		String primeraLineaString = "Producto,Ganancias,Perdidas";
	
		writeCSV.write(primeraLineaString + "\n"); // Se agrega la primera línea
		
		
		Set<String> llaves = inventario.getGanancias().keySet();
		
		for (String llave: llaves)
		{
			String ganancia = inventario.getGanancias().get(llave).toString();
			String perdida = inventario.getPerdidas().get(llave).toString();
			
			String nuevaLinea = llave + "," + ganancia + "," + perdida;
			writeCSV.write(nuevaLinea+"\n");
		}
		writeCSV.close();
	}

}
