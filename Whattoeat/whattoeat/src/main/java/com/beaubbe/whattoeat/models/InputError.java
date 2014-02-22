package com.beaubbe.whattoeat.models;

/**
 * Created by Gab on 07/02/14.
 */
public class InputError {
    public String field;
    public String error;

    public InputError(String field, String error)
    {
        this.field = field;
        this.error=error;
    }
}
