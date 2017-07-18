/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.insanelyinsane.waifushogi.events.SelectionEvent;
import com.insanelyinsane.waifushogi.listeners.SelectionListener;
import com.insanelyinsane.waifushogi.objects.Board;
import com.insanelyinsane.waifushogi.objects.Cell;
import com.insanelyinsane.waifushogi.objects.GameObject;

/**
 *
 * @author alex
 */
public class Highlighter implements SelectionListener
{
    private ShapeRenderer _renderer;
    private GameObject<Board> _board;
    private Cell[][] _cells;
    
    
    public Highlighter(GameObject<Board> board)
    {
        _renderer = new ShapeRenderer();
        
        _board = board;
        _cells = new Cell[Board.ROWS][Board.COLS];
    }
    
    /**
     * A 2d array of cells that will be highlighted. The indices correspond
     * to the col and row on the board. 
     * 
     * Note: A null entry means there is no cell to be highlighted.
     * @param cells 
     */
    public void highlight(Cell[][] cells) { _cells = cells; }
    
    public void unhighlight() { _cells = null; }
    
    
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
            highlight(e.getCells());
        }
        else
        {
            unhighlight();
        }
    }
    
    
    public void draw(SpriteBatch batch)
    {
        // End the current batch for ShapeRenderer
        batch.end();
        
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        
        // Color cells with green filled rectangle
        _renderer.setColor(0.0f, 1.0f, 0.75f, 0.33f);
        _renderer.begin(ShapeType.Filled);
        
        for (int r = 0; r < _cells.length; r++)
        {
            for (int c = 0; c < _cells[r].length; c++)
            {
                if (_cells[r][c] != null)
                {
                    _renderer.rect(_board.getX() + c * Cell.WIDTH,
                                  _board.getY() + r * Cell.HEIGHT,
                                  Cell.WIDTH,
                                  Cell.HEIGHT);
                }
            }
        }
        
        _renderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
        
        // Begin batch again after Shape rendering
        batch.begin();
    }
}
