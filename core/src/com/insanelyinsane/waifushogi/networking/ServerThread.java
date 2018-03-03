/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insanelyinsane.waifushogi.networking;

import com.badlogic.gdx.Gdx;
import com.insanelyinsane.waifushogi.handlers.NetworkRequestHandler;
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
public class ServerThread implements Runnable, PacketMessenger
{
    private Socket _socket;
    private Queue<Packet> _outgoingQueue;
    private Queue<Packet> _incomingQueue;
    private NetworkRequestHandler _netHandler;
    
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
                    String s = in.readLine();
                    System.out.println("Server: " + s);
                    queueIncoming(Packet.fromString(s));
                }
                
                // give output to server here
                if (!_outgoingQueue.isEmpty())
                {
                    out.write(_outgoingQueue.remove().toString());
                    
                    if (out.checkError()) { System.out.println("Error encountered while sending message to server."); }
                }
                
                // handle input from server
                if (!_incomingQueue.isEmpty())
                {
                    while (_netHandler == null);
                    
                    Packet packet = receive();
                    System.out.println("Packet received.");
                    
                    if (packet.getNetworkRequestType() == NetworkRequestType.TEAM_ASSIGNMENT)
                    {
                        _netHandler.setTeam(packet);
                    }
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
        if (data == null)  { return; }
        _incomingQueue.add(data);
    }
    
    @Override
    public void queueOutgoing(Packet data)
    {
        if (data == null) return;
        _outgoingQueue.add(data);
    }
    
    /**
     * If connected to server, send the data with the type of request to the server.
     * @param requestType
     * @param data 
     */
    @Override
    public void send(NetworkRequestType requestType, String data)
    {
        //_data = new Packet(requestType, data);
        queueOutgoing(new Packet(requestType, data));
    }
    
    
    /**
     * Receive data from the server if data is available.
     * @return Packet containing received data.
     */
    @Override
    public Packet receive()
    {
        if (_incomingQueue.isEmpty()) return null;
        return _incomingQueue.remove();
    }
    
    
    public void setNetworkRequestHandler(NetworkRequestHandler handler)
    {
        _netHandler = handler;
    }
    
    
    public ServerThread(Socket socket)
    {
        _socket = socket;
        _outgoingQueue = new LinkedList<>();
        _incomingQueue = new LinkedList<>();
        
        Gdx.app.debug("Warning", "Make sure you set the NetworkRequestHandler for this ServerThread.");
    }
}
