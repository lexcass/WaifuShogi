/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.events;

import com.insanelyinsane.waifushogi.objects.GameObject;

/**
 *
 * @author alex
 */
public class SelectionEvent 
{
    private final GameObject _waifu;
    
    public SelectionEvent(GameObject w)
    {
        _waifu = w;
    }
    
    public GameObject getWaifu() { return _waifu; }
}
