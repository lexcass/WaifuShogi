/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi;

import com.insanelyinsane.waifushogi.pieces.Piece;

/**
 *
 * @author Alex Cassady
 */
public class Selection 
{
    private Piece _piece;
    public Piece getPiece() { return _piece; }
    public void setPiece(Piece p) { _piece = p; }
    
    private int _row;
    public int getRow() { return _row; }
    
    private int _col;
    public int getCol() { return _col; }
    
    public void setCell(int row, int col) { _row = row; _col = col; }
    
    public Selection(Piece p, int r, int c)
    {
        _piece = p;
        _row = r;
        _col = c;
    }
    
    public void reset()
    {
        _piece = null;
        _row = -1;
        _col = -1;
    }
}
