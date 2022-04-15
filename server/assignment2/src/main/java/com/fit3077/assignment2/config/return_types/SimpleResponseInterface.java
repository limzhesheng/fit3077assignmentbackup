package com.fit3077.assignment2.config.return_types;

import java.net.URI;

public interface SimpleResponseInterface<T> {
    
    public int statusCode();

    public URI uri();

    public T body();

}
