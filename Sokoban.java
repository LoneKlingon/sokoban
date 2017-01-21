

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.Image.*;
import java.util.*;
import javax.swing.Timer;




/*
    Author: Anthony Youbi Sobodker
    Uploaded to Github 2017 
    Created for CCPS109
    Description: Sokoban Game 

*/


public class Sokoban extends JPanel {
    LevelReader soko = new LevelReader(); //creates object of class LevelReader
    
    private final int TILESIZE = 20; // Multiplier for how big each tile is in pixels
    
     private boolean isReady = false; //when true board is drawn
     
     private Contents [][] levelContent; //stores the conents of the level
     
     private int ROWS, COLUMNS; //Dimensions of the board
     
     
     
     private JLabel wallLabel;
     
    
     //move offsets
     
     private int dx= 0;
     private int dy= 0;
     
    
    private boolean mark [][]; // triggers when user steps over a goal  or places box on goal
    
    private int boxesOnGoal; //stores the amount of boxes on goals
    
    private int totalGoals; //stores the number of goals in a level
    
    
 
    
   private int totalSteps; //stores the steps
   
   private javax.swing.Timer gameTime;
   
   
   
   private int time=0;
   
   
     
     
    
    public Sokoban(){

        soko.readLevels("m1.txt"); //reads the level file

        isReady = true; //Tells us to wait until true to draw board

        initLevel(0); //Sets the board to the 2nd level 
        
        getTotalGoals();
        
       
       

        this.setFocusable(true); //Sets focus to this PANEL

        this.requestFocus(); //requests focus for this panel
        
        this.setPreferredSize(new Dimension (500, 300));
        this.setLayout(null);
        
        this.gameTime = new javax.swing.Timer(1000, new myTimerListener());
        gameTime.start();
     
        
           class keyEvent implements KeyListener{
        int currentLevel = 0;
        
        public void keyPressed(KeyEvent e){
      
       
            switch (e.getKeyCode()){
                
                case KeyEvent.VK_RIGHT : dx= 1;
                                         dy=0;
                                         totalSteps+=1;
                                            break;
                
                case KeyEvent.VK_LEFT : dx = -1;
                                        dy= 0;
                                        totalSteps+=1;
                                        break;
                case KeyEvent.VK_UP : dy = -1; 
                                      dx = 0;
                                      totalSteps+=1;
                                        break;
                         
                case KeyEvent.VK_DOWN : dy = 1; 
                                        dx=0;
                                        totalSteps+=1;
                                        break;
                                        
              
                case 78 : initLevel(++currentLevel); //Goes to next level when user presses N
                            getTotalGoals();
                            repaint();
                            return;  
                            
                case 82 : initLevel(currentLevel); // Restart level when user presses R
                           getTotalGoals();
                            repaint();
                            return;    
                 
                default : 
                        dx= 0; 
                        dy=0;
                        
                
                
            }
            
            
            
          
          
        
       
            
  
          
            
            for (int x =0; x<ROWS; x++){
              
              for (int y=0; y<COLUMNS; y++){
             
             
            
            //if the tile is on a player    
            
                  if (levelContent[x][y] == Contents.PLAYER){
                   
                    
                //if the next tile is empty     
                if (levelContent[x+dx][y+dy] == Contents.EMPTY  || levelContent[x+dx][y+dy] == Contents.GOAL){
                    
                    levelContent[x][y] = Contents.EMPTY;
                    
                    if (levelContent[x+dx][y+dy] == Contents.GOAL){
                        mark[x+dx][y+dy] = true;
                        
                    }
                    
                    levelContent[x+dx][y+dy] = Contents.PLAYER;
                    
                    repaint();
                    return;
                    
                }
                
                
                
                //if the next tile is a box
                else if (levelContent[x+dx][y+dy] == Contents.BOX){
                    
                    if (levelContent[x+dx+dx][y+dy+dy] == Contents.EMPTY || levelContent[x+dx+dx][y+dy+dy] == Contents.GOAL){
                        
                        if ( levelContent[x+dx+dx][y+dy+dy] == Contents.GOAL){
                            
                             mark[x+dx+dx][y+dy+dy] = true;
                        }
                        
                        levelContent[x][y] = Contents.EMPTY;
                        levelContent[x+dx][y+dy] = Contents.PLAYER;
                        levelContent[x+dx+dx][y+dy+dy] = Contents.BOX;
                        
                       if(checkWin()){
                           
                           initLevel(++currentLevel);
                           getTotalGoals();
                        }
                        
                        repaint();
                        
                        return;
                    }
                    
                }
      
     
    }
    
}

}

   }
      public void keyReleased(KeyEvent e){
          
             
            }
            
     public void keyTyped (KeyEvent e){
             
            }
    
    
    
        
    
     }
 
            
    keyEvent inputKeys = new keyEvent();
    
    
    this.addKeyListener(inputKeys);
    
    
        
        
}           

//class for the timer
public class myTimerListener implements ActionListener{
    
