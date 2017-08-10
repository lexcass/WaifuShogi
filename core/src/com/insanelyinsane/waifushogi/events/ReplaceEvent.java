/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.events;

import com.insanelyinsane.waifushogi.objects.pieces.Piece;

/**
 *
 * @author A Wild Popo Appeared
 */
public class ReplaceEvent 
{
    private Piece _piece;
    private int _toRow;
    private int _toCol;
    
    public ReplaceEvent(Piece p, int tr, int tc)
    {
        if (p == null) throw new NullPointerException();
        
        _piece = p;
        _toRow = tr;
        _toCol = tc;
    }
    
    public Piece getPiece() { return _piece; }
    public int toRow() { return _toRow; }
    public int toCol() { return _toCol; }
}
