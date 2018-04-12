/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentS;

import java.io.IOException;
import java.io.File;
import javax.sound.sampled.*;

/**
 *
 * @author sstev
 */
public class gameAudio {

    public static final gameAudio sounds = new gameAudio();

    public static void playLoop(String songName) {
        try {
            AudioInputStream in = AudioSystem.getAudioInputStream(new File(songName));
            Clip clip = AudioSystem.getClip();
            clip.open(in);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception m) {
            System.out.println("error loading music");
        }
    }

    public static void playSound(String songName) {
        try {
            Clip mp3 = AudioSystem.getClip();
            mp3.open(AudioSystem.getAudioInputStream(new File(songName)));
            mp3.loop(0);
        } catch (Exception m1) {
            System.out.println("error loading sound clip");
        }
    }
    


}
