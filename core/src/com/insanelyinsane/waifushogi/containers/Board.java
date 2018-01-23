/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.containers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;
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
    public static final int CELL_WIDTH = 42;
    public static final int CELL_HEIGHT = 42;
    
    private final Piece[][] _pieces;
    
    
    public Board()
    {
        _pieces = new Piece[ROWS][COLS];
    }
    
    /**
     * Make a copy of the given Board.
     * @param other Board to copy
     */
    public Board(Board other)
    {
        _pieces = new Piece[ROWS][COLS];
        
        for (int r = 0; r < ROWS; r++)
        {
            for (int c = 0; c < COLS; c++)
            {
                if (other.getPieceAt(r,c) != null)
                {
                    this.addPiece(other.getPieceAt(r, c).copy(), r, c);
                }
            }
        }
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
     * Returns a 2D array that represents all of the pieces on the Board.
     * @return Piece[][]
     */
    public Piece[][] getPieces() { return _pieces; }
    
    
    /**
     * Get a Piece at (r, c) assuming r and c are in bounds.
     * @param r
     * @param c
     * @return 
     */
    public Piece getPieceAt(int r, int c)
    {
        if (!inBounds(r, c)) Gdx.app.debug("Error", "No cell at (" + r + ", " + c + "), out of bounds.");
        
        return _pieces[r][c];
    }
    
    
    
    /**
     * Replace this board with another board. All pieces are removed and this
     * board becomes a duplicate of the other board. Intended for AI use.
     * @param other 
     */
    public void setBoard(Board other)
    {
        Piece[][] otherPieces = other.getPieces();
        
        for (int r = 0; r < ROWS; r++)
        {
            for (int c = 0; c < COLS; c++)
            {
                _pieces[r][c] = otherPieces[r][c];
            }
        }
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
        if (_pieces[e.fromRow()][e.fromCol()] != e.getPiece())
        {
            throw new GdxRuntimeException("Fatal error! Piece on board doesn't match piece being moved. Desynchronization error!");
        }
        
        _pieces[e.fromRow()][e.fromCol()] = null;
        _pieces[e.toRow()][e.toCol()] = e.getPiece();
        
        //print();
    }
    
    
    /**
     * Drop a piece onto the Board using a generated DropEvent.
     * @param e 
     */
    @Override
    public void onWaifuDropped(DropEvent e)
    {
        _pieces[e.toRow()][e.toCol()] = e.getPiece();
    }
    
    
    // FOR DEBUGGING
    private void print()
    {
        for (int r = 8; r >= 0; r--) 
        {
            for (int c = 0; c < _pieces[r].length; c++)
            {
                if (_pieces[r][c] == null)
                {
                    System.out.print(" X ");
                }
                else
                {
                    System.out.print(" O ");
                }
            }
            
            System.out.println("");
        }
    }
}
