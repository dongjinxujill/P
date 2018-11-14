package Sandbox;

import GraphicsLib.G;
import GraphicsLib.UC;
import GraphicsLib.Window;
import Reaction.Ink;
import Reaction.Shape;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class ShapeTrainer extends Window {

    public static final String ILLEGAL = "This name is illegal";
    public static final String KNOWN = "This is known";
    public static final String UNKNOWN = "This is unknown";
    public static String currentName=  "";
    public static String currentStatus = "ILLEGAL";
    public static Shape.Prototype.List pList = null;
    public ShapeTrainer(){
        super("Shape Trainer", UC.screenWidth, UC.screenHeight);

    }
    public void paintComponent(Graphics g){
        G.fillBackground(g, Color.WHITE);
        g.setColor(Color.BLACK);
        g.drawString(currentName, 600, 30);
        g.drawString(currentStatus, 600, 60);
        Ink.BUFFER.show(g);
        if (pList != null){
            pList.show(g);
        }
    }

    public static void setStatus(){
        currentStatus = !Shape.Database.isLegal(currentName) ? ILLEGAL : UNKNOWN;
        if (currentStatus == UNKNOWN){
            if (Shape.DB.containsKey(currentName)){
                currentStatus = KNOWN;
                pList = Shape.DB.get(currentName).prototypes;
            } else {
                pList = null;
            }
        }
    }

    public void keyTyped(KeyEvent ke){
        char c = ke.getKeyChar();
        System.out.println("Type " + c);
        currentName = c == ' ' ? "" : currentName + c;
        if (c == 10 || c == 13){
            currentName = "";
            Shape.saveDB();
            System.out.println("saved for DB");
        }
        setStatus();
        repaint();
    }

    public void mousePressed(MouseEvent me){
        Ink.BUFFER.dn(me.getX(), me.getY());
    }

    public void mouseDragged(MouseEvent me){Ink.BUFFER.drag(me.getX(), me.getY());}

    public void mouseReleased(MouseEvent me){
        Ink ink = new Ink();
        Shape.DB.train(currentName, ink.norm);
        setStatus();
        repaint();
    }
}
