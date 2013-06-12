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
