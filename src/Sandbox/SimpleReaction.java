package Sandbox;

import GraphicsLib.*;
import GraphicsLib.Window;
import Reaction.Gesture.*;
import Reaction.*;

import java.awt.*;
import java.awt.event.MouseEvent;


public class SimpleReaction extends Window {
    static{//run code in initialization time
        new Layer("BACK");
        new Layer("FORE");
    }
    public SimpleReaction(){
        super("SimpleReaction", UC.screenWidth, UC.screenHeight);
        Reaction.initialReactions.add(new Reaction("SW-SW"){
            public int bid(Gesture gesture) {
                return 0;
            }

            public void act(Gesture gesture) {
                new Box(gesture.vs);
            }
        });
    }

    public void paintComponent(Graphics g){
        G.fillBackground(g, Color.WHITE);
        g.setColor(Color.BLUE);
        Ink.BUFFER.show(g);
        Layer.ALL.show(g);
    }

    @Override
    public void mousePressed(MouseEvent me) {
        Gesture.AREA.dn(me.getX(), me.getY());
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        Gesture.AREA.drag(me.getX(), me.getY());
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        Gesture.AREA.up(me.getX(), me.getY());
    }

    public static class Box extends Mass{
        public G.VS vs;
        public Color c = G.rndColor();
        public Box(G.VS vs){
            super("BACK");

        }

        @Override
        public void show(Graphics g) {
            vs.fill(g, c);
        }
    }

}
