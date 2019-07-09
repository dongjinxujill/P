package Sandbox;

import GraphicsLib.G;
import GraphicsLib.UC;
import GraphicsLib.Window;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Splines extends Window {
    public Splines(){
        super("Splines", UC.screenWidth, UC.screenHeight);
    }

    public void paintComponent(Graphics g){
        G.fillBackground(g, Color.white);
        g.setColor(Color.red);
        g.fillRect(100, 100, 100, 100);
        G.pSpline(points[0].x, points[0].y, points[1].x, points[1].y, points[2].x, points[2].y, 6);
        g.fillPolygon(G.poly);
    }

    public static int xA = 100, yA = 100, xB= 200, yB= 200, xC = 300, yC = 300;
    public static Point[] points = {new Point(100, 100), new Point(100, 200), new Point(300, 300)};
    public static int cPoint = 0;

    public void mousePressed(MouseEvent me) {
        cPoint = closestPoint(me.getX(), me.getY());
        repaint();
    }

    public void mouseDragged(MouseEvent me) {
        points[cPoint].x = me.getX();
        points[cPoint].y = me.getY();
        repaint();
    }

    public void mouseReleased(MouseEvent me) {
        repaint();
    }

    public int closestPoint(int x, int y){
        int res = 0;
        int closestDistance = Integer.MAX_VALUE;
        for (int i = 0; i < points.length; i++){
            Point p = points[i];
            int d = (p.x - x) * (p.x - x) + (p.y - y) * (p.y - y);
            if (d < closestDistance){
                closestDistance = d;
                res = i;
            }
        }
        return res;
    }
}
