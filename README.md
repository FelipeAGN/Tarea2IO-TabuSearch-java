# Tarea2IO-TabuSearch-java

Para compilar y ejecutar el proyecto:

Se necesita: JDK 8+.

1. Clonar el repositorio
2. Añadir la carpeta a tu IDE.
3. Compilar.
4. Ejecutar Main.


Hint: Ya que nuestra implementación de Tabú Search tiene como condición de termino el número de iteraciones es necesario modificar el código fuente para obtener los resultados que se han ingresado en el informe:

Clase 'TabuSearch.java'
  Linea: 54 -> for (int i = 0; i < tamanoProblema; i++)
  Multiplicar el tamanoProblema por un n de 1 a 10 (Así obtuvimos los valores en las tablas del informe).
