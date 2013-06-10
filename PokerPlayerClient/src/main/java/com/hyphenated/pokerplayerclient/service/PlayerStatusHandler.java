package com.hyphenated.pokerplayerclient.service;

import com.hyphenated.pokerplayerclient.domain.PlayerStatus;

/**
 * Interface that describes the class that will handle updates to the player's status as
 * the game progresses.  This will likely be an activity (or closely coupled with one)
 * <br /><br />
 * Created by jacobhyphenated on 6/5/13.
 */
public interface PlayerStatusHandler {

    /**
     * Take a player status ({@link PlayerStatus}) and handle the game state updates
     * @param playerStatus Updated Player Status
     */
    public void updatePlayerStatus(PlayerStatus playerStatus);

    /**
     * After the player attempts to perform an action (fold, check, bet, call)
     * the server response will tell whether or not the action was successful
     * @param success true if the action was successful, false otherwise
     */
    public void actionResponse(boolean success);
}
