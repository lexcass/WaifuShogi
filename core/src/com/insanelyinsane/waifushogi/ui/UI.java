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
import com.insanelyinsane.waifushogi.RequestHandler;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author A Wild Popo Appeared
 */
public abstract class UI 
{
    private List<Actor> _elements;
    private Skin _skin;
    private Stage _stage;
    
    public UI(Stage stage)
    {
        _stage = stage;
        _elements = new LinkedList<>();
        _skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        
        createElements();
    }
    
    
    protected abstract void createElements();
    
    public void addElement(Actor e)
    {
        e.toFront();
        _elements.add(e);
    }
    
    
    public void dispose()
    {
        _elements.clear();
        _skin.dispose();
    }
    
    
    public void show(boolean show)
    {
        _elements.forEach(e -> e.setVisible(show));
    }
    
    public List<Actor> getElements() { return _elements; }
    
    public Stage getStage() { return _stage; }
    public Skin getSkin() { return _skin; } 
}
