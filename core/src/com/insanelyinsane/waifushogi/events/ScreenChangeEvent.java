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
    
    public ScreenChangeEvent(ScreenType t)
    {
        _type = t;
    }
    
    public ScreenType getType() { return _type; }
}
