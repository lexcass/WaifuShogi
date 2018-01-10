/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.interfaces;

import com.insanelyinsane.waifushogi.pieces.Team;
import com.insanelyinsane.waifushogi.handlers.WinGameHandler;

/**
 *
 * @author Alex Cassady
 */
public interface WinConfirmation 
{
    public void confirmWin(WinGameHandler handler, Team team);
}
