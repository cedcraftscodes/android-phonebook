package com.umak.myphonebook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Icorrelate on 10/17/2017.
 */

public class ContactsDbHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 3;

    public static final String TABLE_CONTACTS = "employees";
    public static final String COLUMN_ID = "contactid";
    public static final String COLUMN_NAME = "contactname";
    public static final String COLUMN_NUMBER = "contactnumber";
    public static final String COLUMN_EMAIL = "contactemail";
    public static final String COLUMN_ADDRESS = "contactaddress";



    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_CONTACTS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_NUMBER + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_ADDRESS + " TEXT " +
                    ")";


    public ContactsDbHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_CONTACTS);
        db.execSQL(TABLE_CREATE);
    }
}
