/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.pieces;

import com.insanelyinsane.waifushogi.objects.Cell;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author alex
 */
public class Pawn extends Piece
{
    public List<Cell> getValidMoves()
    {
        return new LinkedList<>();
    }
    
    public List<Cell> getValidReplacements()
    {
        return new LinkedList<>();
    }
}
