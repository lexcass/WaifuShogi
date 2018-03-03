/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.networking;

/**
 *
 * @author Alex Cassady
 */
public interface PacketMessenger 
{
    public void send(NetworkRequestType requestType, String data);
    public Packet receive();
    
    public void queueOutgoing(Packet packet);
    public void queueIncoming(Packet packet);
}
