/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.events;

import com.insanelyinsane.waifushogi.pieces.Piece;

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
    private boolean _promoZone;
    
    /**
     * Event data containing the piece that moved as well as the cells it
     * moved from and to. It has a boolean value indicating if the piece
     * moved into the promotion zone.
     * @param p
     * @param fr
     * @param fc
     * @param tr
     * @param tc
     * @param z 
     */
    public MoveEvent(Piece p, int fr, int fc, int tr, int tc, boolean z)
    {
        if (p == null) throw new NullPointerException();
        
        _piece = p;
        _fromRow = fr;
        _fromCol = fc;
        _toRow = tr;
        _toCol = tc;
        _promoZone = z;
    }
    
    public Piece getPiece() { return _piece; }
    public int fromRow() { return _fromRow; }
    public int fromCol() { return _fromCol; }
    public int toRow() { return _toRow; }
    public int toCol() { return _toCol; }
    public boolean inPromotionZone() { return _promoZone; }
}
