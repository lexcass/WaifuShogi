/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.events;

import com.insanelyinsane.waifushogi.pieces.Piece;

/**
 *
 * @author Alex Cassady
 */
public class PromotionEvent 
{
    Piece _piece;
    
    public PromotionEvent(Piece p)
    {
        if (p == null) throw new NullPointerException();
        
        _piece = p;
    }
    
    
    public Piece getPiece() { return _piece; }
}
