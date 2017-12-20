/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.insanelyinsane.waifushogi.WaifuShogi;
import com.insanelyinsane.waifushogi.ui.UIController;

/**
 *
 * @author alex
 */
public class LoadScreen extends Screen
{
    private BitmapFont _font;
    private final AssetManager _assetsToLoad;
    private boolean _loadingDone;
    
    // Constants
    private final int LOAD_Y = 48;
    private final int LOAD_X = Gdx.graphics.getWidth() - 100;
    
    private float T = 0;
    
    public LoadScreen(WaifuShogi game, SpriteBatch batch, UIController ui, AssetManager assets)
    {
        super(game, batch, ui);
        
        _assetsToLoad = assets;
        _loadingDone = false;
    }
    
    @Override
    public void create()
    {
        _font = new BitmapFont();
        
        addActor(new Actor()
        {
            @Override
            public void draw(Batch batch, float a)
            {
                _font.draw(batch, "Loading...", LOAD_X, LOAD_Y);
            }
        });
    }
    
    public boolean loadingCompleted() { return _loadingDone; }
    
    
    @Override
    public void update(float delta) 
    {
        // Update assets until finished
        if (_assetsToLoad.update())
        {
            _loadingDone = true;
            
            Gdx.app.debug("Game message", "screen loaded in " + T);
        }
        
        T += delta;
    }
    
    
    @Override
    public void draw(Batch batch) {}
    
    @Override
    public void handleGameQuit()
    {
        
    }
}
