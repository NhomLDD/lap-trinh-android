package com.example.leeji.danhb;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String intents="intents";

    private ListView lvContact;
    private SQLiteHelper sqLiteHelper;
    private Adapter adapter;
    private ArrayList<Contact> contactList;
    private List<Contact> mcontact;

    private TextView dltvName;
    private TextView dltvAddress;
    private TextView dltvEmail;
    private TextView dltvNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqLiteHelper = new SQLiteHelper(this);
        initWidget();
        mcontact = sqLiteHelper.getAllContact();
        setAdapter();
        checkAndReQuestPermissions();
        lvContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                showLongOnclickDialog(position);
                return false;
            }
        });

        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                    showDialogConfirm(position);
                }
        });


    }

    private void checkAndReQuestPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission : permissions){
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()){
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),1);
        }
    }

    private void initWidget() {
        lvContact = (ListView) findViewById(R.id.lv_danhba);

    }

    private void setAdapter(){
        if(adapter==null){
            adapter = new Adapter(this,R.layout.item_listview,mcontact);
            adapter.notifyDataSetChanged();
        }
        lvContact.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent intent = new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void showLongOnclickDialog(final int position){
        final View viewdialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.longclick_dialog_layout,null,false);
        AlertDialog.Builder alertDialog =new AlertDialog.Builder(MainActivity.this);
        alertDialog.setView(viewdialog);

        Log.d(String.valueOf(position)," xxx");
        Button btnUpdate = viewdialog.findViewById(R.id.btn_update);
        Button btnDelete = viewdialog.findViewById(R.id.btn_delete);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                intent.putExtra(intents,position);
                startActivity(intent);
            }
        });
//                View viewdialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.update_layout,null,false);
//                AlertDialog.Builder alertDialog =new AlertDialog.Builder(MainActivity.this);
//                alertDialog.setView(viewdialog);
//
//                Button btnSaveUpdate = viewdialog.findViewById(R.id.btn_save_update);
//                Button btnCancelUpdate = viewdialog.findViewById(R.id.btn_save_update);
//
//
//                btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        intentUpdate(position);
//                    }
//                });
//                btnCancelUpdate.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        onBackPressed();
//                    }
//                });
//                alertDialog.show();
//            }
//
//        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact contact = mcontact.get(position);

                sqLiteHelper.deleteContact(contact.getmID());
                Toast.makeText(MainActivity.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                mcontact.clear();
                mcontact.addAll(sqLiteHelper.getAllContact());
                setAdapter();
            }
        });
        alertDialog.show();
    }

//    private void intentUpdate(int position){
//        View viewdialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.update_layout,null,false);
//        AlertDialog.Builder alertDialog =new AlertDialog.Builder(MainActivity.this);
//        alertDialog.setView(viewdialog);
//
//        alertDialog.show();
//    }


    public void showDialogConfirm(final int position){
        View viewdialog = LayoutInflater.from(MainActivity.this).inflate(R.layout.contact_dialog_layout,null,false);
        AlertDialog.Builder alertDialog =new AlertDialog.Builder(MainActivity.this);
        alertDialog.setView(viewdialog);

        dltvName = (TextView) viewdialog.findViewById(R.id.dl_tv_name);
        dltvAddress = (TextView) viewdialog.findViewById(R.id.dl_tv_address);
        dltvEmail = (TextView) viewdialog.findViewById(R.id.dl_tv_email);
        dltvNumber = (TextView) viewdialog.findViewById(R.id.dl_tv_number);
        Button btnCall = viewdialog.findViewById(R.id.btn_call);
        Button btnSendMessage = viewdialog.findViewById(R.id.btn_send_message);

        Contact contact = mcontact.get(position);
        dltvName.setText(contact.getmName());
        dltvAddress.setText(contact.getmAddress());
        dltvEmail.setText(contact.getmEmail());
        dltvNumber.setText(contact.getmNumber());

        Log.d(String.valueOf(position)," xxx");
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentCall(position);
            }
        });

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentSendMessage(position);
            }
        });
        alertDialog.show();
    }

    private void intentCall(int position){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+mcontact.get(position).getmNumber()));
        startActivity(intent);

    }

    private void intentSendMessage(int position){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("sms:"+mcontact.get(position).getmNumber()));
        startActivity(intent);
    }

}
