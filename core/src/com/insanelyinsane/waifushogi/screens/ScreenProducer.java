/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.insanelyinsane.waifushogi.WaifuShogi;
import com.insanelyinsane.waifushogi.ui.UIController;

/**
 *
 * @author alex
 */
public class ScreenProducer 
{
    public static Screen produceScreen(ScreenType type, WaifuShogi l, SpriteBatch batch, UIController ui)
    {
        switch (type)
        {
            case MAIN_MENU:
                return new MainMenuScreen(l, batch, ui);
            case LOCAL_MULTIPLAYER:
                return new LocalMultiplayerScreen(l, batch, ui);
            case VERSUS_AI:
                return new VersusAIScreen(l, batch, ui);
            case NETWORK_CONNECTION:
                return new NetworkConnectionScreen(l, batch, ui);
            case NETWORK_MULTIPLAYER:
                return new NetworkMultiplayerScreen(l, batch, ui);
        }
        
        return null;
    }
}
