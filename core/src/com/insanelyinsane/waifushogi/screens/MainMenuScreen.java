/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.insanelyinsane.waifushogi.listeners.ScreenChangeListener;

/**
 *
 * @author alex
 */
public class MainMenuScreen extends Screen
{
    public MainMenuScreen(ScreenChangeListener l, SpriteBatch b)
    {
        super(l, b);
    }
    
    
    public void create()
    {
        
    }
    
    public void render(float delta)
    {
        Gdx.app.debug("From main menu", "I work!");
    }
    
    public boolean  touchDown(int screenX, int screenY, int pointer, int button)
    {
        return true;
    }
    
    public void resume() {}
    
    public void pause() {}
    
    public void resize(int x, int y) {}
    
    public void show() {}
    
    public void hide() {}
}
