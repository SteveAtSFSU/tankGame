/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentS;

import java.util.Observable;

/**
 *
 * @author sstev
 */
public class gameClock extends Observable {
    private int startTime;
    private int frame;
    
    public gameClock(){
        startTime = (int) System.currentTimeMillis();
        frame = 0;
    }
    
    public void tick(){
        frame++;
        setChanged();
        this.notifyObservers();
    }
    
    public int getFrame(){
        return this.frame;
    }
    
    public int getTime(){
        return (int)System.currentTimeMillis()-startTime;
    }
}
