package States;

import Algorithms.SortAlgorithm;

import javax.swing.*;

public class AlgCheckBox {
    private final SortAlgorithm algorithm;
    private final JCheckBox box;

    public AlgCheckBox(SortAlgorithm algorithm, JCheckBox box){
        this.algorithm = algorithm;
        this.box = box;
        this.box.setText(algorithm.getName());
    }

    public void deselect(){
        box.setSelected(false);
    }

    public boolean isSelected(){
        return box.isSelected();
    }

    public SortAlgorithm getAlgorithm() {
        return algorithm;
    }
}
