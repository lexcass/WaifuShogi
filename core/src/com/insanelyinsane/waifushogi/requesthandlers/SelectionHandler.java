/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.requesthandlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.insanelyinsane.waifushogi.listeners.SelectionListener;
import com.insanelyinsane.waifushogi.pieces.Piece;
import java.util.List;

/**
 *
 * @author Alex Cassady
 */
public class SelectionHandler extends BaseHandler<SelectionListener>
{
    public SelectionHandler(List<SelectionListener> listeners)
    {
        super(listeners);
    }
    
    
    @Override
    public void handleRequest()
    {
        if (_validMoves == null || _piece == null)
        {
            throw new GdxRuntimeException("Error: SelectionHandler can't have null moves or piece.");
        }
        
        if (_selected == false)
        {
            Gdx.app.debug("Warning", "selection was false for SelectionHandler.");
        }
        
        // THE REST GOES HERE
    }
    
    
    private boolean[][] _validMoves;
    
    public SelectionHandler setValidMoves(boolean[][] moves)
    {
        _validMoves = moves;
        return this;
    }
    
    private Piece _piece;
    
    public SelectionHandler setPiece(Piece p)
    {
        _piece = p;
        return this;
    }
    
    private boolean _selected;
    
    public SelectionHandler setSelected(boolean s)
    {
        _selected = s;
        return this;
    }
}
