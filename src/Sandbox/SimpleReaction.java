package Sandbox;

import GraphicsLib.*;
import GraphicsLib.Window;
import Reaction.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;


public class SimpleReaction extends Window {
    static{//run code in initialization time
        new Layer("BACK");
        new Layer("FORE");
    }
    public static int SEED = 1234;

    public SimpleReaction(){
        super("SimpleReaction", UC.screenWidth, UC.screenHeight);
        Reaction.initialReactions.addReaction(new Reaction("SW-SW"){
            public int bid(Gesture gesture) {
                return 0;
            }

            public void act(Gesture gesture) {
                new Box(gesture.vs);
            }
        });
        G.RND = new Random(SEED);
        Reaction.initialAction = new I.Act(){
            @Override
            public void act(Gesture gesture) {
                G.RND = new Random(SEED);
            }
        };
    }

    public void paintComponent(Graphics g){
        G.fillBackground(g, Color.WHITE);
        g.setColor(Color.BLUE);
        Ink.BUFFER.show(g);
        Layer.ALL.show(g);
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent me) {
        Gesture.AREA.dn(me.getX(), me.getY());
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        Gesture.AREA.drag(me.getX(), me.getY());
        repaint();

    }

    @Override
    public void mouseReleased(MouseEvent me) {
        Gesture.AREA.up(me.getX(), me.getY());repaint();
    }

    public static class Box extends Mass{
        public G.VS vs;
        public Color c = G.rndColor();
        public Box(G.VS vs){
            super("BACK");
            addReaction(new Reaction("S-S"){
                @Override
                public void act(Gesture gesture) {
                    Box.this.delete();
                }
                @Override
                public int bid(Gesture gesture){
                    int x = gesture.vs.midx();
                    int y = gesture.vs.loy();
                    if (Box.this.vs.hit(x,y)){
                        return Math.abs(x-Box.this.vs.midx());
                    }else{
                        return UC.noBid;
                    }
                }
            });

            addReaction(new Reaction("DOT"){
                @Override
                public void act(Gesture gesture) {
                    c =  G.rndColor();
                }
                @Override
                public int bid(Gesture gesture){
                    int x = gesture.vs.midx();
                    int y = gesture.vs.loy();
                    if (Box.this.vs.hit(x,y)){
                        return Math.abs(x-Box.this.vs.midx());
                    }else{
                        return UC.noBid;
                    }
                }
            });

        }

//        public void delete(Gesture gesture) {
//            Box.this.delete(gesture);
//        }

        @Override
        public void show(Graphics g) {
            vs.fill(g, c);
        }
    }

}
