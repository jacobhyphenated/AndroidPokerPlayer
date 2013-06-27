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
package com.hyphenated.pokerplayerclient.activity.Task;

import com.hyphenated.pokerplayerclient.concurrency.AsyncTask;
import com.hyphenated.pokerplayerclient.domain.PlayerStatus;
import com.hyphenated.pokerplayerclient.service.PlayerStatusHandler;
import com.hyphenated.pokerplayerclient.service.PokerNetworkService;

/**
 * Helper Async Task Class.  Takes in the required parameters in the constructor,
 * and then it will send use them to make an http request to get the player status
 * from the server.  The results go back to the handler on the main thread.
 * <br /><br />
 * This is mostly in a separate class for readability purposes.  However, make sure
 * we allow the class to be cancelled gracefully and allow the ability to unset
 * the handler, just to be sure we are not leaking memory.
 * <br /><br />
 * Created by jacobhyphenated on 6/5/13.
 */
public class PlayerStatusTask extends AsyncTask<Void, Void, PlayerStatus> {

    private String serverName;
    private long gameId;
    private String playerId;
    private PlayerStatusHandler playerStatusHandler;

    public PlayerStatusTask(String serverName, long gameId, String playerId,
                            PlayerStatusHandler playerStatusHandler){
        this.serverName = serverName;
        this.gameId = gameId;
        this.playerId = playerId;
        this.playerStatusHandler = playerStatusHandler;
    }

    public void setPlayerStatusHandler(PlayerStatusHandler playerStatusHandler){
        this.playerStatusHandler = playerStatusHandler;
    }

    @Override
    protected PlayerStatus doInBackground(Void... voids) {
        return PokerNetworkService.getInstance().status(serverName, gameId, playerId);
    }

    @Override
    protected void onPostExecute(PlayerStatus playerStatus) {
        if(playerStatusHandler != null && !this.isCancelled()){
            playerStatusHandler.updatePlayerStatus(playerStatus);
        }
    }
}
