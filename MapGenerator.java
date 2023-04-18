package com.mycompany.brickbreaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author chinm
 */
public class MapGenerator {     // MapGenerator CLASS defined here to draw bricks
    public int map[][];         // A 2D Array to store [row][col] of bricks
    public int bricksWidth;
    public int bricksHeight;
    public MapGenerator(int row , int col){ //Constructor for MapGen class
        map = new int[row][col];
         for (int[] map1 : map) {
             for (int j = 0; j < map[0].length; j++) {
                 map1[j] = 1;
             }
         }
        bricksWidth = 540/col;
        bricksHeight = 120/row;
    }
    public void draw(Graphics2D g) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    
                    g.setColor(Color.red);  //To Color each Brick
                    g.fillRect(j * bricksWidth + 80, i * bricksHeight + 50, bricksWidth, bricksHeight);

                    g.setStroke(new BasicStroke(3)); //To divide the bricks
                    g.setColor(Color.darkGray);
                    g.drawRect(j * bricksWidth + 80, i * bricksHeight + 50, bricksWidth, bricksHeight);

                }
            }

        }
    }
    public void setBricksValue(int value,int row,int col)
    {
        map[row][col] = value;

    }
    
}
