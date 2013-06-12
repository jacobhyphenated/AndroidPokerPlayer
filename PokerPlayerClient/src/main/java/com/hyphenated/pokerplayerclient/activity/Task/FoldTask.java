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
package com.hyphenated.pokerplayerclient.activity.Task;

import com.hyphenated.pokerplayerclient.service.PlayerStatusHandler;
import com.hyphenated.pokerplayerclient.service.PokerNetworkService;

/**
 * Perform the Network call to do the fold player action on a background thread
 * <br /><br />
 * Created by jacobhyphenated on 6/7/13.
 */
public class FoldTask extends ActionTask {

    public FoldTask(String serverName, long gameId, long playerId, PlayerStatusHandler playerHandler){
        super(serverName,gameId, playerId, playerHandler);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return PokerNetworkService.getInstance().fold(serverName,gameId,playerId);
    }


}
