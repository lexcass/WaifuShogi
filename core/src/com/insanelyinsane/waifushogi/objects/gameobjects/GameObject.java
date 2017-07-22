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
    private int _width;
    private int _height;
    
    
    public GameObject(final Texture tex, float x, float y)
    {
        _texture = tex;
        _xPos = x;
        _yPos = y;
        _width = tex.getWidth();
        _height = tex.getHeight();
    }
    
    public GameObject(float x, float y, int width, int height)
    {
        _texture = null;
        _xPos = x;
        _yPos = y;
        _width = width;
        _height = height;
    }
    
    // Getters
    public Texture getTexture() { return _texture; }
    
    public float getX() { return _xPos; }
    public float getY() { return _yPos; }
    
    // Setters
    protected void setX(float x) { _xPos = x; }
    protected void setY(float y) { _yPos = y; }
    
    
    /**
     * Checks if the given coords intersect this object.
     * @param x
     * @param y
     * @return boolean
     */
    public boolean containsPoint(float x, float y)
    {
        return (x >= _xPos) && (y >= _yPos) && (x <= (_xPos + _width) && y <= (_yPos + _height));
    }
    
    
    /**
     * Draw the texture of this object to the given SpriteBatch.
     * @param batch 
     */
    public void draw(final SpriteBatch batch)
    {
        if (_texture != null)
        {
            batch.draw(_texture, _xPos, _yPos);
        }
    }
}
