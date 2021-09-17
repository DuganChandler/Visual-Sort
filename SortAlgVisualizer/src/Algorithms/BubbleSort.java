package Algorithms;

import Array.SortArray;

public class BubbleSort implements SortAlgorithm {
    private long stepDelay = 2;

    @Override
    public void runSort(SortArray array){
        int length = array.arrayLength();
        for(int i = 0; i < length - 1; i++){
            if(array.getVal(i) > array.getVal(i+1)){
                array.swap(i, i + 1, getDelay(), true);
            }
        }
        if (length - 1 > 1){
            runSort(array);
        }
    }



    @Override
    public String getName(){
        return "Bubble Sort";
    }

    @Override
    public long getDelay(){
        return  stepDelay;
    }

    @Override
    public void setDelay(long delay){
        this.stepDelay = delay;
    }
}
