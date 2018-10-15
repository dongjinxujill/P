package Reaction;

import GraphicsLib.G;
import GraphicsLib.I;
import GraphicsLib.UC;

import javax.xml.crypto.Data;
import java.awt.*;
import java.io.*;
import java.util.*;

public class Shape implements Serializable {
    public String name;

    public Shape(String name) {
        this.name = name;
    }

    public Prototype.List prototypes = new Prototype.List();

    public static class Database extends HashMap<String, Shape>{
        private Database(){
            super( );
            String dot = "DOT";
            put(dot, new Shape(dot));
        }

        public Shape forceShape(String name){
            if (!DB.containsKey(name)){
                DB.put(name, new Shape(name));
            }
            return DB.get(name);
        }
        public void train(String name, Ink.Norm norm){
            if (isLegal(name)){
                forceShape(name).prototypes.train(norm);
            }
        }


        public static boolean isLegal(String name){
            return !name.equals("") && !name.equals("DOT");
        }
    }
    public static Database DB = loadDB();

    public static Shape DOT = DB.get("DOT");

    public static Collection<Shape> LIST = DB.values();

    public static Database loadDB(){
        Database  res = new Database();
//        res.put("DOT", new Shape("DOT"));
//        res.put("1", new Shape("1"));
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(UC.shapeDBFileName));
            res = (Database) ois.readObject();
            System.out.println("Loaded Shape DB.");
        } catch (Exception e){
            System.out.println("Failed loading Shape DB.");
            System.out.println(e);
            res = new Database();
        }
        return res;
    }

    public static void saveDB(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(UC.shapeDBFileName));
        } catch(Exception e){
            System.out.println(e);
        }
    }

    public static Shape recognize(Ink ink){
        if (ink.vs.size.x < UC.dotThreshold && ink.vs.size.y < UC.dotThreshold){
            return DOT;
        }
        Shape bestMatched = null;
        int bestSoFar = UC.noMatchDist;

        for (Shape s : LIST){
            int d = s.prototypes.bestDist(ink.norm);
            if (d < bestSoFar){bestSoFar = d; bestMatched = s;}
        }

        return bestMatched;
    }

    public static class Prototype extends Ink.Norm implements Serializable{
        public int nBlends = 1;//noise inside prototypes, trick for doing a running average

        public static class List extends ArrayList<Prototype> implements I.Show, Serializable{//compare list prototype for norms coming in

            public static Prototype bestMatch;// tells actual prototype that is matching

            @Override
            public void show(Graphics g){
                g.setColor(Color.orange);
                for (int i = 0; i < size(); i++){
                    int x = (i +1) * m + i * w;
                    showBox.set(x, m, w, w);
                    Prototype p = get(i);
                    p.drawAt(g, showBox);
                    g.drawString("" + p.nBlends, x, m);
                }
            }
            public void train(Ink.Norm norm){
                if (bestDist(norm) < UC.noMatchDist){
                    bestMatch.blend(norm);
                }else{
                    this.add(new Shape.Prototype());
                }
            }
            private static int m = 10, w = 60;
            private static G.VS showBox = new G.VS(m,m,w,w);

            public int bestDist(Ink.Norm norm){//tells how far you are from prototypes
                bestMatch = null;
                int bestSoFar = UC.noMatchDist; //how good the closest matches so far
                for (Prototype p : this){
                    int d  = p.dist(norm);
                    if (d < bestSoFar){bestSoFar = d; bestMatch = p;}
                }
                return bestSoFar;
            }
        }


        public void blend(Ink.Norm norm){
            //normalize each thing
            for (int i = 0; i < N; i++){
                points[i].blend(norm.points[i], nBlends);
            }
            nBlends++;
        }
    }
}
