package com.hyphenated.pokerplayerclient.activity.Task;

import com.hyphenated.pokerplayerclient.concurrency.AsyncTask;
import com.hyphenated.pokerplayerclient.service.PlayerStatusHandler;

/**
 * Abstract parent class for Player Actions
 * <br /><br />
 * Created by jacobhyphenated on 6/7/13.
 */
public abstract class ActionTask extends AsyncTask<Void,Void,Boolean> {
    protected String serverName;
    protected long gameId;
    protected long playerId;
    protected PlayerStatusHandler playerHandler;

    public ActionTask(String serverName, long gameId, long playerId, PlayerStatusHandler playerHandler){
        this.serverName = serverName;
        this.gameId = gameId;
        this.playerId = playerId;
        this.playerHandler = playerHandler;
    }

    public void setPlayerHandler(PlayerStatusHandler playerHandler){
        this.playerHandler = playerHandler;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if(playerHandler == null || this.isCancelled()){
            return;
        }
        playerHandler.actionResponse(success);
    }
}
