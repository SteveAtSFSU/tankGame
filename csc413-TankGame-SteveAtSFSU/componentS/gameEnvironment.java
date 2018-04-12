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
import javax.imageio.*;
import java.awt.event.KeyEvent;
import java.io.File;
import javax.swing.*;
import java.util.ArrayList;

/**
 *
 * @author sstev
 */
public final class gameEnvironment extends JPanel implements Runnable, Observer {

    private Thread thread;
    

    private static final gameEnvironment tankGame = new gameEnvironment();
    public static final gameAudio gameMusic = new gameAudio();
    public static final gameClock clock = new gameClock();

    public BufferedImage buffImag, background, wall, tankR, tankL, dWall;

    Image minMap;

    Graphics g;

    int mapWidth = 1200;
    int mapHeight = 700;

    int tankLimit = 0;

    ImageObserver obs;
    public Player player1;
    public Player player2;
    gameEvents play1, play2;
    controller p1, p2;
    //destructableWall[] dWalls = new destructableWall[20];
    ArrayList<destructableWall> dWalls = new ArrayList(20);
    ArrayList<smallExplosion> wallExp = new ArrayList(20);
    ArrayList<bigExplosion> tankDeaths = new ArrayList(10);

    Point spawn1, spawn2;

    boolean gameVerdict, gg;

    public gameEnvironment() {
        this.setFocusable(true);
        
        // backgroundL = new ArrayList<>();
        // solidWallsL = new ArrayList<>();
        //sprites = new HashMap<>();
        //bSprites = new HashMap<>();
    }

    public static gameEnvironment getInstance() {
        return tankGame;
    }

