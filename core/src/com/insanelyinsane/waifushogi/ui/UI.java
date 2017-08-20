/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import java.util.List;

/**
 *
 * @author A Wild Popo Appeared
 */
public abstract class UI 
{
    private List<Actor> _elements;
    
    public UI()
    {
        createElements();
    }
    
    
    public abstract void createElements();
    
    public List<Actor> getElements() { return _elements; }
}
