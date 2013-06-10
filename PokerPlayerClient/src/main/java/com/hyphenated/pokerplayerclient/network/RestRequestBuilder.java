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
