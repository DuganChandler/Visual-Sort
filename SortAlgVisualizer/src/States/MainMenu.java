package States;

import Algorithms.CountingSort;
import Algorithms.InsertionSort;
import Algorithms.SortAlgorithm;
import Algorithms.BubbleSort;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public final class MainMenu extends State{
    private static final Color BACKGROUND = Color.DARK_GRAY;
    private final ArrayList<AlgCheckBox> checkBoxes;

    public MainMenu(Application app){
        super(app);
        checkBoxes = new ArrayList<>();
        createGUI();
    }

    private void addCheckBox(SortAlgorithm algorithm, JPanel panel){
        JCheckBox box = new JCheckBox("", true);
        box.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.setBackground(BACKGROUND);
        box.setForeground(Color.WHITE);
        checkBoxes.add(new AlgCheckBox(algorithm, box));
        panel.add(box);
    }

    private void initPanel(JPanel p){
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        p.setBackground(BACKGROUND);
    }

    public void createGUI(){
        JPanel algPanel = new JPanel();
        JPanel optPanel = new JPanel();
        JPanel outPanel = new JPanel();
        initPanel(this);
        initPanel(optPanel);
        initPanel(algPanel);

        outPanel.setBackground(BACKGROUND);
        outPanel.setLayout(new BoxLayout(outPanel, BoxLayout.LINE_AXIS));

        try{
            ClassLoader loader = getClass().getClassLoader();
            BufferedImage image = ImageIO.read(new File(loader.getResource("pics/Sort.png").getFile()));
            JLabel label = new JLabel((new ImageIcon(image)));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(label);
        } catch (IOException e){
            System.out.println("Unable to load Image");
        }

        algPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        addCheckBox(new BubbleSort(), algPanel);
        addCheckBox(new InsertionSort(), algPanel);
        addCheckBox(new CountingSort(), algPanel);

        JCheckBox soundBox = new JCheckBox("Sound");
        soundBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        soundBox.setBackground(BACKGROUND);
        soundBox.setBackground(BACKGROUND);
        soundBox.setForeground(Color.WHITE);

        optPanel.add(soundBox);

        JButton startButton = new JButton("Begin Sort!");
        startButton.addActionListener((ActionEvent e) -> {
            ArrayList<SortAlgorithm> algorithms = new ArrayList<SortAlgorithm>();
            for(AlgCheckBox b : checkBoxes) {
                if(b.isSelected()){
                    algorithms.add(b.getAlgorithm());
                }
            }
            app.pushState(new SortState(algorithms,soundBox.isSelected(),app));
        });
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        outPanel.add(optPanel);
        outPanel.add(Box.createRigidArea(new Dimension(5,0)));
        outPanel.add(algPanel);

        int gap = 15;
        add(Box.createRigidArea(new Dimension(0, gap)));
        add(outPanel);
        add(Box.createRigidArea(new Dimension(0, gap)));
        add(startButton);

    }

    @Override
    public void onOpen(){
        checkBoxes.forEach((box) -> {
            box.deselect();
        });
    }

}
