package Sound;

import javax.sound.midi.*;
import java.util.ArrayList;

public class MidiSoundPlayer {
    private final ArrayList<Integer> keys;
    private Synthesizer synth;
    private final MidiChannel channel;

    private final int inputValueMaximum;
    private static int INDEX = -1;

    public MidiSoundPlayer(int maxVal){
        try{
            synth = MidiSystem.getSynthesizer();
            synth.open();
        } catch (MidiUnavailableException ex){
            ex.printStackTrace();
        }
        inputValueMaximum = maxVal;

        channel = synth.getChannels()[0];

        Instrument[] instruments = synth.getDefaultSoundbank().getInstruments();
        if(INDEX == -1){
            boolean found = false;
            int i;
            for(i = 0; i < instruments.length; i++){
                Instrument instrument = instruments[i];
                if(instrument.getName().equals("Honky Tonk Piano")) {
                    found = true;
                    break;
                }
            }
            if(!found){
                i = 2;
            }
            INDEX = i;
        }

        channel.programChange(instruments[INDEX].getPatch().getProgram());

        //Setting up Keys
        keys = new ArrayList<>();
        //this is for the start and end of the major key
        for(int i = 24; i < 108; i += 12){
            keys.add(i);
            keys.add(i + 2);
            keys.add(i + 4);
            keys.add(i + 5);
            keys.add(i + 7);
            keys.add(i + 9);
            keys.add(i + 11);
        }
    }

    private int convertToMajor(int val){
        float n = ((float)val / (float)inputValueMaximum);
        int index = (int)(n * (float)keys.size());
        index = Math.max(1, Math.min(107, index));
        return keys.get(index);
    }

    public void makeSound(int val){
        int note = convertToMajor(val);
        channel.noteOn(note, 25);
    }
}
