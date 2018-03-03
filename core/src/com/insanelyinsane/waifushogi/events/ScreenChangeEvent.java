/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.events;

import com.insanelyinsane.waifushogi.screens.ScreenType;

/**
 *
 * @author alex
 */
public class ScreenChangeEvent 
{
    ScreenType _type;
    Object _arg1;
    Object _arg2;
    
    public ScreenChangeEvent(ScreenType t)
    {
        _type = t;
        _arg1 = null;
        _arg2 = null;
    }
    
    public ScreenChangeEvent(ScreenType t, Object arg1)
    {
        _type = t;
        _arg1 = arg1;
        _arg2 = null;
    }
    
    public ScreenChangeEvent(ScreenType t, Object arg1, Object arg2)
    {
        _type = t;
        _arg1 = arg1;
        _arg2 = arg2;
    }
    
    public ScreenType getType() { return _type; }
    public Object getArg1() { return _arg1; }
    public Object getArg2() { return _arg2; }
}
