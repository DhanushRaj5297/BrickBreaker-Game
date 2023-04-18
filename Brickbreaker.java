

//                      MAIN CLASS
//______________________________________________________________________________
package com.mycompany.brickbreaker;

import javax.swing.JFrame;

/**
 *
 * @author chinm
 */
public class Brickbreaker {
    public static void main(String[] args) {               // MAIN METHOD
        JFrame JframeObj = new JFrame();                    // Jframe obj created
        GamePlay gameplay = new GamePlay();                // Gameplay Obj of the Gameplay Class that is created
        
        JframeObj.setBounds(270,40,700,600); //(frame's positionx,positiony,width,height)
        JframeObj.setTitle("BrickBreaker");
        JframeObj.setResizable(false);
        JframeObj.setVisible(true);
        JframeObj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JframeObj.add(gameplay);   //Adding gameplay obj to the Jframe
    }
    
}
