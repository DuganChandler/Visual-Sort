package States;

import javax.swing.*;
import java.util.ArrayList;

public class Application {
    private final JFrame frame;

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    private final ArrayList<State> states;

    public Application() {
        states = new ArrayList<>();
        frame = new JFrame ("Sort Algorithm Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public State getCurrentState(){
        return states.get(states.size() -1);
    }

    public void pushState(State state){
        if(!states.isEmpty()){
            frame.remove(getCurrentState());
        }
        states.add(state);
        frame.setContentPane(state);
        frame.validate();
        state.onOpen();
    }

    public void popState(){
        if(!states.isEmpty()){
            State previous = getCurrentState();
            states.remove(previous);
            frame.remove(previous);
            if(!states.isEmpty()){
                State current = getCurrentState();
                frame.setContentPane(current);
                frame.validate();
                current.onOpen();
            }
            else{
                frame.dispose();
            }
        }
    }

    public void start(){
        pushState(new MainMenu(this));
        frame.pack();
    }

    public static void main(String... args){
        System.setProperty("sun.java2d.open1", "true");
        SwingUtilities.invokeLater(()->{
            new Application().start();
        });
    }
}