      public void actionPerformed(ActionEvent e) {
          time++;
          repaint();

              
        }
    
}

/*
 * Gets the total number of goals in a level 
 */
public void getTotalGoals(){

    for (int x= 0; x<ROWS; x++){
         for (int y = 0; y<COLUMNS; y++){
             if (levelContent[x][y] == Contents.GOAL || levelContent[x][y] == Contents.PLAYERONGOAL || levelContent[x][y] == Contents.BOXONGOAL){
                 
                 totalGoals++;
                }
            }
}

}

/*
 * Checks to see if the user won
 */
 private boolean checkWin() {
     
     boolean result;
     for (int x= 0; x<ROWS; x++){
         for (int y = 0; y<COLUMNS; y++){
             
             if (mark[x][y] && levelContent[x][y] == Contents.BOX ){
                 
                boxesOnGoal++;
                }
             
            }
         
        }
        
        
        if (totalGoals == boxesOnGoal){
            result = true;
        }
        
        else {
            boxesOnGoal = 0; //resets it back to 0
            result = false;
        }
        
         if (result){
                            
                            
                            
                            return true;
                        }
                        
         else{
             
             return false;
            }
     
    } 
 
        
 

    public void initLevel(int level) {
        
        ROWS = soko.getWidth(level);
        COLUMNS = soko.getHeight(level);
        
        boxesOnGoal = 0;
        totalGoals = 0;
        totalSteps =0;
        
        
        time = 0;


        
        levelContent= new Contents[ROWS][COLUMNS];
        mark = new boolean [ROWS][COLUMNS];
        
        for (int i = 0; i<ROWS; i++){
            
            for (int j=0; j<COLUMNS; j++){
                
                levelContent[i][j] = soko.getTile(level, i, j);
                repaint();
                
            }
            
     
        }
        
        
    }
    
     public void paintComponent(Graphics g){
         
        super.paintComponent(g);

         Graphics2D g2 = (Graphics2D)g; 
        
        ImageIcon wall = new ImageIcon("wall.png");
        ImageIcon player = new ImageIcon("player.png");
        ImageIcon box = new ImageIcon ("box.png");
        ImageIcon ground = new ImageIcon("ground.png");
        ImageIcon goal = new ImageIcon("goal.png");
         
         if (!isReady){
         
         return; 
         }
         
         else {
             
             g2.drawString("Steps Taken: " + totalSteps, 350, 200);
             g2.drawString("Time Elapsed: " + time, 350, 250);
             //Draws each tile
             for (int i=0; i<ROWS; i++){
                 
                 for(int j=0; j<COLUMNS; j++){
                    
                    
                    if (levelContent[i][j] == Contents.WALL){
                        
                        //draw image
                       
                        wall.paintIcon(this, g, i*TILESIZE, j*TILESIZE);

                        
                    }
                    
                    else if (levelContent[i][j] == Contents.EMPTY){
                        ground.paintIcon(this, g, i*TILESIZE, j*TILESIZE);
                    }
                     
                     if (levelContent[i][j] == Contents.EMPTY && mark[i][j] == true){
                         levelContent[i][j] = Contents.GOAL;
                         goal.paintIcon(this, g, i*TILESIZE, j*TILESIZE);
                         
                        }
                     
                     else if (levelContent[i][j] == Contents.PLAYER || levelContent[i][j] == Contents.PLAYERONGOAL){
                         
                         //g2.draw(new Ellipse2D.Double(i*TILESIZE, j*TILESIZE, 20, 20));//draw player Normal way
                          if (levelContent[i][j] == Contents.PLAYERONGOAL){
                              

                              mark[i][j] = true;
                              levelContent[i][j] =Contents.PLAYER;

                            }
                           player.paintIcon(this, g, i*TILESIZE, j*TILESIZE);
                         
                         
                        }
                        
                        
                    else if (levelContent[i][j] == Contents.BOX || levelContent[i][j] ==Contents.BOXONGOAL){
                        if (levelContent[i][j] == Contents.BOXONGOAL){
                              

                              mark[i][j] = true;
                              levelContent[i][j] =Contents.BOX;

                            }
                        
                       box.paintIcon(this, g, i*TILESIZE, j*TILESIZE);
                        
                    }
                        
                    
                      else if (levelContent[i][j] == Contents.GOAL){
                        
                       
                        goal.paintIcon(this, g, i*TILESIZE, j*TILESIZE);
                        
                        
                        
                        
                      
                    }
                    
                    else if (levelContent[i][j] == Contents.EMPTY && mark[i][j]){
                        
                        goal.paintIcon(this, g, i*TILESIZE, j*TILESIZE);
                       
                    }
                       
                    }
                    
                   
                 }

             }
             
           
         }

         
    

     public static void main (String [] args){
         
         
         
         
         
         
         JFrame output = new JFrame("Sokoban");
         output.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         output.add(new Sokoban());
         output.pack();
         output.setVisible(true); 
         
         
       
         
        

         
     }
}
