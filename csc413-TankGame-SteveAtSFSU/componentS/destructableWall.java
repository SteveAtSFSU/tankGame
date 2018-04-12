/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentS;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.Observable;
import java.util.Observer;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author sstev
 */
public class destructableWall extends gameObject implements Observer {

    int w, h, next;
    Graphics g;
    public Point location;
    public Point speed;
    public int health;
    BufferedImage dWall;
    boolean live;

    BufferedImage[] smallExp = new BufferedImage[6];

    public destructableWall(Point loc, BufferedImage img, Point speed) {
        super(loc, img, new Point(0, 0), 25);
        this.health = 25;
        live = true;
        this.location = loc;

        this.box = new Rectangle(loc.x, loc.y, width, height);

        try {
            smallExp[0] = convertToBuffered(ImageIO.read(new File("Explosion_small-0.png")));
            smallExp[1] = convertToBuffered(ImageIO.read(new File("Explosion_small-1.png")));
            smallExp[2] = convertToBuffered(ImageIO.read(new File("Explosion_small-2.png")));
            smallExp[3] = convertToBuffered(ImageIO.read(new File("Explosion_small-3.png")));
            smallExp[4] = convertToBuffered(ImageIO.read(new File("Explosion_small-4.png")));
            smallExp[5] = convertToBuffered(ImageIO.read(new File("Explosion_small-5.png")));
        } catch (Exception e) {
            System.out.println("error loading in explosion images.");
        }

    }

    public void healthLoss(int dmag) {
        if (health < dmag) {
            live = false;
        }
        this.health -= dmag;
    }

    public void draw(Graphics graphs, ImageObserver eye) {
        //int w = image.getWidth(eye);
        //int h = image.getHeight(eye);
        if(live){
        graphs.drawImage(this.image, box.x, box.y, eye);
        }

    }

    public void draw(Graphics g, ImageObserver eye, boolean d) {
        if (!d) {
            for (int i = 0; i < smallExp.length; i++) {
                g.drawImage(smallExp[i], box.x, box.y, eye);
            }
        }
    }

    public void death() {
        live = false;
    }

    public BufferedImage convertToBuffered(BufferedImage imag) {
        int w3 = imag.getWidth();
        int h3 = imag.getHeight();

        BufferedImage buffImag = new BufferedImage(w3, h3, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffImag.createGraphics();
        //g2.setBackground(c);
        g2.drawImage(imag, null, 0, 0);
        //g2.clearRect(0, 0, w3, h3);
        g2.dispose();
        return buffImag;
    }

    public void draw(ImageObserver obs) {
        int tWidth = image.getWidth(obs);
        int tHeight = image.getHeight(obs);

        int numX = (int) (w / tWidth);
        int numY = (int) (h / tHeight);

        for (int i = -1; i <= numY; i++) {
            for (int j = 0; j <= numX; j++) {
                g.drawImage(image, i, j, obs);
            }
        }

    }
}
