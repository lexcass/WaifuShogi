/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.events;

import com.insanelyinsane.waifushogi.objects.Cell;
import com.insanelyinsane.waifushogi.objects.GameObject;
import java.util.List;

/**
 *
 * @author alex
 */
public class SelectionEvent 
{
    private final Cell[][] _cells;
    private boolean _selected;
    
    public SelectionEvent(Cell[][] w, boolean selected)
    {
        _cells = w;
        _selected = selected;
    }
    
    /**
     * 2d array of cells with indices corresponding to row and col on board.
     * 
     * Note: Null entries mean the cell at row, col will not be highlighted.
     * @return 
     */
    public Cell[][] getCells() { return _cells; }
    
    public boolean isSelected() { return _selected; }
}
