package GraphicsLib;

import Music.Sys;
import Reaction.Gesture;

import java.awt.*;
import java.util.ArrayList;

public interface I {
    public interface Show {
        public void show(Graphics g);
    }

    public interface Area {
        //being interface we don't implement function, but only list of signatures.
        public boolean hit(int x, int y);
        public void dn(int x, int y);
        public void drag(int x, int y);
        public void release(int x, int y);
        public void up(int x, int y);
    }
    public interface Act {
        public void act(Gesture gesture);
    }
    public interface React extends Act{
        public int bid(Gesture gesture);

    }

    interface Margin {

        int top();

        int bot();

        int left();

        int right();
    }

    interface Page extends Margin {

        Sys.Fmt sysFmt();

        ArrayList<Sys> systems();
    }

    interface MusicApp {

        ArrayList<Page> pages();

        Sys.Fmt sysfmt(Page page);

        ArrayList<Sys> systems(Page page);
    }
}
