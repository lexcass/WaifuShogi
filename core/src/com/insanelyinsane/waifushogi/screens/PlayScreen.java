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
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.insanelyinsane.waifushogi.TestLoader;
import com.insanelyinsane.waifushogi.WaifuShogi;
import com.insanelyinsane.waifushogi.events.TouchEvent;
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
import com.insanelyinsane.waifushogi.objects.pieces.Knight;
import com.insanelyinsane.waifushogi.objects.pieces.Lance;
import com.insanelyinsane.waifushogi.objects.pieces.Rook;
import java.util.Stack;

/**
 *
 * @author alex
 */
public class PlayScreen extends Screen
{
    // Constants
    
    
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
    
    
    // Debugging
    TestLoader _testLoader;
    
    
    public PlayScreen(WaifuShogi game, SpriteBatch batch)
    {
        super(game, batch);
        
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
        
        
        _testLoader = new TestLoader(this);
    }
    
    @Override
    public void create()
    {
        //////////////////////////////
        // Create assets
        AssetManager assets = getAssets();
        
        _woodTex = assets.get("textures/woodbg.jpg");
        Texture boardTex = assets.get("textures/ShogiBoard.png");
        Texture pawnTex = assets.get("textures/Pawn.png");
        Texture rookTex = assets.get("textures/Rook.png");
        Texture bishTex = assets.get("textures/Bishop.png");
        Texture lanceTex = assets.get("textures/Lance.png");
        Texture knightTex = assets.get("textures/Knight.png");
        Texture silverTex = assets.get("textures/SilverGeneral.png");
        Texture goldTex = assets.get("textures/GoldGeneral.png");
        Texture jadeTex = assets.get("textures/JadeGeneral.png");
        
        setBackground(_woodTex);
        
        ///////////////////////////////
        // Initialize board
        int boardX = Gdx.graphics.getWidth() / 2 - boardTex.getWidth() / 2;
        int boardY = Gdx.graphics.getHeight() / 2 - boardTex.getHeight() / 2;
        _board = new BoardObject(boardTex, boardX, boardY, new Board());
        addActor(_board);
        
        
        //////////////////////////////////
        // Initialize player hands
        
        //Gdx.graphics.getHeight() - 
        _blueHand = new HandObject(0, Board.CELL_HEIGHT, Board.CELL_WIDTH, Board.CELL_HEIGHT * Piece.Type.SIZE, new Hand(Team.BLUE));
        addActor(_blueHand);
        
        // The red hand builds from bottom to top (x, y is bottom-left), so negative width and height will give proper bounds for touch coords.
        _redHand = new HandObject(Gdx.graphics.getWidth() - Board.CELL_WIDTH, Board.CELL_HEIGHT, Board.CELL_WIDTH, Board.CELL_HEIGHT * Piece.Type.SIZE, new Hand(Team.RED));
        addActor(_redHand);
        
        
        // Create pieces and place into cells
        
        if (WaifuShogi.DEBUG)
        {
            _testLoader.loadTest(WaifuShogi.testToLoad);
        }
        else
        {
            addPiece(new Pawn(Team.RED), pawnTex, 0, 1);
            addPiece(new Pawn(Team.RED), pawnTex, 1, 0);
            addPiece(new Rook(Team.BLUE), rookTex, 5, 5);
            addPiece(new Pawn(Team.RED), pawnTex, 4, 5);
            addPiece(new Pawn(Team.BLUE), pawnTex, 7, 7);
            addPiece(new Pawn(Team.BLUE), pawnTex, 6, 6);
            addPiece(new Lance(Team.RED), lanceTex, 3, 4);
            addPiece(new Knight(Team.RED), knightTex, 3, 6);
        }
        
        /////////////////////////////////////////
        // Initialize systems
        _highlighter = new Highlighter(_board);
        _referee = new Referee(_board, _highlighter, _waifus, _redHand, _blueHand);
        
        _touchListeners.add(_referee);
    }
    
    
    /**
     * Add a piece with texture to cell at row, col on board.
     * Also adds piece as a new game object for the PlayScreen and adds to touch listeners
 to receive touch events.
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
        addActor(obj);
    }
    
    
    public void reset()
    {
        _board.getBoard().clear();
        _waifus.clear();
        System.out.println("Waifus: " + _waifus.size());
    }
    
    
    @Override
    public void update(float delta)
    {
        //////////////////////////////////////
        // Update objects here
        //_waifus.forEach(w -> w.act(delta));
    }
    
    
    @Override
    public void draw(Batch batch)
    {
        //////////////////////////////////////////
        // Draw highlighted cells to the screen
        _highlighter.draw(batch);
        
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
