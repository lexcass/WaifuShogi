/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.listeners;

import com.insanelyinsane.waifushogi.events.ScreenChangeEvent;
import com.insanelyinsane.waifushogi.screens.Screen;

/**
 *
 * @author alex
 */
public interface ScreenChangeListener 
{
    public void onScreenChanged(ScreenChangeEvent e);
}
