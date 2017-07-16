/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.pieces;

import com.insanelyinsane.waifushogi.objects.Cell;
import java.util.List;

/**
 *
 * @author alex
 */
public abstract class Piece 
{
    /**
     * Returns a list of cells that the piece can move to when in play.
     * @return 
     */
    public abstract List<Cell> getValidMoves();
    
    /**
     * Returns a list of cells a place can be placed into after being captured.
     * @return 
     */
    public abstract List<Cell> getValidReplacements();
}
