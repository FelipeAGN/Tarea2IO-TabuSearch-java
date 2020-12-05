import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TabuSearch {

    private final TabuList tabuList;

    int tamanoProblema;

    double currCost;
    double fitness;

    private Integer[] currSolution;
    private Integer[] bestSolution;

    public TabuSearch(SRFLP srflp){

        tamanoProblema = srflp.getN();
        tabuList = new TabuList(tamanoProblema);

        setupCurrentSolution(); // Se incializa el vector currSolution
        setupBestSolution();    // Se inicializa el vector bestSolution (copia de currSolution)
    }

    private void setupBestSolution() {
        bestSolution = new Integer[tamanoProblema];
        System.arraycopy(currSolution, 0, bestSolution, 0, bestSolution.length);
    }

   private void setupCurrentSolution() {
       currSolution = new Integer[tamanoProblema] ;
       for (int i = 0; i < tamanoProblema; i++){
           currSolution[i] = i;
       }

       List<Integer> list = Arrays.asList(currSolution);
       Collections.shuffle(list);                                  //Desordenamos el arreglo 'currSolution' para tener exploracion.
       System.out.println(list);
       System.out.println();
   }



    public double invoke(SRFLP srflp) {

       currCost = obtainValue(srflp,0,55);              // Valor de la funcion objetivo de la solucion actual (Vector currSolution)
       fitness = 0;                                                 // Valor de la funcion objetivo, cuando se aplica un cambio.
       /**
        * Este for determina cuantas veces ocurrira la búsqueda tabú.
        * como criterio de termino utilizamos hasta que i sea menor a (tamañoProblema * n), donde n es un valor determinado por el usuario
        * buffer1 y buffer2 son variables auxiliares para guardar j,k dado estas se utilizarán para realizar el swap.
       */
       for (int i = 0; i < tamanoProblema; i++) {

           for (int j = 0; j < currSolution.length; j++) {
               for (int k = 1; k < currSolution.length; k++) {

                   if (j != k) {
                       fitness = obtainValue(srflp,j,k);

                       //Como es un ejercicio de minimizacion, buscamos que el currCost sea menor
                       if ((currCost > fitness) && tabuList.esTabu(j,k)) {

                           swap(j,k);                // Actualiza la currSolution como una solucion mejor a la currSolution anterior

                           tabuList.tabuMove(j,k);   // Se condiciona para posteriores movimientos el que se hizo en la linea anterior.

                           System.arraycopy(currSolution, 0, bestSolution, 0, bestSolution.length); // Se encontró un cambio que mejora la solucion 'currSolution' por lo tanto se actualiza bestSolution.

                           currCost = fitness; //Se actualiza el currCost de la nueva solucion.

                       }else{
                            tabuList.decrementTabu();
                       }

                   }
               }
           }
       }

       System.out.println("La función objetivo más alta encontrada: " + currCost);
       System.out.println(Arrays.toString(bestSolution));
       return currCost;
    }

    /**
     *   Método para obtener el valor (Esfuerzo) que significa comprar en los puestos j y k
     */
    private double obtainValue(SRFLP srflp, int j, int k){
        double sumDistancias =0, sum=0;
        int i=j+1;


        while(i<k){                                                                                                     //Loop para obtener la distancia que hay entre los puestos j y k.
            sumDistancias+= srflp.getFacilitySize(i);
            i++;
        }
        sum= srflp.getWeight(j,k) * (srflp.getFacilitySize(j)/2 + sumDistancias + srflp.getFacilitySize(k)/2);          //Formula de esfuerzo
        return sum + srflp.getTotalDistance(currSolution);
    }

    /**
     * Método para realizar el swap de las posiciones en la solucion actual.
     * @param j Posicion del puesto de feria a cambiar
     * @param k Posicion del puesto de feria a cambiar
     */
    private void swap(int j, int k) {
        int temp = currSolution[j];
        currSolution[j] = currSolution[k];
        currSolution[k] = temp;
    }

}



