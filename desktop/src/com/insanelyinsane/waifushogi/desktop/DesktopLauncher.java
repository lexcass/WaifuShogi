package com.insanelyinsane.waifushogi.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.insanelyinsane.waifushogi.WaifuShogi;
import com.insanelyinsane.waifushogi.test.Test;
import com.insanelyinsane.waifushogi.test.movement.PawnMovementTest;
import java.util.Scanner;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                
                // Desktop Window settings
                config.width = 600;
                config.height = 720;
                config.title = "Waifu Shogi";
                config.resizable = false;
                
                WaifuShogi game = null;
                if (WaifuShogi.DEBUG)
                {
                    System.out.println("Run a integration test or live test (type 'int' or 'live')? ");
                    Scanner input = new Scanner(System.in);
                    
                    if (input.nextLine().equalsIgnoreCase("int"))
                    {
                        ClassLoader.getSystemClassLoader().setDefaultAssertionStatus(true);
                        
                        // This is where the integration tests will be run
                        Test test = new PawnMovementTest();
                        test.test();
                        
                        System.out.println("-------------------\nAll tests passed!\n-------------------");
                    }
                    else
                    {
                        System.out.println("Load which test (pawn, knight, silver, etc.)? ");
                        game = new WaifuShogi(input.nextLine());
                    }
                }
                else
                {
                    game = new WaifuShogi("");
                }
                
		if (game != null) new LwjglApplication(game, config);
	}
}
