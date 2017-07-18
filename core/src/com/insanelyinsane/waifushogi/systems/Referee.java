/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.systems;

import com.insanelyinsane.waifushogi.events.TouchEvent;
import com.insanelyinsane.waifushogi.listeners.TouchListener;
import com.insanelyinsane.waifushogi.objects.Board;
import com.insanelyinsane.waifushogi.objects.Cell;
import com.insanelyinsane.waifushogi.objects.GameObject;
import com.insanelyinsane.waifushogi.objects.pieces.Piece;
import java.util.List;

/**
 *
 * @author alex
 */
public class Referee implements TouchListener
{
    private final GameObject<Board> _board;
    private int _boardX;
    
    
    public Referee(GameObject<Board> board)
    {
        _board = board;
    }
    
    
    @Override
    public void onTouch(TouchEvent e)
    {
        // Board touched
        if (_board.containsPoint(e.getX(), e.getY()))
        {
            // Get piece touched on board
            int r = (int)e.getY() / Cell.HEIGHT;
            int c = (int)e.getX() / Cell.WIDTH;
            
            Piece piece = _board.getObject().getCellAt(r, c).getPiece();
            
            // Retrieve vaild moves
            List<Cell> validMoves = piece.getValidMoves(_board.getObject().getCells(), r, c);
            
            if (validMoves.size() > 0)
            {
                // Dispatch MoveEvent
                
                // Dispatch CaptureEvent
            }
        }
    }
}
