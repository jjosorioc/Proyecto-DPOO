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

import modelo.promociones.Combo;
import modelo.promociones.Descuento;
import modelo.promociones.Promocion;
import modelo.promociones.PuntosMultiplicados;
import modelo.promociones.Regalo;

public class POS

{
	// Atributos
	public Inventario inventario = new Inventario();

	public HashMap<String, Integer> clientes = new HashMap<String, Integer>(); // Cédula y Puntos

	public HashMap<String, ArrayList<Integer>> puntos = new HashMap<String, ArrayList<Integer>>(); // Puntos por mes

	public HashMap<String, Integer> unidadesDuranteEjecucion = new HashMap<>();

	// Métodos

	/**
	 * @return the clientes
	 */
	public HashMap<String, Integer> getClientes()
	{
		return clientes;
	}

	public HashMap<String, ArrayList<Integer>> getPuntos()
	{
		return puntos;
	}

	/**
	 * Se agregan los puntos del clente
	 * 
	 * @param cedula
	 * @param puntos
	 */
	public void agregarPuntosCliente(String cedula, Integer puntosActuales)
	{
		if (this.clientes.containsKey(cedula))
		{
			Integer nuevosPuntos = this.clientes.get(cedula) + puntosActuales;
			this.clientes.replace(cedula, nuevosPuntos);
		} else
		{
			this.clientes.put(cedula, puntosActuales);
		}
	}

	public void agregarPuntosClienteMes(String cedula, Integer puntosActuales, Integer mes)
	{
		if (this.puntos.containsKey(cedula))
		{
			Integer nuevosPuntosMes = this.puntos.get(cedula).get(mes - 1) + puntosActuales;
			this.puntos.get(cedula).set(mes - 1, nuevosPuntosMes);

		} else
		{
			ArrayList<Integer> puntosMes = new ArrayList<Integer>();

			for (int i = 0; i < 12; i++)
			{
				puntosMes.add(0);
			}

			for (int j = 0; j < 12; j++)
			{
				if (j != mes - 1)
				{
					puntosMes.set(j, 0);
				} else
				{
					puntosMes.set(j, puntosActuales);
				}
			}

			this.puntos.put(cedula, puntosMes);
		}

	}

	/**
	 * Se carga el inventario de inventario.csv
	 * 
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

			String tipoProducto = elArray[11];

			String subCategorias = elArray[12];

			Lote newLote = new Lote(nombreProducto, categoria, vencimiento, ingreso, proveedor, publico, unidades, peso, empacado, unidad, codigoBarras, tipoProducto, subCategorias);

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

				// Mapa para obtener el nombre del producto con el código de barras.
				this.inventario.getCodigos().put(codigoBarras, nombreProducto);
			}
		}
		csvReader.close();

	}

	public void cargarPromociones() throws IOException// throws Exception
	{
		BufferedReader csvReaderPromociones = new BufferedReader(new FileReader("./data/promociones.csv"));
		// Tipo;FechaInicio;FechaFin;Productos;Valor;Nombre

		csvReaderPromociones.readLine();
		String row;
		while ((row = csvReaderPromociones.readLine()) != null)
		{
			Promocion laPromocion = null; // La promoción que se va a ingresar

			String[] separada = row.split(";");

			String tipo = separada[0];

			String[] fecha1 = separada[1].split("-");
			LocalDate inicioDate = LocalDate.of(Integer.parseInt(fecha1[0]), Integer.parseInt(fecha1[1]), Integer.parseInt(fecha1[2]));

			String[] fecha2 = separada[2].split("-");
			LocalDate finDate = LocalDate.of(Integer.parseInt(fecha2[0]), Integer.parseInt(fecha2[1]), Integer.parseInt(fecha2[2]));

			String productos = separada[3].toLowerCase();

			String valor = separada[4];

			String nombre = separada[5];

			String codigoQR = separada[6];

			// Condicionales

			if (tipo.equals("descuento"))
			{
				laPromocion = new Descuento(inicioDate, finDate, productos, valor);
			} else if (tipo.equals("regalo"))
			{
				laPromocion = new Regalo(inicioDate, finDate, productos, valor);
			} else if (tipo.equals("combo"))
			{
				Combo elCombo = new Combo(nombre, inicioDate, finDate, productos, valor, codigoQR);
				this.inventario.addCombo(elCombo);
			} else if (tipo.equals("puntos"))
			{
				laPromocion = new PuntosMultiplicados(inicioDate, finDate, productos, Integer.parseInt(valor));
			} else
			{
				// throw new Exception("No se encontró la promoción");
			}

			// Se agrega la promoción al inventario, NO se agregan Combos
			this.inventario.addPromocion(laPromocion);
		}
		csvReaderPromociones.close();
	}

	/**
	 * Guardar el inventario al csv
	 * 
	 * @throws IOException
	 */
	public void guardarInventario() throws IOException
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
	}

	/**
	 * Guardar clientes al csv
	 * 
	 * @throws IOException
	 */
	public void guardarClientes() throws IOException
	{
		String dataDirectory = System.getProperty("user.dir") + "/data";
		File csvfile = new File(dataDirectory + "/clientes.csv");
		csvfile.createNewFile();

		FileWriter writeCSV = new FileWriter(csvfile);

		String primeraLineaString = "Cedula,Puntos,PuntosEnero,PuntosFebrero,PuntosMarzo,PuntosAbril,PuntosMayo,PuntosJunio,PuntosJulio,PuntosAgosto,PuntosSeptiembre,PuntosOctubre,PuntosNoviembre,PuntosDiciembre";

		writeCSV.write(primeraLineaString + "\n"); // Se agrega la primera línea

		Set<String> llaves = this.clientes.keySet();

		for (String cedula : llaves)
		{
			String puntosActuales = this.clientes.get(cedula).toString();

			ArrayList<Integer> puntosMes = this.puntos.get(cedula);

			String nuevaLinea = cedula + "," + puntosActuales + "," + puntosMes.get(0) + "," + puntosMes.get(1) + "," + puntosMes.get(2) + "," + puntosMes.get(3) + "," + puntosMes.get(4) + ","
					+ puntosMes.get(5) + "," + puntosMes.get(6) + "," + puntosMes.get(7) + "," + puntosMes.get(8) + "," + puntosMes.get(9) + "," + puntosMes.get(10) + "," + puntosMes.get(11);
			writeCSV.write(nuevaLinea + "\n");
		}

		writeCSV.close();
	}

	/**
	 * Guarda las ganancias y perdidas en gananciasYperdidas.csv
	 * 
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

		for (String llave : llaves)
		{
			String ganancia = inventario.getGanancias().get(llave).toString();
			String perdida = inventario.getPerdidas().get(llave).toString();

			String nuevaLinea = llave + "," + ganancia + "," + perdida;
			writeCSV.write(nuevaLinea + "\n");
		}
		writeCSV.close();
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

	public void updateUnidadesDuranteEjecucion()
	{
		Set<String> llaveSet = inventario.getLotes().keySet();

		for (String llave : llaveSet)
		{
			this.unidadesDuranteEjecucion.put(llave, disponibilidadProducto(llave));
		}
	}

	/**
	 * @return the unidadesDuranteEjecucion
	 */
	public HashMap<String, Integer> getUnidadesDuranteEjecucion()
	{
		return unidadesDuranteEjecucion;
	}
}
