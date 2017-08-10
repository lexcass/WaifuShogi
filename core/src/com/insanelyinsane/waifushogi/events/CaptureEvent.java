/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.events;

import com.insanelyinsane.waifushogi.objects.pieces.Piece;

/**
 *
 * @author alex
 */
public class CaptureEvent 
{
    private final Piece _piece;
    
    public CaptureEvent(Piece piece)
    {
        if (piece == null) throw new NullPointerException();
        
        _piece = piece;
    }
    
    public Piece getPiece() { return _piece; }
}
