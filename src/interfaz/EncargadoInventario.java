package interfaz;

import java.io.File;

import javax.swing.JFileChooser;

public class EncargadoInventario
{
	
	
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
		    //System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		    
		    return (selectedFile.getAbsolutePath());
		}
		
		return ("null");
	}
	
	
	public static void main(String[] args)
	{
		EncargadoInventario objEncargadoInventario = new EncargadoInventario();
		
		System.out.println(objEncargadoInventario.getCSVPath()); // TODO revisar que le sirva a thais
	}
}
