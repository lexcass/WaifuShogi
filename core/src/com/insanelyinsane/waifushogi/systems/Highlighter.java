/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.insanelyinsane.waifushogi.events.SelectionEvent;
import com.insanelyinsane.waifushogi.listeners.SelectionListener;
import com.insanelyinsane.waifushogi.objects.Cell;
import java.util.List;

/**
 *
 * @author alex
 */
public class Highlighter implements SelectionListener
{
    private List<Cell> _cells;
    
    public Highlighter()
    {
        _cells = null;
    }
    
    
    public void highlight(List<Cell> cells) { _cells = cells; }
    
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
        
        ShapeRenderer renderer = new ShapeRenderer();
        renderer.begin();
        
        for (Cell c : _cells)
        {
            renderer.rect(0, 0, 0, 0);
        }
        
        renderer.end();
        
        // Begin batch again after Shape rendering
        batch.begin();
    }
}
