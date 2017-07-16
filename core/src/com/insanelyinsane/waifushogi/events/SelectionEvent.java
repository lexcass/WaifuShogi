/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.events;

import com.insanelyinsane.waifushogi.objects.Waifu;

/**
 *
 * @author alex
 */
public class SelectionEvent 
{
    private final Waifu _waifu;
    
    public SelectionEvent(Waifu w)
    {
        _waifu = w;
    }
    
    public Waifu getWaifu() { return _waifu; }
}
