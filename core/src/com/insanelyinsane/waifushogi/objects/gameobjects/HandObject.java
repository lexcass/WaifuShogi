/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.gameobjects;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.insanelyinsane.waifushogi.objects.Hand;

/**
 *
 * @author alex
 */
public final class HandObject extends Actor
{
    private Hand _hand;
    
    
    public HandObject(float x, float y, int w, int h, Hand hand)
    {
        super();
        setX(x);
        setY(y);
        setSize(w, h);
        _hand = hand;
    }
    
    
     /**
     * Checks if the given coords intersect this object.
     * @param x
     * @param y
     * @return boolean
     */
    public boolean containsPoint(float x, float y)
    {
        return (x >= getX()) && (y >= getY()) && (x <= (getX() + getWidth()) && y <= (getY() + getHeight()));
    }
    
    
    public Hand getHand() { return _hand; }
}
