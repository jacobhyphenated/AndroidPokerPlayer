package com.hyphenated.pokerplayerclient.activity.Task;

import android.util.Log;

import com.hyphenated.pokerplayerclient.service.PlayerStatusHandler;
import com.hyphenated.pokerplayerclient.service.PokerNetworkService;

/**
 * Perform the Network call to do the bet player action on a background thread
 * <br /><br />
 * Created by jacobhyphenated on 6/7/13.
 */
public class BetTask extends ActionTask {

    protected int betAmount;

    public BetTask(String serverName, long gameId, long playerId, PlayerStatusHandler playerHandler,
            int betAmount){
        super(serverName, gameId, playerId, playerHandler);
        this.betAmount = betAmount;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return PokerNetworkService.getInstance().bet(serverName, gameId, playerId, betAmount);
    }
}
