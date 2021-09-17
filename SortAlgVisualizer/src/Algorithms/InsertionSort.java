package Algorithms;

import Array.SortArray;

public class InsertionSort implements SortAlgorithm{
    private long stepDelay = 1;

    public void runSort(SortArray array){
        int n = array.arrayLength();
        for(int i = 1; i < n; i++){
            int key = array.getVal(i);
            int j = i-1;
            while(j >= 0 && array.getVal(j) > key){
                array.updateSingle(j + 1, array.getVal(j), 5, true);
                j--;
            }
            array.updateSingle(j+1, key, getDelay(), true);
        }
    }

    @Override
    public String getName(){
        return "Insertion Sort";
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
