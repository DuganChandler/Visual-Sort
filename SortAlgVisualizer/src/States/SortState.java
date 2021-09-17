package States;

import Algorithms.SortAlgorithm;
import Array.SortArray;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

public final class SortState extends State{
    private final SortArray sortArray;
    private final ArrayList<SortAlgorithm> sortQueue;

    public SortState(ArrayList<SortAlgorithm> algorithms, boolean playSounds, Application app){
        super(app);
        setLayout(new BorderLayout());
        sortArray = new SortArray(playSounds);
        add(sortArray, BorderLayout.CENTER);
        sortQueue = algorithms;
    }

    private void longSleep(){
        try{
            Thread.sleep(1000);
        } catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }

    private void shuffleAndWait(){
        sortArray.shuffle();
        sortArray.resetColors();
        longSleep();
    }

    public void onOpen(){
        SwingWorker<Void, Void> swingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try{
                    Thread.sleep(250);
                } catch (InterruptedException ex){
                    ex.printStackTrace();
                }
                for (SortAlgorithm algorithm : sortQueue){
                    shuffleAndWait();

                    sortArray.setName(algorithm.getName());
                    sortArray.setAlgorithm(algorithm);

                    algorithm.runSort(sortArray);
                    sortArray.resetColors();
                    sortArray.highlight();
                    sortArray.resetColors();
                    longSleep();
                }
                return null;
            }

            @Override
            public void done(){
                app.popState();
            }
        };

        swingWorker.execute();
    }
}
