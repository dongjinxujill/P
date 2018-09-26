package Sandbox;

import GraphicsLib.G;
import GraphicsLib.UC;
import GraphicsLib.Window;
import Reaction.Ink;

import java.awt.*;
import java.awt.event.MouseEvent;

public class PaintInk extends Window {
    public static Ink.List inkList = new Ink.List();

    public PaintInk(){super("PaintInk", UC.screenWidth, UC.screenHeight);}

    public void paintComponent(Graphics g){
        G.fillBackground(g, Color.white);

        g.setColor(Color.red); g.fillRect(100, 100, 100, 100);

        inkList.show(g);

        Ink.BUFFER.show(g);
    }

    public void mousePressed(MouseEvent me){Ink.BUFFER.pressed(me.getX(), me.getY()); repaint();}
    public void mouseDragged(MouseEvent me){Ink.BUFFER.dragged(me.getX(), me.getY()); repaint();}
    public void mouseReleased(MouseEvent me){inkList.add(new Ink()); repaint();}

}
