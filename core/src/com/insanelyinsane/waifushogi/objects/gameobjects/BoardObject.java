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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.insanelyinsane.waifushogi.RequestHandler;
import com.insanelyinsane.waifushogi.events.MoveEvent;
import com.insanelyinsane.waifushogi.events.SelectionEvent;
import com.insanelyinsane.waifushogi.objects.Board;
import com.insanelyinsane.waifushogi.objects.pieces.Piece;

/**
 * A GameObject that contains a board. Acts as a visual representation of the board.
 * @author alex
 */
public final class BoardObject extends Actor
{   
    private Board _board;
    private Sprite _sprite;
    
    private RequestHandler _handler;
    
    
    public BoardObject(Texture tex, float x, float y, Board board, RequestHandler handler)
    {
        setBounds(x, y, tex.getWidth(), tex.getHeight());
        
        // Sprite init
        _sprite = new Sprite(tex);
        _sprite.setX(x);
        _sprite.setY(y);
        _board = board;
        
        // Proxy between touch input and event dispatching
        _handler = handler;
        
        // Handle touch
        addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent e, float screenX, float screenY, int pointer, int button)
            {
                int r = (int)(screenY / Board.CELL_HEIGHT);
                int c = (int)(screenX / Board.CELL_WIDTH);
                
                System.out.println(r + " " + c);
                Piece target = getBoard().getPieceAt(r, c);
                
                _handler.requestMove(r, c);
                _handler.requestReplace(r, c);
                
                if (target != null)
                {
                    _handler.requestSelection(RequestHandler.Sender.BOARD, target, r, c);
                }
                
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
