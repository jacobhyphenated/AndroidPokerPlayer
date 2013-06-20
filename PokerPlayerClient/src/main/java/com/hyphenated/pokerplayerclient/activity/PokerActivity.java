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
package com.hyphenated.pokerplayerclient.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenated.pokerplayerclient.R;
import com.hyphenated.pokerplayerclient.activity.Task.ActionTask;
import com.hyphenated.pokerplayerclient.activity.Task.BetTask;
import com.hyphenated.pokerplayerclient.activity.Task.CallTask;
import com.hyphenated.pokerplayerclient.activity.Task.CheckTask;
import com.hyphenated.pokerplayerclient.activity.Task.FoldTask;
import com.hyphenated.pokerplayerclient.activity.Task.PlayerStatusTask;
import com.hyphenated.pokerplayerclient.concurrency.AsyncTask;
import com.hyphenated.pokerplayerclient.domain.PlayerStatus;
import com.hyphenated.pokerplayerclient.domain.PlayerStatusType;
import com.hyphenated.pokerplayerclient.manager.PreferencesManager;
import com.hyphenated.pokerplayerclient.service.PlayerStatusHandler;

/**
 * Main activity of the Android Application.
 *
 * Get updates from the server, perform actions, keep the game status
 */
public class PokerActivity extends Activity implements PlayerStatusHandler{

    public static final int GAME_SELECTION_REQUEST = 8009;
    public static final long UPDATE_SLEEP_TIME = 5000;

    private boolean isFinished;
    private PlayerStatusTask playerStatusTask;
    private StatusUpdateTimer statusUpdateTimer;
    private ActionTask currentAction;
    private int serverConnectFailCount;

