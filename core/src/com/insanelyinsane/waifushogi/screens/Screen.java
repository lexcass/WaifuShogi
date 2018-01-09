/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.insanelyinsane.waifushogi.WaifuShogi;
import com.insanelyinsane.waifushogi.events.ScreenChangeEvent;
import com.insanelyinsane.waifushogi.gamecomponents.GameComponent;
import com.insanelyinsane.waifushogi.gamecomponents.GameComponentType;
import com.insanelyinsane.waifushogi.listeners.QuitListener;
import com.insanelyinsane.waifushogi.listeners.ScreenChangeListener;
import com.insanelyinsane.waifushogi.ui.UIController;
import java.util.LinkedList;
import java.util.List;
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
    
    private final List<GameComponent> _components;
    
    private Actor _background;
    
    
    
    public Screen(WaifuShogi game, SpriteBatch batch, UIController ui)
    {
        // Init ScreenChangeListeners and add game (WaifuShogi class) to the list
        _screenChangeListener = (ScreenChangeListener)game;
        
        _spriteBatch = batch;
        _assets = new AssetManager();
        _stage = game.getStage();
        _uiController = ui;
        
        _components = new LinkedList<>();
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
     * Load an asset using the Screen's asset manager.
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
    }
    
    /**
     * Add an Actor (Waifu, Board, Hand, etc.) to the Stage for this Screen.
     * The Screen will update and draw these Actors automatically.
     * @param a 
     */
    public final void addActor(Actor a)
    {
        _stage.addActor(a);
    }
    
    
    /**
     * Add a GameComponent that adds functionality to the Screen.
     * @param comp 
     */
    public final void addComponent(GameComponent comp)
    {
        _components.add(comp);
    }
    
    /**
     * Remove a GameComponent of the specific type.
     * @param type 
     */
    public final void removeComponent(GameComponentType type)
    {
        _components.removeIf(comp -> comp.getType() == type);
    }
    
    
    /**
     * Get a GameComponent attached to this Screen. If one of the specified type
     * doesn't exist, a runtime error will occur.
     * @param <T>
     * @param type
     * @return 
     */
    public <T extends GameComponent> T getComponent(GameComponentType type)
    {
        for (GameComponent comp : _components)
        {
            if (comp.getType() == type)
            {
                return (T)comp;
            }
        }
        
        throw new GdxRuntimeException("No component was found of type " + type.toString());
    }
    
    
    /**
     * 
     * @param delta 
     */
    public final void render(float delta)
    {
        // Update Stage and GameComponents
        _stage.act(delta);
        _components.forEach(comp -> comp.update(delta));
        
        // Draw Stage and GameComponents
        _stage.draw();
        _components.forEach(comp -> comp.draw(_spriteBatch));
    }
    
    
    // Accessor Methods
    public Stage getStage() { return _stage; }
    public SpriteBatch getSpriteBatch() { return _spriteBatch; }
    public AssetManager getAssets() { return _assets; }
    public UIController getUIController() { return _uiController; }
    
    
    /**
     * Override this method in child class to code the creation logic 
     * for the screen.
     */
    public abstract void create();
    
    
    
    /**
     * Handle the release of game assets and system exit when game
     * is quit through the GUI.
     */
    @Override
    public abstract void handleGameQuit();
}
