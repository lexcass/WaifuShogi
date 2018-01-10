/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.insanelyinsane.waifushogi.handlers.RequestHandler;
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
    
    
    /**
     * Uses the Stage from the Screen using this UI and calls the createElements
     * method overridden by child classes.
     * @param stage 
     */
    public UI(Stage stage)
    {
        _stage = stage;
        _elements = new LinkedList<>();
        _skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        
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
}
