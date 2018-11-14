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
    public void deleteMass(){
        this.layer.remove(this);
        clearAll();//clears everything in the Reaction list of the mass
    }

}
