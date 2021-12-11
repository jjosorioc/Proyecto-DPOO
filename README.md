# Proyecto-DPOO

```diff
! IMPORTANTE LEER:
```

__Intrucciones de como usar nuestro Proyecto 2:__

Por Juan José Osorio (202021720)
Mariana Diaz (202020993)
y Thais Tamaio (2020222213)


```diff
- INFORMACIÓN ACERCA DEL PROYECTO 3:
```

Para los requerimientos adicionales del proyecto 3 se tiene lo siguiente:


__Cajero:__

__1.__ Para las promociones: La carga se realiza automáticamente al ejecutar el programa, el cual solo tendrá en cuenta las promociones vigentes.

__2.__ Para la promoción de regalo, se decidió que cada promoción de tipo regalo solo se puede aplicar una vez por compra. Es decir, se pueden aplicar diferentes promociones de tipo regalo, solo que en una sola ocasión cada una. Ejemplo: si hay una promoción de pague 1 lleve 2 manzanas, esta solo se aplicará para las primeras tres manzanas que lleve el cliente. No obstante, si existe otra promoción de pague 1 lleve 2 peras, esta si se podrá aplicar luego de la promoción de las manzanas. 

__3.__ En la factura se imprimirá el nombre de las promociones que fueron aplicadas y el precio total ya incluye las promociones aplicadas.

__Encargado inventario:__ 

__1.__ Para consultar el comportamiento de un producto, la información se carga desde un archivo csv (Estadísticas.csv) que contiene el producto y las unidades de ese producto para cada una de las fechas. En la ventana de encargado inventario se debe ingresar el producto y un rango de fechas en el formato (YYYY-MM-DD) y esto mostrará un gráfico de barras con las fechas en la parte inferior. Si se realiza una compra, se actualiza la información y se agrega una nueva fecha con las unidades ahora disponibles y si esa fecha ya existía, sencillamente se actualiza la cantidad y posteriormente se guarda en el csv (se debe oprimir guardar y cerrar). Esta opcións e ejecuta desde un nuevo botón en la interfaz de encargado inventario, el cuál se encuentra en la esquina inferior derecha.


```diff
- Para ejecutar la interfaz de encargado inventario:
```

__1.__ Para ejecutar la interfaz de __encargado inventario__ se debe acceder a la clase __"encargadoInventario.interfaz"__ y abrir y ejecutar el archivo __"VentanaPrincipalEncargado.java"__.

__2.__ Para cargar el lote, se abrirá una ventana en la que debe seleccionar el archivo. Para que estos cambios se vean reflejados en el archivo de inventario.csv se debe orpimir __guardar y cerrar__ después de cargar el lote. 

__3.__ Para cualquier opción de consultar, se le pedirá el nombre del producto, el cuál debe ingresar __con minúsculas__, luego se mostrará la información solicitada. 

__4.__  Para eliminar lotes vencidos debe orpimir el botón correspondiente y luego se debe oprimir __guardar y cerrar__ para que se vean reflejados los cambios en el archivo inventario.csv

```diff
- Para ejecutar la interfaz de cajero
```

__1.__  Para ejecutar la interfaz de __cajero__ se debe acceder a la clase __"cajeroPrincipal"__ y abrir y ejecutar el archivo __"VentanaPrincipal.java"__.

__2.__  Para agregar un cliente nuevo, oprima su respecitvo botón e ingrese los datos solicitados. Después el programa regresará a la ventana principal y luego se podrá iniciar una compra con el número de cédula del cliente recien agregado. 

__3.__  Para iniciar una compra, el programa le pedirá al usuario el número de cédula. Si este no está registrado el programa abrirá la ventana de agrega cliente. Si el cliente ya está registrado se iniciará una nueva compra y se pasará a la ventana donde se podrá modificar la compra. Si se desea iniciar una compra de un cliente no registrado, solo __se debe oprimir OK sin escribir nada en la ventana__ (para las compras de un cliente no registrado, no se agregarán los puntos al sistema POS).

__4.__  Después de iniciar una compra, para agregar los productos se debe ingresar el __código de barras__ del producto que desea comrpar el cliente. Luego dependiendo de si es empacado o no, se pedirá el peso o la cantidad que desea llevar el cliente. Se pueden agregar la cantidad de productos que se deseen, mientras haya disponibilidad en el inventario. 

__5.__  En caso de que se desee eliminar la compra, se debe oprimir el botón correspondiente. Luego el programa pasará a la ventana principal donde se puede iniciar una compra nueva. 

__6.__  Para finalizar una compra, oprimir el botón correspondiente y luego oprimir __guardar y cerrar__ para que se actualicen las ganancias y pérdidas, los puntos del cliente y el inventario. 
