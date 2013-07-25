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

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class that holds the information related to the player status update call
 * <br /></br >
 * Created by jacobhyphenated on 6/5/13.
 */
public class PlayerStatus {

    private PlayerStatusType status;
    private Card card1;
    private Card card2;
    private int chips;
    private int amountBetRound;
    private int amountToCall;
    private int smallBlind;
    private int bigBlind;

    public PlayerStatus(){

    }

    public PlayerStatus(JSONObject json){
        try {
            status = PlayerStatusType.valueOf(json.getString("status"));
            if(json.has("card1")){
                this.card1 = Card.getCardByIdentifier(json.getString("card1"));
                this.card2 = Card.getCardByIdentifier(json.getString("card2"));
            }
            if(json.has("chips")){
                this.chips = json.getInt("chips");
            }
            if(json.has("amountBetRound")){
                this.amountBetRound = json.getInt("amountBetRound");
            }
            if(json.has("amountToCall")){
                this.amountToCall = json.getInt("amountToCall");
            }
            if(json.has("smallBlind")){
                this.smallBlind = json.getInt("smallBlind");
            }
            if(json.has("bigBlind")){
                this.bigBlind = json.getInt("bigBlind");
            }
        } catch (JSONException e) {
            Log.e("Poker", e.getMessage());
        }
    }

    public PlayerStatusType getStatus() {
        return status;
    }

    public void setStatus(PlayerStatusType status) {
        this.status = status;
    }

    public Card getCard1() {
        return card1;
    }

    public void setCard1(Card card1) {
        this.card1 = card1;
    }

    public Card getCard2() {
        return card2;
    }

    public void setCard2(Card card2) {
        this.card2 = card2;
    }

    public int getChips() {
        return chips;
    }

    public void setChips(int chips) {
        this.chips = chips;
    }

    public int getAmountBetRound() {
        return amountBetRound;
    }

    public void setAmountBetRound(int amountBetRound) {
        this.amountBetRound = amountBetRound;
    }

    public int getAmountToCall() {
        return amountToCall;
    }

    public void setAmountToCall(int amountToCall) {
        this.amountToCall = amountToCall;
    }

    public int getSmallBlind() {
        return smallBlind;
    }

    public void setSmallBlind(int smallBlind) {
        this.smallBlind = smallBlind;
    }

    public int getBigBlind() {
        return bigBlind;
    }

    public void setBigBlind(int bigBlind) {
        this.bigBlind = bigBlind;
    }
}
