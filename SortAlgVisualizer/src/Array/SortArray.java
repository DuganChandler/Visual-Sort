package Array;

import Algorithms.SortAlgorithm;
import Sound.MidiSoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

import static java.util.Arrays.stream;

public class SortArray extends JPanel {
    public static final int PANEL_WIDTH = 1280;
    public static final int PANEL_HEIGHT = 720;
    private static final int BAR_WIDTH = 5;

    private static final double BAR_HEIGHT_PERCENT = 512.0/720.0;
    private static final int NUM_BARS = PANEL_WIDTH / BAR_WIDTH;

    private final int[] array;
    private final int[] barColors;
    private int spinVal = 0;
    private String algName = "";
    private SortAlgorithm algorithm;
    private long algDelay = 0;

    private MidiSoundPlayer player;
    private JSpinner spin;
    private boolean playSound;

    private int arrayChanges = 0; //The number of changes to the array the alg has done

    public SortArray(boolean playSound){
        setBackground(Color.DARK_GRAY);
        array = new int[NUM_BARS];
        barColors = new int[NUM_BARS];
        for (int i = 0; i < NUM_BARS; i++){
            array[i] = i;
            barColors[i] = 0;
        }
        player = new MidiSoundPlayer(NUM_BARS);
        this.playSound = playSound;
        spin = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
        spin.addChangeListener((event) -> {
            algDelay = (Integer) spin.getValue();
            algorithm.setDelay((algDelay));
        });
        add(spin,BorderLayout.LINE_START);
    }

    public int arrayLength() {
        return array.length;
    }

    public int getVal(int index){
        return array[index];
    }

    public int getMaxVal(){
        return Arrays.stream(array).max().orElse(Integer.MIN_VALUE);
    }

    private void finalUpdate(int val, long milliSecDelay, boolean isStep){
        repaint();
        try{
            Thread.sleep(milliSecDelay);
        } catch (InterruptedException ex){
            Thread.currentThread().interrupt();
        }
        if(playSound) {
            player.makeSound(val);
        }
        if(isStep){
            arrayChanges++;
        }
    }

    public void swap(int first, int second, long milliSecDelay, Boolean isStep){
        int temp = array[first];
        array[first] = array[second];
        array[second] = temp;

        barColors[first] = 100;
        barColors[second] = 100;

        finalUpdate((array[first] + array[second]) / 2, milliSecDelay, isStep);
    }

    public void updateSingle(int index, int val, long milliSecDelay, boolean isStep){
        array[index] = val;
        barColors[index] = 100;

        finalUpdate(val, milliSecDelay, isStep);
        repaint();
    }

    public void shuffle(){
        arrayChanges = 0;
        Random rng = new Random();
        for(int i = 0; i < arrayLength(); i++){
            int swapWithIndex = rng.nextInt(arrayLength() - 1);
            swap(i, swapWithIndex, 5, false);
        }
        arrayChanges = 0;
    }

    public void highlight(){
        for(int i = 0; i < arrayLength(); i++){
            updateSingle(i, getVal(i), 5, false);
        }
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(PANEL_WIDTH, PANEL_HEIGHT);
    }

    public void resetColors(){
        for(int i = 0; i < NUM_BARS; i++){
            barColors[i] = 0;
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g.create();

            try {
                Map<RenderingHints.Key, Object> renderingHints = new HashMap<>();
                renderingHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.addRenderingHints(renderingHints);
                graphics.setColor(Color.WHITE);
                graphics.setFont(new Font("Monospaced", Font.BOLD, 20));
                graphics.drawString(" Current algorithm: " + algName, 10, 30);
                graphics.drawString("Current step delay: " + algDelay + "ms", 10, 55);
                graphics.drawString("     Array Changes: " + arrayChanges, 10, 80);

                drawBars(graphics);
            } finally {
                graphics.dispose();
            }
    }

    private void drawBars(Graphics2D graphics){
        int barWidth = getWidth() / NUM_BARS;
        int bfImageWidth = barWidth * NUM_BARS;
        int bfImageHeight = getHeight();

        if(bfImageHeight > 0 && bfImageWidth > 0){
            if(bfImageWidth < 256) {
                bfImageWidth = 256;
            }

            double maxVal = getMaxVal();

            BufferedImage bfImage = new BufferedImage(bfImageWidth, bfImageHeight, BufferedImage.TYPE_INT_ARGB);
            makeBufferedImageTransparent(bfImage);
            Graphics2D bufferedGraphics = null;

            try{
                bufferedGraphics = bfImage.createGraphics();

                for(int i = 0; i < NUM_BARS; i++){
                    double currentVal = getVal(i);
                    double percentMax = currentVal / maxVal;
                    double heightPercentPanel = percentMax * BAR_HEIGHT_PERCENT;
                    int height = (int) (heightPercentPanel * (double) getHeight());
                    int xBegin = i + (barWidth - 1) * i;
                    int yBegin = getHeight() - height;

                    int val = barColors[i] * 2;
                    if(val > 190){
                        bufferedGraphics.setColor(new Color(255 - val, 255, 255 - val));
                    } else {
                        bufferedGraphics.setColor(new Color(255, 255 - val, 255 - val));
                    }
                    bufferedGraphics.fillRect(xBegin, yBegin, barWidth, height);
                    if(barColors[i] > 0){
                        barColors[i] -= 5;
                    }
                }
            } finally {
                if(bufferedGraphics != null){
                    bufferedGraphics.dispose();
                }
            }
            graphics.drawImage(bfImage, 0, 0, getWidth(), getHeight(),
                           0, 0, bfImage.getWidth(), bfImage.getHeight(), null);
        }
    }

    private void makeBufferedImageTransparent(BufferedImage image){
        Graphics2D graphics = null;
            try{
                graphics = image.createGraphics();

                graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
                graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
                graphics.setComposite((AlphaComposite.getInstance(AlphaComposite.SRC_OVER)));
            } finally {
                if(graphics != null){
                    graphics.dispose();
                }
            }
    }

    @Override
    public void setName(String algName){
        this.algName = algName;
    }

    public void setAlgorithm(SortAlgorithm algorithm){
        this.algorithm = algorithm;
        algDelay = algorithm.getDelay();
        spin.setValue((int) algorithm.getDelay());
    }

    public long getAlgDelay(){
        return algDelay;
    }
}
