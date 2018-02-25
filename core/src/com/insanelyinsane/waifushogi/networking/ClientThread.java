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

/**
 *
 * @author Alex Cassady
 */
public class ClientThread implements Runnable
{
    private Socket _socket;
    private MultiplayerServer _server;
    
    
    @Override
    public void run()
    {
        // Get input (in) from client and give output (out) to client.
        try 
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));
            PrintWriter out = new PrintWriter(_socket.getOutputStream(), true);
            
            while (!_socket.isClosed())
            {
                // Handle I/O
            }

            in.close();
            out.close();
            _socket.close();
        }
        catch (IOException e) {}
    }
    
    
    public ClientThread(Socket socket, MultiplayerServer server)
    {
        _socket = socket;
        _server = server;
    }
}
