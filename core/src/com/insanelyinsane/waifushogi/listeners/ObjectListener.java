/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.listeners;

import com.insanelyinsane.waifushogi.events.ObjectEvent;

/**
 *
 * @author alex
 */
public interface ObjectListener 
{
    public void onObjectAdded(ObjectEvent e);
    public void onObjectRemoved(ObjectEvent e);
}
