/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Alex Cassady
 */
public class ServerThread implements Runnable
{
    private Socket _socket;
    private Packet _data;
    private Queue<Packet> _messageQueue;
    
    @Override
    public void run()
    {
        // Get input (in) from server and give output (out) to server.
        try 
        { 
            BufferedReader in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
            PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
            
            while (!_socket.isClosed())
            {
                if (in.ready())
                {
                    // get input from server here
                    queuePacket(Packet.fromString(in.readLine()));
                }
                
                // give output to server here
                if (_data != null)
                {
                    out.write(_data.toString());
                    _data = null;
                }
            }
            
            in.close();
            out.close();
            _socket.close();
        }
        catch (IOException e) {}
    }
    
    
    private void queuePacket(Packet data)
    {
        _messageQueue.add(data);
    }
    
    /**
     * If connected to server, send the data with the type of request to the server.
     * @param requestType
     * @param data 
     */
    public void send(NetworkRequestType requestType, String data)
    {
        _data = new Packet(requestType, data);
    }
    
    
    /**
     * Receive data from the server if data is available.
     * @return Packet containing received data.
     */
    public Packet receive()
    {
        if (_messageQueue.isEmpty()) return null;
            
        return _messageQueue.remove();
    }
    
    
    public ServerThread(Socket socket)
    {
        _socket = socket;
        _messageQueue = new LinkedList<>();
    }
}
