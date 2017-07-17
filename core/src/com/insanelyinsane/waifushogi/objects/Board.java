/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects;

import com.badlogic.gdx.Gdx;
import com.insanelyinsane.waifushogi.events.SelectionEvent;
import com.insanelyinsane.waifushogi.listeners.SelectionListener;
import com.insanelyinsane.waifushogi.objects.pieces.Piece;

/**
 *
 * @author alex
 */
public class Board implements SelectionListener
{
    public static final int ROWS = 9;
    public static final int COLS = 9;
    
    private Cell[][] _cells;
    
    
    public Board()
    {
        _cells = new Cell[ROWS][COLS];
        
        // Add cells to board
        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                _cells[i][j] = new Cell();
            }
        }
    }
    
    
    public Cell getCellAt(int r, int c)
    {
        if (r >= ROWS || c >= COLS) Gdx.app.debug("Error", "No cell at (" + r + ", " + c + "), out of bounds.");
        
        return _cells[r][c];
    }
    
    
    /**
     * Use Waifu's piece valid moves method to determine cells that are valid moves.
     * Dispatch highlight event containing these cells.
     * @param e 
     */
    @Override
    public void onWaifuSelected(SelectionEvent e)
    {
        Piece piece = e.getWaifu().getPiece();
        
        boolean endLoop = false;
        Cell cell;
        for (int r = 0; r < ROWS && !endLoop; r++)
        {
            for (int c = 0; c < COLS && !endLoop; c++)
            {
                if (_cells[r][c].getPiece().equals(piece)) 
                {
                    endLoop = true;
                    cell = _cells[r][c];
                }
            }
        }
    }
}
