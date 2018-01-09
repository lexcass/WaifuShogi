/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.gamecomponents;

import com.badlogic.gdx.graphics.g2d.Batch;

/**
 *
 * @author Alex Cassady
 */
public abstract class GameComponent 
{
    private GameComponentType _type;
    
    
    /**
     * Returns the GameComponentType of the GameComponent.
     * @return GameComponentType
     */
    public GameComponentType getType() { return _type; }
    
    
    /**
     * Important! Call this method in the child's constructor via
     * "super();". Otherwise, the Screen won't know which type of 
     * GameComponent it has.
     * @param type 
     */
    public GameComponent(GameComponentType type)
    {
        _type = type;
    }
    
    /**
     * Override this method to have an update loop for this component (needed
     * for things like movement, animations, etc.).
     * 
     * Important: For frame-rate-neutral animations and movement speeds, multiply
     * the final speed change by deltaTime (i.e. xSpeed *= deltaTime).
     * @param deltaTime 
     */
    public abstract void update(float deltaTime);
    
    /**
     * Override this method to have a draw loop that will draw the visual aspects
     * of this component (like text, icons, etc.). These will be drawn in the order
     * they are written in the code unless you do something fancy with it.
     * @param batch 
     */
    public abstract void draw(Batch batch);
}
