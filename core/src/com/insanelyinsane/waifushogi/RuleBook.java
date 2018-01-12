/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi;

import com.insanelyinsane.waifushogi.containers.Board;
import com.insanelyinsane.waifushogi.containers.Hand;
import com.insanelyinsane.waifushogi.events.DropEvent;
import com.insanelyinsane.waifushogi.events.MoveEvent;
import com.insanelyinsane.waifushogi.events.SelectionEvent;
import com.insanelyinsane.waifushogi.pieces.Piece;
import com.insanelyinsane.waifushogi.pieces.Team;

/**
 *
 * @author Alex Cassady
 */
public class RuleBook 
{
    // Column that starts promotion zone for each team
    private final int PROMO_COLUMN_RED = 6;
    private final int PROMO_COLUMN_BLUE = 2;
    
    
    /**
     * Returns a boolean indicating whether the piece selected on the Board
     * is a valid selection.
     * 
     * @param target
     * @param r
     * @param c
     * @param whoseTurn
     * @return 
     */
    public boolean canSelectPieceOnBoard(Piece target, int r, int c, Team whoseTurn)
    {
        return target.getTeam() == whoseTurn && !target.isCaptured();
    }
    
    
    /**
     * Returns a boolean indicating whether the piece selected in the Hand
     * is a valid selection.
     * 
     * @param target
     * @param whoseTurn
     * @return 
     */
    public boolean canSelectPieceInHand(Piece target, Team whoseTurn)
    {
        return target.getTeam() == whoseTurn && target.isCaptured();
    }
    
    
    /**
     * Returns a boolean indicating whether the selected piece (Selection) can be 
     * moved to the specified row and column.
     * 
     * @param r
     * @param c
     * @return 
     */
    public boolean canMovePieceTo(Selection sel, boolean[][] moves, int r, int c)
    {
        Piece piece = sel.getPiece();
        
        if (piece != null)
        {
            if (!piece.isCaptured() && moves[r][c])
            {
                return true;
            }
        }
        
        return false;
    }
    
    
    /**
     * Moves the currently selected Piece from the Hand to the specified row and column on the Board.
     * If the Drop (drop) is valid as determined by Referee::selectPieceInHand(), this method
     * will generate and return a DropEvent to be handled by the calling object.
 
        ///////////////////////////////////////////////////////////////////////
        Important: Returns null if the selection is invalid. The calling object
        should be able to handle this case.
     * @param r
     * @param c
     * @return 
     */
    public boolean canDropPieceAt(Selection sel, boolean[][] drops, int r, int c)
    {
        Piece piece = sel.getPiece();
        
        if (piece != null)
        {
            if (piece.isCaptured() && drops[r][c])
            {
                return true;
            }
        }
        
        return false;
    }
    
    
    /**
     * Returns a boolean indicating whether the Piece can be captured.
     * 
     * @param target
     * @param whoseTurn
     * @return 
     */
    public boolean canCapturePiece(Piece target, Team whoseTurn)
    {
        if (target != null)
        {
            if (target.getTeam() != whoseTurn) return true;
        }
        
        return false;
    }
    
    
    /**
     * Promote the piece that was just moved if legal. r and c represent the row and
     * column that the piece moved to and returns a boolean indicating whether or not
     * the piece can be promoted.
     * @param selection
     * @param newRow
     * @param whoseTurn
     * @return Piece
     */
    public Piece canPromotePieceAt(Selection sel, int newRow, Team whoseTurn)
    {        
        Piece p = sel.getPiece();
        int selectedRow = sel.getRow();
        
        // Ignore empty cells
        if (p == null)
        {
            return p;
        }
        
        // Jade and Gold Generals can't be promoted, so ignore them.
        if (p.getType() == Piece.Type.JADE || p.getType() == Piece.Type.GOLD)
        {
            return null;
        }
        
        // Don't promote captured pieces
        if (p.isCaptured()) return null;
        
        // If piece started in promotion zone and moves, promote.
        if ((whoseTurn == Team.RED && selectedRow >= PROMO_COLUMN_RED) ||
            (whoseTurn == Team.BLUE && selectedRow <= PROMO_COLUMN_BLUE))
        {
            if (!p.isPromoted())
            {
                return p;
            }
        }
        
        
        // If moved to promotion zone, promote.
        if ((whoseTurn == Team.RED && newRow >= PROMO_COLUMN_RED) ||
            (whoseTurn == Team.BLUE && newRow <= PROMO_COLUMN_BLUE))
        {
            if (!p.isPromoted())
            {
                return p;
            }
        }
        
        
        return null;
    }
    
    
    
    public boolean isPieceStuck(Piece p, int r, int c)
    {
        // Pawn, Lance, and Knight are special cases.
        // If they are in the last row (or second to last row for Knight),
        // they have no future moves and must promoted.
        if (p.getType() == Piece.Type.PAWN || p.getType() == Piece.Type.LANCE)
        {
            int lastRow = p.getTeam() == Team.RED ? Board.COLS - 1 : 0;
            return (r == lastRow);
        }
        else if (p.getType() == Piece.Type.KNIGHT)
        {
            if (p.getTeam() == Team.RED)
            {
                return r >= Board.COLS - 2;
            }
            else
            {
                return r <= 1;
            }
        }
        
        return false;
    }
}
