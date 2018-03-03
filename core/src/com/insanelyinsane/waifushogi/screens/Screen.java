/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.screens;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    private BitmapFont _font;
    
    
    /**
     * Create a Screen that acts as the base visual component for a certain state of the game.
     * @param game
     * @param batch
     * @param ui 
     */
    public Screen(WaifuShogi game, SpriteBatch batch, UIController ui)
    {
        // Init ScreenChangeListeners and add game (WaifuShogi class) to the list
        _screenChangeListener = (ScreenChangeListener)game;
        
        _spriteBatch = batch;
        _assets = new AssetManager();
        _stage = game.getStage();
        _uiController = ui;
        
        _components = new LinkedList<>();
        
        _font = new BitmapFont();
    }
    
    /**
     * Change to a different screen of the specified type, and inform listeners.
     * @param type 
     */
    public final void changeScreen(ScreenType type)
    {
        _screenChangeListener.onScreenChanged(new ScreenChangeEvent(type, null, null));
    }
    
    /**
     * Change to a different screen of the specified type, and inform listeners.
     * @param type 
     */
    public final void changeScreen(ScreenType type, Object arg1)
    {
        _screenChangeListener.onScreenChanged(new ScreenChangeEvent(type, arg1, null));
    }
    
    /**
     * Change to a different screen of the specified type, and inform listeners.
     * @param type 
     */
    public final void changeScreen(ScreenType type, Object arg1, Object arg2)
    {
        _screenChangeListener.onScreenChanged(new ScreenChangeEvent(type, arg1, arg2));
    }
    
    
    /**
     * Load an asset using this Screen's AssetManager.
     * 
     * Important: Only call this class in the constructor! This method will load the assets
     * into the AssetManager before this Screen's create method is called.
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
     * Remove a GameComponent of the specified type.
     * @param type 
     */
    public final void removeComponent(GameComponentType type)
    {
        _components.removeIf(comp -> comp.getType() == type);
    }
    
    
    /**
     * Get a GameComponent attached to this Screen. If one of the specified type
     * doesn't exist, a runtime error will occur. Returns the GameComponent found
     * with the specified type casted to that class (GameComponentType.HIGHLIGHTER will
     * return a HighlighterComponent for example).
     * @param <T>
     * @param type
     * @return GameComponent
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
     * In order: Update Stage and its Actors, update GameComponents attached to Screen, draw Stage and its Actors,
     * draw GameComponents attached to Screen.
     * @param delta 
     */
    public final void render(float delta)
    {
        // Update and Draw Stage
        _stage.act(delta);
        _stage.draw();
        
        // Update and Draw GameComponents
        _components.forEach(comp -> comp.update(delta));
        _components.forEach(comp -> comp.draw(_spriteBatch));
    }
    
    
    /**
     * Get the Stage object that contains the Actors for this Screen.
     * @return Stage
     */
    public Stage getStage() { return _stage; }
    
    /**
     * Get the SpriteBatch (extends Batch) this Screen draws to.
     * @return SpriteBatch
     */
    public SpriteBatch getSpriteBatch() { return _spriteBatch; }
    
    
    /**
     * Returns the BitmapFont for this Screen used for drawing text.
     * @return 
     */
    public BitmapFont getFont() { return _font; }
    
    
    /**
     * Set the BitmapFont for this Screen.
     * @param font 
     */
    public void setFont(BitmapFont font) { _font = font; }
    
    /**
     * Get the AssetManager that will contain the Textures (or other assets)
     * after they are loaded.
     * 
     * Note: This must be used in the create() method. The assets will not be loaded
     * if you call this method in the constructor.
     * @return AssetManager
     */
    public AssetManager getAssets() { return _assets; }
    
    
    /**
     * Returns the UIController so that the UI can be loaded when convenient for the
     * Screen.
     * 
     * Usage: getUIController.loadUI(yourUI);
     * @return UIController
     */
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
