/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.ai;

import com.insanelyinsane.waifushogi.GameState;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Alex Cassady
 */
public class DecisionNode 
{
    private GameState _state;
    private DecisionNode _parent;
    private List<DecisionNode> _children;
    
    
    public List<DecisionNode> getChildren() { return _children; }
    public DecisionNode getParent() { return _parent; }
    public GameState getGameState() { return _state; }
    
    
    public void addChild(DecisionNode child)
    {
        if (child != null)
            _children.add(child);
    }
    
    
    public DecisionNode(GameState state, DecisionNode parent)
    {
        _state = state;
        _parent = parent;
        
        _children = new LinkedList<>();
    }
}
