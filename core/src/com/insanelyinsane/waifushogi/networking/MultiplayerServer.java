package com.insanelyinsane.waifushogi.networking;

import com.insanelyinsane.waifushogi.pieces.Team;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * The multiplayer server serves as a mediator between the two clients (players) in
 * a network multiplayer game. 
 * 
 * @author Alex Cassady
 */
public class MultiplayerServer 
{
    public static final int PORT_NUMBER = 4444;
    
    private ServerSocket _socket;
    private boolean _acceptingConnections;
    private List<ClientThread> _clientThreads;
    
    private Random _rand;
    private Team _teamTaken;
    
    
    /**
     * Start accepting connections to the multiplayer server.
     */
    public void acceptConnections()
    {
        if (!_acceptingConnections)
        {
            _acceptingConnections = true;
            new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    while (_acceptingConnections)
                    {
                        try
                        {
                            Socket clientSocket = _socket.accept();
                            ClientThread thread = new ClientThread(clientSocket, MultiplayerServer.this);
                            new Thread(thread).start();
                            _clientThreads.add(thread);
                            
                            System.out.println("Client Connected!");
                        }
                        catch (IOException e)
                        {
                            System.err.println("Client failed to connect to server.");
                        }
                        
                        if (_clientThreads.size() == 2)
                        {
                            
                            System.out.println("Found a game!");
                            _acceptingConnections = false;
                            
                            for (ClientThread client : _clientThreads)
                            {
                                client.send(NetworkRequestType.TEAM_ASSIGNMENT, generateTeam().toString());
                            }
                        }
                    }
                }
            }).start();
        }
        
    }
    
    
    public Team generateTeam()
    {
        if (_teamTaken == null)
        {
            // generate a random team and store in _teamTaken
            _rand = new Random();
            _teamTaken = Team.values()[_rand.nextInt(2)];
            return _teamTaken;
        }
        else
        {
            return (_teamTaken == Team.BLUE) ? Team.RED : Team.BLUE;
        }
    }
    
    
    public MultiplayerServer() throws IOException
    {
        _acceptingConnections = false;
        _clientThreads = new LinkedList<>();
        
        
        _socket = new ServerSocket(PORT_NUMBER);
        
        _teamTaken = null;
    }
}
