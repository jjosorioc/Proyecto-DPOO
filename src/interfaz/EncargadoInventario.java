package interfaz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;
import javax.swing.RowFilter;

import modelo.Inventario;
import modelo.Lote;

public class EncargadoInventario
{
	private Inventario inventario = new Inventario();
	
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
		
		
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    // TODO Save to data folder
		    
		    return (selectedFile.getAbsolutePath());
		}
		
		return ("null");
	}
	
	
	
	private void readCSV(String pathCSV) throws IOException
	{
		BufferedReader csvReader = new BufferedReader(new FileReader(pathCSV));
		
		/* Estructura CSV:
		 * Producto	
		 * Categoría	
		 * Vencimiento (D/M/A)	
		 * Ingreso (D/M/A)	
		 * Precio Proveedor	
		 * Precio Público	
		 * Unidades	
		 * Peso  (g)	
		 * Empacado 
		 * TODO cosa de thais precio/peso unidad
		 */
		csvReader.readLine(); // Lee primera linea
		String row;
		while ((row = csvReader.readLine()) != null)
		{
			String[] elArray = row.split(","); // Main array
			
			
			String nombreProducto = elArray[0].toLowerCase();
			
			String categoria = elArray[1];
			
			String[] vencimientoString = (elArray[2]).split("/");
			LocalDate vencimiento = LocalDate.of(Integer.parseInt(vencimientoString[2]), Integer.parseInt(vencimientoString[1]), Integer.parseInt(vencimientoString[0]));
			
			String[] ingresoString = (elArray[3]).split("/");
			LocalDate ingreso = LocalDate.of(Integer.parseInt(ingresoString[2]), Integer.parseInt(ingresoString[1]), Integer.parseInt(ingresoString[0]));
			
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
				ArrayList arrayDelHash = this.inventario.getLotes().get(nombreProducto);
				arrayDelHash.add(newLote); // TODO
			}
			else {
				ArrayList<Lote> arrayDelHash = new ArrayList<Lote>();
				arrayDelHash.add(newLote);
				this.inventario.getLotes().put(nombreProducto, arrayDelHash);
			}
		}
		csvReader.close();
		
	}
	
	
	/**
	 * @return the inventario
	 */
	public Inventario getInventario()
	{
		return inventario;
	}
	
	
	public static void main(String[] args) throws IOException
	{
		EncargadoInventario objEncargadoInventario = new EncargadoInventario();
		
		
		// if encargado quiere agregar un lote
		String pathCSV =  objEncargadoInventario.getCSVPath();
		objEncargadoInventario.readCSV(pathCSV);
		objEncargadoInventario.ejecutarOpcion();
		
	}
	
	public void mostrarMenu()
	{
		System.out.println("\n******************** MENÚ PRINCIPAL ********************\n");
		System.out.println("\nBienvenido a la consola para el encargado de inventario");
		System.out.println("\n0. Cargar lotes.");
		System.out.println("\n1. Consultar disponibilidad de un producto.");
		System.out.println("\n2. Eliminar lotes vacíos.");
		System.out.println("\n3. Consultar unidades disponibles en un lote.");
		System.out.println("\n4. Consultar fehca de vencimiento de un lote.\n");
		System.out.println("\n5. Consultar desempeño financiero de un prodcuto.\n");
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
			}
			catch (NumberFormatException e)
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


}
