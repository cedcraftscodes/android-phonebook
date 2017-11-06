package com.umak.myphonebook;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditContact extends AppCompatActivity {

    EditText txtnamein , txtnumberin, txtemail, txtaddress;
    Button btnedit;
    ContactsOperation contactsOperation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        // To retrieve object in second Activity
        final Contact contact  =(Contact)getIntent().getSerializableExtra("CurrentContact");
        setTitle(contact.getName());


        contactsOperation = new ContactsOperation(this);

        txtnamein = (EditText)findViewById(R.id.txtnamein_edit);
        txtnumberin = (EditText)findViewById(R.id.txtnumberin_edit);
        txtemail = (EditText)findViewById(R.id.txtemail_edit);
        txtaddress = (EditText)findViewById(R.id.txtpostal_edit);
        btnedit = (Button)findViewById(R.id.btnaddcontact_edit);


        txtnamein.setText(contact.getName());
        txtnumberin.setText(contact.getNumber());
        txtemail.setText(contact.getEmail());
        txtaddress.setText(contact.getAddress());


        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contact.setName(txtnamein.getText().toString());
                contact.setEmail(txtemail.getText().toString());
                contact.setAddress(txtaddress.getText().toString());
                contact.setNumber(txtnumberin.getText().toString());



                if(txtnamein.getText().toString().trim().equals("") || txtnumberin.getText().toString().trim().equals("")){
                    Snackbar snackbar = Snackbar
                            .make(findViewById(R.id.mycoordinator_add), "Name and Number is required!", Snackbar.LENGTH_LONG);

                    snackbar.show();


                }else{
                    contactsOperation.open();
                    contactsOperation.updateContact(contact);
                    contactsOperation.close();

                    Toast t = Toast.makeText(getApplicationContext(), "New Contact Successfully Updated!", Toast.LENGTH_SHORT);
                    t.show();

                    MainActivity.LoadListView();
                    finish();

                }





            }
        });





    }
}
