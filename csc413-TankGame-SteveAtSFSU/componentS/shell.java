/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentS;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Observer;
import java.util.Observable;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

/**
 *
 * @author sstev
 */
public class shell extends gameObject implements Observer {
    int dmg;

    public shell(Point location, BufferedImage img, Point speed, int dmg) {
        super(location, img, speed, dmg);
        this.dmg = dmg;
        this.box = new Rectangle(location.x, location.y, width, height);
    }

    public void update() {
        this.box.x += speed.x;
        this.box.y += speed.y;
        this.box.setBounds(this.box.x, this.box.y, width, height);

    }

    public void draw(Graphics g, ImageObserver obs) {
        g.drawImage(image, this.box.x, this.box.y, obs);
    }
    
   
    @Override
    public boolean collision(gameObject other) {
        if (this.box.getBounds().intersects(other.box.getBounds())) {
            return true;
        }
        return false;
    }

}
