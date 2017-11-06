package com.umak.myphonebook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ContactsAdapter extends BaseAdapter{
    Context context;
    List<Contact> contacts;
    private static LayoutInflater inflater=null;
    public ContactsAdapter(MainActivity mainActivity, List<Contact> contacts) {
        // TODO Auto-generated constructor stub
        context=mainActivity;
        this.contacts = contacts;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return contacts.get(position).getContactid();
    }

    public class Holder
    {
        TextView tvname;
        TextView tvnumber;



    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.contact_list, null);
        holder.tvname=(TextView) rowView.findViewById(R.id.txtname);
        holder.tvnumber=(TextView) rowView.findViewById(R.id.txtnumber);

        holder.tvname.setText(contacts.get(position).getName());
        holder.tvnumber.setText(contacts.get(position).getNumber());

        /*
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

            }
        });

        */
        return rowView;
    }

}