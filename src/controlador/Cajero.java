package controlador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import modelo.Compra;
import modelo.Inventario;
import modelo.Lote;
import modelo.POS;

public class Cajero
{
	public static void main(String[] args) throws IOException
	{
		Cajero objCajero = new Cajero();
		objCajero.readCSV(System.getProperty("user.dir") + "/data/clientes.csv"); // se cargan los clientes
		objCajero.pos.cargarInventario(); // Se carga inventario.csv
		objCajero.ejecutarOpcion();
	}

	// Atributos

	public POS pos = new POS();

	public Compra compraActiva = null;

	// Métodos
	
	/**
	 * Método para imprimir el menú en consola
	 */
	private void mostrarMenu()
	{
		System.out.println("\n******************** MENÚ PRINCIPAL ********************\n");
		System.out.println("\nBienvenido a la consola para el cajero");
		System.out.println("\n0. Agregar un nuevo cliente al sistema.");
		System.out.println("\n1. Iniciar una compra de un cliente.");
		System.out.println("\n2. Agregar un producto a la compra del cliente.");
		System.out.println("\n3. Finalizar compra cliente.");
		System.out.println("\n4. Eliminar compra cliente.");
		System.out.println("\n5. GUARDAR y CERRAR (Si no selecciona esta opción sus cambios no serán guardados).\n");
		System.out.println("*********************************************************\n");
	}

	/**
	 * Método que interactúa con el usuario a través de la consola.
	 * @throws IOException 
	 */
	public void ejecutarOpcion() throws IOException
	{
		System.out.println("Iniciando programa...");

		boolean continuar = true;

		while (continuar)
		{

			
			mostrarMenu();
			int opcion_seleccionada = -1;
			try
			{
				opcion_seleccionada = Integer.parseInt(input("\nPor favor seleccione una opción\n"));
			} catch (NumberFormatException e)
			{
				opcion_seleccionada = -1;
			}
			
			
			if (opcion_seleccionada == 0)
			{
				String cedula = input("\nPor favor ingrese el número de cédula del cliente");
				int edad = Integer.parseInt(input("\nPor favor ingrese la edad del cliente"));
				String sexo = input("\nIngrese el género del cliente");
				String estadoCivil = input("\nIngrese el estado civil del cliente");
				String situacionLaboral = input("\nIngrese la situación laboral del cliente");
				crearCliente(cedula);
				System.out.println("\nCliente agregado al sistema de puntos! ATENCIÓN: Asegúrese de seleccionar la opción 5) para completar la solicitud.\n");
				
			}
			else if (opcion_seleccionada == 1)
			{
				String cedula = input("\nPor favor ingrese el número de cédula del cliente (Espiche enter si no está registrado)");
				Boolean existe = pos.getClientes().containsKey(cedula);
				if (existe)
				{
					inicarCompraCliente(cedula);
				}
				else
				{
					inicarCompraCliente(null);
				}
				System.out.println("\nCompra iniciada con éxito!\n");
			}
			else if (opcion_seleccionada == 2)
			{
				if (this.compraActiva != null)
				{
					String codigoProducto = input("\nPor favor ingrese el código de barras del producto que desea registrar");
					
					String nombreProducto = this.pos.inventario.getCodigos().get(codigoProducto);
					
					if (nombreProducto == null) // Si el producto no se encontró
					{
						System.out.println("\nEl producto con código " +  codigoProducto + " no forma parte de nuestro inventario.\n");
					}
					else 
					{	
						boolean esEmpacado = this.pos.inventario.getLotes().get(nombreProducto).get(0).getEsEmpacado();
						
						if (esEmpacado) // Si es empacado
						{
							Double cantidad = (double) Integer.parseInt(input("\nPor favor ingrese la cantidad de "+ nombreProducto + " que desea comprar el cliente"));
							Double peso = -1.0;
							agregarProducto(nombreProducto, cantidad, peso);
						}
						else // Si es no empacado
						{
							//Double cantidad = (double) Integer.parseInt(input("\nPor favor ingrese la cantidad de "+ nombreProducto + " que desea comprar el cliente"));
							Double peso = Double.parseDouble(input("\nPor favor ingrese el peso de "+ nombreProducto + " que desea comprar el cliente"));
							Double cantidad = Math.ceil(peso / this.pos.inventario.getLotes().get(nombreProducto).get(0).getPeso());
							System.out.println("\nLa cantidad de " + nombreProducto + " según el peso ingresado es: " + cantidad);
							agregarProducto(nombreProducto, cantidad, peso);
						}
				}
			}else {
				System.out.println("\nINICIE UNA COMPRA");
			}
				
			}
			
			else if (opcion_seleccionada == 3)
			{
				if (this.compraActiva != null)
				{
					finalizarCompra();
					System.out.println("\nCompra finalizada! ATENCIÓN: Asegúrese de seleccionar la opción 5) para completar la solicitud.\n");
				}
				else
				{
					System.out.println("\nINICIE UNA COMPRA");
				}
				
			}
			else if (opcion_seleccionada == 4)
			{
				eliminarCompra();
			}
			else if (opcion_seleccionada == 5)
			{
				guardarYcerrar();
			}
			else {
				System.out.println("\n¡INGRESE UNA OPCIÓN VÁLIDA!");
			}

		}
	}

