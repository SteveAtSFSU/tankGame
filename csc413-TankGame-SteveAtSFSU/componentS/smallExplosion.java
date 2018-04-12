/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentS;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.Observer;
import javax.imageio.ImageIO;
import componentS.gameEnvironment;

/**
 *
 * @author sstev
 */
public class smallExplosion extends gameObject implements Observer {
    boolean show;
    int time;
    int img;

    BufferedImage[] images = new BufferedImage[7];

    public smallExplosion(Point location) {
        super(location);
        time = 0;
        img = 0;
        show = true;

        try {
            images[0] = ImageIO.read(new File("Explosion_Large-0.png"));
            images[1] = ImageIO.read(new File("Explosion_small-1.png"));
            images[2] = ImageIO.read(new File("Explosion_small-2.png"));
            images[3] = ImageIO.read(new File("Explosion_small-3.png"));
            images[4] = ImageIO.read(new File("Explosion_small-4.png"));
            images[5] = ImageIO.read(new File("Explosion_small-5.png"));
        } catch (Exception e) {
            System.out.println("error loading in explosion images.");
        }
        
        gameEnvironment.gameMusic.playSound("Explosion_small.wav");
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
            if (img < 6) {
                super.setImg(images[img]);
                img++;
            } else {
                this.show = false;
            }
        }

    }

}
