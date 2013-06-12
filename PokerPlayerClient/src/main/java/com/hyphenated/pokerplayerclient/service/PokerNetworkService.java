/*
The MIT License (MIT)

Copyright (c) 2013 Jacob Kanipe-Illig

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 */
package com.hyphenated.pokerplayerclient.service;

import android.util.Log;

import com.hyphenated.pokerplayerclient.domain.PlayerStatus;
import com.hyphenated.pokerplayerclient.network.RestObjectRequestBuilder;

import org.json.JSONObject;

/**
 * Singleton class to handle network communication from the android client to the poker server.
 *
 * Designed around Poker Server API version 0.1
 *
 * Created by jacobhyphenated on 6/4/13.
 */
public class PokerNetworkService {
    private static PokerNetworkService ourInstance = new PokerNetworkService();

    public static PokerNetworkService getInstance() {
        return ourInstance;
    }

    private PokerNetworkService() {

    }

    /**
     * Ping the server to see if the connection is successful
     * @param serverName URL of the server
     * @return true if connected to the server
     */
    public boolean ping(String serverName){
        String uri = "/ping";

        RestObjectRequestBuilder request = new RestObjectRequestBuilder();
        request.setURL(serverName + uri);
        try{
            return request.sendRequest().getBoolean("success");
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * Join a game.  This will add you as a player to a game
     * @param serverName URL of the server
     * @param playerName Name you will be playing under.  This is your username.
     * @param gameId Unique ID of the game you will join.
     * @return PlayerID. This is the unique ID of the player object that corresponds
     * to this player client. It will be used in subsequent server requests.  Returns 0 if
     * there was a failure at some point, including a server error.
     */
    public long joinGame(String serverName, String playerName, long gameId){
        String uri = "/join";

        RestObjectRequestBuilder request = new RestObjectRequestBuilder();
        request.setURL(serverName + uri);
        request.addParameter("gameId", gameId);
        request.addParameter("playerName", playerName);
        try{
            return request.sendRequest().getLong("playerId");
        }
        catch (Exception e){
            Log.e("Poker", "Exception: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Player status - gets the players status update for the game
     * @param serverName Server URL
     * @param gameId unique ID for the game
     * @param playerId unique ID for the player
     * @return {@link PlayerStatus} Object that represents the player's status
     */
    public PlayerStatus status(String serverName, long gameId, long playerId){
        String uri = "/status";

        RestObjectRequestBuilder request = new RestObjectRequestBuilder();
        request.setURL(serverName + uri);
        request.addParameter("gameId", gameId);
        request.addParameter("playerId", playerId);
        try{
            JSONObject response =  request.sendRequest();
            return new PlayerStatus(response);
        }
        catch (Exception e){
            Log.e("Poker", "Exception: " + e.getMessage());
            return null;
        }
    }

    /**
     * Send a request to the server to fold the hand
     *
     * @param serverName server name
     * @param gameId game id
     * @param playerId player id
     * @return true if the hand was folded.  False otherwise
     */
    public boolean fold(String serverName, long gameId, long playerId){
        String uri = "/fold";

        RestObjectRequestBuilder request = new RestObjectRequestBuilder();
        request.setURL(serverName + uri);
        request.addParameter("gameId", gameId);
        request.addParameter("playerId", playerId);
        try{
            return request.sendRequest().getBoolean("success");
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * Check the hand
     * @param serverName server name
     * @param gameId game id
     * @param playerId player id
     * @return true if the check was successful, false otherwise
     */
    public boolean check(String serverName, long gameId, long playerId){
        String uri = "/check";

        RestObjectRequestBuilder request = new RestObjectRequestBuilder();
        request.setURL(serverName + uri);
        request.addParameter("gameId", gameId);
        request.addParameter("playerId", playerId);
        try{
            return request.sendRequest().getBoolean("success");
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * Call a bet
     * @param serverName server name
     * @param gameId game id
     * @param playerId player id
     * @return true if the bet was called, false otherwise
     */
    public boolean call(String serverName, long gameId, long playerId){
        String uri = "/call";

        RestObjectRequestBuilder request = new RestObjectRequestBuilder();
        request.setURL(serverName + uri);
        request.addParameter("gameId", gameId);
        request.addParameter("playerId", playerId);
        try{
            return request.sendRequest().getBoolean("success");
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * Place a bet or raise
     * @param serverName server name
     * @param gameId game id
     * @param playerId player id
     * @param betAmount amount of bet or raise. This is not the total bet amount, but rather
     *                  the additional amount beyond what was needed to call
     * @return true if the bet was successful, false otherwise
     */
    public boolean bet(String serverName, long gameId, long playerId, int betAmount){
        String uri = "/bet";

        RestObjectRequestBuilder request = new RestObjectRequestBuilder();
        request.setURL(serverName + uri);
        request.addParameter("gameId", gameId);
        request.addParameter("playerId", playerId);
        request.addParameter("betAmount", betAmount);
        try{
            return request.sendRequest().getBoolean("success");
        }
        catch (Exception e){
            return false;
        }
    }
}
