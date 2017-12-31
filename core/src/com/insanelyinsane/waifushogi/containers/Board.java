/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.containers;

import com.badlogic.gdx.Gdx;
import com.insanelyinsane.waifushogi.events.MoveEvent;
import com.insanelyinsane.waifushogi.events.DropEvent;
import com.insanelyinsane.waifushogi.listeners.MoveListener;
import com.insanelyinsane.waifushogi.pieces.Piece;
import com.insanelyinsane.waifushogi.listeners.DropListener;

/**
 *
 * @author alex
 */
public class Board implements MoveListener, DropListener
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
    public boolean addPiece(Piece p, int r, int c)
    {
        if (_pieces[r][c] == null)
        {
            _pieces[r][c] = p;
            Gdx.app.debug("Debug", p.getType().toString() + " added at (" + r + ", " + c + ").");
            return true;
        }
        else
        {
            Gdx.app.debug("Warning", "Piece wasn't added. Cell not empty.");
            return false;
        }
    }
    
    
    /**
     * Remove piece at the given row and column.
     * 
     * WARNING: This is unsynchronized with the GUI and may cause issues if used improperly.
     * @param r
     * @param c 
     */
    public void removePieceAt(int r, int c)
    {
        _pieces[r][c] = null;
    }
    
    
    /**
     * Clears the board of all of its pieces
     */
    public void clear()
    {
        for (Piece[] arr : _pieces)
        {
            for (Piece p : arr)
            {
                p = null;
            }
        }
    }
    
    public Piece[][] getPieces() { return _pieces; }
    
    public Piece getPieceAt(int r, int c)
    {
        if (!inBounds(r, c)) Gdx.app.debug("Error", "No cell at (" + r + ", " + c + "), out of bounds.");
        
        return _pieces[r][c];
    }
    
    
    /**
     * Move a piece from one cell to another.
     * 
     * WARNING: This will overwrite any piece that is currently in this cell!
     * @param from
     * @param to 
     */
//    public void movePiece(int fromR, int fromC, int toR, int toC)
//    {
//        if (inBounds(fromR, fromC) && inBounds(toR, toC))
//        {
//            Piece p = _pieces[fromR][fromC];
//            _pieces[fromR][fromC] = null;
//            _pieces[toR][toC] = p;
//        }
//    }
    
    
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
        Piece target = _pieces[e.fromRow()][e.fromCol()];
        
        if (target != null)
        {
            if (target.getTeam() != e.getPiece().getTeam())
            {
                _pieces[e.fromRow()][e.fromCol()] = null;
                _pieces[e.toRow()][e.toCol()] = e.getPiece();
            }
        }
    }
    
    
    @Override
    public void onWaifuDropped(DropEvent e)
    {
        _pieces[e.toRow()][e.toCol()] = e.getPiece();
    }
}
