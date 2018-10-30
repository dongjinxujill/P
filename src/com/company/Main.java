package com.company;

import GraphicsLib.Window;
import Music.Music1;
import Sandbox.*;


public class Main {

    public static void main(String[] args){
//        Window.PANEL = new Squares();
//        Window.PANEL = new PaintInk();
//        Window.PANEL = new ShapeTrainer();
//        Window.PANEL =  new SimpleReaction();
        Window.PANEL = new Music1();

        Window.launch();
    }
}
