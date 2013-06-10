package com.hyphenated.pokerplayerclient.domain;

import com.hyphenated.pokerplayerclient.R;

/**
 * Enum type to track the Player's status in the game
 *
 * Created by jacobhyphenated on 6/5/13.
 */
public enum PlayerStatusType {
    /** The game has not been started */
    NOT_STARTED(R.string.not_started),
    /** Players are taking there seats */
    SEATING(R.string.seating),
    /** Waiting for another player to act */
    WAITING(R.string.waiting),
    /** Player is still in the hand, but has committed all of his/her chips and will take no further action */
    ALL_IN(R.string.all_in),
    /** The hand went to showdown, but the player lost */
    LOST_HAND(R.string.lost_hand),
    /** Won chips in the previous hand.  The old English for won is winnan. */
    WON_HAND(R.string.won_hand),
    /** Post the Small Blind for this hand */
    POST_SB(R.string.small_blind),
    /** Post the Big Blind for this hand */
    POST_BB(R.string.big_blind),
    /** Player is ready to act.  Player must call or raise to continue. */
    ACTION_TO_CALL(R.string.action_call),
    /** Player is ready to act. There is no bet, so the player may check. */
    ACTION_TO_CHECK(R.string.action_check),
    /** The Player is still in the game but not in the hand. */
    SIT_OUT(R.string.sit_out),
    /** The Player is no longer in the game.  */
    ELIMINATED(R.string.eliminated);

    private int displayString;

    private PlayerStatusType(int displayString){
        this.displayString = displayString;
    }

    public int getStringResource(){
        return displayString;
    }
}
