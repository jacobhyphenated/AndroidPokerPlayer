package com.hyphenated.pokerplayerclient.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenated.pokerplayerclient.R;
import com.hyphenated.pokerplayerclient.concurrency.AsyncTask;
import com.hyphenated.pokerplayerclient.service.PokerNetworkService;
import com.hyphenated.pokerplayerclient.manager.PreferencesManager;

/**
 * Activity class to set up the server/game configuration
 *
 * Created by jacobhyphenated on 6/4/13.
 */
public class GameSelectionActivity extends Activity{

    private static final String VALID_SERVER_KEY = "valid_server";
    private static final int URL_MIN_LENGTH = 10;

    private boolean validServer;
    private GameConnectedTask gameConnectedTask;

    private EditText gameIdField;
    private EditText playerNameField;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selection);

        validServer = false;
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        gameIdField = (EditText) findViewById(R.id.txt_game_id);
        playerNameField = (EditText) findViewById(R.id.txt_player_name);

        //Done keyboard button will act as Join Button click
        playerNameField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    enterGame(playerNameField);
                    return true;
                }
                return false;
            }
        });

        //Set up text watchers to disable/enable Join Button when fields are complete
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) { }
            @Override
            public void afterTextChanged(Editable editable) {
                checkEnabledSubmit();
            }
        };
        gameIdField.addTextChangedListener(watcher);
        playerNameField.addTextChangedListener(watcher);

        //Special logic to check the server field
        EditText serverText = (EditText) findViewById(R.id.txt_server_name);
        serverText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
            @Override
            public void afterTextChanged(Editable editable) {
                //Check to see if the entered text is valid. Use different thread
                //as the server check is a network call
                if(editable.toString().length() > URL_MIN_LENGTH){
                    if(gameConnectedTask != null){
                        gameConnectedTask.cancel(true);
                    }
                    gameConnectedTask = new GameConnectedTask();
                    gameConnectedTask.execute(editable.toString());
                }
            }
        });

        if(savedInstanceState != null){
            validServer = savedInstanceState.getBoolean(VALID_SERVER_KEY, false);
            setServerFieldBGColor();
        }else{
            String serverName = PreferencesManager.getServerName(this);
            if(serverName != null && serverName.length() > 0){
                serverText.setText(serverName);
            }
        }

        checkEnabledSubmit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putBoolean(VALID_SERVER_KEY, validServer);
    }

    //Button click event handler.  Handle what happens when the join button is clicked
    public void enterGame(View v){
        //If the join button is disabled, do nothing
        if(!findViewById(R.id.btn_game_select).isEnabled()){
            return;
        }

        //Get the values for the server name, gameId, and playerName
        EditText serverText = (EditText) findViewById(R.id.txt_server_name);
        String serverName = serverText.getText().toString();
        //Go ahead and save the server name.  At this point, we know it works
        PreferencesManager.setServerName(serverName, this);
        try{
            //Sanity check for not long values
            long gameId = Long.parseLong(gameIdField.getText().toString());
            String playerName = playerNameField.getText().toString();

            //Launch new thread to send the join game request
            JoinGameTask joinGameTask = new JoinGameTask(gameId, playerName, serverName);

            //Pseudo lock ui
            findViewById(R.id.btn_game_select).setEnabled(false);
            progressBar.setVisibility(ViewGroup.VISIBLE);
            joinGameTask.execute();
        }
        catch (Exception e){
            Toast.makeText(this, "Invalid field entry.", Toast.LENGTH_SHORT).show();
        }
    }

    //Helper method to adjust the server text field background color based
    //on whether or not the server URL is a valid poker server
    private void setServerFieldBGColor(){
        EditText serverText = (EditText) findViewById(R.id.txt_server_name);
        int color = this.getResources().getColor(R.color.SpringGreen);
        if(!validServer){
            color = this.getResources().getColor(R.color.PaleVioletRed);
        }
        serverText.setBackgroundColor(color);
    }

    //Check to see if the submit (join game) button should be enabled or disabled
    private void checkEnabledSubmit(){
        Button joinGameButton = (Button) findViewById(R.id.btn_game_select);
        if(validServer && (gameIdField.getText().toString().length() > 0) &&
                (playerNameField.getText().toString().length() > 0)){
            joinGameButton.setEnabled(true);
        }
        else{
            joinGameButton.setEnabled(false);
        }
    }

    //Private helper Async Task Class
    //Used for Connecting to the server to verify the server URL
    private class GameConnectedTask extends AsyncTask<String,Void,Boolean>{

        @Override
        protected Boolean doInBackground(String... params){
            //We expect multiple of these game connect tasks, therefore allow each task
            //to be canceled individually, saving on threads and network time
            if(this.isCancelled()){
                return false;
            }
            String serverName = params[0];
            //Ping the server
            return PokerNetworkService.getInstance().ping(serverName);
        }

        @Override
        protected void onPostExecute(Boolean result){
            //If this has been cancelled them the result is useless anyway
            if(this.isCancelled()){
                return;
            }

            //Store result and update UI
            validServer = result;
            setServerFieldBGColor();
            checkEnabledSubmit();
        }
    }

    //Private Helper Async Task class
    //Used to do the network service call to join the game
    private class JoinGameTask extends AsyncTask<Void,Void,Long> {

        private String playerName;
        private Long gameId;
        private String serverName;

        public JoinGameTask(long gameId, String playerName, String serverName){
            this.serverName = serverName;
            this.gameId = gameId;
            this.playerName = playerName;
        }

        @Override
        protected Long doInBackground(Void... params){
            return PokerNetworkService.getInstance().joinGame(serverName, playerName, gameId);
        }

        @Override
        protected void onPostExecute(Long result){
            //Generic error handling for now
            if(result <= 0){
                new AlertDialog.Builder(GameSelectionActivity.this)
                    .setTitle("Error")
                    .setMessage("Could not join the game.")
                    .setPositiveButton("OK", null)
                    .create()
                    .show();
                checkEnabledSubmit();
                progressBar.setVisibility(ViewGroup.GONE);
                return;
            }
            //If it all worked, store everything in the preferences manager
            PreferencesManager.setGameId(this.gameId, GameSelectionActivity.this);
            PreferencesManager.setPlayerId(result, GameSelectionActivity.this);
            //Finish the activity
            GameSelectionActivity.this.setResult(RESULT_OK);
            finish();
        }
    }
}
