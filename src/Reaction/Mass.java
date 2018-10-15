package Reaction;

import GraphicsLib.*;
//import Reaction.Layer;

public abstract class Mass extends Reaction.List implements I.Show {
    public Layer layer;
    public Mass(String layerName){
        this.layer = layer.byName.get(layerName);
        if (this.layer == null){
            System.out.println("Unable to find: " + layerName);
        }else{
            this.layer.add(this);
        }
    }
    public void delete(){
        this.layer.remove(this);
        clearAll();//mass self is a reaction list, clearall() is the function we wrote in the reaction
        //when we delete a single mass, we need to clean it in other place as well
    }

}
