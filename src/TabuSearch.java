import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TabuSearch {

    private final TabuList tabuList;

    int cantIteraciones;
    int tamanoProblema;

    private Integer[] currSolution;
    private Integer[] bestSolution;

    public TabuSearch(SRFLP srflp){

        tamanoProblema = srflp.getN();
        cantIteraciones = tamanoProblema;
        tabuList = new TabuList(tamanoProblema);

        setupCurrentSolution();
        setupBestSolution();
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
       /**
        En las siguientes 3 lineas, desordenamos el arreglo 'currSolution' para tener exploracion.
        */
        List<Integer> list = Arrays.asList(currSolution);
        Collections.shuffle(list);
        System.out.println(list);
        System.out.println();
   }

   private double obtainValue(SRFLP srflp,int i, int j){
        double sumdistances =0;
        double sum=0;
        int k=i+1;
        while(k<j){
            sumdistances+= srflp.getFacilitySize(k);
            k++;
        }
        sum+= srflp.getWeight(i,j) * (srflp.getFacilitySize(i)/2 + sumdistances + srflp.getFacilitySize(j)/2);
        return sum + srflp.getTotalDistance(currSolution);
   }

    public double invoke(SRFLP srflp) {
        double currCost = srflp.getTotalDistance(currSolution);
        double bestCost = currCost;
        for (int i = 0; i < cantIteraciones*10; i++) {
            int buffer1 = 0;
            int buffer2 = 0;

            for (int j = 0; j < currSolution.length; j++) {
                for (int k = 1; k < currSolution.length; k++) {
                    if (j != k) {
                        bestCost = obtainValue(srflp,j,k);

                        if ((currCost < bestCost) && tabuList.tabuList[j][k] == 0) {
                            buffer1 = j;
                            buffer2 = k;
                            swap(j,k);
                            System.arraycopy(currSolution, 0, bestSolution, 0, bestSolution.length);
                            currCost = bestCost;
                        }
                    }
                }
            }
            if (buffer1 != 0) {
                tabuList.decrementTabu();
                tabuList.tabuMove(buffer1, buffer2);
            }
        }

        System.out.println("La función objetivo más alta encontrada: " + currCost);
        System.out.println(Arrays.toString(bestSolution));
        return currCost;
    }

    private void swap(int i, int k) {
        int temp = currSolution[i];
        currSolution[i] = currSolution[k];
        currSolution[k] = temp;
    }

}



