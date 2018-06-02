package com.example.leeji.danhb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by lee ji on 02/06/2018.
 */

public class UpdateActivity extends AppCompatActivity {
    private SQLiteHelper sqLiteHelper;
    private Adapter Adapter;
    private List<Contact> contactList;
    private EditText edtUpname;
    private EditText edtUpaddress;
    private EditText edtUpemail;
    private EditText edtUpnumber;
    private Button btnSaveUp;
    private Button btnCancelUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_layout);

        Intent intent = getIntent();
        final int position = intent.getIntExtra(MainActivity.intents, 0);
        //Log.d(String.valueOf(position)," xxxxxx");

        sqLiteHelper= new SQLiteHelper(this);
        edtUpname = (EditText) findViewById(R.id.edt_upname);
        edtUpaddress = (EditText) findViewById(R.id.edt_upaddress);
        edtUpnumber = (EditText) findViewById(R.id.edt_upnumber);
        edtUpemail = (EditText) findViewById(R.id.edt_upemail);
        btnSaveUp = (Button) findViewById(R.id.btn_save_update);
        btnCancelUp = (Button) findViewById(R.id.btn_cancel_update);

        contactList = sqLiteHelper.getAllContact();
        Contact contact = contactList.get(position);

        edtUpname.setText(contact.getmName());
        edtUpaddress.setText(contact.getmAddress());
        edtUpemail.setText(contact.getmEmail());
        edtUpnumber.setText(contact.getmNumber());

        btnSaveUp.setOnClickListener(new View.OnClickListener() {
            Contact contact = contactList.get(position);
            int id=contact.getmID();

            @Override
            public void onClick(View view) {

                Contact contacts = new Contact();
                contacts.setmID(id);
                contacts.setmName(edtUpname.getText().toString());
                contacts.setmAddress(edtUpaddress.getText().toString());
                contacts.setmEmail(edtUpemail.getText().toString());
                contacts.setmNumber(edtUpnumber.getText().toString());
                int result = sqLiteHelper.updateContact(contacts);
                if(TextUtils.isEmpty(contacts.getmName())||TextUtils.isEmpty(contacts.getmAddress())
                        ||TextUtils.isEmpty(contacts.getmEmail())||TextUtils.isEmpty(contacts.getmNumber())){
                    Toast.makeText(UpdateActivity.this, "Liên hệ trống!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(result>0){
                        updateListContact();
                        Toast.makeText(UpdateActivity.this, "cập nhật thành công!", Toast.LENGTH_SHORT).show();
                        contactList.clear();
                        contactList.addAll(sqLiteHelper.getAllContact());
                        updateListContact();
                        Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                }
            }
        });

        btnCancelUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    public void updateListContact(){
        contactList.clear();
        contactList.addAll(sqLiteHelper.getAllContact());
        if(Adapter!= null){
            Adapter.notifyDataSetChanged();
        }
    }
}
