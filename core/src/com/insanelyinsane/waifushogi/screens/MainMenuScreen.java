/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.insanelyinsane.waifushogi.WaifuShogi;
import com.insanelyinsane.waifushogi.gamecomponents.TestComponent;
import com.insanelyinsane.waifushogi.ui.UIController;

/**
 *
 * @author alex
 */
public class MainMenuScreen extends Screen
{
    public MainMenuScreen(WaifuShogi l, SpriteBatch b, UIController ui)
    {
        super(l, b, ui);
    }
    
    
    @Override
    public void create()
    {
        addComponent(new TestComponent());
    }
    
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        Gdx.app.debug("Touch?", "Yes, please!");
        return true;
    }
    
    
    @Override
    public void handleGameQuit()
    {
        
    }
}
