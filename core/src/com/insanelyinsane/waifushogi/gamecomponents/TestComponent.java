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
public class TestComponent extends GameComponent
{
    public TestComponent()
    {
        super(GameComponentType.TEST);
    }
    
    
    @Override
    public void update(float deltaTime)
    {
        // Update the component here
    }
    
    
    @Override
    public void draw(Batch batch)
    {
        // Use the Batch to draw stuff here
    }
}
