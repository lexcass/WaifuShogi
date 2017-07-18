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
public class Board
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
    
    public Cell[][] getCells() { return _cells; }
    
    public Cell getCellAt(int r, int c)
    {
        if (r >= ROWS || c >= COLS) Gdx.app.debug("Error", "No cell at (" + r + ", " + c + "), out of bounds.");
        
        return _cells[r][c];
    }
}
