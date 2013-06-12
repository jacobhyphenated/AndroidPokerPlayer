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
