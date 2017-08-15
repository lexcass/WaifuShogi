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
import com.insanelyinsane.waifushogi.TestLoader;
import com.insanelyinsane.waifushogi.WaifuShogi;
import com.insanelyinsane.waifushogi.events.TouchEvent;
import com.insanelyinsane.waifushogi.listeners.ScreenChangeListener;
import com.insanelyinsane.waifushogi.listeners.TouchListener;
import com.insanelyinsane.waifushogi.objects.Board;
import com.insanelyinsane.waifushogi.objects.Hand;
import com.insanelyinsane.waifushogi.objects.pieces.Piece;
import com.insanelyinsane.waifushogi.objects.pieces.Team;
import com.insanelyinsane.waifushogi.systems.Highlighter;
import com.insanelyinsane.waifushogi.systems.Referee;
import java.util.LinkedList;
import java.util.List;
import com.insanelyinsane.waifushogi.objects.gameobjects.BoardObject;
import com.insanelyinsane.waifushogi.objects.gameobjects.HandObject;
import com.insanelyinsane.waifushogi.objects.gameobjects.Waifu;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author alex
 */
public class TestScreen extends Screen
{
    TestLoader _testLoader;
    
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
    
    
    public TestScreen(ScreenChangeListener game, SpriteBatch batch)
    {
        super(game, batch);
        
        _testLoader = new TestLoader(this);
        
        _waifus = new LinkedList<>();
        _touchListeners = new LinkedList<>();
        
        // Load assets
        loadAsset("textures/woodbg.jpg", Texture.class);
        loadAsset("textures/ShogiBoard.png", Texture.class);
        loadAsset("textures/Pawn.png", Texture.class);
        loadAsset("textures/Rook.png", Texture.class);
        loadAsset("textures/Bishop.png", Texture.class);
        loadAsset("textures/Lance.png", Texture.class);
        loadAsset("textures/Knight.png", Texture.class);
        loadAsset("textures/SilverGeneral.png", Texture.class);
        loadAsset("textures/GoldGeneral.png", Texture.class);
        loadAsset("textures/JadeGeneral.png", Texture.class);
    }
    
    @Override
    public void create()
    {
        //////////////////////////////
        // Create assets
        AssetManager assets = getAssets();
        
        _woodTex = assets.get("textures/woodbg.jpg");
        Texture boardTex = assets.get("textures/ShogiBoard.png");
        
        ///////////////////////////////
        // Initialize board
        int boardX = Gdx.graphics.getWidth() / 2 - boardTex.getWidth() / 2;
        int boardY = Gdx.graphics.getHeight() / 2 - boardTex.getHeight() / 2;
        _board = new BoardObject(boardTex, boardX, boardY, new Board());
        
        
        //////////////////////////////////
        // Initialize player hands
        
        //Gdx.graphics.getHeight() - 
        _blueHand = new HandObject(0, Board.CELL_HEIGHT, Board.CELL_WIDTH, Board.CELL_HEIGHT * Piece.Type.SIZE, new Hand(Team.BLUE));
        
        // The red hand builds from bottom to top (x, y is bottom-left), so negative width and height will give proper bounds for touch coords.
        _redHand = new HandObject(Gdx.graphics.getWidth() - Board.CELL_WIDTH, Board.CELL_HEIGHT, Board.CELL_WIDTH, Board.CELL_HEIGHT * Piece.Type.SIZE, new Hand(Team.RED));
        
        
        /////////////////////////////////////////
        // Initialize systems
        _highlighter = new Highlighter(_board);
        _referee = new Referee(_board, _highlighter, _waifus, _redHand, _blueHand);
        
        _touchListeners.add(_referee);
        
        
        ///////////////////////////////////////
        // Load tests
        reset();
        loadTestFor(WaifuShogi.testToLoad);
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
        
        Waifu obj = new Waifu(tex, _board.getX() + col * Board.CELL_WIDTH, _board.getY() + row * Board.CELL_HEIGHT, piece, _board, _redHand, _blueHand);
        _waifus.add(obj);
    }
    
    
    public void reset()
    {
        _board.getBoard().clear();
        _waifus.clear();
    }
    
    
    private void loadTestFor(String name)
    {
        if (name.equalsIgnoreCase("pawn"))
        {
            _testLoader.loadPawnTest();
        }
        else if (name.equalsIgnoreCase("rook"))
        {
            _testLoader.loadRookTest();
        }
        else if (name.equalsIgnoreCase("bishop"))
        {
            _testLoader.loadBishopTest();
        }
        else if (name.equalsIgnoreCase("knight"))
        {
            _testLoader.loadKnightTest();
        }
        else if (name.equalsIgnoreCase("lance"))
        {
            _testLoader.loadLanceTest();
        }
        else if (name.equalsIgnoreCase("silver"))
        {
            _testLoader.loadSilverTest();
        }
        else if (name.equalsIgnoreCase("gold"))
        {
            _testLoader.loadGoldTest();
        }
        else if (name.equalsIgnoreCase("jade"))
        {
            _testLoader.loadJadeTest();
        }
        else
        {
            return;
        }
    }
    
    
    @Override
    public void render(float delta)
    {
        SpriteBatch batch = getSpriteBatch();
        
        //////////////////////////////////////
        // Update objects here
        _waifus.forEach(w -> w.update(delta));

        
        ////////////////////////////////////////
        // Draw textures and text to the screen
        batch.begin();
        batch.draw(_woodTex, 0, 0);
        _board.draw(batch);
        
        
        //////////////////////////////////////////
        // Draw highlighted cells to the screen
        _highlighter.draw(batch);
        
        
        //////////////////////////////////////////
        // Draw waifu textures to the screen
        // Update animations
        for (Waifu waifu : _waifus)
        {
            Piece p = waifu.getPiece();
            
            // Only draw waifus that have pieces
            if (p != null)
            {
                waifu.update(delta);
                waifu.draw(batch);
            }
        }
        batch.setColor(Color.WHITE);
        
        /////////////////////////////////////////////////
        // Draw quantity at corner of captured pieces
        final int xOffset = 4;
        
        for (Stack<Piece> s  : _blueHand.getHand().getPieces().values())
        {
            if (!s.empty())
            {
                Piece p = s.peek();
                _font.draw(batch, s.size() + "", _blueHand.getX() + xOffset, Gdx.graphics.getHeight() - _blueHand.getY() - p.getType().getIndex() * Board.CELL_HEIGHT);
            }
        }
        
        for (Stack<Piece> s  : _redHand.getHand().getPieces().values())
        {
            if (!s.empty())
            {
                Piece p = s.peek();
                _font.draw(batch, s.size() + "", _redHand.getX() + xOffset, _redHand.getY() + (p.getType().getIndex() + 1) * Board.CELL_HEIGHT );
            }
        }
        
        
        // DEBUGGING "BUTTON"
        if (WaifuShogi.debugMode)
            _font.draw(batch, "Reset", 0, Gdx.graphics.getHeight());
        
        
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
        
        // For testing purposes, if the top-left corner is touched, reset the game
        // REMOVE! DEBUGGING ONLY!!!
        if (WaifuShogi.debugMode)
        {
            if (screenX < 32 && screenY < 32) 
            {
                reset();
                loadTestFor(WaifuShogi.testToLoad);
            }
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
        _font.dispose();
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
