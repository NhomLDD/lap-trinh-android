package com.example.leeji.danhb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;

/**
 * Created by lee ji on 01/06/2018.
 */

public class AddActivity extends AppCompatActivity {

    private EditText edtName;
    private EditText editAddress;
    private EditText edtPhoneNumber;
    private EditText edtEmail;
    private EditText edtId;
    private RadioButton rbtnMale;
    private RadioButton rbtnFemale;
    private Button btnSave;
    private Button btnCancel;
    private ListView lvStudent;
    private SQLiteHelper sqLiteHelper;
    private Adapter Adapter;
    private List<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_contact);

        sqLiteHelper= new SQLiteHelper(this);
        initWidget();
        contactList = sqLiteHelper.getAllContact();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact contact = createContact();
                if(TextUtils.isEmpty(createContact().getmName())||TextUtils.isEmpty(createContact().getmAddress())
                        ||TextUtils.isEmpty(createContact().getmEmail())||TextUtils.isEmpty(createContact().getmNumber())){
                    Toast.makeText(AddActivity.this, "Liên hệ trống", Toast.LENGTH_SHORT).show();}
                else{
                    sqLiteHelper.addContact(contact);
                    Toast.makeText(AddActivity.this, "Thêm danh bạ thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                contactList.clear();
                contactList.addAll(sqLiteHelper.getAllContact());
                updateListContact();


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }

        });
    }

      private Contact createContact() {
        String name = edtName.getText().toString();
        String address = editAddress.getText().toString();
        String email = edtEmail.getText().toString();
        String phoneNumber = edtPhoneNumber.getText().toString();
        int ismale;
          if (rbtnMale.isChecked()){
              ismale=1;
          }else {
              ismale=2;
          }

        Contact contact = new Contact(name, address, email, phoneNumber, ismale);
        return contact;
    }

    private void initWidget() {
        edtName = (EditText) findViewById(R.id.edt_name);
        editAddress = (EditText) findViewById(R.id.edt_address);
        edtPhoneNumber = (EditText) findViewById(R.id.edt_number);
        edtEmail = (EditText) findViewById(R.id.edt_email);
        rbtnMale = findViewById(R.id.rbtn_male);
        rbtnFemale = findViewById(R.id.rbtn_female);
        btnSave = (Button) findViewById(R.id.btn_add_contact);
        lvStudent = (ListView) findViewById(R.id.lv_danhba);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
    }

    public void updateListContact(){
        if(Adapter!= null){
            Adapter.notifyDataSetChanged();
            lvStudent.setSelection(Adapter.getCount()-1);
        }
    }
}
