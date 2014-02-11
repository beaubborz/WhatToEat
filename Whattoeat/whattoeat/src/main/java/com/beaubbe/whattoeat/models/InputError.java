package com.beaubbe.whattoeat.models;

/**
 * Created by Gab on 07/02/14.
 */
public class InputError {
    public String id;
    public String error;

    public InputError(String id, String error)
    {
        this.id = id;
        this.error=error;
    }
}
