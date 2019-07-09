package Sandbox;

import GraphicsLib.G;
import GraphicsLib.I;
import GraphicsLib.UC;
import GraphicsLib.Window;
import Music.APP;
import Music.Beam;
import Music.Sys;
import Reaction.Reaction;
import Reaction.Gesture;
import Reaction.Ink;
import Reaction.Layer;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Music2 extends Window implements I.MusicApp {

    static {
        new Layer("BACK");
        new Layer("NOTE");
        new Layer("FORE");
    }

    public static I.Page PAGE = new M2Page();
    public static ArrayList<I.Page> PAGES = new ArrayList<>();

    static {
        PAGES.add(PAGE);
    }

    public static Sys.Fmt SYSFMT = null;
    public static ArrayList<Sys> SYSTEMS = new ArrayList<>();

    public Sys.Fmt sysfmt(I.Page page) {
        return SYSFMT;
    }

    public ArrayList<Sys> systems(I.Page page) {
        return SYSTEMS;
    }

    public ArrayList<I.Page> pages() {
        return PAGES;
    }

    public Music2() {
        super("Music1", UC.screenWidth, UC.screenHeight);
        APP.get = this;
        Reaction.initialAction = new I.Act() {
            public void act(Gesture gesture) {
                SYSFMT = null;
            }
        };
        Reaction.initialReactions.addReaction(new Reaction("E-E") {
            public int bid(Gesture g) {
                if (SYSFMT == null) {
                    return 0;
                }
                int y = g.vs.midy();
                if (y > PAGE.top() + SYSFMT.height() + 15) { // 15 or 10
                    return 100;
                } else {
                    return UC.noBid;
                }
            }

            public void act(Gesture g) {
                int y = g.vs.midy();
                if (SYSFMT == null) {
                    ((M2Page) PAGE).top = y;
                    SYSFMT = new Sys.Fmt();
                    SYSTEMS.clear();
                    new Sys(PAGE);
                }
                SYSFMT.addNewStaff(y, PAGE);
            }
        });
        Reaction.initialReactions.addReaction(new Reaction("E-W") {
            @Override
            public int bid(Gesture g) {
                if (SYSFMT == null) {
                    return UC.noBid;
                }
                int y = g.vs.midy();
                if (y > SYSTEMS.get(SYSTEMS.size() - 1).yBot() + 15) {
                    return 100;
                }
                return UC.noBid;
            }

            @Override
            public void act(Gesture g) {
                int y = g.vs.midy();
                if (SYSTEMS.size() == 1) {
                    PAGE.sysFmt().sysGap = y - (PAGE.top() + SYSFMT.height());
                }
                new Sys(PAGE);
            }
        });
    }

    static int[] xPoly = {100, 200, 200, 100};
    static int[] yPoly = {50, 70, 80, 60};
    static Polygon poly = new Polygon(xPoly, yPoly, 4);

    @Override
    public void paintComponent(Graphics g) {
        G.fillBackground(g, Color.WHITE);
        g.setColor(Color.BLACK);
        Ink.BUFFER.show(g);
        Layer.ALL.show(g);
        int h = 8, x1 = 100, x2 = 200;
        Beam.setMasterBeam(x1, G.rnd(50) + 100, x2, G.rnd(50) + 100);
        Beam.drawBeamStack(g, 0, 1, x1, x2, h);
        g.setColor(Color.RED);
        Beam.drawBeamStack(g, 1, 3, x1 + 20, x2 - 20, h);

        brace(200, 400, 500, 8);
        tie(500, 600, 500, 8, 20);
        g.fillPolygon(G.poly);
    }


    public void brace(int y1, int y2, int x, int h){
        int yH = 2*h;
        G.poly.reset();
        int yM = (y1+y2)/2;
        G.pSpline(x, y1 + yH, x, y1, x + h, y1, 4);//top
        G.pSpline(x + h + h, y1, x + h, y1, x+h, y1+yH, 4);
        G.pSpline(x+h, yM - yH, x + h, yM, x, yM, 4);//mid
        G.pSpline(x, yM, x+h, yM, x+h, yM+yH, 4);
        G.pSpline(x+h, y2-yH, x+h, y2, x+h+h, y2, 4);//bottom
        G.pSpline(x+h, y2, x, y2, x, y2-yH, 4);
        G.pSpline(x, yM+yH, x, yM, x-h, yM, 4);
        G.pSpline(x-h, yM, x, yM, x, yM-yH, 4);
    }

    public void tie(int x1, int x2, int y, int h, int b){//draw the tie between notes
        poly.reset();
        int xM = (x1 + x2)/2;
        G.pSpline(x1, y, xM, y+b+h, x2, y, 4);//top
        G.pSpline(x2, y, xM, y+b, x1, y, 4);//bottom
    }
    @Override
    public void mousePressed(MouseEvent me) {
        Gesture.AREA.dn(me.getX(), me.getY());
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        Gesture.AREA.drag(me.getX(), me.getY());
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        Gesture.AREA.up(me.getX(), me.getY());
        repaint();
    }

    public static class M2Page implements I.Page {

        private int top = 50;

        @Override
        public int top() {
            return top;
        }

        @Override
        public int bot() {
            return UC.screenHeight - 50;
        }

        @Override
        public int left() {
            return 50;
        }

        @Override
        public int right() {
            return UC.screenWidth - 50;
        }

        @Override
        public Music.Sys.Fmt sysFmt() {
            return SYSFMT;
        }

        @Override
        public ArrayList<Sys> systems() {
            return SYSTEMS;
        }
    }
}
