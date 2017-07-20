/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author alex
 */
public class GameObject
{
    private final Texture _texture;
    private float _xPos;
    private float _yPos;
//    
//    private final List<SelectionListener> _selectionListeners;
    
    /**
     * Checks if the given coords intersect this waifu.
     * @param x
     * @param y
     * @return boolean
     */
    public boolean containsPoint(float x, float y)
    {
        return (x >= _xPos) && (y >= _yPos) && (x <= (_xPos + _texture.getWidth()) && y <= (_yPos + _texture.getHeight()));
    }
    
    
    public GameObject(final Texture tex, float x, float y)
    {
        _texture = tex;
        _xPos = x;
        _yPos = y;
        //_selectionListeners = new LinkedList<>();
    }
    
    // Getters
    public Texture getTexture() { return _texture; }
    
    public float getX() { return _xPos; }
    public float getY() { return _yPos; }
    
    public void updatePosition(float x, float y)
    {
        _xPos = x;
        _yPos = y;
    }
    
    /**
     * Draw the texture of this waifu to the given SpriteBatch.
     * @param batch 
     */
    public void draw(final SpriteBatch batch)
    {
        batch.draw(_texture, _xPos, _yPos);
    }
}