    private PlayerStatus lastPlayerStatus;
    private boolean card1Shown;
    private boolean card2Shown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poker);
        isFinished = false;

        //Remove some conditional layout fields for the default view
        View betView = findViewById(R.id.action_layout);
        betView.setVisibility(ViewGroup.GONE);
        View cardLayout = findViewById(R.id.cards_layout);
        cardLayout.setVisibility(ViewGroup.INVISIBLE);

        card1Shown = false;
        card2Shown = false;

        //As long as the status view is on screen, do not sleep
        ((TextView) findViewById(R.id.status_text)).setKeepScreenOn(true);

        EditText betAmountField = (EditText) findViewById(R.id.input_bet_amount);
        betAmountField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
            @Override
            public void afterTextChanged(Editable editable) {
                int newBetAmount;
                try{
                    newBetAmount = Integer.valueOf(editable.toString());
                }
                catch(Exception e){
                    return;
                }
                int amountToCall = 0;
                if(lastPlayerStatus != null){
                    amountToCall = lastPlayerStatus.getAmountToCall();
                }
                Button raiseButton = (Button) findViewById(R.id.btn_raise);
                raiseButton.setText(String.format(
                        PokerActivity.this.getString(R.string.btn_raise_formatted),
                        newBetAmount + amountToCall));
                Button betButton = (Button) findViewById(R.id.btn_bet);
                betButton.setText(String.format(
                        PokerActivity.this.getString(R.string.btn_bet_formatted), newBetAmount));

            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

        //Use the isFinished flag for low memory case (possibly android bug)
        if(isFinished){
            return;
        }
        if(PreferencesManager.getGameId(this) == 0 || PreferencesManager.getPlayerId(this) == 0
                || PreferencesManager.getServerName(this) == null){
            startActivityForResult(new Intent(this, GameSelectionActivity.class), GAME_SELECTION_REQUEST);
        }
        else{
            statusUpdateTimer = new StatusUpdateTimer();
            statusUpdateTimer.execute();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        statusUpdateTimer.cancel(true);
        if(playerStatusTask != null){
            playerStatusTask.setPlayerStatusHandler(null);
            playerStatusTask.cancel(true);
        }

        //Current action is interrupted.  Currently we do nothing to recover the
        //action, should probably add a visual indicator (somehow).
        if(currentAction != null){
            currentAction.setPlayerHandler(null);
            currentAction.cancel(true);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == GAME_SELECTION_REQUEST && resultCode != Activity.RESULT_OK)  {
            //We have come back from game selection, but the the result code was bad
            isFinished = true;
            finish();
        }
        else if(requestCode == GAME_SELECTION_REQUEST){
            statusUpdateTimer = new StatusUpdateTimer();
            statusUpdateTimer.execute();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /* Button Event Handlers*/
    public void fold(View v){
        String serverName = PreferencesManager.getServerName(this);
        long gameId = PreferencesManager.getGameId(this);
        long playerId = PreferencesManager.getPlayerId(this);
        currentAction = new FoldTask(serverName, gameId, playerId, this);
        currentAction.execute();
    }

    public void check(View v){
        String serverName = PreferencesManager.getServerName(this);
        long gameId = PreferencesManager.getGameId(this);
        long playerId = PreferencesManager.getPlayerId(this);
        currentAction = new CheckTask(serverName, gameId, playerId, this);
        currentAction.execute();
    }

    public void call(View v){
        String serverName = PreferencesManager.getServerName(this);
        long gameId = PreferencesManager.getGameId(this);
        long playerId = PreferencesManager.getPlayerId(this);
        currentAction = new CallTask(serverName, gameId, playerId, this);
        currentAction.execute();
    }

    public void bet(View v){
        String serverName = PreferencesManager.getServerName(this);
        long gameId = PreferencesManager.getGameId(this);
        long playerId = PreferencesManager.getPlayerId(this);
        EditText betText = (EditText) findViewById(R.id.input_bet_amount);
        try{
            int betAmount = Integer.parseInt(betText.getText().toString());
            currentAction = new BetTask(serverName, gameId, playerId, this, betAmount);
            currentAction.execute();
            betText.setText("0");
        }
        catch (Exception e){
            Toast.makeText(this, getString(R.string.bad_bet), Toast.LENGTH_SHORT).show();
        }
    }

    public void showCard1(View v){
        if(lastPlayerStatus == null || lastPlayerStatus.getCard1() == null ){
            return;
        }
        ImageView card1 = (ImageView) findViewById(R.id.card1);
        if(!card1Shown){
            card1.setImageResource(lastPlayerStatus.getCard1().getDrawable());
        }
        else{
            card1.setImageResource(R.drawable.card_bg);
        }
        card1Shown = !card1Shown;
    }

    public void showCard2(View v){
        if(lastPlayerStatus == null || lastPlayerStatus.getCard2() == null ){
            return;
        }
        ImageView card2 = (ImageView) findViewById(R.id.card2);
        if(!card2Shown){
            card2.setImageResource(lastPlayerStatus.getCard2().getDrawable());
        }
        else{
            card2.setImageResource(R.drawable.card_bg);
        }
        card2Shown = !card2Shown;
    }

    public void changeBet3x(View v){
        EditText betAmountField = (EditText) findViewById(R.id.input_bet_amount);
        int amountToCall = lastPlayerStatus.getAmountToCall();
        if(amountToCall != 0){
            betAmountField.setText("" + amountToCall * 2);
        }
    }

    public void changeBet4x(View v){
        EditText betAmountField = (EditText) findViewById(R.id.input_bet_amount);
        int amountToCall = lastPlayerStatus.getAmountToCall();
        if(amountToCall != 0){
            betAmountField.setText("" + amountToCall * 3);
        }
    }

    @Override
    public void updatePlayerStatus(PlayerStatus playerStatus) {
        //Player status comes back wrong, probably a connection problem
        if(playerStatus == null || playerStatus.getStatus() == null){
            Log.e("Poker", "Server Error");

            serverConnectFailCount++;
            //If we fail connecting to the server enough times, shut it down
            if(serverConnectFailCount > 3){
                new AlertDialog.Builder(this).setTitle(getString(R.string.error))
                        .setMessage(getString(R.string.no_connection))
                        .setPositiveButton(getString(R.string.ok), null).create().show();
                statusUpdateTimer.cancel(true);
            }
            else{
                Toast.makeText(this, getString(R.string.server_error), Toast.LENGTH_SHORT).show();
            }
            return;
        }
        serverConnectFailCount = 0;

        setupUI(playerStatus);

        lastPlayerStatus = playerStatus;
    }

    @Override
    public void actionResponse(boolean success) {
        if(!success){
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.warning))
                    .setMessage(getString(R.string.not_allowed))
                    .setPositiveButton(getString(R.string.ok), null)
                    .create().show();
        }
        else{
            //We have completed the action.  The next status update will bring more info,
            //but for now, we know the action is no longer on us, so switch to waiting and update fields
            lastPlayerStatus.setStatus(PlayerStatusType.WAITING);
            setupUI(lastPlayerStatus);
        }

    }

    //Helper for updating UI based on player status
    private void setupUI(PlayerStatus playerStatus){
        //If the cards change, and the cards are being shown, hide the cards
        //Use this to make sure new dealt cards are not immediately displayed
        if(lastPlayerStatus != null && playerStatus.getCard1() != lastPlayerStatus.getCard1() && card1Shown){
            showCard1(null);
        }
        if(lastPlayerStatus != null && playerStatus.getCard2() != lastPlayerStatus.getCard2() && card2Shown){
            showCard2(null);
        }

        /*Lots of if logic here.  Basically, if the previous status was not already an ACTION status
          and the current status is an ACTION status, vibrate.
          To put it another way, vibrate when it is the player's turn to act.*/
        if((lastPlayerStatus == null || lastPlayerStatus.getStatus() == null ||
                (lastPlayerStatus.getStatus() != PlayerStatusType.ACTION_TO_CALL &&
                lastPlayerStatus.getStatus() != PlayerStatusType.ACTION_TO_CHECK) )
                && (playerStatus.getStatus() == PlayerStatusType.ACTION_TO_CALL ||
                playerStatus.getStatus() == PlayerStatusType.ACTION_TO_CHECK)){
            Vibrator v = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
            v.vibrate(600);
        }

        setupInfoFields(playerStatus);
        setupBetFields(playerStatus);
    }

    //Set up player info, cards, chips, status, etc. based on player status
    private void setupInfoFields(PlayerStatus playerStatus){
        TextView statusText = (TextView) findViewById(R.id.status_text);
        statusText.setText(getString(playerStatus.getStatus().getStringResource()));

        RelativeLayout cardLayout = (RelativeLayout) findViewById(R.id.cards_layout);
        if(playerStatus.getCard1() != null){
            cardLayout.setVisibility(ViewGroup.VISIBLE);
        }
        else{
            cardLayout.setVisibility(ViewGroup.INVISIBLE);
        }
        ((TextView) findViewById(R.id.txt_chip_amount)).setText("" + playerStatus.getChips());
    }

    //Helper method to layout and add default values for the bet/raise fields
    private void setupBetFields(PlayerStatus playerStatus){
        View betView = findViewById(R.id.action_layout);

        if(playerStatus.getStatus() != PlayerStatusType.ACTION_TO_CALL &&
                playerStatus.getStatus() != PlayerStatusType.ACTION_TO_CHECK){
            betView.setVisibility(ViewGroup.GONE);
            return;
        }

        betView.setVisibility(ViewGroup.VISIBLE);
        if(playerStatus.getStatus() == PlayerStatusType.ACTION_TO_CHECK){
            findViewById(R.id.button_call_layout).setVisibility(ViewGroup.GONE);
            findViewById(R.id.button_check_layout).setVisibility(ViewGroup.VISIBLE);
        }
        else{
            findViewById(R.id.button_call_layout).setVisibility(ViewGroup.VISIBLE);
            findViewById(R.id.button_check_layout).setVisibility(ViewGroup.GONE);
        }

        TextView amountToCall = (TextView) findViewById(R.id.txt_bet_amount);
        if(playerStatus.getAmountToCall() > 0){
            amountToCall.setText("" + playerStatus.getAmountToCall());
        }
        else{
            amountToCall.setText("0");
        }

        EditText betAmount = (EditText) findViewById(R.id.input_bet_amount);
        if(betAmount.getText().toString().equals("0") || betAmount.getText().toString().equals("")){
            betAmount.setText("" + playerStatus.getAmountToCall());
        }
    }

    //Helper method that starts the background thread to update the player status
    private void sendPlayerStatusRequest(){
        long gameId = PreferencesManager.getGameId(this);
        long playerId = PreferencesManager.getPlayerId(this);
        String serverName = PreferencesManager.getServerName(this);
        if(playerStatusTask != null){
            playerStatusTask.setPlayerStatusHandler(null);
            playerStatusTask.cancel(true);
        }
        playerStatusTask = new PlayerStatusTask(serverName, gameId, playerId, this);
        playerStatusTask.execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.poker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_settings:
                Toast.makeText(this, "Show Settings Menu", Toast.LENGTH_SHORT).show();
                return true;
            //TODO Settings
            case R.id.action_leave:
                //Clear game settings, playerId, gameId, and launch selection screen
                PreferencesManager.setGameId(0l, this);
                PreferencesManager.setPlayerId(0l, this);
                startActivityForResult(new Intent(this, GameSelectionActivity.class), GAME_SELECTION_REQUEST);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Helper class.  Runs on a separate thread on a loop. Every specified interval,
    //it kicks off the status call on the main thread
    private class StatusUpdateTimer extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            while(!this.isCancelled()){
                this.publishProgress();
                try{
                    Thread.sleep(UPDATE_SLEEP_TIME);
                }
                catch (Exception e){
                    //Don't care
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            sendPlayerStatusRequest();
        }
    }
}
