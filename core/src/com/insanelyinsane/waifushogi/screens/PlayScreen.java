/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.insanelyinsane.waifushogi.events.TouchEvent;
import com.insanelyinsane.waifushogi.listeners.ScreenChangeListener;
import com.insanelyinsane.waifushogi.listeners.TouchListener;
import com.insanelyinsane.waifushogi.objects.Board;
import com.insanelyinsane.waifushogi.objects.Hand;
import com.insanelyinsane.waifushogi.objects.pieces.Pawn;
import com.insanelyinsane.waifushogi.objects.pieces.Piece;
import com.insanelyinsane.waifushogi.objects.pieces.Team;
import com.insanelyinsane.waifushogi.systems.Highlighter;
import com.insanelyinsane.waifushogi.systems.Referee;
import java.util.LinkedList;
import java.util.List;
import com.insanelyinsane.waifushogi.objects.gameobjects.BoardObject;
import com.insanelyinsane.waifushogi.objects.gameobjects.HandObject;
import com.insanelyinsane.waifushogi.objects.gameobjects.Waifu;
import com.insanelyinsane.waifushogi.objects.pieces.Rook;
import java.util.Stack;

/**
 *
 * @author alex
 */
public class PlayScreen extends Screen
{
    // Constants
    final Color RED_TINT = new Color(1.0f, 0.8f, 0.8f, 1.0f);
    final Color BLUE_TINT = new Color(0.8f, 0.8f, 1.0f, 1.0f);
    
    // Assets to load
    Texture _woodTex;
    BitmapFont _font = new BitmapFont();    // This is TEMPORARY!!!
    
    // Objects to update
    BoardObject  _board;
    HandObject _redHand;
    HandObject _blueHand;
    List<Waifu> _waifus;
    
    // Systems
    Referee _referee;
    Highlighter _highlighter;
    
    List<TouchListener> _touchListeners;
    
    
    public PlayScreen(ScreenChangeListener game, SpriteBatch batch)
    {
        super(game, batch);
        
        _waifus = new LinkedList<>();
        _touchListeners = new LinkedList<>();
        
        // Load assets
        loadAsset("textures/woodbg.jpg", Texture.class);
        loadAsset("textures/ShogiBoard.png", Texture.class);
        loadAsset("textures/Pawn.png", Texture.class);
        loadAsset("textures/Rook.png", Texture.class);
    }
    
    @Override
    public void create()
    {
        // Create assets
        AssetManager assets = getAssets();
        
        _woodTex = assets.get("textures/woodbg.jpg");
        Texture boardTex = assets.get("textures/ShogiBoard.png");
        Texture pawnTex = assets.get("textures/Pawn.png");
        Texture rookTex = assets.get("textures/Rook.png");
        
        
        // Initialize board
        int boardX = Gdx.graphics.getWidth() / 2 - boardTex.getWidth() / 2;
        int boardY = Gdx.graphics.getHeight() / 2 - boardTex.getHeight() / 2;
        _board = new BoardObject(boardTex, boardX, boardY, new Board());
        
        _redHand = new HandObject(0, Gdx.graphics.getHeight() - Board.CELL_HEIGHT * 2, new Hand(Team.RED));
        
        // Create pieces and place into cells
        addPiece(new Pawn(Team.RED), pawnTex, 2, 3);
        addPiece(new Rook(Team.RED), rookTex, 4, 7);
        addPiece(new Pawn(Team.BLUE), pawnTex, 3, 3);
        addPiece(new Rook(Team.RED), rookTex, 4, 3);
        addPiece(new Pawn(Team.BLUE), pawnTex, 5, 7);
        
        // Initialize systems
        _highlighter = new Highlighter(_board);
        _referee = new Referee(_board, _highlighter, _waifus, _redHand, _blueHand);
        
        _touchListeners.add(_referee);
    }
    
    
    /**
     * Add a piece with texture to cell at row, col on board.
     * Also adds piece as a new game object for the PlayScreen and adds to touch listeners
     * to receive touch events.
     * @param piece
     * @param tex
     * @param row
     * @param col 
     */
    public void addPiece(Piece piece, Texture tex, int row, int col)
    {
        _board.getBoard().addPiece(piece, row, col);
        
        Waifu obj = new Waifu(tex, _board.getX() + col * Board.CELL_WIDTH, _board.getY() + row * Board.CELL_HEIGHT, piece);
        _waifus.add(obj);
    }
    
    
    @Override
    public void render(float delta)
    {
        SpriteBatch batch = getSpriteBatch();
        
        // Update objects here
        _waifus.forEach(w -> w.update(delta));

        // Draw textures and text to the screen
        batch.begin();
        batch.draw(_woodTex, 0, 0);
        _board.draw(batch);
        
        // Draw highlighted cells to the screen
        _highlighter.draw(batch);
        
        // Draw waifu textures to the screen
        for (Waifu waifu : _waifus)
        {
            Piece p = waifu.getPiece();
            
            // Only draw waifus that have pieces
            if (p != null)
            {
                Color c = p.getTeam() == Team.RED ? RED_TINT : BLUE_TINT;
                batch.setColor(c);
                waifu.draw(batch);
            }
        }
        batch.setColor(Color.WHITE);
        
        
        // Draw quantity at corner of captured pieces
        for (Stack<Piece> s  : _redHand.getHand().getPieces().values())
        {
            if (!s.empty())
            {
                Piece p = s.peek();
                _font.draw(batch, s.size() + "", HandObject.X_POS + 4, HandObject.START_Y_POS - (p.getType().ordinal() - 1) * Board.CELL_HEIGHT );
            }
        }
        
        
        batch.end();
    }
    
    
    /**
     * Inform all touch listeners of the location the screen was touched (clicked) at in
     * render coordinates (bottom-to-top, left-to-right).
     * @param screenX
     * @param screenY
     * @param pointer
     * @param button
     * @return 
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button)
    {
        for (TouchListener l : _touchListeners)
        {
            // IMPORTANT: Touch coordinates go top-to-bottom left-to-right. This converts
            // these coordinates to bottom-to-top left-to-right which the rendering system
            // works with.
            
            l.onTouch(new TouchEvent(true, screenX, Gdx.graphics.getHeight() - screenY));
        }
        
        return true;
    }
    
    
    @Override
    public void resume()
    {
        
    }
    
    @Override
    public void pause()
    {
        
    }
    
    @Override
    public void resize(int x, int y)
    {
        
    }
    
    @Override
    public void show()
    {
        
    }
    
    @Override
    public void hide()
    {
        
    }
}
