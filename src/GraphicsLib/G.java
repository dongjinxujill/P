package GraphicsLib;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;



public class G{
    public static Random RND = new Random();
    public static int rnd(int max){return RND.nextInt(max);}
    public static Color rndColor(){return new Color(rnd(256),rnd(256),rnd(256)); }
    public static void fillBackground(Graphics g, Color c){g.setColor(c); g.fillRect(0,0,3000,3000);}
    public static void drawCircle(Graphics g, int x, int y, int r){g.drawOval(x-r,y-r,r+r,r+r);}

    public static class V implements Serializable {
        public int x,y;

        public V(int x, int y){this.x = x; this.y = y;}
        public V(V v){x = v.x; y = v.y;}

        public static Transform T = new Transform(); // the single isomorphic one that V will use for tx, ty and setT

        public void add(int dx, int dy){x += dx; y += dy;}
        public void add(V v){x += v.x; y += v.y;}
        public void set(int x, int y){this.x = x; this.y = y;}
        public void set(V v){x = v.x; y = v.y;}
        public void blend(V v, int k){set((k*x + v.x)/(k+1), (k*y + v.y)/(k+1));}
        public int tx(){return x * T.newScale/ T.oldScale + T.dx;}
        public int ty(){return y * T.newScale/ T.oldScale + T.dy;}
        public void setT(V v){set(this.tx(), this.ty());} // sets this v to transform of v

        public static class Transform{
            private int dx=0, dy=0, oldScale=1, newScale=1; // the single scale multiplier is newScal/oldScale

            public void set(VS from, VS to){
                setScale(from.size.x, from.size.y, to.size.x, to.size.y);
                dx = trans(from.loc.x, from.size.x, to.loc.x, to.size.x);
                dy = trans(from.loc.y, from.size.y, to.loc.y, to.size.y);
            }
            public void set(BBox from, VS to){
                setScale(from.h.size(), from.v.size(), to.size.x, to.size.y);
                dx = trans(from.h.lo, from.h.size(), to.loc.x, to.size.x);
                dy = trans(from.v.lo, from.v.size(), to.loc.y, to.size.y);
            }
            private void setScale(int oldW, int oldH, int newW, int newH){
                oldScale = (oldW>oldH)?oldW:oldH; newScale = (newW>newH)?newW:newH;
            }
            private int trans(int oldX, int oldW, int newX, int newW){ // assumes that scale has already been set
                return (-oldX - oldW/2)*newScale/oldScale + (newX + newW/2);
            }
        }
    }

    public static class VS implements Serializable{
        public V loc, size;
        //public VS(V loc, V size){this.loc = new V(loc); this.size = new V(size);}
        public VS(int x, int y, int w, int h){loc = new V(x,y); size = new V(w,h);}
        public void fill(Graphics g, Color c){g.setColor(c); g.fillRect(loc.x, loc.y, size.x, size.y);}
        public boolean hit(int x, int y){return loc.x<=x && loc.y <=y && x<=(loc.x+size.x) && y<=(loc.y+size.y);}
        public int lox(){return loc.x;}
        public int hix(){return loc.x + size.x;}
        public int midx(){return (loc.x + loc.x + size.x)/2;}
        public int loy(){return loc.y;}
        public int hiy(){return loc.y + size.y;}
        public int midy(){return (loc.y + loc.y + size.y)/2;}
        public void resize(int x, int y){ size.x = x; size.y = y;}
        public void set(int x, int y, int w, int h){loc.set(x, y); size.set(w,h);}
    }

    public static class LoHi{
        int lo, hi;
        public LoHi(int min, int max){lo = min; hi = max;}
        public void set(int v){lo = v; hi = v;} // first value into the box
        public void add(int v){if(v<lo){lo = v;} if(v>hi){hi=v;}} // move bounds if necessary
        public int size(){return (hi-lo)>0 ? hi-lo : 1;} // force size not zero
        public int constrain(int v){if(v<lo){return lo;} else return (v<hi)?v:hi;}
    }

    public static class BBox{ // Bounding Box
        LoHi h, v;  // horizontal and vertical ranges.
        public BBox(){h = new LoHi(0,0); v = new LoHi(0,0);}
        public void set(int x, int y){h.set(x); v.set(y);} // sets it to a single point
        public void add(int x, int y){h.add(x); v.add(y);}
        public void add(V v){add(v.x, v.y);}
        public VS getNewVS(){return new VS(h.lo, v.lo, h.hi-h.lo, v.hi-v.lo);}
        public void draw(Graphics g){g.drawRect(h.lo, v.lo, h.hi-h.lo, v.hi-v.lo);}
    }

