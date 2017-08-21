/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.listeners;

import com.insanelyinsane.waifushogi.objects.pieces.Piece;

/**
 *
 * @author A Wild Popo Appeared
 */
public interface PromotionListener 
{
    public void onWaifuPromoted(Piece p);
}
