/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.events;

import com.insanelyinsane.waifushogi.objects.pieces.Piece;

/**
 *
 * @author alex
 */
public class SelectionEvent 
{
    private final boolean[][] _board;
    private boolean _selected;
    
    public SelectionEvent(boolean[][] w, boolean selected)
    {
        _board = w;
        _selected = selected;
    }
    
    /**
     * 2d array of cells with indices corresponding to row and col on board.
     * 
     * Note: Null entries mean the cell at row, col will not be highlighted.
     * @return 
     */
    public boolean[][] getValidMoves() { return _board; }
    
    public boolean isSelected() { return _selected; }
}
