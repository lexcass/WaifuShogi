/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.insanelyinsane.waifushogi.objects.Board;

/**
 *
 * @author alex
 */
public final class BoardObject extends GameObject
{   
    public static float X_POS;
    public static float Y_POS;
    
    private Board _board;
    
    
    public BoardObject(Texture tex, float x, float y, Board board)
    {
        super(tex, x, y);
        _board = board;
        
        X_POS = x;
        Y_POS = y;
    }
    
    
    public Board getBoard() { return _board; }
}
