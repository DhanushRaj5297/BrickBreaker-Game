/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycompany.brickbreaker;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author chinm
 */
//declaring gameplay class as child class of swing.Jpanel
public class GamePlay extends JPanel implements KeyListener, ActionListener {
    
    private boolean play = false;
    private int score = 0;
    private int totalbricks = 21;
    private Timer Timer;
    private int delay = 8;
    private int playerX = 310; //positioning slider in middle
    private int ballposX = 120; //positioning ball in middle
    private int ballposY = 350;
    private int ballXdir = -1; // So that when game starts, ball moves upward and towards left
    private int ballYdir = -2;
    private MapGenerator mapgen;

    public GamePlay() {
        mapgen = new MapGenerator(3, 7); // Initialising our MapGen class
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        Timer = new Timer(delay, this);
        Timer.start();
    }
    
     public void paint(Graphics g) {      //Will be using Graphics class to paint everything
        g.setColor(Color.darkGray);
        g.fillRect(1, 1, 692, 592); //painting inside Jframe Background 

        mapgen.draw((Graphics2D) g);             //Going to use Graphics2D class to draw slider,ball,score

         //Frame Borders
        g.setColor(Color.orange);             
        g.fillRect(0, 0, 3, 592);   //Left Border
        g.fillRect(0, 0, 692, 3);   //Upper Border
        g.fillRect(683, 0, 3, 592); //Right border

        //Score board Display
        g.setColor(Color.LIGHT_GRAY);            
        g.setFont(new Font("serif", Font.PLAIN, 23));
        g.drawString("Score: " + score, 580, 30);

        //Slider 
        g.setColor(Color.white);             
        g.fill3DRect(playerX+2, 502, 96, 5,false);//100 is width of slider, true for raised
        g.setColor(Color.black);             
        g.draw3DRect(playerX, 500, 100, 9,true);

        //Ball
        g.setColor(Color.green);
        g.drawOval(ballposX, ballposY, 19, 19);
        g.setColor(Color.yellow);
        g.fillOval(ballposX+1, ballposY+1, 17, 17);

        if (ballposY > 580) { // If ball falls down beyond 580 on y axis
            play = false;
            ballXdir = 0;      
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("    Game Over Score: " + score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("   Press Enter to Restart", 190, 340);
            
            if(totalbricks<10){
                g.setColor(Color.blue);
                g.setFont(new Font("serif", Font.PLAIN, 40));
                g.drawString("     Close!", 190, 250);
            }
        }
        if(totalbricks == 0){ // If game Won
            play = false;
            ballYdir = 0; 
            ballXdir = 0;
            g.setColor(Color.blue);
            g.setFont(new Font("serif",Font.BOLD,40));
            g.drawString("    You Won! ",190,300);

            g.setFont(new Font("serif", Font.ITALIC, 26));
            g.drawString("   Press Enter to Restart", 190, 390);


        }

        g.dispose();   //No use for graphics anymore?


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Timer.start();

        if (play) { 
            if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 500, 100, 9))) {
                ballYdir = -ballYdir;   //IF Ball hits Slider.. change Y direction,let X continue
            }

            BrickForming:
            for (int i = 0; i < mapgen.map.length; i++) {
                for (int j = 0; j < mapgen.map[0].length; j++) {
                    if (mapgen.map[i][j] > 0) { //if value of bricks set to 0, those wont be formed
                        int brickX = j * mapgen.bricksWidth + 80;// To position the bricks
                        int brickY = i * mapgen.bricksHeight + 50;
                        int bricksWidth = mapgen.bricksWidth;
                        int bricksHeight = mapgen.bricksHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, bricksWidth, bricksHeight);
                        Rectangle ballrect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickrect = rect;

                        //If Ball hits Brick...
                        if (ballrect.intersects(brickrect)) {  
                            mapgen.setBricksValue(0, i, j); //sets value of brick to 0 in matrix[i][j]
                            totalbricks--;      //decreases counter of totalbricks
                            score += 5;
                            
                            //if Balls hits left || Right of bricks..ball width is 19
                            if (ballposX + 19 <= brickrect.x || ballposX + 1 >= brickrect.x + bricksWidth) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                            break BrickForming;// Breaks back to rebuild bricks all over in matrix for loops
                        }
                    }


                }
            }


            ballposX += ballXdir;        //ball keeps moving according to direction
            ballposY += ballYdir;
            
            if (ballposX < 0) {             //If ball hits Left border
                ballXdir = -ballXdir;       
            }
            if (ballposY < 0) {             //If ball hits Upper ceiling border
                ballYdir = -ballYdir;
            }
            if (ballposX > 670) {           //If ball hits Right Border
                ballXdir = -ballXdir;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

       }


    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
       
           if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
                if (playerX >= 590) {playerX = 0;}     // If Slider hits Right Border
                else { moveRight();}
            }
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            if (playerX < 20) {playerX = 590;} // If Slider hits Left Border
            else { moveLeft();}
        }
  

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                score = 0;
                playerX = 310;
                totalbricks = 21;
                mapgen = new MapGenerator(3, 7);

                repaint();
            }
        }


        }

        public void moveRight()
        {
            play = true;
            playerX += 40;
        }
        public void moveLeft()
        {
            play = true;
            playerX -= 40;
        }
        
    
    
}