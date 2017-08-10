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
public class MoveEvent 
{
    private Piece _piece;
    private int _toRow;
    private int _toCol;
    private int _fromRow;
    private int _fromCol;
    
    public MoveEvent(Piece p, int fr, int fc, int tr, int tc)
    {
        if (p == null) throw new NullPointerException();
        
        _piece = p;
        _fromRow = fr;
        _fromCol = fc;
        _toRow = tr;
        _toCol = tc;
    }
    
    public Piece getPiece() { return _piece; }
    public int fromRow() { return _fromRow; }
    public int fromCol() { return _fromCol; }
    public int toRow() { return _toRow; }
    public int toCol() { return _toCol; }
}
