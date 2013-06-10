package com.hyphenated.pokerplayerclient.network;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * Class to wrap the network code behind building and sending an HTTP (REST) request
 * and parsing the output into a JSON Object
 *
 * Created by jacobhyphenated on 6/5/13.
 */
public class RestObjectRequestBuilder extends RestRequestBuilder<JSONObject> {

    @Override
    public JSONObject sendRequest() throws Exception {
        HttpClient httpClient = new DefaultHttpClient(httpParams);
        httpPost.setEntity(new UrlEncodedFormEntity(params));
        HttpResponse httpResponse = httpClient.execute(httpPost);
        String response = EntityUtils.toString(httpResponse.getEntity());
        return new JSONObject(response);
    }
}
