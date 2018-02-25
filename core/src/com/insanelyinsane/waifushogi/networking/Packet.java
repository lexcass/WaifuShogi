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
public class Packet 
{
    private NetworkRequestType _type;
    public NetworkRequestType getNetworkRequestType() { return _type; }
    
    private String _data;
    public String getData() { return _data; }
    
    public static Packet fromString(String data)
    {
        NetworkRequestType type;
        String packetData;
        String[] parts = data.split(",");
        
        try
        {
            type = NetworkRequestType.valueOf(parts[0]);
        }
        catch (IllegalArgumentException e) 
        {
            type = NetworkRequestType.UNKNOWN;
        }
        
        packetData = parts[1];
        
        return new Packet(type, packetData);
    }
    
    @Override
    public String toString()
    {
        return _type.toString() + "," + _data;
    }
    
    public Packet(NetworkRequestType type, String data)
    {
        _type = type;
        _data = data;
    }
}
