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
public class ClientThread implements Runnable, PacketMessenger
{
    private Socket _socket;
    private MultiplayerServer _server;
    private Queue<Packet> _outgoingQueue;
    private Queue<Packet> _incomingQueue;
    
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
                if (in.ready())
                {
                    // get input from client here
                    queueIncoming(Packet.fromString(in.readLine()));
                }
                
                // give output to client here
                if (!_outgoingQueue.isEmpty())
                {
                    String s = _outgoingQueue.remove().toString();
                    System.out.println(s);
                    out.write(s);
                    
                    if (out.checkError()) { System.out.println("Error sending packet to clients."); }
                }
                
                // This is where the server will process incoming requests from the client
                if (!_incomingQueue.isEmpty())
                {
                    Packet packet = receive();
                }
            }

            in.close();
            out.close();
            _socket.close();
        }
        catch (IOException e) {}
    }
    
    
    @Override
    public void queueIncoming(Packet data)
    {
        if (data == null) return;
        _incomingQueue.add(data);
    }
    
    @Override
    public void queueOutgoing(Packet data)
    {
        if (data == null) return;
        _outgoingQueue.add(data);
    }
    
    
    @Override
    public void send(NetworkRequestType requestType, String data)
    {
        queueOutgoing(new Packet(requestType, data));
    }
    
    
    @Override
    public Packet receive()
    {
        if (_incomingQueue.isEmpty()) return null;
        
        return _incomingQueue.remove();
    }
    
    
    public ClientThread(Socket socket, MultiplayerServer server)
    {
        _socket = socket;
        _server = server;
        
        _incomingQueue = new LinkedList<>();
        _outgoingQueue = new LinkedList<>();
    }
}
