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
}
