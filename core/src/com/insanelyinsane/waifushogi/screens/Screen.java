/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.screens;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.insanelyinsane.waifushogi.listeners.ObjectListener;
import com.insanelyinsane.waifushogi.listeners.ScreenChangeListener;
import java.util.LinkedList;

/**
 *
 * @author alex
 */
public abstract class Screen implements ObjectListener
{
    private final LinkedList<ScreenChangeListener> _screenChangeListeners;
    private final SpriteBatch _spriteBatch;
    
    public Screen(ScreenChangeListener game, SpriteBatch batch)
    {
        // Init ScreenChangeListeners and add game (WaifuShogi class) to the list
        _screenChangeListeners = new LinkedList<>();
        _screenChangeListeners.add(game);
        
        _spriteBatch = batch;
    }
    
    /**
     * Change to a different screen of the specified type, and inform listeners.
     * @param type 
     */
    public void changeScreen(ScreenType type)
    {
        for (ScreenChangeListener l : _screenChangeListeners)
        {
            l.onScreenChanged(type);
        }
    }
    
    @Override
    public void onObjectAdded(final Object object)
    {
        if (object instanceof ScreenChangeListener)
        {
            _screenChangeListeners.add((ScreenChangeListener)object);
        }
    }
    
    @Override
    public void onObjectRemoved(final Object object)
    {
        _screenChangeListeners.remove(object);
    }
    
    
    public SpriteBatch getSpriteBatch() { return _spriteBatch; }
    
    
    public abstract void render(float delta);
    
    public abstract void resume();
    
    public abstract void pause();
    
    public abstract void resize(int x, int y);
    
    public abstract void show();
    
    public abstract void hide();
}
