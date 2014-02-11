package com.beaubbe.whattoeat.models;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gab on 07/02/14.
 */
public abstract class DatabaseModel {
    private DatabaseManager dbManager;
    private SQLiteDatabase db;
    protected List<InputError> errors;
    Context context;
    boolean isNew;

    public DatabaseModel(Context c)
    {
        context = c;
        dbManager = new DatabaseManager(c);
        errors = new ArrayList<InputError>();
        isNew = true;
    }

    public void closeDbConnexion() {
        dbManager.close();
    }

    public List<InputError> getErrors()
    {
        return errors;
    }

    public boolean hasErrors()
    {
        return !errors.isEmpty();
    }

    //warning, must call closeDbConnexion when done with the DB!
    protected SQLiteDatabase getDatabase()
    {
        if(db == null || !db.isOpen())
            db = dbManager.getWritableDatabase();
        return db;
    }

    public boolean save()
    {
        SQLiteDatabase db = getDatabase();
        boolean outcome = save(db);
        db.close();
        closeDbConnexion();
        return outcome;
    };

    public abstract boolean save(SQLiteDatabase db);
    public abstract boolean validate();

    public boolean removeFromDatabase()
    {
        SQLiteDatabase db = getDatabase();
        boolean outcome = removeFromDatabase(db);
        db.close();
        closeDbConnexion();
        return outcome;
    };

    public abstract boolean removeFromDatabase(SQLiteDatabase db);
}
