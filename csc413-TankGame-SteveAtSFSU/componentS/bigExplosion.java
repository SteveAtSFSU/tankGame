/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentS;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.Observer;
import javax.imageio.ImageIO;

/**
 *
 * @author sstev
 */
public class bigExplosion extends gameObject implements Observer {

    boolean show;
    int time;
    int img;

    BufferedImage[] images = new BufferedImage[7];

    public bigExplosion(Point location) {
        super(location);
        time = 0;
        img = 0;
        show = true;

        try {
            images[0] = ImageIO.read(new File("Explosion_large-0.png"));
            images[1] = ImageIO.read(new File("Explosion_large-1.png"));
            images[2] = ImageIO.read(new File("Explosion_large-2.png"));
            images[3] = ImageIO.read(new File("Explosion_large-3.png"));
            images[4] = ImageIO.read(new File("Explosion_large-4.png"));
            images[5] = ImageIO.read(new File("Explosion_large-5.png"));
            images[6] = ImageIO.read(new File("Explosion_large-6.png"));
        } catch (Exception e) {
            System.out.println("error loading in explosion images.");
        }
        
        gameEnvironment.gameMusic.playSound("Explosion_large.wav");
    }

    public void draw(Graphics graphs, ImageObserver eye) {
        //int w = image.getWidth(eye);
        //int h = image.getHeight(eye);
        if (this.show) {
            graphs.drawImage(image, location.x, location.y, eye);
        }
    }

    public void update() {
        time++;
        if (time % 5 == 0) {
            img++;
            if (img < 6) {
                super.setImg(images[img]);
                
            } else {
                this.show = false;
            }
        }

    }



}
