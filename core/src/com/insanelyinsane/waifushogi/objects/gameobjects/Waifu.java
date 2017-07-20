/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.insanelyinsane.waifushogi.objects.pieces.Piece;

/**
 *
 * @author alex
 */
public class Waifu extends GameObject
{
    private Piece _piece;
    
    public Waifu(Texture tex, float x, float y, Piece piece)
    {
        super(tex, x, y);
        _piece = piece;
    }
    
    
    public Piece getPiece() { return _piece; }
    
    
    
}