    public void init() {
        this.setFocusable(true);
        setBackground(Color.white);
        //loadSprites();
        obs = this;
        gameMusic.playLoop("Music.wav");
        try {
            dWall = ImageIO.read(new File("Wall1.gif"));
            background = ImageIO.read(new File("Background.png"));
            wall = ImageIO.read(new File("Wall2.png"));
            tankR = convertToBuffered(ImageIO.read(new File("Tank1-R.png")));
            tankL = convertToBuffered(ImageIO.read(new File("Tank1-L.png")));
        } catch (Exception e) {
            System.out.println("error loading images");
        }
        //drawBackground(g2);
        //Player(Image img, int hp, int lives, int id, int damage, Point speed, Point spawn, int right, int left, int up, int down, int fire)
        createDwalls();
        spawn1 = new Point(1050, 350);
        spawn2 = new Point(50, 350);
        player2 = new Player(tankR, 100, 5, 1, 25, new Point(15, 15), spawn2, KeyEvent.VK_D, KeyEvent.VK_A, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_SPACE, KeyEvent.VK_1);
        player1 = new Player(tankL, 100, 5, 1, 25, new Point(15, 15), spawn1, KeyEvent.VK_RIGHT, KeyEvent.VK_LEFT, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_ENTER, KeyEvent.VK_2);
        player1.setAngle(180);
        player2.setAngle(0);
        //drawTank(tank, g2);
        play1 = new gameEvents();
        play2 = new gameEvents();
        play1.addObserver(player1);
        play2.addObserver(player2);
        p1 = new controller(play1);
        p2 = new controller(play2);
        addKeyListener(p1);
        addKeyListener(p2);

        //ge2 = new gameEvents();
        //ge2.addObserver(tank2);
        //ctrl key2 = new ctrl(ge2);
        //addKeyListener(key2);
        gameVerdict = false;

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

    public void setDimensions(int w, int h) {
        this.mapWidth = w;
        this.mapHeight = h;
    }

    public void createDwalls() {
        int Dx = 550;
        int Dy = 40;
        for (int i = 0; i < 19; i++) {
            dWalls.add(new destructableWall(new Point(Dx, Dy), dWall, new Point(0, 0)));
            Dy += 30;

        }
    }

    public void drawBackground(Graphics g) {
        int tWidth = background.getWidth(this);
        int tHeight = background.getHeight(this);

        int numberX = (int) (mapWidth / tWidth);
        int numberY = (int) (mapHeight / tHeight);
        for (int i = -1; i <= numberY; i++) {
            for (int j = 0; j <= numberX; j++) {
                // g2.drawImage(background, j * tWidth, i * tHeight, tWidth, tHeight, this);
                g.drawImage(background, j * tWidth, i * tHeight, tWidth, tHeight, this);
            }
        }

        for (int i = 0; i <= mapHeight + 320; i += wall.getHeight(this)) {
            g.drawImage(wall, 0, i, obs);
        }
        for (int i = 0; i <= mapHeight + 320; i += wall.getHeight(this)) {
            g.drawImage(wall, 1170, i, obs);
        }
        for (int i = 0; i <= mapWidth + 180; i += wall.getWidth(this)) {
            g.drawImage(wall, i, 0, obs);
        }
        for (int i = 0; i <= mapWidth + 180; i += wall.getWidth(this)) {
            g.drawImage(wall, i, 650, obs);
        }

    }

    //check if shells hit dWall
    public void checkCollisionsBullets(gameObject shells) {

        for (int w = 0; w < dWalls.size(); w++) {
            if (shells.collision(dWalls.get(w))) {
                if (dWalls.get(w).live) {
                    wallExp.add(new smallExplosion(dWalls.get(w).location));
                }
                dWalls.get(w).death();
            }
        }

    }

    //check if tanks collided
    public void checkCollisionsTanks1(Player tank) {

        if (tank.collision(player2)) {
            player2.death = true;
            player1.setImg(tankL);
            tankDeaths.add(new bigExplosion(player2.box.getLocation()));
            player2.setLocation(new Point(50, 350));

            player1.death = true;
            player2.setImg(tankR);
            tankDeaths.add(new bigExplosion(player1.box.getLocation()));
            player1.setLocation(new Point(1050, 350));

            tankLimit += 2;
        }

    }

    //check if shell hit tank
    public void checkCollisionsTank1(shell shells) {

        if (shells.collision(player1)) {
            player1.death = true;
            player1.setImg(tankL);
            tankDeaths.add(new bigExplosion(player1.box.getLocation()));
            player1.setLocation(new Point(1050, 350));
        }

    }

    //check if shell hit tank
    public void checkCollisionsTank2(shell shells) {

        if (shells.collision(player2)) {
            player2.death = true;
            player2.setImg(tankR);
            tankDeaths.add(new bigExplosion(player2.box.getLocation()));
            player2.setLocation(new Point(50, 350));

        }

    }

    public Graphics2D createGraphics(int w, int h) {
        Graphics2D g2 = null;
        if (buffImag == null || buffImag.getWidth() != w || buffImag.getHeight() != h) {
            buffImag = (BufferedImage) createImage(w, h);
        }

        g2 = buffImag.createGraphics();
        g2.setBackground(getBackground());
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.clearRect(0, 0, w, h);
        return g2;
    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        Dimension windowSize = getSize();
        Graphics2D g2 = createGraphics(windowSize.width, windowSize.height);
        //drawGameFrame(windowSize.width, windowSize.height, g2);
        drawBackground(g);

        //g.drawImage(player1.image, player1.box.x, player1.box.y, obs);
        //g.drawImage(player2.image, player2.box.x, player2.box.y, obs);
        player1.draw(g, obs);
        player2.draw(g, obs);

        for (int i = 0; i < player1.shots; i++) {
            player1.bullets[i].draw(g, obs);
            player1.bullets[i].update();
            checkCollisionsBullets(player1.bullets[i]);
            checkCollisionsTank2(player1.bullets[i]);

        }
        for (int i = 0; i < player2.shots; i++) {
            player2.bullets[i].draw(g, obs);
            player2.bullets[i].update();
            checkCollisionsBullets(player2.bullets[i]);
            checkCollisionsTank1(player2.bullets[i]);
        }

        for (int i = 0; i < dWalls.size(); i++) {
            dWalls.get(i).draw(g, obs);
        }

        for (int i = 0; i < wallExp.size(); i++) {
            wallExp.get(i).draw(g, obs);
            wallExp.get(i).update();
        }
        
        for (int i = 0; i < tankDeaths.size(); i++) {
            tankDeaths.get(i).draw(g, obs);
            tankDeaths.get(i).update();
        }

        checkCollisionsTanks1(player1);

    }

    public void start() {
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

    @Override
    public void run() {
        Thread game = Thread.currentThread();
        while (thread == game) {
            this.requestFocusInWindow();

            repaint();

            try {
                thread.sleep(2);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
