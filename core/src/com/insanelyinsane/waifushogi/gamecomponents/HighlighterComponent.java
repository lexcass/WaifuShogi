/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.gamecomponents;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.insanelyinsane.waifushogi.events.SelectionEvent;
import com.insanelyinsane.waifushogi.listeners.SelectionListener;
import com.insanelyinsane.waifushogi.containers.Board;

/**
 *
 * @author alex
 */
public class HighlighterComponent extends GameComponent implements SelectionListener
{
    private final ShapeRenderer _renderer;
    
    private final float _boardX;
    private final float _boardY;
    private boolean[][] _validMoves;
    
    
    public HighlighterComponent(float boardX, float boardY)
    {
        super(GameComponentType.HIGHLIGHTER);
        
        _renderer = new ShapeRenderer();
        
        _boardX = boardX;
        _boardY = boardY;
        _validMoves = new boolean[Board.ROWS][Board.COLS];
    }
    
    /**
     * A 2d array of cells that will be highlighted. The indices correspond
     * to the col and row on the board and if the value is true, the cell
     * will be highlighted.
     * @param valid 
     */
    public void highlight(boolean[][] valid) { _validMoves = valid; }
    
    /**
     * Unhighlight the cells
     */
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
    
    
    // Override GameComponent interface methods and ignore update since this 
    // is a drawable component only.
    
    @Override
    public void update(float deltaTime){}
    
    @Override
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
                        _renderer.rect(_boardX + c * Board.CELL_WIDTH,
                                      _boardY + r * Board.CELL_HEIGHT,
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
