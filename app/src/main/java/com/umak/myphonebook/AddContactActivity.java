package com.umak.myphonebook;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddContactActivity extends AppCompatActivity {

    EditText txtnamein , txtnumberin, txtemail, txtaddress;
    Button btnaddnew;
    Contact newcontact;
    ContactsOperation contactsOperation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);


        contactsOperation = new ContactsOperation(this);


        setTitle("New Contact");

        txtnamein = (EditText)findViewById(R.id.txtnamein);
        txtnumberin = (EditText)findViewById(R.id.txtnumberin);
        txtemail = (EditText)findViewById(R.id.txtemail);
        txtaddress = (EditText)findViewById(R.id.txtpostal);
        btnaddnew = (Button)findViewById(R.id.btnaddcontact);



        btnaddnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newcontact = new Contact();
                newcontact.setName(txtnamein.getText().toString());
                newcontact.setNumber(txtnumberin.getText().toString());
                newcontact.setEmail(txtemail.getText().toString());
                newcontact.setAddress(txtaddress.getText().toString());


                if(txtnamein.getText().toString().trim().equals("") || txtnumberin.getText().toString().trim().equals("")){
                    Snackbar snackbar = Snackbar
                            .make(findViewById(R.id.mycoordinator), "Name and Number is required!", Snackbar.LENGTH_LONG);

                    snackbar.show();


                }else{
                    contactsOperation.addContact(newcontact);
                    Toast t = Toast.makeText(getApplicationContext(), "New Contact Successfully Added!", Toast.LENGTH_SHORT);
                    t.show();

                    MainActivity.LoadListView();
                    finish();

                }


            }
        });


    }
}
