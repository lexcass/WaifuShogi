/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.insanelyinsane.waifushogi.events.MoveEvent;
import com.insanelyinsane.waifushogi.listeners.MoveListener;
import com.insanelyinsane.waifushogi.objects.Cell;

/**
 *
 * @author alex
 */
public class Waifu extends GameObject implements MoveListener
{
    // Board containing this waifu
    private BoardObject _board;
    
    // Cell containing this waifu
    private Cell _cell;
    
    public Waifu(Texture tex, float x, float y, BoardObject board, Cell cell)
    {
        super(tex, x, y);
        _board = board;
        _cell = cell;
    }
    
    
    /**
     * Runs in the game loop. Update the properties and systems of this waifu.
     * @param delta 
     */
    public void update(float delta)
    {
        setX(_board.getX() + _cell.getCol() * Cell.WIDTH);
        setY(_board.getY() + _cell.getRow() * Cell.HEIGHT);
    }
    
    
    @Override
    public void onWaifuMoved(MoveEvent e)
    {
        if (e.from().equals(_cell))
        {
            _cell = e.to();
            System.out.println("from equals cell");
        }
    }
    
    
    public Cell getCell() { return _cell; }
}
