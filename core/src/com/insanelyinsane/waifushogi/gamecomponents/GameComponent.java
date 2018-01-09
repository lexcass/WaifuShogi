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
    
    
    public abstract void update(float deltaTime);
    public abstract void draw(Batch batch);
}
