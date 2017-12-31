package com.insanelyinsane.waifushogi.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.insanelyinsane.waifushogi.WaifuShogi;
import java.util.Scanner;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                
                // Desktop Window settings
                config.width = 600;
                config.height = 720;
                config.title = "Waifu Shogi";
                config.resizable = false;
                
                /*System.out.println("Waifu Shogi Launcher: Input \"t\" to test the game, or press \"Enter\" to play.");
                
                Scanner input = new Scanner(System.in);
                String choice = input.nextLine();
                
                if (choice.equals("t"))
                {
                    System.out.println("Do you want to perform a logic test (input \"t\") or a live test (press \"Enter\")?");
                    
                    String nextChoice = input.nextLine();
                    if (nextChoice.equals("t"))
                    {
                        System.out.println("Logic testing...");
                    }
                    else
                    {*/
                        new LwjglApplication(new WaifuShogi(false), config);
                    /*}
                }
                else
                {
                    new LwjglApplication(new WaifuShogi(false), config);
                }*/
	}
}
