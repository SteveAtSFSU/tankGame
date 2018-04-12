/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentS;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.Observer;
import java.util.Observable;
import javax.imageio.ImageIO;

/**
 *
 * @author sstev
 */
public class Player extends gameObject implements Observer {

    public int life, playerID;
    public int health, dmg, bulletDmg;
    public int lifeCount;
    public boolean death;
    final Point spawnS;
    public Point tankMid;
    public int tankMidX, tankMidY;
    boolean shot;
    shell[] bullets = new shell[150];
    int shots = 0;
    Graphics2D bulg;
    ImageObserver obs;
    int right, left, up, down, fire, respawn;
    BufferedImage def;
    BufferedImage tankImages[] = new BufferedImage[8];
    BufferedImage shellImages[] = new BufferedImage[8];

    private Image[] healthBar, healthImg;
    private gameObject lifeBar;

    public Player(BufferedImage img, int hp, int lives, int id, int damage, Point speed, Point spawn, int right, int left, int up, int down, int fire, int respawn) {
        super(spawn, img, speed, hp);
        this.image = img;
        health = hp;
        this.dmg = damage;
        this.bulletDmg = 25;
        this.right = right;
        this.left = left;
        this.up = up;
        this.down = down;
        this.fire = fire;
        this.respawn = respawn;

        this.playerID = id;
        width = img.getWidth(obs);
        height = img.getHeight(obs);
        tankMidX = box.x + (width / 2);
        tankMidY = box.y + (height / 2);
        death = false;
        spawnS = spawn;
        def = img;
        tankMid = new Point(tankMidX, tankMidY);

        //this.healthBar = new Image[4];
        lifeCount = lives;

        try {
            tankImages[0] = ImageIO.read(new File("Tank1-R.png"));
            tankImages[1] = ImageIO.read(new File("Tank1-L.png"));
            tankImages[2] = ImageIO.read(new File("Tank1-D.png"));
            tankImages[3] = ImageIO.read(new File("Tank1-U.png"));
            tankImages[4] = ImageIO.read(new File("Tank1-DD.png"));
            tankImages[5] = ImageIO.read(new File("Tank1-UR.png"));
            tankImages[6] = ImageIO.read(new File("Tank1-LU.png"));
            tankImages[7] = ImageIO.read(new File("Tank1-DL.png"));
            shellImages[0] = ImageIO.read(new File("Shell-R.png"));
            shellImages[1] = ImageIO.read(new File("Shell-L.png"));
            shellImages[2] = ImageIO.read(new File("Shell-D.png"));
            shellImages[3] = ImageIO.read(new File("Shell-U.png"));
            shellImages[4] = ImageIO.read(new File("Shell-LU.png"));
            shellImages[5] = ImageIO.read(new File("Shell-UR.png"));
            shellImages[6] = ImageIO.read(new File("Shell-DL.png"));
            shellImages[7] = ImageIO.read(new File("Shell-DR.png"));
        } catch (Exception e) {
            System.out.println("error loading images");
        }

    }

    public int getDmg() {
        return this.dmg;
    }

    public void healthLoss(int dmag) {
        if (health < 1) {
            System.out.print("healthLoss before isDead");
            isDead();
        } else {
            System.out.print("else statement");
            health -= dmag;
        }
    }

    public void isDead() {
        if (lifeCount > 1) {
            lifeCount--;
            health = 100;

            box.setLocation(spawnS.x, spawnS.y);
        } else {
            death = true;

        }
    }

    public void draw(Graphics graphs, ImageObserver eye) {
        //int w = image.getWidth(eye);
        //int h = image.getHeight(eye);
        if (!death) {
            graphs.drawImage(this.image, box.x, box.y, eye);
        }
    }

    public void setImg(BufferedImage imag) {
        image = imag;
        //this.height = imag.getHeight(observer);
        //this.width = imag.getWidth(observer);
    }
    
    public void setLocation(Point newLoc){
        box.setLocation(newLoc);
    }

    /*
    public shell fire() {
        switch (getAngle()) {
            case 0: {
                return new shell(tankMid, shellImages[0], new Point(10, 0), 25);

                //break;
            }
            case 90: {
                return new shell(tankMid, shellImages[3], new Point(0, -10), 25);
                //break;

            }
            case 180: {
                return new shell(tankMid, shellImages[1], new Point(-10, 0), 25);
                //break;

            }
            case 270: {
                return new shell(tankMid, shellImages[2], new Point(0, 10), 25);
                //break;

            }
            default:
                break;
        }
        return new shell(tankMid, shellImages[2], new Point(0, 10), 25);
    }
     */
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

    public void update(Observable object, Object arg) {
        gameEvents ge = (gameEvents) arg;

        if (ge.type == 1) {
            KeyEvent e = (KeyEvent) ge.event;
            int keyCode = e.getKeyCode();

            if (keyCode == right) {
                if (box.x < 1110) {
                    box.x += speed.x;
                    location.x += speed.x;
                    super.setImg(tankImages[0]);
                    super.setAngle(0);

                }
            } else if (keyCode == left) {
                if (box.x > 30) {
                    box.x -= speed.x;
                    location.x -= speed.x;
                    super.setImg(tankImages[1]);
                    super.setAngle(180);
                }
            } else if (keyCode == up) {
                if (box.y > 30) {
                    box.y -= speed.y;
                    location.y += speed.y;
                    super.setImg(tankImages[3]);
                    super.setAngle(90);
                }
            } else if (keyCode == down) {
                if (box.y < 590) {
                    box.y += speed.y;
                    location.y += speed.y;
                    super.setImg(tankImages[2]);
                    super.setAngle(270);
                }
            } else if (keyCode == fire) {
                //bullets[shots++] = fire();
                switch (getAngle()) {
                    case 0: {
                        bullets[shots++] = new shell(box.getLocation(), shellImages[0], new Point(5, 0), 25);

                        break;

                    }
                    case 90: {
                        bullets[shots++] = new shell(box.getLocation(), shellImages[3], new Point(0, -5), 25);
                        break;

                    }
                    case 180: {
                        bullets[shots++] = new shell(box.getLocation(), shellImages[1], new Point(-5, 0), 25);
                        break;

                    }
                    case 270: {
                        bullets[shots++] = new shell(box.getLocation(), shellImages[2], new Point(0, 5), 25);
                        break;

                    }
                }
            } else if (keyCode == respawn) {
                
                death = false;
            }
        }
    }

    public int getPlayer() {
        return this.playerID;
    }

}
