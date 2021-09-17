package Algorithms;

import Array.SortArray;

public class CountingSort implements SortAlgorithm {

    private long stepDelay = 5;

    public void runSort(SortArray array){
        int[] result = new int[array.arrayLength()];
        int[] count = new int[array.getMaxVal()+1];
        for(int i = 0; i < result.length; i++){
            array.updateSingle(i, result[i] = array.getVal(i), getDelay(), false);
            count[array.getVal(i)]++;
        }
        for(int i = 1; i < count.length; i++){
            count[i] += count[i-1];
        }
        for(int i = result.length-1; i > -1; i--){
            array.updateSingle(count[result[i]]--, result[i], getDelay(), true);
        }
    }


    @Override
    public String getName(){
        return "Counting Sort";
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
