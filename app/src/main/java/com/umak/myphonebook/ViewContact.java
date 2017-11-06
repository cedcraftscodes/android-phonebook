package com.umak.myphonebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewContact extends AppCompatActivity {

    TextView tvname, tvnumber, tvemail, tvaddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        tvname = (TextView)findViewById(R.id.tvname);
        tvnumber = (TextView)findViewById(R.id.tvnumber);
        tvemail = (TextView)findViewById(R.id.tvemail);
        tvaddress = (TextView)findViewById(R.id.tvaddress);


        final Contact contact  =(Contact)getIntent().getSerializableExtra("CurrentContact");
        setTitle(contact.getName());
        tvname.setText(contact.getName());
        tvnumber.setText(contact.getNumber());
        tvemail.setText(contact.getEmail());
        tvaddress.setText(contact.getAddress());





    }
}
