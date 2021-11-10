package controlador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import modelo.Inventario;
import modelo.Lote;

public class EncargadoInventario
{
	public static void main(String[] args) throws IOException
	{
		EncargadoInventario objEncargadoInventario = new EncargadoInventario();
		objEncargadoInventario.cargarGananciasPerdidas(); // Cargar ganancias y pérdidas
		objEncargadoInventario.readCSV(System.getProperty("user.dir") + "/data/inventario.csv"); // Leer inventario.csv

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

	/**
	 * 
	 * @return el path del archivo o null si no es valido
	 */
	public String getCSVPath()
	{
		String pathInbox = System.getProperty("user.dir") + "/inbox";

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Seleccione el archivo con los lotes");
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setCurrentDirectory(new File(pathInbox));

		FileNameExtensionFilter restringirExtensionFilter = new FileNameExtensionFilter("Solo archivos CSV con la estructura de los lotes", "csv");
		fileChooser.addChoosableFileFilter(restringirExtensionFilter);
		int result = fileChooser.showOpenDialog(fileChooser);

		if (result == JFileChooser.APPROVE_OPTION)
		{

			File selectedFile = fileChooser.getSelectedFile();

			return selectedFile.getAbsolutePath();
		}

		return null;

	}

	/**
	 * Método para leer un csv con el archivo con Lotes
	 * 
	 * @param pathCSV
	 * @throws IOException
	 */
	public void readCSV(String pathCSV) throws IOException // Opción 1
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

			String tipoProducto = elArray[11];

			String subCategorias = elArray[12];

			Lote newLote = new Lote(nombreProducto, categoria, vencimiento, ingreso, proveedor, publico, unidades, peso, empacado, unidad, codigoBarras, tipoProducto, subCategorias);

			// Poner lo de abajo en Inventario + camiar el precio
			if (this.inventario.getLotes().containsKey(nombreProducto))
			{
				ArrayList<Lote> arrayDelHash = this.inventario.getLotes().get(nombreProducto);

				for (int counter = 0; counter < arrayDelHash.size(); counter++) // Se reemplaza la categoría de artículos previamente cargados si se ingresa un lote con un artículo que ya forma parte
																				// del inventario, pero que tiene una categoría distinta.
				{
					Double precioProveedor = arrayDelHash.get(counter).getPrecioProveedor(); // Se mantiene el precio, unidades y fechas que se tenía antes, lo demás se mantiene igual al nuevo lote.
					Integer unidadesOtroLote = arrayDelHash.get(counter).getCantidadUnidades();
					LocalDate vencimientoOtroLote = arrayDelHash.get(counter).getfechaDeVencimiento();
					LocalDate ingresoOtroLote = arrayDelHash.get(counter).getfechaDeIngreso();
					Lote loteCambiado = new Lote(nombreProducto, categoria, vencimientoOtroLote, ingresoOtroLote, precioProveedor, publico, unidadesOtroLote, peso, empacado, unidad, codigoBarras,
							tipoProducto, subCategorias);
					arrayDelHash.set(counter, loteCambiado);
				}

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

				if (!this.inventario.getGanancias().containsKey(nombreProducto) && !this.inventario.getPerdidas().containsKey(nombreProducto))
				{
					// Agregar a Ganancias/Pérdidas
					this.inventario.getGanancias().put(newLote.getNameProducto(), 0.0);
					this.inventario.getPerdidas().put(newLote.getNameProducto(), 0.0);
				}

				// Mapa para obtener el nombre del producto con el código de barras.
				this.inventario.getCodigos().put(codigoBarras, nombreProducto);
			}
		}
		csvReader.close();

	}

	/**
	 * Se carga el archivo de ganaciasYperdidas.csv
	 * 
	 * @throws IOException
	 */
	public void cargarGananciasPerdidas() throws IOException
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
	public int disponibilidadProducto(String nombreProducto) // nombreProducto debe estar en minúsculas
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
	 * Retorna *true* si existe el lote con el nombre del producto ingresado.
	 * 
	 * @param nombreProducto
	 * @return
	 */
	public boolean existeElLote(String nombreProducto)
	{
		return this.inventario.getLotes().containsKey(nombreProducto);
	}

	/**
	 * Retorna un ArrayList con los lotes de un producto, sabiendo que este ya existe
	 * 
	 * @param nombreProducto
	 * @return
	 */
	public ArrayList<Lote> lotesDeUnProducto(String nombreProducto)
	{
		return this.inventario.getLotes().get(nombreProducto);
	}

	/**
	 * Retorna un String[] con los lotes como Strings según la fecha de ingreso y vencimiento
	 * 
	 * @param lotesDelProducto
	 * @return
	 */
	public String[] lotesDeUnProductoIngresoVencimientoStrings(ArrayList<Lote> lotesDelProducto)
	{

		ArrayList<String> ordenadosSegunNombre = new ArrayList<String>();

		for (Lote i : lotesDelProducto)
		{
			String loteEspecifico = "Fecha de ingreso: " + i.getfechaDeIngreso() + " | Fecha de vencimiento: " + i.getfechaDeVencimiento();

			ordenadosSegunNombre.add(loteEspecifico);
		}

		return ordenadosSegunNombre.toArray(new String[0]); // String[]
	}

	/**
	 * Retorna un String[] con los lotes como Strings según la fecha de ingreso y la cantidad de unidades.
	 * 
	 * @param lotesDelProducto
	 * @return
	 */
	public String[] lotesDeUnProductoIngresoCantidad(ArrayList<Lote> lotesDelProducto)
	{
		ArrayList<String> ordenadosSegunNombre = new ArrayList<String>();

		for (Lote i : lotesDelProducto)
		{
			String loteEspecifico = "Fecha de ingreso: " + i.getfechaDeIngreso() + " | Cantidad de unidades: " + i.getCantidadUnidades();

			ordenadosSegunNombre.add(loteEspecifico);
		}

		return ordenadosSegunNombre.toArray(new String[0]); // String[]
	}

	/**
	 * Retorna la cantidad de unidades del lote seleccionado.
	 * 
	 * @param loteElegido
	 * @param lotesComoStrings
	 * @param arrayConLotes
	 * @return
	 */
	public int unidadesDisponibles(String loteElegido, String[] lotesComoStrings, ArrayList<Lote> arrayConLotes)
	{
		Integer indiceDelLote = null;

		int sizeArray = lotesComoStrings.length;

		int iterador = 0;

		// Ciclo para encontrar el índice en el que se encuentra en lote elegido
		while (iterador < sizeArray && indiceDelLote == null)
		{
			if (loteElegido.equals(lotesComoStrings[iterador]))
			{
				indiceDelLote = iterador;
			}

			iterador++;
		}

		return arrayConLotes.get(indiceDelLote).getCantidadUnidades();
	}

	/**
	 * Retorna la fecha de vencimiento del Lote seleccionado.
	 * 
	 * @param loteElegido
	 * @param lotesComoStrings
	 * @param arrayConLotes
	 * @return
	 */
	public LocalDate fechaVencimientoLote(String loteElegido, String[] lotesComoStrings, ArrayList<Lote> arrayConLotes)
	{
		Integer indiceDelLote = null;

		int sizeArray = lotesComoStrings.length;

		int iterador = 0;

		// Ciclo para encontrar el índice en el que se encuentra en lote elegido
		while (iterador < sizeArray && indiceDelLote == null)
		{
			if (loteElegido.equals(lotesComoStrings[iterador]))
			{
				indiceDelLote = iterador;
			}

			iterador++;
		}

		return arrayConLotes.get(indiceDelLote).getfechaDeVencimiento();

	}

	/**
	 * Elimina los Lotes vencidos. Se toma la fecha actual.
	 * 
	 * @param year
	 * @param month
	 * @param day
	 */
	public void eliminarLotesVencidos() // YYYY-MM-DD
	{
		LocalDate fechaDate = LocalDate.now();
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

			for (Lote i : porBorrar)
			{
				contenido.remove(i);

				// Se agregan las pérdidas
				double perdidas = (i.getPrecioProveedor() * i.getCantidadUnidades());
				this.inventario.getPerdidas().replace(i.getNameProducto(), perdidas);
			}
		}

	}

	/**
	 * Se consulta el desempeño financiero de un producto por su nombre.
	 * 
	 * @param nombreProducto
	 * @return ArrayList<Double> [Ganancias, Perdidas]
	 */
	public ArrayList<Double> consultarDesempenoFinanciero(String nombreProducto)
	{
		ArrayList<Double> gananciasYperdidas = new ArrayList<Double>();
		Double ganancias = inventario.getGanancias().get(nombreProducto);
		Double perdidas = inventario.getPerdidas().get(nombreProducto);

		gananciasYperdidas.add(ganancias);
		gananciasYperdidas.add(perdidas);

		return (gananciasYperdidas);

	}

	/**
	 * Método para guardar toda la información en sus csv respectivos. Luego cierra la aplicación.
	 * 
	 * @throws IOException
	 */
	public void guardarYcerrar() throws IOException
	{
		String dataDirectory = System.getProperty("user.dir") + "/data";
		File csvfile = new File(dataDirectory + "/inventario.csv");
		csvfile.createNewFile();

		FileWriter writeCSV = new FileWriter(csvfile);

		String primeraLineaString = "Producto,Categor�a,Vencimiento (YYYY-MM-DD),Ingreso (YYYY-MM-DD),Precio Proveedor,Precio P�blico,Unidades,Peso por una unidad (g),Empacado,Unidad,Codigo de barras,Tipo producto,subCategorias";

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

				String tipoProducto = i.getTipoProducto();

				String subCategorias = i.getSubCategorias();

				// Nueva linea
				String nuevaLinea = producto + "," + categoria + "," + vencimiento + "," + ingreso + "," + proveedor + "," + publico + "," + unidades + "," + peso + "," + empacado + "," + unidadPeso
						+ "," + codigoBarras + "," + tipoProducto + "," + subCategorias;
				writeCSV.write(nuevaLinea + "\n");
			}
		}
		writeCSV.close();
		guardarGananciasYPerdidas();
		System.exit(0);// Successful
	}

	/**
	 * Guarda las ganancias y perdidas en gananciasYperdidas.csv
	 * 
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

		for (String llave : llaves)
		{
			String ganancia = inventario.getGanancias().get(llave).toString();
			String perdida = inventario.getPerdidas().get(llave).toString();

			String nuevaLinea = llave + "," + ganancia + "," + perdida;
			writeCSV.write(nuevaLinea + "\n");
		}
		writeCSV.close();
	}

}
