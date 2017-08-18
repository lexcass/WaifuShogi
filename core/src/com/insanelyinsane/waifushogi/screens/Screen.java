/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.insanelyinsane.waifushogi.WaifuShogi;
import com.insanelyinsane.waifushogi.events.ScreenChangeEvent;
import com.insanelyinsane.waifushogi.listeners.ScreenChangeListener;
/**
 *
 * @author alex
 */
public abstract class Screen
{
    private final ScreenChangeListener _screenChangeListener;
    private final SpriteBatch _spriteBatch;
    private final AssetManager _assets;
    private final Stage _stage;
    
    private Actor _background;
    private Texture _backgroundTex;
    
    public Screen(WaifuShogi game, SpriteBatch batch)
    {
        // Init ScreenChangeListeners and add game (WaifuShogi class) to the list
        _screenChangeListener = (ScreenChangeListener)game;
        
        _spriteBatch = batch;
        _assets = new AssetManager();
        _stage = game.getStage();
        
        
        // Add the background texture first so that it will be drawn first
        _stage.addActor(new Actor() 
        {
            @Override
            public void draw(Batch batch, float a)
            {
                if (_backgroundTex != null) batch.draw(_backgroundTex, 0, 0);
            }
        });
    }
    
    /**
     * Change to a different screen of the specified type, and inform listeners.
     * @param type 
     */
    public final void changeScreen(ScreenType type)
    {
        _screenChangeListener.onScreenChanged(new ScreenChangeEvent(type));
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
    
    public final void setBackground(Texture tex)
    {
        _backgroundTex = tex;
    }
    
    public final void addActor(Actor a)
    {
        _stage.addActor(a);
    }
    
    public final void render(float delta)
    {
        _stage.act(delta);
        update(delta);
        
        _stage.draw();
        draw(_spriteBatch);
    }
    
    public SpriteBatch getSpriteBatch() { return _spriteBatch; }
    
    public AssetManager getAssets() { return _assets; }
    
    public abstract void create();
    
    public abstract void update(float delta);
    public abstract void draw(Batch batch);
    
    public abstract boolean  touchDown(int screenX, int screenY, int pointer, int button);
    
    public abstract void resume();
    
    public abstract void pause();
    
    public abstract void resize(int x, int y);
    
    public abstract void show();
    
    public abstract void hide();
}
