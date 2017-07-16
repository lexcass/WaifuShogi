/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.insanelyinsane.waifushogi.events.SelectionEvent;
import com.insanelyinsane.waifushogi.events.TouchEvent;
import com.insanelyinsane.waifushogi.listeners.SelectionListener;
import com.insanelyinsane.waifushogi.listeners.TouchListener;
import com.insanelyinsane.waifushogi.objects.pieces.Piece;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author alex
 */
public class Waifu implements TouchListener
{
    private Piece _piece;
    private Texture _texture;
    private float _xPos;
    private float _yPos;
    
    private List<SelectionListener> _selectionListeners;
    
    public Waifu(Texture tex, Piece obj, float x, float y)
    {
        _texture = tex;
        _piece = obj;
        _xPos = x;
        _yPos = y;
        _selectionListeners = new LinkedList<SelectionListener>();
    }
    
    public Piece getPiece() { return _piece; }
    public Texture getTexture() { return _texture; }
    
    public float getX() { return _xPos; }
    public float getY() { return _yPos; }
    
    public void updatePosition(float x, float y)
    {
        _xPos = x;
        _yPos = y;
    }
    
    public void draw(SpriteBatch batch)
    {
        batch.draw(_texture, _xPos, _yPos);
    }
    
    /**
     * Respond to touch event by informing selection listeners this waifu was selected.
     * @param x
     * @param y 
     */
    @Override
    public void onTouch(TouchEvent e)
    {
        if (containsPoint(e.getX(), e.getY()))
        {
            for (SelectionListener l : _selectionListeners)
            {
                l.onWaifuSelected(new SelectionEvent(this));
            }
        }
    }
    
    public boolean containsPoint(float x, float y)
    {
        return (x >= _xPos) && (y >= _yPos) && (x <= (_xPos + _texture.getWidth()) && y <= (_yPos + _texture.getHeight()));
    }
}
