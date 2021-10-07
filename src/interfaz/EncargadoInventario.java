package interfaz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.RowFilter;

import modelo.Inventario;

public class EncargadoInventario
{
	Inventario inventario = new Inventario();
	
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
		 * Peso por una unidad (g)	
		 * Empacado 
		 */
		csvReader.readLine(); // Lee primera linea
		String row;
		while ((row = csvReader.readLine()) != null)
		{
			String[] elArray = row.split(",");
		}
	}
	
	
	public static void main(String[] args) throws IOException
	{
		EncargadoInventario objEncargadoInventario = new EncargadoInventario();
		
		String pathCSV =  objEncargadoInventario.getCSVPath();
		objEncargadoInventario.readCSV(pathCSV);
	}
}
