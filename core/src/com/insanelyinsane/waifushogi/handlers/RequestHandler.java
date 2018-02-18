/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.handlers;

import com.badlogic.gdx.Gdx;
import com.insanelyinsane.waifushogi.Player;
import com.insanelyinsane.waifushogi.Referee;
import com.insanelyinsane.waifushogi.Sender;
import com.insanelyinsane.waifushogi.events.CaptureEvent;
import com.insanelyinsane.waifushogi.events.MoveEvent;
import com.insanelyinsane.waifushogi.events.DropEvent;
import com.insanelyinsane.waifushogi.events.SelectionEvent;
import com.insanelyinsane.waifushogi.listeners.CaptureListener;
import com.insanelyinsane.waifushogi.listeners.MoveListener;
import com.insanelyinsane.waifushogi.listeners.PromotionListener;
import com.insanelyinsane.waifushogi.listeners.SelectionListener;
import com.insanelyinsane.waifushogi.containers.Board;
import com.insanelyinsane.waifushogi.actors.Waifu;
import com.insanelyinsane.waifushogi.events.TurnEndEvent;
import com.insanelyinsane.waifushogi.pieces.Piece;
import java.util.LinkedList;
import java.util.List;
import com.insanelyinsane.waifushogi.interfaces.PromotionConfirmation;
import com.insanelyinsane.waifushogi.interfaces.WinConfirmation;
import com.insanelyinsane.waifushogi.listeners.DropListener;
import com.insanelyinsane.waifushogi.listeners.TurnEndListener;
import com.insanelyinsane.waifushogi.pieces.Team;
import com.insanelyinsane.waifushogi.screens.MatchScreen;
import com.insanelyinsane.waifushogi.screens.Screen;
import com.insanelyinsane.waifushogi.screens.ScreenType;

/**
 * The RequestHandler acts as a proxy between the Board and Hand objects and the
 Referee object (the rule enforcer). The Board and Hands will make requests to the 
 RequestHandler (selection, move, etc.), and if the Referee determines the request
 is valid, a generated event is dispatched to the respective listeners.
 * @author Alex Cassady
 */
public abstract class RequestHandler implements PromotionHandler, WinGameHandler
{
    protected final Screen  _screen;
    protected final Referee _referee;
    protected final List<Waifu> _waifus;
    protected final List<SelectionListener> _selectionListeners;
    protected final List<MoveListener> _moveListeners;
    protected final List<DropListener> _dropListeners;
    protected final List<CaptureListener> _captureListeners;
    protected final List<PromotionListener> _promotionListeners;
    protected final List<TurnEndListener> _turnEndListeners;
    protected final PromotionConfirmation _promoConfirmer;
    protected final WinConfirmation  _winConfirmer;
    
    
   public abstract void requestSelection(Sender from, Piece target, int r, int c);
   public abstract void requestMove(Sender sender, int r, int c);
   public abstract void requestDrop(int r, int c);
   protected abstract void requestPromotion(Piece p, boolean auto);
   
   
   public void registerSelectionListener(SelectionListener l)
    {
        if (l != null)
        {
            _selectionListeners.add(l);
        }
        else
        {
            Gdx.app.debug("SelectionListener error", "Listener was not registered to the RequestHandler.");
        }
    }
    
    
    public void registerMoveListener(MoveListener l)
    {
        if (l != null)
        {
            _moveListeners.add(l);
        }
        else
        {
            Gdx.app.debug("MoveListener error", "Listener was not registered to the RequestHandler.");
        }
    }
    
    
    public void registerDropListener(DropListener l)
    {
        if (l != null)
        {
            _dropListeners.add(l);
        }
        else
        {
            Gdx.app.debug("DropListener error", "Listener was not registered to the RequestHandler.");
        }
    }
    
    
    public void registerCaptureListener(CaptureListener l)
    {
        if (l != null)
        {
            _captureListeners.add(l);
        }
        else
        {
            Gdx.app.debug("CaptureListener error", "Listener was not registered to the RequestHandler.");
        }
    }
    
    
    public void registerPromotionListener(PromotionListener l)
    {
        if (l != null)
        {
            _promotionListeners.add(l);
        }
        else
        {
            Gdx.app.debug("PromotionListener error", "Listener was not registered to the RequestHandler.");
        }
    }
    
    
    public void registerTurnEndListener(TurnEndListener l)
    {
        if (l != null)
        {
            _turnEndListeners.add(l);
        }
        else
        {
            Gdx.app.debug("TurnEndListener error", "Listener was not registered to the RequestHandler.");
        }
    }
    
    
    
    /**
     * Add a Waifu object to each of the listener lists (selection, move, replace, and capture).
     * @param w 
     */
    public void registerWaifu(Waifu w)
    {
        _waifus.add(w);
        _selectionListeners.add(w);
        _moveListeners.add(w);
        _dropListeners.add(w);
        _captureListeners.add(w);
        _promotionListeners.add(w);
    }
   
   
   
   /**
     * Stores a reference to the Referee and registered Waifus, and uses the Referee's
     * references to the Board and Hands to register them (and the highlighter) as listeners.
     * 
     * ///////////////////////////////////////////////////////////////////////
     * Important: Waifus that want to receive events must manually register to the RequestHandler
     * via RequestHandler::registerWaifu().
     * @param ref
     * @param highlighter
     * @param c         The object that handles confirmation of promotion from the player (UI).
     */
    public RequestHandler(MatchScreen screen, Referee ref, SelectionListener highlighter, PromotionConfirmation c, WinConfirmation w)
    {
        _waifus = new LinkedList<>();
        _selectionListeners = new LinkedList<>();
        _moveListeners = new LinkedList<>();
        _dropListeners = new LinkedList<>();
        _captureListeners = new LinkedList<>();
        _promotionListeners = new LinkedList<>();
        _turnEndListeners = new LinkedList<>();
        
        _promoConfirmer = c;
        _winConfirmer = w;
        _screen = screen;
        _referee = ref;
        
        // Register listeners
        // Selection
        _selectionListeners.add(highlighter);
        
        // Move
        _moveListeners.add(_referee.getBoard());
        
        // Replace
        _dropListeners.add(_referee.getBoard());
        _dropListeners.add(_referee.getRedHand());
        _dropListeners.add(_referee.getBlueHand());
        
        // Capture
        _captureListeners.add(_referee.getRedHand());
        _captureListeners.add(_referee.getBlueHand());
    }
}
