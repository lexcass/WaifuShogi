/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects;

import com.badlogic.gdx.Gdx;
import com.insanelyinsane.waifushogi.events.MoveEvent;
import com.insanelyinsane.waifushogi.listeners.MoveListener;

/**
 *
 * @author alex
 */
public class Board implements MoveListener
{
    public static final int ROWS = 9;
    public static final int COLS = 9;
    
    private final Cell[][] _cells;
    
    
    public Board()
    {
        _cells = new Cell[ROWS][COLS];
        
        // Add cells to board
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                _cells[i][j] = new Cell(i, j);
            }
        }
    }
    
    public Cell[][] getCells() { return _cells; }
    
    public Cell getCellAt(int r, int c)
    {
        if (r >= ROWS || c >= COLS) Gdx.app.debug("Error", "No cell at (" + r + ", " + c + "), out of bounds.");
        
        return _cells[r][c];
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
//        int fromRow = e.from().getRow();
//        int fromCol = e.from().getCol();
//        int toRow = e.to().getRow();
//        int toCol = e.to().getCol();
        
        e.from().setPiece(null);
        e.to().setPiece(e.getPiece());
    }
}
