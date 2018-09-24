package GraphicsLib;

public interface I {
    public interface Area {
        //being interface we don't implement function, but only list of signatures.
        public boolean hit(int x, int y);
        public void pressed(int x, int y);
        public void dragged(int x, int y);
        public void released(int x, int y);



    }
}