    public static class PL implements Serializable { // Polyline
        public V[] points;
        public PL(int count){
            points = new V[count];
            for(int i = 0; i < count; i++) { points[i] = new V(0, 0); }
        }
        public int size(){return points.length; }
        public void drawN(Graphics g, int n){
            for(int i = 1; i < n; i++) {
                g.drawLine(points[i - 1].x, points[i - 1].y, points[i].x, points[i].y);
            }
            drawNDots(g,n);
        }
        public void drawNDots(Graphics g, int n){
            g.setColor(Color.RED);
            for(int i = 1; i <n; i++) {
                drawCircle(g, points[i].x, points[i].y, 2);
            }
        }
        public void draw(Graphics g){drawN(g, points.length);}
        public void transform(){for(int i = 0; i<points.length; i++){points[i].setT(points[i]);}}
    }
}
// graphic tools
//public class G {
//    public static Random RND = new Random();
//
//    public static int rnd(int max){return RND.nextInt(max);}
//    public static Color rndColor(){return new Color(rnd(256), rnd(256), rnd(256));}
//    public static void fillBackground(Graphics g, Color c){g.setColor(c); g.fillRect(0,0, 3000,3000);}
//    // V is a single 2D vector INT X, INT Y
//    public static void drawCircle(Graphics g, int x, int y, int r){
//        g.drawOval(x - r, y - r, r + r, r + r);
//    }
//
//    public static class V{
//        public static Transform T;
//        public int x,y;
//        public V(V v){ this.x = v.x;this.y = v.y; }
//        public V(int x, int y){ this.x = x; this.y = y; }
//        public void add(V v){this.x += v.x; this.y += v.y;}
//        public void set(V v){this.x = v.x; this.y = v.y;}
//        public void set(int x, int y){this.x = x; this.y = y;}
//        public int tx(){return this.x * T.newScale/T.oldScale + T.dx;}
//        public int ty(){return this.y * T.newScale/T.oldScale + T.dy;}
//        public static class Transform{
//
//            public int dx, dy, oldScale, newScale;
//            //isomorphic: single value to scale x and y
//            //an-isomorphic: two values to scale x and y
//            public void setScale(int oldW, int oldH, int newW, int newH){
//                oldScale = oldW < oldH ? oldH : oldW;
//                newScale = newW < newH ? newH : newH;
//            }
//            public void set(BBox from, VS to){
//                setScale(from.h.size(), from.v.size(), to.size.x, to.size.y);
//                dx = trans(from.h.lo, from.h.size(), to.loc.x, to.size.x);
//                dy = trans(from.v.lo, from.v.size(), to.loc.y, to.size.y);
//            }
//
//            public int trans(int oldX, int oldW, int newX, int newW){
//                return (-oldX - oldW/2)*newW / oldW + newX + newW/2;
//            }
//            public void set(VS from, VS to){
//                setScale(from.size.x, from.size.y, to.size.x, to.size.y);
//                dx = trans(from.loc.x, from.size.x, to.loc.x, to.size.x);
//                dy = trans(from.loc.y, from.size.y, to.loc.y, to.size.y);
//            }
//
//        }
//    }
//    public static class VS{
//        public V loc, size;
////      public VS(int loc, int size){ this.loc = loc; this.size = size; }
//        //this one is more secure
//        public VS(int x, int y, int w, int h){loc = new V(x, y); size = new V(w, h);}
//        public void fill(Graphics g, Color c){g.setColor(c); g.fillRect(loc.x, loc.y, size.x, size.y);}
//        // hit detection, whether one mouse event is within a specific rec
//        public boolean hit(int x, int y){return loc.x <= x && loc.y <= y && x <= (loc.x + size.x) && y <= (loc.y + size.y);}
//        public void resize(int x, int y){ size.x = x; size.y = y;}
//        public int lox(){return loc.x;};
//        public int midx() {return loc.x + size.x/2;};
//        public int hix() { return loc.x + size.x;};
//        public int loy() {return loc.y;};
//        public int midy() {return loc.y + size.y/2;};
//        public int hiy() { return loc.y + size.y;};
//
//    }
//    // pair of integers one low one high represents range of values.
//    // to expand range to keep track of the bound of data
//    public static class LoHi{
//        int lo, hi;
//        public LoHi(int min, int max){
//            lo = min;
//            hi = max;
//        }
//        //set everything into single point
//        public void set(int val){lo = val; hi = val;}
//        //upd low if that value is lower than lo
//        public void add(int val){lo = val < lo ? val : lo;hi = val > hi ? val : hi;}
//        public int size(){return lo < hi ? hi - lo: 1;}
//    }
//
//    //bounding box, lowest x, lowest y, highest x, highest y
//    public static class BBox{
//        //horizontal range, vertical range
//        LoHi h,v;
//        public BBox(){h = new LoHi(0, 0); v = new LoHi(0, 0);}
//        public void set(int x, int y) {h.set(x); v.set(y);}
//        public void add(int x, int y){h.add(x); v.add(y);}
//        public void add(V vector) { h.add(vector.x); v.add(vector.y); }
//        //vector in size
//        public VS getNewVS(){return new VS(h.lo, v.lo, h.size(), v.size());}
//        public void draw(Graphics g){g.drawRect(h.lo, v.lo, h.size(), v.size());}
//    }
//
//    //POLY line. list of points connected together by draw function
//    public static class PL{
//        public V[] points;
//
//        public PL(int count){
//            points = new V[count];
//            for (int i = 0; i < count; i++){
//                points[i] = new V(0, 0);
//            }
//        }
//
//        public int size(){return points.length; }
//
//        public void drawN(Graphics g, int n){
//            for (int i = 1; i < n; i++) {
//                g.drawLine(points[i - 1].x, points[i - 1].y, points[i].x, points[i].y);
//            }
////            drawNDots(g,n);
//        }
//        public void drawNDots(Graphics g, int n){
//            for (int i = 0; i < n; i++){
//                drawCircle(g, points[i].x, points[i].y, 4);
//            }
//        }
//
//        public void draw(Graphics g){drawN(g, size());}
//        public void transform(){
//            for (int i = 0; i < points.length; i++){
//                points[i].set(points[i].tx(), points[i].ty());
//            }
//        }
//    }
//
//}
