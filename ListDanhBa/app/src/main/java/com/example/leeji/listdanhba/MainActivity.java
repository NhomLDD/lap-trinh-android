package com.example.leeji.listdanhba;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.leeji.listdanhba.Adapter.ContactAdapter;
import com.example.leeji.listdanhba.model.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Contact> arrayContact;
    private ContactAdapter adapter;
    private EditText edtName;
    private EditText edtNumber;
    private RadioButton rbtnMale;
    private RadioButton rbtnFemale;
    private Button btnAddContact;
    private ListView lvContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setWidget();
        arrayContact = new ArrayList<>();
        adapter=new ContactAdapter(this,R.layout.item_listview,arrayContact);
        lvContact.setAdapter(adapter);

    }
    public void setWidget(){
        edtName=(EditText) findViewById(R.id.edt_name);
        edtNumber=(EditText) findViewById(R.id.edt_number);
        rbtnMale=(RadioButton) findViewById(R.id.rbtn_male);
        rbtnFemale=(RadioButton) findViewById(R.id.rbtn_female);
        btnAddContact=(Button) findViewById(R.id.btn_add_contact);
        lvContact=(ListView) findViewById(R.id.lv_contact);
    }
    public void addContact(View view){
        if(view.getId()==R.id.btn_add_contact){
            String name=edtName.getText().toString().trim();
            String number=edtNumber.getText().toString().trim();
            boolean isMale=true;
            if (rbtnMale.isChecked()){
                isMale=true;
            }else {
                isMale=false;
            }
            if(TextUtils.isEmpty(name) || TextUtils.isEmpty(number)){
                Toast.makeText(this, "Liên hệ trống", Toast.LENGTH_SHORT).show();
            }else {
                Contact contact = new Contact(isMale,name,number);
                arrayContact.add(contact);
            }
            adapter.notifyDataSetChanged();
        }


    }
}
