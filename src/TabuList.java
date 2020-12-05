public class TabuList {
    /**
     * Matriz tabú que almacena movimientos realizados
     */
    int [][] tabuList ;

    public TabuList(int numCities){
        tabuList = new int[numCities][numCities];
    }

    /**
     * Se condicionan aquellos movimientos realizados con los puestos j y k
     * Se suma 5 al campo (j,k) para que este no pueda volver a ser seleccionado como un swap
     */
    public void tabuMove(int j, int k){ // se agregan 5 a la operacion tabu
        tabuList[j][k]+= 5;
    }

    /**
     * Si en una iteracion no se determina que el movimiento (j,k) tiene un buen fitness, entonces se decrementan todos los tabú
     */
    public void decrementTabu(){        // tras una iteración se reduce en 1 la condicion tabú
        for(int i = 0; i<tabuList.length; i++){
            for(int j = 0; j<tabuList.length; j++){
                tabuList[i][j]-=tabuList[i][j]<=0?0:1;
            }
        }
    }

    /**
     * Determina si j,k está en tabú
     */
    public boolean esTabu(int j, int k){
        return tabuList[j][k] == 0;
    }

}