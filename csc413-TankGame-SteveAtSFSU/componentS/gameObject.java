/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package componentS;

import java.awt.*;
import java.awt.image.*;
import java.util.Observable;
import java.util.Observer;
import java.awt.Point;
import java.awt.Rectangle;



/**
 *
 * @author sstev
 */
abstract public class gameObject implements Observer {

    public Point location;
    public Point speed;
    private int health;
    public Rectangle box;
    public int width;
    public int height;
    public BufferedImage image;
    ImageObserver observer;
    private boolean dead;
    public boolean show;
    public int angle;
    
    public gameObject(){
        
    }
    
    public gameObject(Point location){
        this.location = location;
        show = true;
    }

    //for tanks, solid walls, destructable walls
    public gameObject(Point loc, BufferedImage imag, Point speed, int life) {
        this.location = loc;
        this.speed = speed;
        this.image = imag;
        this.health = life;
        
        width = imag.getWidth(observer);
        height = imag.getHeight(observer);
        show = true;
        dead = false;
        this.box = new Rectangle(loc.x, loc.y, width, height);

    }
    
    public gameObject(Point loc, BufferedImage img){
        this(loc, img, new Point(0,0),10);
        show = true;
    }
    
    

    
   
    public void draw(Graphics graphs, ImageObserver eye) {
        //int w = imag.getWidth(eye);
        // int h = imag.getHeight(eye);
        if(show){
        graphs.drawImage(this.image, box.x, box.y, eye);
        }
    }
    
    public void setAngle(int newAngle){
        if(newAngle > 360){
            newAngle-=360;
        } else if(newAngle<0){
            newAngle+=360;
        }
        angle = newAngle;
    }

    public void setImg(BufferedImage imag) {
        this.image = imag;
        //this.height = imag.getHeight(observer);
        //this.width = imag.getWidth(observer);
    }

 
    @Override
    public void update(Observable object, Object arg){
        box.x += speed.x;
        box.y += speed.y;
            
    }
    
    public boolean collision(gameObject other){
        if(box.getBounds().intersects(other.box.getBounds())){
            return true;
        }
        return false;
    }
    
     
    public int getX() {
        return box.x;
    }
    
    public int getAngle(){
        return angle;
    }

    public int getY() {
        return box.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Point getSpeed() {
        return speed;
    }
    
    public Rectangle getRectangleLocation(){
        return new Rectangle(this.box);
    }

    public void setLocation(Point newLoc) {
        box.setLocation(newLoc);
    }

    public void move(int dx, int dy) {
        box.translate(speed.x, speed.y);
    }

    public void move() {
        box.translate(speed.x, speed.y);
    }

    public Point getLoc() {
        return new Point(box.x, box.y);
    }

    public void hide() {
        this.show = false;
    }

    public void show() {
        this.show = true;
    }

    public void isDead() {
        this.dead = true;
    }

    public void isAlive() {
        this.dead = false;
    }
}
