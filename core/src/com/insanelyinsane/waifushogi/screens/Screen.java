/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.insanelyinsane.waifushogi.listeners.ObjectListener;
import com.insanelyinsane.waifushogi.listeners.ScreenChangeListener;
import java.util.LinkedList;

/**
 *
 * @author alex
 */
public abstract class Screen
{
    private final ScreenChangeListener _screenChangeListener;
    private final SpriteBatch _spriteBatch;
    private final AssetManager _assets;
    
    public Screen(ScreenChangeListener game, SpriteBatch batch)
    {
        // Init ScreenChangeListeners and add game (WaifuShogi class) to the list
        _screenChangeListener = game;
        
        _spriteBatch = batch;
        _assets = new AssetManager();
    }
    
    /**
     * Change to a different screen of the specified type, and inform listeners.
     * @param type 
     */
    public final void changeScreen(ScreenType type)
    {
        _screenChangeListener.onScreenChanged(type);
    }
    
    /**
     * Load an asset using the screen's asset manager
     * @param fileName
     * @param c 
     */
    public final void loadAsset(String fileName, Class c)
    {
        _assets.load(fileName, c);
    }
    
    
    public SpriteBatch getSpriteBatch() { return _spriteBatch; }
    
    public AssetManager getAssets() { return _assets; }
    
    public abstract void create();
    
    public abstract void render(float delta);
    
    public abstract boolean  touchDown(int screenX, int screenY, int pointer, int button);
    
    public abstract void resume();
    
    public abstract void pause();
    
    public abstract void resize(int x, int y);
    
    public abstract void show();
    
    public abstract void hide();
}
