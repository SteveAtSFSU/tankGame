/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentS;

import java.awt.event.*;
/**
 *
 * @author sstev
 */
public class controller extends KeyAdapter {
    public gameEvents gameEvent;
    
    public controller(){
        
    }
    
    public controller(gameEvents ge){
        this.gameEvent = ge;
    }
    
    @Override
    public void keyPressed(KeyEvent e){
        gameEvent.setValue(e);
    }
    
   /* @Override
    public void keyReleased(KeyEvent e){
        gameEvent.setValue(e);
    }
*/
}
