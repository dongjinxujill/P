package Sandbox;

import GraphicsLib.*;
import GraphicsLib.Window;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;


public class Paint extends Window{
    public Paint(){
        super("Paint", 1000, 800);
    }

    public static Path thePath = new Path();

    public static Random rnd = new Random();

    public static int rnd(int max) {
        return rnd.nextInt(max);
    }

    protected void paintComponent(Graphics g) {
        Color c = new Color(rnd(265), rnd(265), rnd(265));
        g.setColor(c);
        g.fillRect(100, 100, 200, 300);

        g.drawOval(400, 200, 3, 3);
        FontMetrics fm = g.getFontMetrics();
        String message = "clicks = " + clicks;
        int a = fm.getAscent();
        int d = fm.getDescent();
        int w = fm.stringWidth(message);
        g.setColor(Color.YELLOW);
        int x = 400;
        int y = 200;
        g.fillRect(x, y - a, w, a + d);
        g.setColor(Color.BLUE);
        g.drawString("Something", x, y);
        g.drawLine(600, 100, 100,600);
        thePath.draw(g);
    }

    @Override
    public void mousePressed(MouseEvent me) {
//        System.out.println(me.getX(), me.getY());
        clicks++;
        thePath.clear();
        thePath.add(new Point(me.getX(), me.getY()));
        //me.getPoint()
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent me){
        thePath.add(me.getPoint());
        repaint();


    }

    public static int clicks = 0;

    public static class Path extends ArrayList<Point> {
        public void draw(Graphics g) {
            for (int i = 1; i < this.size(); i++){
                Point p = this.get(i-1), n = this.get(i);
                g.drawLine(p.x, p.y, n.x, n.y);
            }
        }
    }
}
