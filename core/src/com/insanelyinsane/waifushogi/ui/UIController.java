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
 * @author A Wild Popo Appeared
 */
public class UIController 
{
    private final Stage _stage;
    private UI _ui;
    
    
    public UIController()
    {
        _stage = new Stage();
    }
    
    /**
     * Loads a UI of the given type and sets it as the current UI.
     * @param type 
     */
    public UIController(UI ui)
    {
        this();
        loadUI(ui);
    }
    
    
    public void loadUI(UI ui)
    {
        _stage.clear();
        
        _ui = ui;
        _ui.getElements().forEach(e -> _stage.addActor(e));
    }
    
    
    public Stage getStage() { return _stage; }
}
