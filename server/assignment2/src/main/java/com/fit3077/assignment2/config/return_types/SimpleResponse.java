package com.fit3077.assignment2.config.return_types;

import java.net.URI;

/**
 * Class for responses back to front end using generic types.
 */
public class SimpleResponse<T> implements SimpleResponseInterface<T> {
    private int responseStatusCode;
    private URI responseUri;
    private T responseBody;

    public SimpleResponse(int statusCode, URI uri, T body) {
        this.responseStatusCode = statusCode;
        this.responseUri = uri;
        this.responseBody = body;
    }

    @Override
    public int statusCode() {
        return this.responseStatusCode;
    }

    @Override
    public URI uri() {
        return this.responseUri;
    }

    @Override
    public T body() {
        return this.responseBody;
    }
    
}
