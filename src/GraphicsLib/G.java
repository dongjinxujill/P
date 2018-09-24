package GraphicsLib;

import java.awt.*;
import java.util.Random;

// graphic tools
public class G {
    public static Random RND = new Random();

    public static int rnd(int max){return RND.nextInt(max);}
    public static Color rndColor(){return new Color(rnd(256), rnd(256), rnd(256));}
    public static void fillBackground(Graphics g, Color c){g.setColor(c); g.fillRect(0,0, 3000,3000);}
    // V is a single 2D vector INT X, INT Y
    public static class V{

        public int x,y;

        public V(int x, int y){
            this.x = x;
            this.y = y;
        }
        public void add(V v){this.x += v.x; this.y += v.y;}

    }
    public static class VS{
        public V loc, size;
//      public VS(int loc, int size){ this.loc = loc; this.size = size; }
        //this one is more secure
        public VS(int x, int y, int w, int h){loc = new V(x, y); size = new V(w, h);}
        public void fill(Graphics g, Color c){g.setColor(c); g.fillRect(loc.x, loc.y, size.x, size.y);}
        // hit detection, whether one mouse event is within a specific rec
        public boolean hit(int x, int y){return loc.x <= x && loc.y <= y && x <= (loc.x + size.x) && y <= (loc.y + size.y);}
        public void resize(int x, int y){ size.x = x; size.y = y;}

    }
    // pair of integers one low one high represents range of values.
    // to expand range to keep track of the bound of data
    public static class LoHi{

    }

    //bounding box, lowest x, lowest y, highest x, highest y
    public static class BBox{

    }

    //pulley line. list of points connected together by draw function
    public static class PL{

    }



}
