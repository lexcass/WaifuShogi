/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.insanelyinsane.waifushogi.GameState;
import com.insanelyinsane.waifushogi.Player;
import com.insanelyinsane.waifushogi.handlers.RequestHandler;
import com.insanelyinsane.waifushogi.WaifuShogi;
import com.insanelyinsane.waifushogi.containers.Board;
import com.insanelyinsane.waifushogi.containers.Hand;
import com.insanelyinsane.waifushogi.pieces.Pawn;
import com.insanelyinsane.waifushogi.pieces.Piece;
import com.insanelyinsane.waifushogi.pieces.Team;
import com.insanelyinsane.waifushogi.Referee;
import com.insanelyinsane.waifushogi.actors.BoardObject;
import com.insanelyinsane.waifushogi.actors.HandObject;
import com.insanelyinsane.waifushogi.actors.Waifu;
import com.insanelyinsane.waifushogi.assets.Textures;
import com.insanelyinsane.waifushogi.gamecomponents.GameComponentType;
import com.insanelyinsane.waifushogi.gamecomponents.HighlighterComponent;
import com.insanelyinsane.waifushogi.pieces.Bishop;
import com.insanelyinsane.waifushogi.pieces.GoldGeneral;
import com.insanelyinsane.waifushogi.pieces.JadeGeneral;
import com.insanelyinsane.waifushogi.pieces.Knight;
import com.insanelyinsane.waifushogi.pieces.Lance;
import com.insanelyinsane.waifushogi.pieces.Rook;
import com.insanelyinsane.waifushogi.pieces.SilverGeneral;
import com.insanelyinsane.waifushogi.ui.MatchUI;
import com.insanelyinsane.waifushogi.ui.UIController;
import java.util.Map.Entry;
import java.util.Stack;

/**
 *
 * @author Alex Cassady
 */
public abstract class MatchScreen extends Screen
{
    // Assets to load
    Texture _woodTex;
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
    
    Player _redPlayer;
    Player _bluePlayer;
    
    RequestHandler _requestHandler;
    Referee _referee;
    GameState _gameState;
    MatchUI _ui;
    
    
    public MatchScreen(WaifuShogi game, SpriteBatch batch, UIController _ui)
    {
        super(game, batch, _ui);
        
        // Load assets
        loadAsset(Textures.WOOD_BACKGROUND, Texture.class);
        loadAsset(Textures.BOARD, Texture.class);
        loadAsset(Textures.PAWN, Texture.class);
        loadAsset(Textures.ROOK, Texture.class);
        loadAsset(Textures.BISHOP, Texture.class);
        loadAsset(Textures.LANCE, Texture.class);
        loadAsset(Textures.KNIGHT, Texture.class);
        loadAsset(Textures.SILVER_GENERAL, Texture.class);
        loadAsset(Textures.GOLD_GENERAL, Texture.class);
        loadAsset(Textures.JADE_GENERAL, Texture.class);
    }
    
    @Override
    public void create()
    {
        //////////////////////////////
        // Create assets
        AssetManager assets = getAssets();
        
        _woodTex = assets.get(Textures.WOOD_BACKGROUND);
        _boardTex = assets.get(Textures.BOARD);
        _pawnTex = assets.get(Textures.PAWN);
        _rookTex = assets.get(Textures.ROOK);
        _bishTex = assets.get(Textures.BISHOP);
        _lanceTex = assets.get(Textures.LANCE);
        _knightTex = assets.get(Textures.KNIGHT);
        _silverTex = assets.get(Textures.SILVER_GENERAL);
        _goldTex = assets.get(Textures.GOLD_GENERAL);
        _jadeTex = assets.get(Textures.JADE_GENERAL);
        
        
        // Set the background of the Match Screen
        setBackground(_woodTex);
        
        // Load the MatchUI
        _ui = new MatchUI(getStage(), this);
        
        // Initialize Hands and Board (logic components)
        Hand blue = new Hand(Team.BLUE);
        Hand red = new Hand(Team.RED);
        Board board = new Board();
        int boardX = Gdx.graphics.getWidth() / 2 - _boardTex.getWidth() / 2;
        int boardY = Gdx.graphics.getHeight() / 2 - _boardTex.getHeight() / 2;
        
        
        // Add GameComponents to extend Screen functionality
        addComponent(new HighlighterComponent(boardX, boardY));
        
         // Wire the RequestHandler to this MatchScreen
         _redPlayer = new Player(Player.Type.LOCAL_ONE, true);
         _bluePlayer = getBluePlayer();
         _referee = new Referee(_redPlayer, _bluePlayer, board, red, blue);
         
        _requestHandler = initRequestHandler();//new RequestHandler(this, _referee, getComponent(GameComponentType.HIGHLIGHTER), _ui, _ui);
        
        
        // Initialize HandObjects and BoardObject (visual components)
        _board = new BoardObject(_boardTex, boardX, boardY, board, _requestHandler);
        addActor(_board);
        
        int offsetFromBoard = Board.CELL_HEIGHT * 2;
        
        _blueHand = new HandObject(boardX, boardY + _boardTex.getHeight() + offsetFromBoard, Board.CELL_WIDTH * Board.COLS, Board.CELL_HEIGHT, blue, getFont(), _requestHandler);
        addActor(_blueHand);
        
        _redHand = new HandObject(boardX, boardY - offsetFromBoard, Board.CELL_WIDTH * Board.COLS, Board.CELL_HEIGHT, red, getFont(), _requestHandler);
        addActor(_redHand);
        
        // Add Pieces to the board
        setupGameBoard();
        
        // Store GameState
        _gameState = new GameState(board, red, blue);
        
        // Call abstract method that will extend creation logic for MatchScreens
        setupMatch();
        
        // Load the UI
        getUIController().loadUI(_ui);
    }
    
    
    // Override in child
    protected abstract void setupMatch();
    //protected abstract Player getRedPlayer();
    protected abstract Player getBluePlayer();
    protected abstract RequestHandler initRequestHandler();
    
    
    /**
     * Add the pieces to the board during creation or reset.
     */
    private void setupGameBoard() 
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
     * Also adds piece as a new game object for the MatchScreen and adds to touch listeners
 to receive touch events.
     * @param piece
     * @param tex
     * @param row
     * @param col 
     */
    public void addPiece(Piece piece, Texture tex, int row, int col)
    {
        if (_board.getBoard().addPiece(piece, row, col))
        {
            int xPos = (int)_board.getX() + col * Board.CELL_WIDTH;
            int yPos = (int)_board.getY() + row * Board.CELL_HEIGHT;
            
            Waifu obj = new Waifu(tex, xPos, yPos, piece, _board, _redHand, _blueHand);
            _requestHandler.registerWaifu(obj);
            addActor(obj);
        }
        else
        {
            Gdx.app.debug("Warning", piece.getType().toString() + " was not added at (" + row + ", " + col + ").");
        }
    }
    
    
    @Override
    public void handleGameQuit()
    {
        changeScreen(ScreenType.MAIN_MENU);
    }
    
    
    
    public MatchUI getUI() { return _ui; }
    public GameState getGameState() { return _gameState; }
    public Referee getReferee() { return _referee; }
    protected RequestHandler getRequestHandler() { return _requestHandler; }
    protected Player getRedPlayer() { return _redPlayer; }
}
