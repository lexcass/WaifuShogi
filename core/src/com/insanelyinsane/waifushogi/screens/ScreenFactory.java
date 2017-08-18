/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.insanelyinsane.waifushogi.WaifuShogi;

/**
 *
 * @author alex
 */
public class ScreenFactory 
{
    public static Screen createScreen(ScreenType type, WaifuShogi l, SpriteBatch batch)
    {
        switch (type)
        {
            case MAIN_MENU:
                return new MainMenuScreen(l, batch);
            case PLAY:
                return new PlayScreen(l, batch);
        }
        
        return null;
    }
}
