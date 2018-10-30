package Reaction;

import GraphicsLib.*;

import java.awt.*;
import java.util.*;

public class Layer extends ArrayList<I.Show> implements I.Show{
    public String name;
    public static HashMap<String, Layer> byName = new HashMap<>();
    public static Layer ALL = new Layer("ALL"); //all layers we created
    public Layer(String name){
        this.name = name;
        if (!name.equals("ALL")){
            ALL.add(this);//block recursive call to ALL itself

        }
        byName.put(name, this);
    }
    @Override
    public void show(Graphics g){
        for (I.Show item : this){
            item.show(g);
        }
    }

    public static void nuke(){
        for (I.Show layer: ALL){
            ((Layer)layer).clear();
        }
    }
}
