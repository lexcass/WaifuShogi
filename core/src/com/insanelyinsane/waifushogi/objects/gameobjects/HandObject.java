/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.gameobjects;

import com.insanelyinsane.waifushogi.objects.Hand;

/**
 *
 * @author alex
 */
public final class HandObject extends GameObject
{
    private Hand _hand;
    
    
    public HandObject(float x, float y, Hand hand)
    {
        super(null, x, y);
        _hand = hand;
    }
    
    
    public Hand getHand() { return _hand; }
}
