public class Main {


    public static void main(String[] args) {


        SRFLP srflp = new SRFLP("QAP_sko56_04_n.txt");
        srflp.printInstance();
        TabuSearch tabuSearch = new TabuSearch(srflp);
        double[] resultados = new double[10];
        int iteracion = 0;

        while (iteracion < 10) {

            long start = System.currentTimeMillis();

            resultados[iteracion] = tabuSearch.invoke(srflp);

            long stop = System.currentTimeMillis() - start;

            System.out.printf("iteracion: %d\t tiempo: %d %n \n", iteracion, stop);

            iteracion ++;

        }

        double media = 0;
        double SD = calcularDesviacionEstandar(resultados);

        for(int i=0; i<resultados.length ; i++){
            System.out.println("Resultado obtenido en la iteracion numero: " + i + " de Tabu Search: " + resultados[i]);
            media+= resultados[i];
        }
        media = media /10;

        System.out.println("\nMedia obtenida de los resultados: " + media);
        System.out.println("Desviación estándar de los resultados: " + SD);

    }

    public static double calcularDesviacionEstandar(double[] array)
    {
        double sum = 0, desEstandar = 0;
        int length = array.length;

        for(double num : array) {
            sum += num;
        }

        double mean = sum/length;

        for(double num: array) {
            desEstandar += Math.pow(num - mean, 2);
        }

        return Math.sqrt(desEstandar/length);
    }
}