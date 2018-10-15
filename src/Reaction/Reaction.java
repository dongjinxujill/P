package Reaction;

import GraphicsLib.*;

import java.util.*;

public abstract class Reaction implements I.React{
    public Shape shape;
    public static List initialReactions = new List();

    private static Map byShapeMap = new Map();
    public Reaction(String shapeName){
        this.shape = Shape.DB.get(shapeName);
        if (this.shape == null){
            System.out.println("Shape not in DB: " + shapeName);
        }
    }

    public static Reaction bestGesture(Gesture gesture){
        return byShapeMap.getList(gesture.shape).lowBid(gesture);
    }

    public static class List extends ArrayList<Reaction> {
        public void addReaction(Reaction r){
            this.add(r);
            byShapeMap.addReaction(r);
        }
        public void removeReaction(Reaction r){
            this.remove(r);
            byShapeMap.removeReaction(r);
        }
        public void clearAll(){
            for (Reaction r : this){
                byShapeMap.removeReaction(r);
            }
            this.clear();
        }
        public Reaction lowBid(Gesture gesture){
            Reaction res = null;
            int bestSoFar = UC.noBid;
            for (Reaction r:this){
                int b = r.bid(gesture);
                if (b < bestSoFar){
                    bestSoFar = b;
                    res = r;
                }
            }
            return res;
        }
    }
    public static class Map extends HashMap<Shape, List>{
        public List getList(Shape shape){
            List res = get(shape);
            if (res == null){
                res = new List();
            }
            this.put(shape, res);
            return res;
        }
        public void addReaction(Reaction r){
            byShapeMap.getList(r.shape).add(r);
        }

        public void removeReaction(Reaction r){
            byShapeMap.getList(r.shape).remove(r);
        }
    }




}
