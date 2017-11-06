package com.umak.myphonebook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    static ListView lv;
    static Context context;
    static ContactsOperation contactsOperation;
    static List<Contact> contacts;
    static ContactsAdapter contactsAdapter;

    EditText txtsearch;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Contacts");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        context=this;

        contactsOperation = new ContactsOperation(this);

        lv=(ListView) findViewById(R.id.lstview);
        txtsearch = (EditText)findViewById(R.id.etsearch);


        contacts = new ArrayList<>();
        contacts = contactsOperation.getAllContacts();
        contactsAdapter = new ContactsAdapter((MainActivity)context, contacts);


        lv.setAdapter(contactsAdapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add_intent = new Intent(getApplicationContext(), AddContactActivity.class);
                startActivity(add_intent);





            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contact contact = contacts.get(position);

                Intent view_intent = new Intent(getApplicationContext(), ViewContact.class);
                view_intent.putExtra("CurrentContact", contact);
                startActivity(view_intent);

            }
        });


         txtsearch.addTextChangedListener(new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

             @Override
             public void onTextChanged(CharSequence s, int start, int before, int count) {
                getContacts(s.toString());
             }

             @Override
             public void afterTextChanged(Editable s) {

             }
         });

        registerForContextMenu(lv);








    }

    private Contact curr_contact;
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.lstview) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            curr_contact = (Contact) lv.getItemAtPosition(acmi.position);


            menu.setHeaderTitle(curr_contact.getName());
            menu.add(0, 1, 0, "Edit");//groupId, itemId, order, title
            menu.add(0, 2, 0, "Delete");



        }
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case 1:
                Intent edit_intent = new Intent(getApplicationContext(), EditContact.class);
                edit_intent.putExtra("CurrentContact", curr_contact);
                startActivity(edit_intent);


                return true;

            case 2:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to delete " + curr_contact.getName() + "?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        contactsOperation.removeContact(curr_contact);
                        dialog.dismiss();
                        LoadListView();
                        txtsearch.setText("");
                        Toast.makeText(getApplicationContext(), "Contact Deleted!", Toast.LENGTH_SHORT).show();

                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
                return true;


        }

        return super.onContextItemSelected(item);
    }



    public static void LoadListView(){
        contactsAdapter = new ContactsAdapter((MainActivity)context, contactsOperation.getAllContacts());
        lv.setAdapter(contactsAdapter);
        contactsAdapter.notifyDataSetChanged();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }



    private void getContacts(String searchTerm)
    {
        if(searchTerm.equals("") == false){
            contacts.clear();
            ContactsOperation db= new ContactsOperation(this);
            db.open();
            Contact p=null;
            Cursor c = db.retrieve(searchTerm);
            while (c != null && c.moveToNext())
            {
                p=new Contact();
                p.setContactid(c.getLong(c.getColumnIndex(ContactsDbHandler.COLUMN_ID)));
                p.setName(c.getString(c.getColumnIndex(ContactsDbHandler.COLUMN_NAME)));
                p.setNumber(c.getString(c.getColumnIndex(ContactsDbHandler.COLUMN_NUMBER)));
                p.setEmail(c.getString(c.getColumnIndex(ContactsDbHandler.COLUMN_EMAIL)));
                p.setAddress(c.getString(c.getColumnIndex(ContactsDbHandler.COLUMN_ADDRESS)));
                contacts.add(p);
            }
            db.close();


            contactsAdapter = new ContactsAdapter((MainActivity)context, contacts);
            lv.setAdapter(contactsAdapter);
            contactsAdapter.notifyDataSetChanged();
        }else{
            LoadListView();
        }

    }



}
