package com.hyphenated.pokerplayerclient.activity.Task;

import android.util.Log;

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
    private long playerId;
    private PlayerStatusHandler playerStatusHandler;

    public PlayerStatusTask(String serverName, long gameId, long playerId,
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
