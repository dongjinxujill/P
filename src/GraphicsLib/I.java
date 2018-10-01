package GraphicsLib;

import java.awt.*;

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

    }
}
