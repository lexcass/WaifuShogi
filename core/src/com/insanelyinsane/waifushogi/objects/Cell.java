package com.insanelyinsane.waifushogi.objects;

import com.insanelyinsane.waifushogi.objects.pieces.Piece;

/**
 *
 * @author alex
 */
public class Cell
{
    public static final int WIDTH = 53;
    public static final int HEIGHT = 71;
    
    private int _row;
    private int _col;
    
    private Piece _piece;
    
    public Cell(int r, int c)
    {
        _piece = null;
        _row = r;
        _col = c;
    }
    
    public Piece getPiece() { return _piece; }
    public int getRow() { return _row; }
    public int getCol() { return _col; }
    
    public void setPiece(Piece p) { _piece = p; };
}
