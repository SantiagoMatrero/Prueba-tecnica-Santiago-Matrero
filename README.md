# Prueba-tecnica-Santiago-Matrero

* Como compilar y ejecutar el programa.
  
1. Abrir una terminal dentro de la carpeta donde se encuentra el archivo .java.
2. Compilar el archivo ejecutando la siguiente instruccion: "javac Main.java".
3. Ejecutar el programa con la siguiente instruccion: "java Main".


* Funcionalidades implementadas

  - Listar libros: muestra la lista de libros cargados ,indicando id, nombre, autor y copias disponibles.
  - Listar socios: muestra la lista de los socios registrados en el sistema, indicando id y nombre.
  - Registrar prestamo: solicita id del socio solicitante e id del libro solicitado, valida existencia y stock, y descuenta una unidad de las disponibles del libro.
  - Registrar devolucion: solicita id de la devolucion, valida existencia y estado, y modifica tanto unidad disponible del libro como estado de la devolucion.
  - Listar prestamos activos: muesta la lista de prestamos activos en el momento (los que no fueron devueltos), indicando socio, libro y fecha.
  - Manejo de errores: controla las entradas erroneas ya sea por ser no numericas, ids inexistentes o pedidos de libros sin stock. 
