/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.insanelyinsane.waifushogi.WaifuShogi;
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
    
    
    public void create()
    {
        
    }
    
    @Override
    public void update(float delta)
    {
        Gdx.app.debug("From main menu", "I work!");
    }
    
    @Override
    public void draw(Batch batch) { };
    
    public boolean  touchDown(int screenX, int screenY, int pointer, int button)
    {
        return true;
    }
    
    
    @Override
    public void handleGameQuit()
    {
        
    }
}