	/**
	 * Método para obtener el input del usuario.
	 * 
	 * @param mensaje
	 * @return String o null
	 */
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
	 * Método para leer un csv con la infromación de los clientes.
	 * 
	 * @param pathCSV
	 * @throws IOException
	 */
	public void readCSV(String pathCSV) throws IOException // Opción 1
	{
		BufferedReader csvReader = new BufferedReader(new FileReader(pathCSV));

		/*
		 * Estructura CSV: Nombre cliente Numero de cedula Puntos totales Edad Sexo Estado civil Situacion laboral
		 */
		csvReader.readLine(); // Lee primera linea
		String row;
		while ((row = csvReader.readLine()) != null)
		{
			String[] elArray = row.split(","); // Main array

			String documento = elArray[0];

			Integer puntos = Integer.parseInt(elArray[1]);

			pos.getClientes().put(documento, puntos);

		}
		csvReader.close();
	}

	/**
	 * Se agrega un cliente al HashMap de POS
	 * 
	 * @param cedula
	 */
	public void crearCliente(String cedula)
	{
		this.pos.getClientes().put(cedula, 0);
	}

	/**
	 * 
	 * @param cedula
	 */
	public void inicarCompraCliente(String cedula)
	{
		this.compraActiva = new Compra(cedula);
		this.pos.updateUnidadesDuranteEjecucion();
	}

	/**
	 * 
	 * @param productoNombre
	 * @param cantidad
	 * @param peso
	 */
	public int agregarProducto(String productoNombre, Double cantidad, Double peso) // el producto debe estar en minúsculas
	{
		if (this.compraActiva != null)
		{	
			if (disponibilidadProducto(productoNombre) >= cantidad && disponibilidadProducto(productoNombre) != -1 && this.pos.getUnidadesDuranteEjecucion().get(productoNombre) >= cantidad)
			{
				this.compraActiva.agregarProducto(productoNombre, cantidad, peso);
				Integer cantidadActual = this.pos.getUnidadesDuranteEjecucion().get(productoNombre);
				this.pos.getUnidadesDuranteEjecucion().replace(productoNombre, (int) (cantidadActual - cantidad));
				return (1);
			} else
			{
				//El producto no existe o la cantidad de unidades no alcanza.
				return(2);
			}
		} else
		{
			//No se agregó ningún producto... Inicie una compra.
			return(3);
		}
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

		boolean existeLote = this.pos.inventario.getLotes().containsKey(nombreProducto);
		if (!existeLote)
		{
			cantidadTotal = -1;
		}

		else
		{
			ArrayList<Lote> lotesDelProducto = this.pos.inventario.getLotes().get(nombreProducto);

			for (Lote i : lotesDelProducto)
			{
				cantidadTotal += i.getCantidadUnidades();
			}
		}

		return cantidadTotal;
	}

	/**
	 * Método para finalizar la compra del cliente
	 * @return 
	 */
	public String finalizarCompra()
	{
		String resultado = compraActiva.getFactura(this.pos.inventario);
		if (compraActiva.cedula != null)
		{
			this.pos.agregarPuntosCliente(compraActiva.cedula, compraActiva.puntos);
		}
		this.compraActiva = null;
		this.pos.updateUnidadesDuranteEjecucion();
		return resultado;
	}

	/**
	 * Método para eliminar la compra del cliente activo.
	 */
	public void eliminarCompra()
	{
		this.compraActiva = null;
		System.out.println("\nSe eliminó la compra.\n");
	}

	/**
	 * Método para guardar toda la información en sus csv respectivos. Luego cierra la aplicación.
	 * 
	 * @throws IOException
	 */
	public void guardarYcerrar() throws IOException
	{
		this.pos.guardarInventario();
		this.pos.guardarClientes();
		this.pos.guardarGananciasYPerdidas();
	}

}
