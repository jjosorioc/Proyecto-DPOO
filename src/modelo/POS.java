package modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap; // import the HashMap class
import java.util.Set;

public class POS

{
	// Atributos
	public Inventario inventario = new Inventario();

	private HashMap<String, Integer> clientes = new HashMap<String, Integer>(); // Cédula y Puntos

	// Métodos

	/**
	 * @return the clientes
	 */
	public HashMap<String, Integer> getClientes()
	{
		return clientes;
	}
	
	
	/**
	 * Se agregan los puntos del clente
	 * @param cedula
	 * @param puntos
	 */
	public void agregarPuntosCliente(String cedula, Integer puntos)
	{
		this.clientes.replace(cedula, puntos);
	}
	
	
	/**
	 * Se carga el inventario de inventario.csv
	 * @throws IOException
	 */
	public void cargarInventario() throws IOException // Se carga el inventario
	{
		String pathCSV = System.getProperty("user.dir") + "/data/inventario.csv";
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
				this.inventario.getPerdidas().put(newLote.getNameProducto(), 0.0);
				
				
				//Mapa para obtener el nombre del producto con el código de barras.
				this.inventario.getCodigos().put(codigoBarras, nombreProducto);
			}
		}
		csvReader.close();

	}
	
	
	/**
	 * Guardar el inventario al csv
	 * @throws IOException
	 */
	public void guardarInventario() throws IOException
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
	}
	
	
	/**
	 * Guardar clientes al csv
	 * @throws IOException
	 */
	public void guardarClientes() throws IOException
	{
		String dataDirectory = System.getProperty("user.dir") + "/data";
		File csvfile = new File(dataDirectory + "/clientes.csv");
		csvfile.createNewFile();

		FileWriter writeCSV = new FileWriter(csvfile);
		
		String primeraLineaString = "Cedula,Puntos";
	
		writeCSV.write(primeraLineaString + "\n"); // Se agrega la primera línea
	
		Set<String> llaves = this.clientes.keySet();
		
		for (String cedula: llaves)
		{
			String puntos = this.clientes.get(cedula).toString();
			
			String nuevaLinea = cedula + "," + puntos; //TODO hacer que el archivo se separe por comas
			writeCSV.write(nuevaLinea + "\n");
		}
		
		writeCSV.close();
	}
	
	
	/**
	 * Guarda las ganancias y perdidas en gananciasYperdidas.csv
	 * @throws IOException
	 */
	public void guardarGananciasYPerdidas() throws IOException
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
