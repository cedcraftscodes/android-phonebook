package com.umak.myphonebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Icorrelate on 10/17/2017.
 */

public class ContactsOperation {
    public static final String LOGTAG = "PHONEBOOK_APP";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase database;
    private static final String[] allColumns = {
            ContactsDbHandler.COLUMN_ID,
            ContactsDbHandler.COLUMN_NAME,
            ContactsDbHandler.COLUMN_NUMBER,
            ContactsDbHandler.COLUMN_EMAIL,
            ContactsDbHandler.COLUMN_ADDRESS


    };
    public ContactsOperation(Context context){
        dbhandler = new ContactsDbHandler(context);
    }

    public void open(){
        Log.i(LOGTAG,"Database Opened");
        database = dbhandler.getWritableDatabase();


    }

    public void close(){
        Log.i(LOGTAG, "Database Closed");
        dbhandler.close();

    }

    public Contact addContact(Contact contact){
        open();
        ContentValues values  = new ContentValues();
        values.put(ContactsDbHandler.COLUMN_NAME,   contact.getName());
        values.put(ContactsDbHandler.COLUMN_NUMBER, contact.getNumber());
        values.put(ContactsDbHandler.COLUMN_EMAIL,   contact.getEmail());
        values.put(ContactsDbHandler.COLUMN_ADDRESS, contact.getAddress());


        long insertid = database.insert(ContactsDbHandler.TABLE_CONTACTS,null,values);
        contact.setContactid(insertid);
        close();
        return contact;

    }


    public Contact getEmployee(long id) {
        open();
        Cursor cursor = database.query(ContactsDbHandler.TABLE_CONTACTS,allColumns, ContactsDbHandler.COLUMN_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact e = new Contact(Long.parseLong(cursor.getString(0)),cursor.getString(1),cursor.getString(2), cursor.getString(3), cursor.getString(4));
        // return Employee

        close();
        return e;
    }

    public List<Contact> getAllContacts() {
        open();
        Cursor cursor = database.query(ContactsDbHandler.TABLE_CONTACTS,allColumns,null,null,null, null, null);

        List<Contact> contacts = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                Contact contact = new Contact();
                contact.setContactid(cursor.getLong(cursor.getColumnIndex(ContactsDbHandler.COLUMN_ID)));
                contact.setName(cursor.getString(cursor.getColumnIndex(ContactsDbHandler.COLUMN_NAME)));
                contact.setNumber(cursor.getString(cursor.getColumnIndex(ContactsDbHandler.COLUMN_NUMBER)));
                contact.setEmail(cursor.getString(cursor.getColumnIndex(ContactsDbHandler.COLUMN_EMAIL)));
                contact.setAddress(cursor.getString(cursor.getColumnIndex(ContactsDbHandler.COLUMN_ADDRESS)));

                contacts.add(contact);
            }
        }
        close();
        return contacts;
    }



    // Updating Employee
    public int updateContact(Contact contact) {

        ContentValues values = new ContentValues();
        values.put(ContactsDbHandler.COLUMN_NAME, contact.getName());
        values.put(ContactsDbHandler.COLUMN_NUMBER, contact.getNumber());
        values.put(ContactsDbHandler.COLUMN_EMAIL, contact.getEmail());
        values.put(ContactsDbHandler.COLUMN_ADDRESS, contact.getAddress());

        // updating row
        return database.update(ContactsDbHandler.TABLE_CONTACTS, values,
                ContactsDbHandler.COLUMN_ID + "=?",new String[] { String.valueOf(contact.getContactid())});
    }

    // Deleting Employee
    public void removeContact(Contact contact) {
        open();
        database.delete(ContactsDbHandler.TABLE_CONTACTS, ContactsDbHandler.COLUMN_ID + "=" + contact.getContactid(), null);
        close();
    }






    public Cursor retrieve(String searchTerm)
    {
        String[] columns={ContactsDbHandler.COLUMN_ID, ContactsDbHandler.COLUMN_NAME};
        Cursor c=null;
        if(searchTerm != null && searchTerm.length()>0)
        {
            String sql="SELECT * FROM "+ContactsDbHandler.TABLE_CONTACTS+" WHERE "+ContactsDbHandler.COLUMN_NAME+" LIKE '%"+searchTerm+"%' OR " +ContactsDbHandler.COLUMN_NUMBER + " LIKE '%"+searchTerm+"%'";
            c= database.rawQuery(sql,null);
            return c;
        }
        c=database.query(ContactsDbHandler.TABLE_CONTACTS ,columns,null,null,null,null,null);
        return c;
    }




}
