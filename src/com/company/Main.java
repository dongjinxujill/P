package com.company;

import GraphicsLib.Window;
import Sandbox.PaintInk;
import Sandbox.Squares;


public class Main {

    public static void main(String[] args){
//        Window.PANEL = new Squares();
        Window.PANEL = new PaintInk();
        Window.launch();
    }
}
