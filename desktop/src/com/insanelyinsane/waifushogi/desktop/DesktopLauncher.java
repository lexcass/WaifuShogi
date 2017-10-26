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
                
                WaifuShogi game;
                if (WaifuShogi.DEBUG)
                {
                    System.out.println("Load which test (pawn, knight, silver, etc.)? ");
                    Scanner input = new Scanner(System.in);
                    game = new WaifuShogi(input.nextLine());
                }
                else
                {
                    game = new WaifuShogi("");
                }
                
		new LwjglApplication(game, config);
	}
}
