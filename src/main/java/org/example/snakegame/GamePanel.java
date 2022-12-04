package org.example.snakegame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final  int SCREEN_WIDTH = 600;
    static final  int SCREEN_HEIGHT = 600;
    static final  int UNIT_SIZE = 20;
    static final  int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT/UNIT_SIZE);
    static final  int DELAY = 75;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];

    int bodySize = 6;
    int applesEaten;
    int appleX;
    int appleY;

    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }

    GamePanel(){
    random = new Random();
    this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

    this.setBackground(new Color(10, 10, 10));
    this.setFocusable(true);
    this.addKeyListener(new MyKeyAdapter());
    startGame();
    }


    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void newApple(){
        appleX = random.nextInt(SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;
    }

    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        draw(graphics);
    }

    public void draw(Graphics graphics){
        if(running){
           /* for (int i = 0; i < (SCREEN_HEIGHT/UNIT_SIZE); i++) {
                graphics.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                graphics.drawLine(0, i*UNIT_SIZE, SCREEN_HEIGHT, i*UNIT_SIZE);
            }*/
            graphics.setColor(new Color(158, 34,34));
            graphics.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodySize; i++) {
                if(i == 0){
                    graphics.setColor(new Color(21, 148, 24));
                    graphics.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                else {
                    graphics.setColor(new Color(76, 136, 23));
                    graphics.fillOval(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

                }
            }
            graphics.setColor(new Color(175, 10, 34));
            FontMetrics metrics = getFontMetrics(graphics.getFont());
            graphics.setFont(new Font("", Font.PLAIN, 20));
            graphics.drawString("SCORE: " + applesEaten, 10, 35);


        }else {
            gameOver(graphics);
        }

    }

    public void move(){
        for (int i = bodySize; i > 0 ; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction) {
            case 'U' -> y[0] = y[0] - UNIT_SIZE;
            case 'D' -> y[0] = y[0] + UNIT_SIZE;
            case 'L' -> x[0] = x[0] - UNIT_SIZE;
            case 'R' -> x[0] = x[0] + UNIT_SIZE;
        }
    }
    public void checkApple(){
        if((x[0] == appleX) && (y[0] == appleY)){
            bodySize++;
            applesEaten++;
            newApple();
        }
    }
    public void  checkCollision(){
        for (int i = bodySize; i > 0 ; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
                break;
            }
        }
        if((x[0] < 0) || (x[0] > SCREEN_WIDTH)){
            running = false;
        }

        if((y[0] < 0) || (y[0] > SCREEN_HEIGHT)){
            running = false;
        }
        if(!running){
            timer.stop();
        }

    }
    public void gameOver(Graphics graphics){
        graphics.setColor(new Color(183, 150, 27));
        graphics.setFont(new Font("", Font.PLAIN, 75));
        FontMetrics metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/4);
        graphics.setFont(new Font("", Font.ITALIC, 40));
        graphics.drawString("SCORE: " + applesEaten, SCREEN_WIDTH/3, SCREEN_HEIGHT/3);
    }


    public class MyKeyAdapter implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                   if(direction != 'R'){
                       direction = 'L';
                   }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
