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
package com.hyphenated.pokerplayerclient.network;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic Abstract Class that wraps HTTP Post Rest Network requests.
 *
 * Going to over-engineer the networking code a bit.
 *
 * Created by jacobhyphenated on 6/5/13.
 */
public abstract class RestRequestBuilder<ResponseType> implements RequestBuilder<ResponseType> {

    protected HttpParams httpParams;
    protected HttpPost httpPost;
    protected List<NameValuePair> params;

    public RestRequestBuilder(){
        httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
        HttpConnectionParams.setSoTimeout(httpParams, 5000);
        params = new ArrayList<NameValuePair>();
    }

    @Override
    public void setURL(String url) {
        httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
    }

    @Override
    public void addParameter(String paramName, String paramValue) {
        params.add(new BasicNameValuePair(paramName, paramValue));
    }

    @Override
    public void addParameter(String paramName, long paramValue) {
        addParameter(paramName, Long.toString(paramValue));
    }

    @Override
    public void addParameter(String paramName, int paramValue) {
        addParameter(paramName, Integer.toString(paramValue));
    }

    @Override
    public void addParameter(String paramName, boolean paramValue) {
        addParameter(paramName, Boolean.toString(paramValue));
    }
}
