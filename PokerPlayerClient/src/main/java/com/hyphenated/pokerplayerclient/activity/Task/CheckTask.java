package com.hyphenated.pokerplayerclient.activity.Task;

import com.hyphenated.pokerplayerclient.service.PlayerStatusHandler;
import com.hyphenated.pokerplayerclient.service.PokerNetworkService;

/**
 * Perform the Network call to do the check player action on a background thread
 * <br /><br />
 * Created by jacobhyphenated on 6/7/13.
 */
public class CheckTask extends ActionTask {

    public CheckTask(String serverName, long gameId, long playerId, PlayerStatusHandler playerHandler){
        super(serverName,gameId, playerId, playerHandler);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return PokerNetworkService.getInstance().check(serverName, gameId, playerId);
    }
}
