/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.insanelyinsane.waifushogi.listeners.ScreenChangeListener;

/**
 *
 * @author alex
 */
public class PlayScreen extends Screen
{
    // Assets to load
    Texture boardTex;
    
    // Objects to update
    
    
    public PlayScreen(ScreenChangeListener game, SpriteBatch batch)
    {
        super(game, batch);
        
        loadAsset("ShogiBoard.png", Texture.class);
    }
    
    @Override
    public void create()
    {
        boardTex = getAssets().get("ShogiBoard.png");
    }
    
    @Override
    public void onObjectAdded(Object object)
    {
        // IMPORTANT: Super's callback must be called first.
        super.onObjectAdded(object);
        
        
    }
    
    @Override
    public void onObjectRemoved(Object object)
    {
        // IMPORTANT: Super's callback must be called first.
        super.onObjectRemoved(object);
        
        
    }
    
    
    @Override
    public void render(float delta)
    {
        SpriteBatch batch = getSpriteBatch();
        
        // Update objects here

        // Draw textures and text to the screen
        batch.begin();
        batch.draw(boardTex, 100, 100);
        batch.end();
    }
    
    
    @Override
    public void resume()
    {
        
    }
    
    @Override
    public void pause()
    {
        
    }
    
    @Override
    public void resize(int x, int y)
    {
        
    }
    
    @Override
    public void show()
    {
        
    }
    
    @Override
    public void hide()
    {
        
    }
}
