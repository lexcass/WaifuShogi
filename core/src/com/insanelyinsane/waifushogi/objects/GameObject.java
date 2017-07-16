/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author alex
 */
public class GameObject<TObject>
{
    private TObject _object;
    private Texture _texture;
    private float _xPos;
    private float _yPos;
    
    public GameObject(Texture tex, TObject obj, float x, float y)
    {
        _texture = tex;
        _object = obj;
        _xPos = x;
        _yPos = y;
    }
    
    public TObject getObject() { return _object; }
    public Texture getTexture() { return _texture; }
    
    public void updatePosition(float x, float y)
    {
        _xPos = x;
        _yPos = y;
    }
    
    public void draw(SpriteBatch batch)
    {
        batch.draw(_texture, _xPos, _yPos);
    }
}
