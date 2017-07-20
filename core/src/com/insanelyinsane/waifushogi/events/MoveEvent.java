/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.events;

import com.insanelyinsane.waifushogi.objects.Cell;
import com.insanelyinsane.waifushogi.objects.pieces.Piece;

/**
 *
 * @author alex
 */
public class MoveEvent 
{
    private Piece _piece;
    private Cell _from;
    private Cell _to;
    
    public MoveEvent(Piece p, Cell from, Cell to)
    {
        _piece = p;
        _to = to;
        _from = from;
    }
    
    public Piece getPiece() { return _piece; }
    
    public Cell from() { return _from; }
    
    public Cell to() { return _to; }
    
}
