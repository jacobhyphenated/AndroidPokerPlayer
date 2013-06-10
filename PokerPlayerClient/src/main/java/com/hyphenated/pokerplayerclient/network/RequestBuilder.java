package com.hyphenated.pokerplayerclient.network;

/**
 * Interface for making network requests.  Use a generic return type
 * so implementations classes can specify how to handle the network response
 *
 * Created by jacobhyphenated on 6/5/13.
 */
public interface RequestBuilder<ReturnType> {

    /**
     * Set the url endpoint for the network request
     * @param url complete URL (<em>http://xxx.yyy.zzz/uri</em>)
     */
    public void setURL(String url);

    /**
     * Set a string request parameter
     * @param paramName Name of the parameter
     * @param paramValue value of the string parameter
     */
    public void addParameter(String paramName, String paramValue);

    /**
     * Set a long integer type request parameter
     * @param paramName Name of the parameter
     * @param paramValue long value of the parameter
     */
    public void addParameter(String paramName, long paramValue);

    /**
     * Set an integer type request parameter
     * @param paramName Name of the parameter
     * @param paramValue integer value of the parameter
     */
    public void addParameter(String paramName, int paramValue);

    /**
     * Set a boolean type request parameter
     * @param paramName Name of the parameter
     * @param paramValue boolean value of the parameter
     */
    public void addParameter(String paramName, boolean paramValue);

    /**
     * Send the network request and get the response
     * @return Formatted network response
     */
    public ReturnType sendRequest() throws Exception;
}
