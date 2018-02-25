/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.ui;

import com.badlogic.gdx.Gdx;
import static com.badlogic.gdx.graphics.profiling.GLProfiler.listener;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.insanelyinsane.waifushogi.listeners.QuitListener;
import com.insanelyinsane.waifushogi.screens.Screen;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author Alex Cassady
 */
public abstract class UI 
{
    private List<Actor> _elements;
    private Skin _skin;
    private Stage _stage;
    private Screen _screen;
    private List<QuitListener> _quitListeners;
    
    
    /**
     * Uses the Stage from the Screen using this UI and calls the createElements
     * method overridden by child classes.
     * @param stage 
     */
    public UI(Stage stage, Screen parent)
    {
        _stage = stage;
        _elements = new LinkedList<>();
        _skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        _quitListeners = new LinkedList<>();
        _quitListeners.add(parent);
        _screen = parent;
        
        createElements();
    }
    
    
    /**
     * Override this method in child classes and add any elements
     * that the UI will need using the addElement method.
     */
    protected abstract void createElements();
    
    
    /**
     * Add an element (Actor) to the UI.
     * 
     * Warning: Elements will not be added to the Stage after the UIController
     * loads this UI.
     * @param e 
     */
    public void addElement(Actor e)
    {
        e.toFront();
        _elements.add(e);
    }
    
    
    /**
     * Free any assets or resources that this UI has allocated on the heap.
     */
    public void dispose()
    {
        _elements.clear();
        _skin.dispose();
    }
    
    /**
     * Get a list of the elements (Actors) that were added to this UI. Used
     * by UIController to load the UI.
     * @return 
     */
    public List<Actor> getElements() { return _elements; }
    
    /**
     * Get the Stage this UI is working with (the one associated with the Screen
     * it belongs to).
     * @return 
     */
    public Stage getStage() { return _stage; }
    
    
    /**
     * Get the Skin for this UI. This is required by new elements when adding them to this
     * UI.
     * @return 
     */
    public Skin getSkin() { return _skin; } 
    
    
    public Screen getScreen() { return _screen; }
    
    
    public void quitGame()
    {
        _quitListeners.forEach(l -> l.handleGameQuit());
    }
    
    
    public void registerQuitListener(QuitListener l)
    {
        if (l != null)
        {
            _quitListeners.add(l);
        }
    }
}
