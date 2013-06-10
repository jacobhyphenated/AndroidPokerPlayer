package com.hyphenated.pokerplayerclient.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by jacobhyphenated on 6/4/13.
 *
 * Helper class for managing read/write access to the android shared preferences
 */
public class PreferencesManager {

    private static final String SERVER_KEY = "Field1";
    private static final String GAME_ID_KEY = "Field2";
    private static final String PLAYER_ID_KEY = "Field3";

    /**
     * Get the Server Name from the stored preferences
     * @param ctx context
     * @return Server name or null
     */
    public static String getServerName(Context ctx){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        return pref.getString(SERVER_KEY, null);
    }

    /**
     * Set the server name for later use
     * @param serverName name of the server to connect to
     * @param ctx Context
     */
    public static void setServerName(String serverName, Context ctx){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(SERVER_KEY, serverName);
        editor.commit();
    }

    /**
     * Get the unique ID of the game being played
     * @param ctx Context
     * @return Game ID
     */
    public static long getGameId(Context ctx){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        return pref.getLong(GAME_ID_KEY, 0);
    }

    /**
     * Store the unique ID of the the game being played
     * @param gameId game unique ID
     * @param ctx Context
     */
    public static void setGameId(Long gameId, Context ctx){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(GAME_ID_KEY, gameId);
        editor.commit();
    }

    /**
     * Get the unique ID of the player (this android client)
     * @param ctx context
     * @return player id
     */
    public static long getPlayerId(Context ctx){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        return pref.getLong(PLAYER_ID_KEY, 0);
    }

    /**
     * store the unique id of the player (this android client)
     * @param playerId unique id
     * @param ctx context
     */
    public static void setPlayerId(Long playerId, Context ctx){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(PLAYER_ID_KEY, playerId);
        editor.commit();
    }
}
