/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.insanelyinsane.waifushogi.objects.Board;

/**
 * A GameObject that contains a board. Acts as a visual representation of the board.
 * @author alex
 */
public final class BoardObject extends Actor
{   
    private Board _board;
    private Sprite _sprite;
    
    public BoardObject(Texture tex, float x, float y, Board board)
    {
        _sprite = new Sprite(tex);
        setX(x);
        setY(y);
        setSize(tex.getWidth(), tex.getHeight());
        _sprite.setX(x);
        _sprite.setY(y);
        _board = board;
    }
    
    
    @Override
    public void draw(Batch batch, float alpha)
    {
        _sprite.draw(batch);
    }
    
    
    public Board getBoard() { return _board; }
    
    public Sprite getSprite() { return _sprite; }
}
