/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.requesthandlers;

import com.insanelyinsane.waifushogi.pieces.Piece;

/**
 *
 * @author Alex Cassady
 */
public interface PromotionHandler 
{
    public void handlePromotion(Piece p);
}
