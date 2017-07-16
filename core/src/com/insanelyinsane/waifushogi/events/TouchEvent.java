/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.events;

/**
 *
 * @author alex
 */
public class TouchEvent 
{
    private boolean _down;
    private float _x;
    private float _y;
    
    public TouchEvent(boolean down, float x, float y)
    {
        _down = down;
        _x = x;
        _y = y;
    }
    
    public boolean isTouchDown() { return _down; }
    public float getX() { return _x; }
    public float getY() { return _y; }
}
