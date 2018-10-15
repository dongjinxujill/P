package GraphicsLib;

public interface UC {
    // universal constants, global name, works in the whole project
    public static int screenHeight = 800;
    public static int screenWidth = 1000;

    public static int inkBufferMax = 800;
    public static int normSampleSize = 25;
    public static int normCoordMax = 500;
    public static int noMatchDist = 500000;//based on norm sample size and norm coordmax
    public static int dotThreshold = 5;
    public static String shapeDBFileName = "/Users/TOKYO/IdeaProjects/P/ShapeDB.bin";
}
