package org.example.snakegame;
import org.example.snakegame.GameFrame;

import javax.swing.*;
public class App 
{

    public static JFrame window;

    public static void main( String[] args )
    {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");}
        catch (Exception ignored) {};
        
        GameFrame gFrame = new GameFrame();


    }
}
