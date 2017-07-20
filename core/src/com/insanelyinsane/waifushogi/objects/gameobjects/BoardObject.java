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
public class BoardObject extends GameObject
{
    private Board _board;
    
    public BoardObject(Texture tex, float x, float y, Board board)
    {
        super(tex, x, y);
        _board = board;
    }
    
    
    public Board getBoard() { return _board; }
}
