package com.insanelyinsane.waifushogi.objects;

import com.insanelyinsane.waifushogi.objects.pieces.Piece;

/**
 *
 * @author alex
 */
public class Cell
{
    private Piece _piece;
    
    public Cell()
    {
        _piece = null;
    }   
    
    public Piece getPiece() { return _piece; }
    
    public void setPiece(Piece p) { _piece = p; };
}
