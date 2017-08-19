/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
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
//        setX(x);
//        setY(y);
//        setSize(tex.getWidth(), tex.getHeight());
        setBounds(x, y, tex.getWidth(), tex.getHeight());
        _sprite.setX(x);
        _sprite.setY(y);
        _board = board;
        
        // Handle touch
        addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent e, float screenX, float screenY, int pointer, int button)
            {
                Gdx.app.debug("Board", "touched");
                return true;
            }
        });
    }
    
    
    @Override
    public void draw(Batch batch, float alpha)
    {
        _sprite.draw(batch);
    }
    
    
    public Board getBoard() { return _board; }
    
    public Sprite getSprite() { return _sprite; }
}
