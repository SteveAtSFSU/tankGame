    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentS;

import java.awt.event.*;
import java.util.Observer;
import java.util.Observable;
/**
 *
 * @author sstev
 */
public class gameEvents extends Observable{
    public int type;
    final int keyE = 1;
    final int collision = 2;
    public Object event;
    public KeyAdapter key;
    
    public void setValue(KeyEvent e){
        type = keyE;
        this.event = e;
        setChanged();
        notifyObservers(this); 
    }
    
    public void setValue(String msg){
        type = collision;
        event = msg;
        
        setChanged();
        
        notifyObservers(this);
    }
    
    public int getType(){
        return this.type;
    }
    
    public void setType(int newType){
        this.type = newType;
    }
    
    public Object getEvent(){
        return this.event;
    }
    
    public KeyAdapter getKey(){
        return this.key;
    }
    
}
