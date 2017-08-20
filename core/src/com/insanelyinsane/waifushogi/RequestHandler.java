/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi;

import com.insanelyinsane.waifushogi.events.CaptureEvent;
import com.insanelyinsane.waifushogi.events.MoveEvent;
import com.insanelyinsane.waifushogi.events.ReplaceEvent;
import com.insanelyinsane.waifushogi.events.SelectionEvent;
import com.insanelyinsane.waifushogi.listeners.CaptureListener;
import com.insanelyinsane.waifushogi.listeners.MoveListener;
import com.insanelyinsane.waifushogi.listeners.ReplaceListener;
import com.insanelyinsane.waifushogi.listeners.SelectionListener;
import com.insanelyinsane.waifushogi.objects.Board;
import com.insanelyinsane.waifushogi.objects.Hand;
import com.insanelyinsane.waifushogi.objects.gameobjects.Waifu;
import com.insanelyinsane.waifushogi.objects.pieces.Piece;
import com.insanelyinsane.waifushogi.systems.Referee;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author A Wild Popo Appeared
 */
public class RequestHandler
{
    public enum Sender { BOARD, HAND };
    
    private final Referee _referee;
    private final List<Waifu> _waifus;
    private final List<SelectionListener> _selectionListeners;
    private final List<MoveListener> _moveListeners;
    private final List<ReplaceListener> _replaceListeners;
    private final List<CaptureListener> _captureListeners;
    
    
    public RequestHandler(Referee ref, SelectionListener highlighter, List<Waifu> waifus)
    {
        _selectionListeners = new LinkedList<>();
        _moveListeners = new LinkedList<>();
        _replaceListeners = new LinkedList<>();
        _captureListeners = new LinkedList<>();
        
        _referee = ref;
        _waifus = waifus;
        
        // Register listeners
        // Selection
        _selectionListeners.add(highlighter);
        //_waifus.forEach(w -> _selectionListeners.add(w));
        
        // Move
        _moveListeners.add(_referee.getBoard());
        //_waifus.forEach(w -> _moveListeners.add(w));
        
        // Replace
        _replaceListeners.add(_referee.getBoard());
        _replaceListeners.add(_referee.getRedHand());
        _replaceListeners.add(_referee.getBlueHand());
        //_waifus.forEach(w -> _replaceListeners.add(w));
        
        // Capture
        _captureListeners.add(_referee.getRedHand());
        _captureListeners.add(_referee.getBlueHand());
        //_waifus.forEach(w -> _captureListeners.add(w));
    }
    
    
    public void requestSelection(Sender from, Piece target, int r, int c)
    {
        SelectionEvent e;
        if (from == Sender.BOARD)
        {
            e = _referee.selectPieceOnBoard(target, r, c);
        }
        else
        {
            e = _referee.selectPieceInHand(target);
        }
        
        if (e != null) _selectionListeners.forEach(l -> l.onWaifuSelected(e));
    }
    
    
    public void requestMove(int r, int c)
    {
        if (!inBounds(r, c)) return;
        
        // Generate move event and stop processing if move is invalid
        MoveEvent e = _referee.movePieceTo(r, c);
        if (e == null) return;
        
        // Capture the piece in the cell (if there is one)
        Piece captured = _referee.capturePieceAt(r, c);
        if (captured != null)
        {
            _captureListeners.forEach(l -> l.onWaifuCaptured(new CaptureEvent(captured)));
        }
        
        // Move the selected piece
        _moveListeners.forEach(l -> l.onWaifuMoved(e));
        
        // Deselect all pieces
        _selectionListeners.forEach(l -> l.onWaifuSelected(new SelectionEvent(null, _waifus.get(0).getPiece(), false)));
        
        // Finish turn
        _referee.finishTurn();
    }
    
    public void requestReplace(int r, int c)
    {
        if (!inBounds(r, c)) return;
        
        ReplaceEvent e = _referee.replacePieceTo(r, c);
        if (e == null) return;
        
        // Drop the selected piece
        _replaceListeners.forEach(l -> l.onWaifuReplaced(e));
        
        // Deselect all pieces
        _selectionListeners.forEach(l -> l.onWaifuSelected(new SelectionEvent(null, _waifus.get(0).getPiece(), false)));
        
        // Finish turn
        _referee.finishTurn();
    }
    
    
    public void registerWaifu(Waifu w)
    {
        _waifus.add(w);
        _selectionListeners.add(w);
        _moveListeners.add(w);
        _replaceListeners.add(w);
        _captureListeners.add(w);
    }
    
    
    private boolean inBounds(int r, int c)
    {
        return r < Board.ROWS || c < Board.COLS || r >= 0 || c >= 0;
    }
}
