package Sandbox;

import GraphicsLib.G;
import GraphicsLib.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Squares extends Window implements ActionListener {

    public Squares() {
        super("Squares", 1000, 800);
        timer  = new Timer(33,  this);
        timer.setInitialDelay(5000);
        timer.start();
    }

    public static Timer timer;

    public static G.VS theVS =  new G.VS(100, 100, 200, 300);
    public static Color theColor = G.rndColor();
    public static Square theSquare = new Square(200, 328);
    public static Square.List theList = new Square.List();
    static G.V firstPressed = new G.V(0, 0);


    public void paintComponent(Graphics g){
        G.fillBackground(g, Color.white);
        // set color to be consistent in order to adjust to diff systems
//        g.setColor(Color.blue);
//      g.fillRect(100, 100, 200, 300);
//        theVS.fill(g, theColor);
//        theSquare.draw(g);
        theList.draw(g);
    }
    // dragging need to decide whether we should resize the square or drag the current existing square
    public static boolean dragging = false;

//    @Override
    public void mousePressed(MouseEvent me){
//        if (theVS.hit(me.getX(), me.getY())){
//            theColor = G.rndColor();
//        }
        firstPressed.set(me.getX(), me.getY());

        theSquare = new Square(me.getX(), me.getY());
        int x = me.getX(), y = me.getY();
        theSquare = theList.hit(x, y);
//        theSquare == null ? dragging = false : dragging = true;
        if (theSquare == null){
            dragging = false;
            theList.add(new Square(me.getX(), me.getY()));
        } else {
            dragging= true;
            theSquare.dv.set(0, 0);
        }
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent me){
        int x = me.getX(), y = me.getY();
//        s.resize((x - s.loc.x) > 0 ? x - s.loc.x : 0, (y - s.loc.y) > 0 ?  x - s.loc.y : 0);
        if (dragging){
            theSquare.loc.x = x; theSquare.loc.y = y;
        } else {
            Square s = theList.get(theList.size()-1);
            s.resize(Math.abs(x - s.loc.x), Math.abs(y - s.loc.y));
        }
        repaint();
    }

    public void mouseReleased(MouseEvent me) {
        if (dragging) {
            theSquare.dv.set(me.getX() - firstPressed.x, me.getY() - firstPressed.y);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae){
        repaint();
    }

    public static class Square extends G.VS{
        public Color c = G.rndColor();
        //create velocity of the square to have animated effect
        public G.V dv = new G.V(G.rnd(20) - 10, G.rnd(20) - 10);
        public Square(int x, int y){ super(x, y, 100, 100);}


        //move things around
        public void draw(Graphics g){this.fill(g, c); moveAndBounce();}

        //when hit border, bounce back
        // when hit on top and bottom, x same, y opposite
        // when hit on left and right, y same, x opposite

        public void moveAndBounce(){
            loc.add(dv);
            //heading to left, off the screen
            if (lox() < 0 && dv.x < 0){ dv.x = -dv.x;}
            if (loy() < 0 && dv.y < 0){dv.y = -dv.y;}
            if (hix() > 1000 && dv.x > 0) {dv.x = -dv.x;}
            if (hiy() > 800 && dv.y > 0){dv.y = -dv.y;}
        }
        public static class List extends ArrayList<Square> {
            public void draw(Graphics g){for(Square s: this){s.draw(g);}}
            public Square hit(int x, int y){
                Square res = null;
                for (Square s: this){
                    // we shouldn't break once we find the first one. we should find the last one that we hit
                    // because the first one we found would be the one at the bottom of canvas
                    if (s.hit(x, y)){
                        res = s;
                    }
                }
                return res;
            }
        }

    }
}
