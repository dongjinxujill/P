package Sandbox;

import GraphicsLib.G;
import GraphicsLib.UC;
import GraphicsLib.Window;
import Reaction.Ink;
import Reaction.Shape;

import java.awt.*;
import java.awt.event.MouseEvent;

public class PaintInk extends Window {
    public static Ink.List inkList = new Ink.List();
    public static String recognized = "";
    public PaintInk(){super("PaintInk", UC.screenWidth, UC.screenHeight);}

    public static Shape.Prototype.List pList = new Shape.Prototype.List();

    public void paintComponent(Graphics g){
        G.fillBackground(g, Color.white);
        inkList.show(g);
        pList.show(g);
        Ink.BUFFER.show(g);
//        if (Ink.BUFFER.n > 0){
//            Ink.Norm norm = new Ink.Norm();
//            norm.drawAt(g, new G.VS(500, 30, 100, 100));
//            norm.drawAt(g, new G.VS(50, 200, 200, 200));
//        }
//        g.setColor(Color.red); g.fillRect(100, 100, 100, 100);
//        g.drawString("points: "+Ink.BUFFER.n, 600,30);
        g.drawString(recognized, 700, 400);
    }

    public void mousePressed(MouseEvent me){Ink.BUFFER.dn(me.getX(), me.getY()); repaint();}
    public void mouseDragged(MouseEvent me){Ink.BUFFER.drag(me.getX(), me.getY()); repaint();}
    public void mouseReleased(MouseEvent me){
        Ink ink = new Ink();
        Shape s = Shape.recognize(ink);
        recognized = "Recognized: " + ((s == null) ? "UNKNOWN" : s.name);
//        inkList.add(ink);
//        Shape.Prototype proto;
//        if (pList.bestDist(ink.norm) < UC.noMatchDist){
//            Shape.Prototype.List.bestMatch.blend(ink.norm);
//            proto = Shape.Prototype.List.bestMatch;
//        }else{
//            proto = new Shape.Prototype();
//            pList.add(proto);
//        }
//        ink.norm = proto;
        repaint();
    }

}
