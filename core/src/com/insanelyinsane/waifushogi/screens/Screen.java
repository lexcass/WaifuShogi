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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.insanelyinsane.waifushogi.WaifuShogi;
import com.insanelyinsane.waifushogi.events.ScreenChangeEvent;
import com.insanelyinsane.waifushogi.listeners.QuitListener;
import com.insanelyinsane.waifushogi.listeners.ScreenChangeListener;
import com.insanelyinsane.waifushogi.ui.UIController;
/**
 *
 * @author alex
 */
public abstract class Screen implements QuitListener
{
    private final ScreenChangeListener _screenChangeListener;
    private final SpriteBatch _spriteBatch;
    private final AssetManager _assets;
    private final Stage _stage;
    private final UIController _uiController;
    
    private Actor _background;
    
    public Screen(WaifuShogi game, SpriteBatch batch, UIController ui)
    {
        // Init ScreenChangeListeners and add game (WaifuShogi class) to the list
        _screenChangeListener = (ScreenChangeListener)game;
        
        _spriteBatch = batch;
        _assets = new AssetManager();
        _stage = game.getStage();
        _uiController = ui;
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
    
    /**
     * Set the background of the screen to the given Texture.
     * @param tex 
     */
    public final void setBackground(Texture tex)
    {
        // Add the background texture first so that it will be drawn first
        _background = new Image(tex);
        _background.toBack();
        _stage.addActor(_background);
        
       // _background.setSize(_backgroundTex.getWidth(), _backgroundTex.getHeight());
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
    
    public Stage getStage() { return _stage; }
    
    public SpriteBatch getSpriteBatch() { return _spriteBatch; }
    
    public AssetManager getAssets() { return _assets; }
    
    public UIController getUIController() { return _uiController; }
    
    public abstract void create();
    public abstract void update(float delta);
    public abstract void draw(Batch batch);
    
    // Handle the release of game assets and system exit when game
    // is quit through the GUI
    @Override
    public abstract void handleGameQuit();
}
