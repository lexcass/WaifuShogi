/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.pieces;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.insanelyinsane.waifushogi.containers.Board;
import com.insanelyinsane.waifushogi.pieces.movepatterns.MovePattern;

/**
 *
 * @author Alex Cassady
 */
public abstract class Piece 
{
    private Team _team;
    private final Type _type;
    private boolean _captured;
    private boolean _promoted;
    
    private final MovePattern _standardPattern;
    private final MovePattern _promotedPattern;
    
    
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
    
    public Piece(Type type, Team team, MovePattern standard, MovePattern promoted)
    {
        _type = type;
        _team = team;
        _captured = false;
        _standardPattern = standard;
        
        if (standard == null)
        {
            throw new GdxRuntimeException("Standard move pattern for piece " + type.toString() + " cannot be null.");
        }
        
        
        if (promoted == null)
        {
            _promotedPattern = standard;
        }
        else
        {
            _promotedPattern = promoted;
        }
    }
    
    
    /**
     * Make a copy of the given Piece.
     * @param other 
     */
    public Piece(Piece other)
    {
        this._type = other._type;
        this._team = other._team;
        this._promoted = other._promoted;
        this._captured = other._captured;
        this._standardPattern = other._standardPattern;
        this._promotedPattern = other._promotedPattern;
    }
    
    
    /**
     * This method is used in conjuction with the copy method to
     * copy data into a child Piece when needed.
     * @param other 
     */
    protected void cloneStateInto(Piece other)
    {
        other.setCaptured(this.isCaptured());
        
        if (this.isPromoted())
        {
            other.promote();
        }
        else
        {
            other.demote();
        }
    }
    
    
    /////////////////////////////////////
    // Getters and setters
    public Type getType() { return _type; }
    public Team getTeam() { return _team; }
    public void setTeam(Team newTeam) 
    {
        _team = newTeam;
        _standardPattern.setTeam(_team);
        _promotedPattern.setTeam(_team);
    }
    
    public void setCaptured(boolean c) { _captured = c; }
    public boolean isCaptured() { return _captured; }
    public boolean isPromoted() { return _promoted; }
    
    public void promote() { _promoted = true; }
    public void demote() { _promoted = false; }
    
    
    /////////////////////////////////
    // Overrideable methods
//    protected abstract boolean[][] findValidMoves(final Piece[][] cells, int row, int col);
    protected abstract boolean[][] findValidDrops(final Piece[][] cells);
    
    /**
     * Make and return a copy of this Piece including child's move patterns and type.
     * @return 
     */
    public abstract Piece copy();
    
    
    /**
     * Returns a 2d array of cells corresponding to rows and cols on board
     * that the piece can move to when in play.
     * If promoted, returns promoted version of piece's valid moves.
     * @param cells
     * @param row
     * @param col
     * @return 
     */
    public final boolean[][] getValidMoves(final Piece[][] cells, int row, int col)
    {
        if (row >= 0 && col >= 0 && row < Board.ROWS && col < Board.COLS)
        {
            return (_promoted ? _promotedPattern.getValidMoves(cells, row, col) : _standardPattern.getValidMoves(cells, row, col));
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
    public final boolean[][] getValidDrops(final Piece[][] cells)
    {
        return findValidDrops(cells);
    }
    
    
    /**
     * A helper method for checking validity of a Drop. Returns true if the cell is empty..
     * @param board
     * @param valid
     * @param r
     * @param c
     */
    public final boolean addIfValidDrop(final Piece[][] board, boolean[][] valid, int r, int c)
    {
        if (inBounds(r, c))
        { 
            valid[r][c] = (board[r][c] == null);
            return valid[r][c];
        }
        
        return false;
    }
    
    
    /**
     * Return validity array after checking every cell on the board. Convenience method
     * covers most drop conditions.
     * @param board
     * @return 
     */
    public final boolean[][] checkAllForDrop(final Piece[][] board)
    {
        boolean[][] valid = new boolean[Board.ROWS][Board.COLS];
        
        for (int r = 0; r < Board.ROWS; r++)
        {
            for (int c = 0; c < Board.COLS; c++)
            {
                addIfValidDrop(board, valid, r, c);
            }
        }
        
        return valid;
    }
    
    
    /**
     * Returns true if the given row/col are in the bounds of the board.
     * @param r
     * @param c
     * @return 
     */
    public final static boolean inBounds(int r, int c) { return r >= 0 && c >=0 && r < Board.ROWS && c < Board.COLS; }
}
