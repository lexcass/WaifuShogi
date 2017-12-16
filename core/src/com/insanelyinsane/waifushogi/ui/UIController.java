/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Controls which UI is currently active and loads new UIs.
 * UIController has its own stage which has an input processor separate
 * from the game's stage.
 * @author Alex Cassady
 */
public class UIController 
{
    private final Stage _stage;
    private UI _ui;
    private boolean _uiShowing;
    
    
    public UIController(Stage stage)
    {
        _stage = stage;
    }
    
    
    public void loadUI(UI ui)
    {
        if (_ui != null) dispose();
        
        _ui = ui;
        _ui.getElements().forEach(e -> _stage.addActor(e));
    }
    
    
    public void showUI(boolean s)
    {
        _uiShowing = s;
        _ui.show(s);
    }
    
    
    public void dispose()
    {
        _ui.dispose();
    }
    
    
    public boolean isUIShowing() { return _uiShowing; }
    
    
    public Stage getStage() { return _stage; }
}
