package modelo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap; // import the HashMap class

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

	public void agregarPuntosCliente(String cedula, Integer puntos)
	{
		this.clientes.replace(cedula, puntos);
	}

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

				// Agregar a Ganancias/Pérdidas
				this.inventario.getGanancias().put(newLote.getNameProducto(), 0.0);
				this.inventario.getPerdidas().put(newLote.getNameProducto(), 0.0);
			}
		}
		csvReader.close();

	}
}
