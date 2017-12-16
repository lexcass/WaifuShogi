/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author Alex Cassady
 */
public class Animator 
{
    
    private final static float FRAME_RATE = 1.0f / 2.0f;
    private TextureRegion[] _frames;
    private final HashMap<String, Animation<TextureRegion>> _animations;
    private int _numRows;
    private int _numCols;
    private int MAX_FRAMES;
    
    private Animation<TextureRegion> _active;
    private TextureRegion _keyFrame;
    private float _elapsedTime;
    
    private static final String ERROR_PREFIX = "Error from Animator.loadFromFile: ";
    
    
    public Animator(Texture tex, int frameWidth, int frameHeight)
    {
        _animations = new HashMap<>();
        _elapsedTime = 0.0f;
        
        // Split texture into texture regions
        TextureRegion[][] regions = TextureRegion.split(tex, frameWidth, frameHeight);
        _numRows = regions.length;
        _numCols = regions[0].length;
        MAX_FRAMES = (_numRows * _numCols) - 1;
        _frames = new TextureRegion[_numRows * _numCols];
        
        // Copy texture regions into frames array
        int index = 0;
        for (int r = 0; r < _numRows; r++)
        {
            for (int c = 0; c < _numCols; c++)
            {
                _frames[index++] = regions[r][c];
            }
        }
    }
    
    public void loadFromFile(String fileName)
    {
        // Read content of .anim file
        FileHandle file = Gdx.files.internal("animations/" + fileName + ".anim");
        String content = file.readString();
        
        // Split into lines since one line is data for one animation
        String[] lines = content.split("\\r?\\n");
        
        // Log message in case of empty file
        if (lines.length < 1) 
        {
            throw new GdxRuntimeException(ERROR_PREFIX + fileName + ".anim is empty or at least two animations are on the same line.");
        }
        
        for (String s : lines)
        {
            String[] data = s.split(" ");
            
            // Elements in data except for the 0th element (the string name)
            String name = data[0];
            String[] frames = Arrays.copyOfRange(data, 1, data.length);
            
            // Constants
            final int MIN_NAME_LENGTH = 2;
            final int MIN_FRAME_COUNT = 1;
            
            // Error checking
            // If string isn't at least 2 characters long, error.
            // If there is not at least 1 index for the animation, error.
            if (data[0].length() < MIN_NAME_LENGTH)
            {
                throw new GdxRuntimeException(ERROR_PREFIX + fileName + ".anim should have a string name for each animation.");
            }
            if (frames.length < MIN_FRAME_COUNT)
            {
                throw new GdxRuntimeException(ERROR_PREFIX + fileName + ".anim should have indices for each animation.");
            }
            
            // Copy TextureRegions at indices into array
            TextureRegion[] frameArr = new TextureRegion[frames.length];
            
            for (int i = 0; i < frames.length; i++)
            {
                int index = Integer.parseInt(frames[i]);
                if (index > MAX_FRAMES)
                {
                    throw new GdxRuntimeException(ERROR_PREFIX + fileName + 
                        ".anim contains a frame index that doesn't exist. frame given is #" + index + " out of " + MAX_FRAMES + " frames.");
                }
                
                if (_frames[index] != null)
                {
                    frameArr[i] = _frames[index];
                }
                else
                {
                    throw new GdxRuntimeException(ERROR_PREFIX + fileName + ".anim has a null frame at index " + index + ".");
                }
            }
            
            // Add animation with array of copied frames to animations HashMap
            Animation<TextureRegion> a = new Animation<>(FRAME_RATE, frameArr);
            _animations.put(name, a);
        }
    }
    
    
    public void update(float delta)
    {
        _elapsedTime += delta;
        _keyFrame = _active.getKeyFrame(_elapsedTime, true);
        
        if (_keyFrame == null)
        {
            int nullIndex = _active.getKeyFrameIndex(_elapsedTime);
            
           throw new NullPointerException("Frame " + nullIndex + " of current animation is null.");
        }
    }
    
    
    public TextureRegion getFrame()
    {
        return _keyFrame;
    }
    
    
    public void setAnimation(String anim)
    {
        if (_animations.containsKey(anim))
        {
            if (_animations.get(anim) != null)
            {
                _active = _animations.get(anim);
                _keyFrame = _active.getKeyFrame(_elapsedTime);
                return;
            }
        }
        
        throw new GdxRuntimeException("Animator has no animation " + anim + ".");
    }
}
