/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.test.movement;

import com.insanelyinsane.waifushogi.events.MoveEvent;
import com.insanelyinsane.waifushogi.objects.Board;
import com.insanelyinsane.waifushogi.objects.pieces.Piece;
import com.insanelyinsane.waifushogi.objects.pieces.Team;
import com.insanelyinsane.waifushogi.test.BoardInitHelper;
import com.insanelyinsane.waifushogi.test.Test;

/**
 *
 * @author A Wild Popo Appeared
 */
public class PawnMovementTest implements Test
{
    Board _board;
    BoardInitHelper _helper;
    
    @Override
    public void test() 
    {
        _board = new Board();
        _helper = new BoardInitHelper(_board);
        
        _helper.addPawn(Team.RED, 0, 0);
        moveUnopposed();
        moveToAlly();
        moveToCapture();
    }
    
    // 1
    private void moveUnopposed()
    {
        Piece pawn = _board.getPieceAt(0, 0);
        assert pawn.getValidMoves(_board.getPieces(), 0, 0)[1][0] == true : "Pawn couldn't move up unopposed.";
    }
    
    // 2
    private void moveToAlly()
    {
        Piece pawn = _board.getPieceAt(0, 0);
        _helper.addPawn(Team.RED, 1, 0);
        assert pawn.getValidMoves(_board.getPieces(), 0, 0)[1][0] == false : "Pawn moved into its ally.";
    }
    
    // 3
    private void moveToCapture()
    {
        Piece pawn = _board.getPieceAt(0, 0);
        _helper.removePieceAt(1, 0);
        _helper.addPawn(Team.BLUE, 1, 0);
        assert pawn.getValidMoves(_board.getPieces(), 0, 0)[1][0] == true : "Pawn didn't capture the enemy.";
    }
}
