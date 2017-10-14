/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.insanelyinsane.waifushogi.RequestHandler;
import com.insanelyinsane.waifushogi.TestLoader;
import com.insanelyinsane.waifushogi.WaifuShogi;
import com.insanelyinsane.waifushogi.listeners.QuitListener;
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
import com.insanelyinsane.waifushogi.ui.PlayUI;
import com.insanelyinsane.waifushogi.ui.UI;
import com.insanelyinsane.waifushogi.ui.UIController;

/**
 *
 * @author alex
 */
public class PlayScreen extends Screen implements QuitListener
{
    // Assets to load
    Texture _woodTex;
    BitmapFont _font = new BitmapFont();
    
    // Textures
    Texture _boardTex;
    Texture _pawnTex;
    Texture _rookTex;
    Texture _knightTex;
    Texture _bishTex;
    Texture _lanceTex;
    Texture _silverTex;
    Texture _goldTex;
    Texture _jadeTex;
    
    // Objects to update
    BoardObject  _board;
    HandObject _redHand;
    HandObject _blueHand;
    List<Waifu> _waifus;
    
    // Systems
    Highlighter _highlighter;
    RequestHandler _requestHandler;
    
    List<TouchListener> _touchListeners;
    
    
    // Debugging
    TestLoader _testLoader;
    
    
    public PlayScreen(WaifuShogi game, SpriteBatch batch, UIController ui)
    {
        super(game, batch, ui);
        
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
        _boardTex = assets.get("textures/ShogiBoard.png");
        _pawnTex = assets.get("textures/Pawn.png");
        _rookTex = assets.get("textures/Rook.png");
        _bishTex = assets.get("textures/Bishop.png");
        _lanceTex = assets.get("textures/Lance.png");
        _knightTex = assets.get("textures/Knight.png");
        _silverTex = assets.get("textures/SilverGeneral.png");
        _goldTex = assets.get("textures/GoldGeneral.png");
        _jadeTex = assets.get("textures/JadeGeneral.png");
        
        setBackground(_woodTex);
        
        // Create UI
        PlayUI ui = new PlayUI(getStage(), this);
        
        // Initialize logic objects (Hand, Board, Highlighter, RequestHandler, etc.)
        Hand blue = new Hand(Team.BLUE);
        Hand red = new Hand(Team.RED);
        Board board = new Board();
        int boardX = Gdx.graphics.getWidth() / 2 - _boardTex.getWidth() / 2;
        int boardY = Gdx.graphics.getHeight() / 2 - _boardTex.getHeight() / 2;
        
        _highlighter = new Highlighter(boardX, boardY);
        _requestHandler = new RequestHandler(new Referee(board, red, blue), _highlighter, ui);
        
        
        ///////////////////////////////
        // Initialize board object
        _board = new BoardObject(_boardTex, boardX, boardY, board, _requestHandler);
        addActor(_board);
        
        
        //////////////////////////////////
        // Initialize player hand objects
        
        _blueHand = new HandObject(0, Board.CELL_HEIGHT, Board.CELL_WIDTH, Board.CELL_HEIGHT * Piece.Type.SIZE, blue, _font, _requestHandler);
        addActor(_blueHand);
        
        // The red hand builds from bottom to top (x, y is bottom-left), so negative width and height will give proper bounds for touch coords.
        
        _redHand = new HandObject(Gdx.graphics.getWidth() - Board.CELL_WIDTH, Board.CELL_HEIGHT, Board.CELL_WIDTH, Board.CELL_HEIGHT * Piece.Type.SIZE, red, _font, _requestHandler);
        addActor(_redHand);
        
        
        // Add the UI's actors to the stage and draw on top of Board and Pieces
        getUIController().loadUI(ui);
    }
    
    
    /**
     * Add the pieces to the board during creation or reset.
     */
    private void addPieces() 
    {
        // Create pieces and place into cells
        if (WaifuShogi.DEBUG)
        {
            _testLoader.loadTest(WaifuShogi.testToLoad);
        }
        else
        {
            addPiece(new Pawn(Team.RED), _pawnTex, 0, 1);
            addPiece(new Pawn(Team.RED), _pawnTex, 1, 0);
            addPiece(new Rook(Team.BLUE), _rookTex, 5, 5);
            addPiece(new Pawn(Team.RED), _pawnTex, 4, 5);
            addPiece(new Pawn(Team.BLUE), _pawnTex, 7, 7);
            addPiece(new Pawn(Team.BLUE), _pawnTex, 6, 6);
            addPiece(new Lance(Team.RED), _lanceTex, 3, 4);
            addPiece(new Knight(Team.RED), _knightTex, 3, 6);
        }
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
        _requestHandler.registerWaifu(obj);
        addActor(obj);
    }
    
    
    public void reset()
    {
        _board.getBoard().clear();
        _waifus.clear();
        System.out.println("Waifus: " + _waifus.size());
        
        addPieces();
    }
    
    
    @Override
    public void update(float delta)
    {
        //////////////////////////////////////
        // Update objects here
    }
    
    
    @Override
    public void draw(Batch batch)
    {
        //////////////////////////////////////////
        // Draw highlighted cells to the screen
        _highlighter.draw(batch);
    }
    
    
    @Override
    public void handleGameQuit()
    {
        changeScreen(ScreenType.MAIN_MENU);
    }
}
