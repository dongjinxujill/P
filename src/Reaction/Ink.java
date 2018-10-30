package Reaction;

import GraphicsLib.G;
import GraphicsLib.I;
import GraphicsLib.UC;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
public class Ink implements I.Show{
    public Norm norm;
    public G.VS vs;
    public static Buffer BUFFER = new Buffer();

    public Ink(){
        norm = new Norm(); // automatically loads from BUFFER
        vs = BUFFER.bbox.getNewVS(); // where the ink was on the screen
    }
    public void show(Graphics g){g.setColor(Color.blue);norm.drawAt(g,vs); }
    // ---- Buffer ------
    public static class Buffer extends G.PL implements I.Show, I.Area{
        public static final int MAX = UC.inkBufferMax;
        public int n = 0; // tracks how many points are in the buffer.
        public G.BBox bbox = new G.BBox();
        private Buffer(){super(MAX);}
        public void add(int x, int y){if(n<MAX){points[n].set(x,y); n++; bbox.add(x,y);}}
        public void clear(){n = 0;} // reset the buffer
        public void subSample(G.PL pl){
            int K = pl.points.length;
            for(int i = 0;i<K;i++){pl.points[i].set(points[i*(n-1)/(K-1)]);}
        }
        @Override //--I.Show interface
        public void show(Graphics g){this.drawN(g, n);}// draw the n points as a line.
        @Override //--I.Area interface
        public boolean hit(int x, int y){return true;} // any point COULD go into ink
        public void dn(int x, int y){clear(); bbox.set(x,y); add(x,y);} // add first point
        public void drag(int x, int y){add(x,y);} // add each point as it comes is
        public void up(int x, int y){}
        public void release(int x, int y){};
    }
    // ---- Norm ----
    public static class Norm extends G.PL implements Serializable {
        public static final int N = UC.normSampleSize, MAX = UC.normCoordMax;
        public static final G.VS CS = new G.VS(0,0,MAX,MAX); // the coordinate box for Transforms

        public Norm(){
            super(N); // creates the PL
            BUFFER.subSample(this);
            G.V.T.set(BUFFER.bbox, CS);
            this.transform();
        }
        public void drawAt(Graphics g, G.VS vs){ // expands Norm to fit in vs
            G.V.T.set(CS, vs); // prepare to move from normalized CS to vs
            for(int i = 1; i<N; i++){
                g.drawLine(points[i-1].tx(), points[i-1].ty(), points[i].tx(), points[i].ty());
            }
        }
        public int dist(Norm n){
            int res = 0;
            for(int i = 0; i<N; i++){
                int dx = points[i].x - n.points[i].x, dy = points[i].y - n.points[i].y;
                res += dx*dx + dy*dy;
            }
            return res;
        }

    }
    // ---- List ------
    public static class List extends ArrayList<Ink> implements I.Show{
        public void show(Graphics g){for(Ink ink : this){ink.show(g);}}
    }
}
//public class Ink extends G.PL implements I.Show{
//    public static Buffer BUFFER = new Buffer();
//    public Ink(){
//        super(BUFFER.n);
//        for (int i = 0; i < BUFFER.n; i++){
//            this.points[i].set(BUFFER.points[i]);
//        }
//        G.V.T.set(Ink.BUFFER.bbox, new G.VS(100, 100, 100, 100));
//        this.transform();
//    }
//
//    public void show(Graphics g) {g.setColor(Color.blue);draw(g); }
//
//    public static class List extends ArrayList<Ink> implements I.Show{
//        public void show(Graphics g) { for (Ink ink: this){ ink.show(g); } }
//    }
//
//    public static class Buffer extends G.PL implements I.Show, I.Area{
//        // final: constant, never change
//        public static final int MAX = UC.inkBufferMax;
//        public int n = 0;
//        //make it private can only allow it to be called in this file
//        private Buffer() { super(MAX); }
//        public G.BBox bbox = new G.BBox();
//
//        @Override
//        public void show(Graphics g) {
//            g.setColor(Color.green);
//            drawN(g, n);
//            drawNDots(g, n);
//            if (n > 0){
//                //show routine
//                G.PL ss = subSample(UC.normSampleSize);
//                g.setColor(Color.blue);
//                ss.drawNDots(g, UC.normSampleSize);
//                ss.draw(g);
//                bbox.draw(g);
//            }
//        }
//
//        public G.PL subSample(int k){
//            G.PL res = new G.PL(k);
//            for(int i = 0; i < k; i++){res.points[i].set(this.points[i * (n-1)/(k-1)]);}
//            return res;
//        }
//
//        public void add(int x, int y){ if (n < MAX) {points[n].set(x, y);n++;bbox.add(x, y);}}
//
//        public void clear(){n = 0;}
//        @Override
//        public boolean hit(int x, int y) { return true; }
//
//        @Override
//        public void dn(int x, int y) {clear(); add(x, y);
//        bbox.set(x, y);
//        }
//
//        @Override
//        public void drag(int x, int y) {add(x, y); }
//
//        @Override
//        public void release(int x, int y) {}
//    }
//}
