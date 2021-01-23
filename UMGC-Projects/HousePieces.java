/*
Name: Dmitry Landy
File: HousePieces.java
Date: August 31, 2020
Description: This file imports java 2D graphic classes in order to create various
shapes of different colors and combine them together to create a representation
of a house. This class will be used in the "House.java" constructor to add it
to the JFrame.
*/     
package cmsc325;

//imports
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Line2D;
import javax.swing.JPanel;

/*
This class includes the paintComponent method to draw the graphics objects by
calling the various methods for the shapes.
*/
public class HousePieces extends JPanel {	

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Call methods to draw                    
        drawRect(g);
        drawTriangle(g); 
        drawCircle(g);
        drawFence (g);
    }//end paintComponent()    
    
    
     // Method to draw a triangle, which is the roof
    private void drawTriangle(Graphics g) {

        Graphics2D roof = (Graphics2D) g;           
        
        int x1Points[] = {50, 250, 150};//X coordinates
        int y1Points[] = {150, 150, 50};//Y coordinates
        
        GeneralPath triangle = new GeneralPath(GeneralPath.WIND_EVEN_ODD, x1Points.length);
        triangle.moveTo(x1Points[0], y1Points[0]);
        
        //Iterate through the x/y coordinate arrays to match the coordinates 
        //for the triangle
        for (int index = 1; index < x1Points.length; index++) {
            triangle.lineTo(x1Points[index], y1Points[index]);
        };//end for (int index = 1; index < x1Points.length; index++)

        triangle.closePath();
        roof.setColor(Color.gray);
        roof.fillPolygon(x1Points, y1Points, 3);
        roof.setColor(Color.black);
        roof.draw(triangle);
    }//end drawTriangle
    
    //method to draw the fence next to the house
    private void drawFence(Graphics g) {

        Graphics2D fence = (Graphics2D) g;           
        
        //X and Y points for the fence polygon
        int x1Points[] = {250, 320, 320, 310, 300, 290, 280, 270, 260, 250};
        int y1Points[] = {350, 350, 320, 310, 320, 310, 320, 310, 320, 310};         
        
        GeneralPath fencePost = new GeneralPath(GeneralPath.WIND_EVEN_ODD, x1Points.length);
        fencePost.moveTo(x1Points[0], y1Points[0]);
        
        //Iterate through the X/Y coordinate arrays to match and points and create the polygon
        for (int index = 1; index < x1Points.length; index++) {
            fencePost.lineTo(x1Points[index], y1Points[index]);            
        };//end  for (int index = 1; index < x1Points.length; index++)
        
        fencePost.closePath();
        fence.setColor(Color.white);
        fence.fillPolygon(x1Points, y1Points, 10);       
        fence.setColor(Color.black);
        fence.draw(fencePost);
    }//end drawFence()
    
     // Method to draw circles
    private void drawCircle(Graphics g) {
        //Front door Window
        Graphics2D doorWindow = (Graphics2D) g;   
        Ellipse2D myCircle = new Ellipse2D.Double(137,260,25,25); 
        doorWindow.setColor(Color.yellow);
        doorWindow.fillOval(137,260,25,25);
        doorWindow.setColor(Color.black);
        doorWindow.draw(myCircle);     
        
        //Roof Window
        Graphics2D roofWindow = (Graphics2D) g;   
        Ellipse2D roofCircle = new Ellipse2D.Double(137,100,25,25); 
        roofWindow.setColor(Color.yellow);
        roofWindow.fillOval(137,100,25,25);
        roofWindow.setColor(Color.black);
        roofWindow.draw(roofCircle);  
        
        //Sun
        Graphics2D sun = (Graphics2D) g;   
        Ellipse2D sunShape = new Ellipse2D.Double(300,50,50,50); 
        sun.setColor(Color.yellow);
        sun.fillOval(300,50,50,50);
        sun.setColor(Color.orange);
        sun.draw(sunShape);
    }//end drawCircle()
    
    
    // Method to draw a Rectangle
     private void drawRect(Graphics g) {
           
        //Chimney
        Graphics2D chimney = (Graphics2D) g;   
        Rectangle2D chimneyShape = new Rectangle2D.Double(50,150,200,200);  

        chimney.setColor(Color.lightGray);
        chimney.fillRect(200,80,20,40);  
        chimney.setColor(Color.gray);
        chimney.draw(chimneyShape);   
         
        //Main house body 
        Graphics2D houseBody = (Graphics2D) g;   
        Rectangle2D houseBodyShape = new Rectangle2D.Double(50,150,200,200);  

        houseBody.setColor(Color.orange);
        houseBody.fillRect(50,150,200,200);
        houseBody.setColor(Color.black);
        houseBody.draw(houseBodyShape);    

        //Left Window
        Graphics2D leftWindow = (Graphics2D) g;   
        Rectangle2D leftWindowShape = new Rectangle2D.Double(70,180,50,50);  

        leftWindow.setColor(Color.gray);
        leftWindow.fillRect(70,180,50,50);
        leftWindow.setColor(Color.black);
        leftWindow.draw(leftWindowShape);

        //Right Window
        Graphics2D rightWindow = (Graphics2D) g;   
        Rectangle2D rightWindowShape = new Rectangle2D.Double(180,180,50,50);  

        rightWindow.setColor(Color.gray);
        rightWindow.fillRect(180,180,50,50);
        rightWindow.setColor(Color.black);
        rightWindow.draw(rightWindowShape);
        
        //Front Door
        Graphics2D frontDoor = (Graphics2D) g;   
        Rectangle2D frontDoorShape = new Rectangle2D.Double(125,250,50,100);  

        frontDoor.setColor(Color.gray);
        frontDoor.fillRect(125,250,50,100);
        frontDoor.setColor(Color.black);
        frontDoor.draw(frontDoorShape);
    }//end drawRect()     
    
}//end HousePieces