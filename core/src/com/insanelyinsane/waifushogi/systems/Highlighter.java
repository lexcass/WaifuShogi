/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.insanelyinsane.waifushogi.events.SelectionEvent;
import com.insanelyinsane.waifushogi.listeners.SelectionListener;
import com.insanelyinsane.waifushogi.objects.Board;
import com.insanelyinsane.waifushogi.objects.gameobjects.BoardObject;

/**
 *
 * @author alex
 */
public class Highlighter implements SelectionListener
{
    private final ShapeRenderer _renderer;
    private final BoardObject _board;
    private boolean[][] _validMoves;
    
    
    public Highlighter(BoardObject board)
    {
        _renderer = new ShapeRenderer();
        
        _board = board;
        _validMoves = new boolean[Board.ROWS][Board.COLS];
    }
    
    /**
     * A 2d array of cells that will be highlighted. The indices correspond
     * to the col and row on the board. 
     * 
     * Note: A null entry means there is no cell to be highlighted.
     * @param cells 
     */
    public void highlight(boolean[][] valid) { _validMoves = valid; }
    
    public void unhighlight() { _validMoves = null; }
    
    
    /**
     * If the cells were selected, highlight them. Otherwise, unhighlight
     * all cells.
     * @param e 
     */
    @Override
    public void onWaifuSelected(SelectionEvent e)
    {
        if (e.isSelected())
        {
            highlight(e.getValidMoves());
        }
        else
        {
            unhighlight();
        }
    }
    
    
    public void draw(Batch batch)
    {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        
        // Color cells with green filled rectangle
        _renderer.setColor(0.0f, 1.0f, 0.75f, 0.33f);
        _renderer.begin(ShapeType.Filled);
        
        if (_validMoves != null)
        {
            for (int r = 0; r < _validMoves.length; r++)
            {
                for (int c = 0; c < _validMoves[r].length; c++)
                {
                    if (_validMoves[r][c])
                    {
                        _renderer.rect(_board.getX() + c * Board.CELL_WIDTH,
                                      _board.getY() + r * Board.CELL_HEIGHT,
                                      Board.CELL_WIDTH,
                                      Board.CELL_HEIGHT);
                    }
                }
            }
        }
        
        _renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
