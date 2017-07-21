/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects;

import com.badlogic.gdx.Gdx;
import com.insanelyinsane.waifushogi.events.MoveEvent;
import com.insanelyinsane.waifushogi.listeners.MoveListener;
import com.insanelyinsane.waifushogi.objects.pieces.Piece;

/**
 *
 * @author alex
 */
public class Board implements MoveListener
{
    public static final int ROWS = 9;
    public static final int COLS = 9;
    public static final int CELL_WIDTH = 53;
    public static final int CELL_HEIGHT = 71;
    
    private final Piece[][] _pieces;
    
    
    public Board()
    {
        _pieces = new Piece[ROWS][COLS];
    }
    
    /**
     * Add a piece at this cell on the board if it's unoccupied.
     * @param p
     * @param r
     * @param c 
     */
    public void addPiece(Piece p, int r, int c)
    {
        if (_pieces[r][c] == null)
        {
            _pieces[r][c] = p;
        }
        else
        {
            Gdx.app.debug("Warning", "Piece wasn't added. Cell not empty.");
        }
    }
    
    public Piece[][] getPieces() { return _pieces; }
    
    public Piece getPieceAt(int r, int c)
    {
        if (r >= ROWS || c >= COLS) Gdx.app.debug("Error", "No cell at (" + r + ", " + c + "), out of bounds.");
        
        return _pieces[r][c];
    }
    
    
    /**
     * Returns whether the given row and col are on the board or out of bounds.
     * @param r
     * @param c
     * @return 
     */
    public boolean inBounds(int r, int c)
    {
        return r >= 0 && c >= 0 && r < Board.ROWS && c < Board.COLS;
    }
    
    /**
     * Move a piece from one cell to another.
     * @param e 
     */
    @Override
    public void onWaifuMoved(MoveEvent e)
    {   
//        e.from().setPiece(null);
//        e.to().setPiece(e.getPiece());
        
        _pieces[e.fromRow()][e.fromCol()] = null;
        _pieces[e.toRow()][e.toCol()] = e.getPiece();
    }
}
