package States;

import javax.swing.*;
import java.awt.*;

public abstract class State extends JPanel {
    protected Application app;

    public State(Application app){
        this.app = app;
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(WIDTH, HEIGHT);
    }

    public abstract void onOpen();
}
