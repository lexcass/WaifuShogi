/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.insanelyinsane.waifushogi.interfaces.PromotionConfirmer;
import com.insanelyinsane.waifushogi.interfaces.PromotionHandler;
import com.insanelyinsane.waifushogi.listeners.QuitListener;
import com.insanelyinsane.waifushogi.objects.pieces.Piece;

/**
 *
 * @author A Wild Popo Appeared
 */
public class PlayUI extends UI implements PromotionConfirmer
{
    private final QuitListener _quitListener;
    
    private Button _quitButton;
    private Button _menuButton;
    
    
    public PlayUI(Stage stage, QuitListener q)
    {
        super(stage);
        _quitListener = q;
    }
    
    @Override
    protected void createElements()
    {
        Table table = new Table();
        
        _menuButton = new ImageButton(getSkin(), "settings");
        _menuButton.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent e, float screenX, float screenY, int pointer, int button) { return true; }
            
            @Override
            public void touchUp(InputEvent e, float screenX, float screenY, int pointer, int button)
            {
                _quitButton.setVisible(!_quitButton.isVisible());
            }
        });
        
        _quitButton = new TextButton("Quit", getSkin());
        _quitButton.addListener(new InputListener()
        {
            @Override
            public boolean touchDown(InputEvent e, float screenX, float screenY, int pointer, int button) { return true; }
            
            @Override
            public void touchUp(InputEvent e, float screenX, float screenY, int pointer, int button)
            {
                _quitListener.handleGameQuit();
            }
        });
        _quitButton.setVisible(false);
        
        
        table.setFillParent(true);
        table.add(_menuButton).expandX().right();
        table.row();
        table.add(_quitButton).expand();//.width(132);
        
        
        addElement(table);
    }
    
    
    @Override
    public void confirmPromotion(PromotionHandler h, Piece p)
    {
        // Dialog widget for promotions
        new Dialog("Promote " + p.getType().toString() + "?", getSkin())
        {
            @Override
            public void result(Object o)
            {
                if (o instanceof Boolean)
                {
                    if ((Boolean)o)
                        h.handlePromotion(p);
                }
            }
        }.button("Yes", true).button("No", false).show(getStage());
    }
}
