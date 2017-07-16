/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.events;

/**
 *
 * @author alex
 */
public class ObjectEvent 
{
    private final Object _object;
    
    public ObjectEvent(Object o)
    {
        _object = o;
    }
    
    public Object getObject() { return _object; }
}
