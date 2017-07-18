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
    private final List<Cell> _cells;
    
    public SelectionEvent(List<Cell> w)
    {
        _cells = w;
    }
    
    public List<Cell> getCells() { return _cells; }
}
