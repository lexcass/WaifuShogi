/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.screens;

import java.util.LinkedList;
import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.insanelyinsane.waifushogi.RequestHandler;
import com.insanelyinsane.waifushogi.WaifuShogi;
import com.insanelyinsane.waifushogi.listeners.TouchListener;
import com.insanelyinsane.waifushogi.containers.Board;
import com.insanelyinsane.waifushogi.containers.Hand;
import com.insanelyinsane.waifushogi.pieces.Pawn;
import com.insanelyinsane.waifushogi.pieces.Piece;
import com.insanelyinsane.waifushogi.pieces.Team;
import com.insanelyinsane.waifushogi.systems.Highlighter;
import com.insanelyinsane.waifushogi.systems.Referee;
import com.insanelyinsane.waifushogi.actors.BoardObject;
import com.insanelyinsane.waifushogi.actors.HandObject;
import com.insanelyinsane.waifushogi.actors.Waifu;
import com.insanelyinsane.waifushogi.pieces.Bishop;
import com.insanelyinsane.waifushogi.pieces.GoldGeneral;
import com.insanelyinsane.waifushogi.pieces.JadeGeneral;
import com.insanelyinsane.waifushogi.pieces.Knight;
import com.insanelyinsane.waifushogi.pieces.Lance;
import com.insanelyinsane.waifushogi.pieces.Rook;
import com.insanelyinsane.waifushogi.pieces.SilverGeneral;
import com.insanelyinsane.waifushogi.ui.PlayUI;
import com.insanelyinsane.waifushogi.ui.UIController;

/**
 *
 * @author Alex Cassady
 */
public class PlayScreen extends Screen
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
        
        
        // Set the background of the screen to wood texture
        setBackground(_woodTex);
        
        // Create UI
        PlayUI ui = new PlayUI(getStage(), this);
        
        // Initialize logic objects (Hand, Board, Highlighter, RequestHandler, etc.)
        Hand blue = new Hand(Team.BLUE);
        Hand red = new Hand(Team.RED);
        Board board = new Board();
        int boardX = Gdx.graphics.getWidth() / 2 - _boardTex.getWidth() / 2;
        int boardY = Gdx.graphics.getHeight() / 2 - _boardTex.getHeight() / 2;
        
        // Initialize systems (lifeblood of game logic)
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
        
        
        // Add Pieces to the board
        addPieces();
    }
    
    
    /**
     * Add the pieces to the board during creation or reset.
     */
    private void addPieces() 
    {
        // Setup game board
        for (int i = 0; i < 9; i++)
        {
            addPiece(new Pawn(Team.RED), _pawnTex, 2, i);
            addPiece(new Pawn(Team.BLUE), _pawnTex, 6, i);
        }

        addPiece(new Rook(Team.RED), _rookTex, 1, 7);
        addPiece(new Bishop(Team.RED), _bishTex, 1, 1);

        addPiece(new Rook(Team.BLUE), _rookTex, 7, 1);
        addPiece(new Bishop(Team.BLUE), _bishTex, 7, 7);

        addPiece(new Lance(Team.RED), _lanceTex, 0, 0);
        addPiece(new Lance(Team.RED), _lanceTex, 0, 8);
        addPiece(new Lance(Team.BLUE), _lanceTex, 8, 0);
        addPiece(new Lance(Team.BLUE), _lanceTex, 8, 8);

        addPiece(new Knight(Team.RED), _knightTex, 0, 1);
        addPiece(new Knight(Team.RED), _knightTex, 0, 7);
        addPiece(new Knight(Team.BLUE), _knightTex, 8, 1);
        addPiece(new Knight(Team.BLUE), _knightTex, 8, 7);

        addPiece(new SilverGeneral(Team.RED), _silverTex, 0, 2);
        addPiece(new SilverGeneral(Team.RED), _silverTex, 0, 6);
        addPiece(new SilverGeneral(Team.BLUE), _silverTex, 8, 2);
        addPiece(new SilverGeneral(Team.BLUE), _silverTex, 8, 6);

        addPiece(new GoldGeneral(Team.RED), _goldTex, 0, 3);
        addPiece(new GoldGeneral(Team.RED), _goldTex, 0, 5);
        addPiece(new GoldGeneral(Team.BLUE), _goldTex, 8, 3);
        addPiece(new GoldGeneral(Team.BLUE), _goldTex, 8, 5);

        addPiece(new JadeGeneral(Team.RED), _jadeTex, 0, 4);
        addPiece(new JadeGeneral(Team.BLUE), _jadeTex, 8, 4);
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
    
    
    /// NOTE: Instead of removing Waifus, just place them back at starting positions?
    /**
     * Reset the board to its initial state.
     */
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
