package Music;

import GraphicsLib.UC;
import Reaction.Gesture;
import Reaction.Reaction;
import Sandbox.Music1;

import java.awt.*;

public class Rest extends Duration {

    public Staff staff;
    public int line = 4;
    public Time time;

    public Rest(Staff staff, Time time) {
        super();
        this.staff = staff;
        this.time = time;
        addReaction(new Reaction("E-E") {
            @Override
            public int bid(Gesture g) {
                int y = g.vs.midy();
                int x1 = g.vs.lox();
                int x2 = g.vs.hix();
                int x = Rest.this.time.x;
                if (x1 > x || x2 < x) {
                    return UC.noBid;
                }
                return Math.abs(y - Rest.this.staff.yLine(4));
            }

            @Override
            public void act(Gesture g) {
                Rest.this.incFlag();
            }
        });

        addReaction(new Reaction("W-W") {
            @Override
            public int bid(Gesture g) {
                int y = g.vs.midx();
                int x1 = g.vs.lox();
                int x2 = g.vs.hix();
                int x = Rest.this.time.x;
                if (x1 > x || x2 < x) {
                    return UC.noBid;
                }
                return Math.abs(y - Rest.this.staff.yLine(4));
            }

            @Override
            public void act(Gesture g) {
                Rest.this.decFlag();
            }
        });

        addReaction(new Reaction("DOT") {
            @Override
            public int bid(Gesture g) {
                int x = g.vs.midx();
                int y = g.vs.midy();
                int yR = Rest.this.staff.yLine(4);
                int xR = Rest.this.time.x;
                if (x < xR + 2 || x > xR + 30 || y < yR - 30 || y > yR + 30) {
                    return UC.noBid;
                }
                return Math.abs(y - yR) + Math.abs(x - xR);
            }

            @Override
            public void act(Gesture g) {
                Rest.this.cycleDot();
            }
        });
    }

    @Override
    public void show(Graphics g) {
        int h = staff.H();
        int top = staff.yTop();
        int y = top + line * h;
        if (nFlag == -2) {
            Glyph.REST_W.showAt(g, h, time.x, y);
        }
        if (nFlag == -1) {
            Glyph.REST_H.showAt(g, h, time.x, y);
        }
        if (nFlag == 0) {
            Glyph.REST_Q.showAt(g, h, time.x, y);
        }
        if (nFlag == 1) {
            Glyph.REST_1F.showAt(g, h, time.x, y);
        }
        if (nFlag == 2) {
            Glyph.REST_2F.showAt(g, h, time.x, y);
        }
        if (nFlag == 3) {
            Glyph.REST_3F.showAt(g, h, time.x, y);
        }
        if (nFlag == 4) {
            Glyph.REST_4F.showAt(g, h, time.x, y);
        }
        for (int i = 0; i < nDot; i++) {
            g.fillOval(time.x + i * 8 + 30, y - h * 3 / 2, h / 2, h / 2);
        }
    }
}
