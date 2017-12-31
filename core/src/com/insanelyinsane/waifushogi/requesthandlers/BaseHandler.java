/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.requesthandlers;

import com.insanelyinsane.waifushogi.pieces.Piece;
import java.util.List;

/**
 *
 * @author Alex Cassady
 */
public abstract class BaseHandler<T>
{
    List<T> _listeners;
    
    public BaseHandler(List<T> listeners)
    {
        _listeners = listeners;
    }
    
    public List<T> getListeners() { return _listeners; }
    
    public abstract void handleRequest();
}
