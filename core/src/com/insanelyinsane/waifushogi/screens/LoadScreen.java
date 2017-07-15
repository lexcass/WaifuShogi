/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.insanelyinsane.waifushogi.listeners.ScreenChangeListener;

/**
 *
 * @author alex
 */
public class LoadScreen extends Screen
{
    private BitmapFont _font;
    private final AssetManager _assetsToLoad;
    private boolean _loadingDone;
    
    private float T = 0;
    
    public LoadScreen(ScreenChangeListener game, SpriteBatch batch, AssetManager assets)
    {
        super(game, batch);
        
        _assetsToLoad = assets;
        _loadingDone = false;
    }
    
    @Override
    public void create()
    {
        _font = new BitmapFont();
    }
    
    public boolean loadingCompleted() { return _loadingDone; }
    
    
    @Override
    public void render(float delta) 
    {
        // Update assets until finished
        if (_assetsToLoad.update())
        {
            _loadingDone = true;
            
            Gdx.app.debug("Game message", "screen loaded in " + T);
        }
        
        T += delta;
        
        SpriteBatch batch = getSpriteBatch();
        
        // Draw "Loading..." text onto screen
        batch.begin();
        _font.draw(batch, "Loading...", Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 100);
        batch.end();
    }
    
    @Override
    public void resume() {}
    
    @Override
    public void pause() {}
    
    @Override
    public void resize(int x, int y) {}
    
    @Override
    public void show() {}
    
    @Override
    public void hide() {}
}
