package Music;

import GraphicsLib.UC;
import Reaction.Gesture;
import Reaction.Mass;
import Reaction.Reaction;
import Sandbox.Music1;

import java.awt.*;

public class Head extends Mass implements Comparable<Head> {

    public Staff staff;
    public int line;
    public Time time;
    public Stem stem;
    public boolean wrongSide;
    public Glyph forcedGlyph;

    public Head(Staff staff, int x, int y) {
        super("NOTE");
        this.staff = staff;
        this.time = staff.sys.getTime(x);
        time.addHead(this);
        int h = staff.H();
        this.line = (y - staff.yTop() + h / 2) / h;
        System.out.println("line equals " + this.line);
        addReaction(new Reaction("S-S") {
            @Override
            public int bid(Gesture g) {
                int x = g.vs.midx(), y1 = g.vs.loy(), y2 = g.vs.hiy();
                int w = Head.this.w(), hY = Head.this.y();
                if (y1 > y || y2 < y) {
                    return UC.noBid;
                }
                int hL = Head.this.time.x, hR = hL + w(); // w
                if (x < hL - w || x > hR + w) {
                    return UC.noBid;
                }
                if (x < hL + w / 2) {
                    return hL - x;
                }
                if (x > hR - w / 2) {
                    return x - hR;
                }
                return UC.noBid;
            }

            @Override
            public void act(Gesture g) {
                int x = g.vs.midx(), y1 = g.vs.loy(), y2 = g.vs.hiy();
                Staff staff = Head.this.staff;
                Time time = Head.this.time;
                int w = Head.this.w();
                boolean isUp = x > time.x + w / 2;
                if (Head.this.stem == null) {
                    Stem.getStem(staff, time, y1, y2, isUp);
                } else {
                    time.unStemHeads(y1, y2);
                }
            }
        });
        addReaction(new Reaction("DOT") {
            @Override
            public int bid(Gesture g) {
                int xH = Head.this.x(), yH = Head.this.y(), h = Head.this.staff.H(), w = Head.this.w();
                int xG = g.vs.midx(), yG = g.vs.midy();
                if (xG < xH || xG > xH + 2 * w || yG < yH - h || yG > yH + h) {
                    return UC.noBid;
                }
                return Math.abs(xH + w - x) + Math.abs(yH - y); // x ? y ?
            }

            @Override
            public void act(Gesture g) {
                if (Head.this.stem != null) {
                    Head.this.stem.cycleDot();
                }
            }
        });
    }

    @Override
    public void show(Graphics g) {
        int h = staff.H();
        g.setColor(stem == null ? Color.RED : Color.BLACK);
//        Glyph.HEAD_Q.showAt(g, h, time.xStem, line * h + staff.yTop());
        (forcedGlyph != null ? forcedGlyph : normalGlyph()).showAt(g, h, x(), y());
        if (stem != null) {
            int offset = UC.restFirstDot, sp = UC.dotSpace;
            for (int i = 0; i < stem.nDot; i++) {
                g.fillOval(time.x + offset + i * sp, y(), h / 2, h / 2);
            }
        }
    }

    public int x() {
        if (stem == null || stem.heads.size() == 0 || !wrongSide) {
            return time.x;
        }
        return time.x + (stem.isUp ? w() : -w());
    }

    public int y() {
        return staff.yLine(line);
    }

    public Glyph normalGlyph() {
        if (stem == null) {
            return Glyph.HEAD_Q;
        }
        if (stem.nFlag == -2) {
            return Glyph.HEAD_W;
        }
        if (stem.nFlag == -1) {
            return Glyph.HEAD_HALF;
        }
        return Glyph.HEAD_Q;
    }

    @Override
    public int compareTo(Head h) {
        return this.staff.ndx != h.staff.ndx ? this.staff.ndx - h.staff.ndx : line - h.line;
    }

    public int w() {
        return 243 * staff.H() / 100;
    }

    public void deleteHead() {
        time.heads.remove(this);
        deleteMass();
    }
//
//      public void joinStem(Stem s) {
//        unStem();
//        s.heads.add(this);
//        stem = s;
//      }

    public void unStem() {
        if (stem == null) {
            return;
        }
        stem.heads.remove(this);
        if (stem.heads.size() == 0) {
            stem.deleteStem();
        }
        stem = null;
        wrongSide = false;
    }
}