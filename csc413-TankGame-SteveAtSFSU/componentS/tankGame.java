/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentS;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JPanel;
import java.awt.*;

import javax.swing.JFrame;


/**
 *
 * @author sstev
 */
public class tankGame {
    gameEnvironment game;
    Thread thread;
    
    public static void main(String args[]){
        final gameEnvironment game = gameEnvironment.getInstance();
        JFrame frame = new JFrame("Tank Wars");
        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowGainedFocus(WindowEvent e) {
                game.requestFocusInWindow();
            }
        });
        frame.getContentPane().add("Center", game);
        frame.pack();
        frame.setSize(new Dimension(1200, 700));
        game.setDimensions(1100, 600);
        game.init();
        
        
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        game.start();
    }
}
