/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.objects.pieces;

import com.insanelyinsane.waifushogi.objects.Board;

/**
 *
 * @author alex
 */
public abstract class Piece 
{
    private Team _team;
    private final Type _type;
    private boolean _captured;
    
    
    /**
     * Types of pieces; these are given to child classes through constructor.
     */
    public enum Type
    {
        PAWN(0), ROOK(1), BISHOP(2), KNIGHT(3), LANCE(4), SILVER(5), GOLD(6), JADE(7);
        
        private int _i;
        public static final int SIZE = 8;
        
        private Type(int i)
        {
            _i = i;
        }
        
        public int getIndex() { return _i; }
        
        
    }
    
    public Piece(Type type, Team team)
    {
        _type = type;
        _team = team;
        _captured = false;
    }
    
    
    /////////////////////////////////////
    // Getters and setters
    public Type getType() { return _type; }
    public Team getTeam() { return _team; }
    public void setTeam(Team newTeam) { _team = newTeam; }
    
    public void setCaptured(boolean c) { _captured = c; }
    public boolean isCaptured() { return _captured; }
    
    
    /////////////////////////////////
    // Overrideable methods
    protected abstract boolean[][] findValidMoves(final Piece[][] cells, int row, int col);
    protected abstract Piece[][] findValidReplacements(final Piece[][] cells);
    
    
    /**
     * Returns a 2d array of cells corresponding to rows and cols on board
     * that the piece can move to when in play.
     * @param cells
     * @param row
     * @param col
     * @return 
     */
    public final boolean[][] getValidMoves(final Piece[][] cells, int row, int col)
    {
        if (row >= 0 && col >= 0 && row < Board.ROWS && col < Board.COLS)
        {
            return findValidMoves(cells, row, col);
        }
        else
        {
            return new boolean[Board.ROWS][Board.COLS];
        }
    }
    
    
    /**
     * Returns a 2d array of cells corresponding to rows and cols on board
     * that a piece can be placed into after being captured.
     * @param cells
     * @return 
     */
    public final Piece[][] getValidReplacements(final Piece[][] cells)
    {
        return findValidReplacements(cells);
    }
    
    
    /**
     * A helper method for checking validity of a move. Returns true if the cell is empty
     * or contains a piece from the other team.
     * @param board
     * @param valid
     * @param r
     * @param c
     */
    public final boolean addIfValid(final Piece[][] board, boolean[][] valid, int r, int c)
    {
        if (inBounds(r, c))
        {
            Piece toCheck = board[r][c];

            if (toCheck != null)
            {
                valid[r][c] = (toCheck.getTeam() != _team);
            }
            else
            {
                valid[r][c] = true;
            }
            
            return valid[r][c];
        }
        
        return false;
    }
    
    
    /**
     * Returns true if the given row/col are in the bounds of the board.
     * @param r
     * @param c
     * @return 
     */
    public final boolean inBounds(int r, int c) { return r >= 0 && c >=0 && r < Board.ROWS && c < Board.COLS; }
}
