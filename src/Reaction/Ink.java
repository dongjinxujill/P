package Reaction;

import GraphicsLib.G;
import GraphicsLib.I;
import GraphicsLib.UC;

import java.awt.*;
import java.util.ArrayList;

public class Ink extends G.PL implements I.Show{
    public static Buffer BUFFER = new Buffer();
    public Ink(){
        super(BUFFER.n);
        for (int i = 0; i < BUFFER.n; i++){
            this.points[i].set(BUFFER.points[i]);
        }
    }

    public void show(Graphics g) {g.setColor(Color.blue);draw(g);}

    public static class List extends ArrayList<Ink> implements I.Show{
        public void show(Graphics g) { for (Ink ink: this){ ink.show(g); } }
    }

    public static class Buffer extends G.PL implements I.Show, I.Area{
        // final: constant, never change
        public static final int MAX = UC.inkBufferMax;
        public int n = 0;
        //make it private can only allow it to be called in this file
        private Buffer() { super(MAX); }

        @Override
        public void show(Graphics g) {

            g.setColor(Color.green);

            drawN(g, n);

        }

        public void add(int x, int y){ if (n < MAX) {points[n].set(x, y);n++;}}

        public void clear(){n = 0;}
        @Override
        public boolean hit(int x, int y) { return true; }

        @Override
        public void pressed(int x, int y) {clear(); add(x, y);}

        @Override
        public void dragged(int x, int y) {add(x, y);}

        @Override
        public void released(int x, int y) {}
    }
}
