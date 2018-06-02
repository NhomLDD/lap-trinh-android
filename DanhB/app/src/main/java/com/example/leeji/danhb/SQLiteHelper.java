package com.example.leeji.danhb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import com.example.leeji.danhb.Contact;

/**
 * Created by lee ji on 01/06/2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private final String TAG = "DBManager";
    private static final String DATABASE_NAME = "DBDanhBa";
    private static final String TABLE_NAME = "table_danhba";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String ADDRESS = "address";
    private static final String EMAIL = "email";
    private static final String PHONE_NUMBER = "number";
    private static final String ISMALE = "ismale";
    private static int VERSION = 1;

    private Context context;
    private String SQLQuery = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " integer primary key, " +
            NAME + " TEXT, " +
            ADDRESS + " TEXT, " +
            EMAIL + " TEXT, " +
            PHONE_NUMBER + " TEXT, " +
            ISMALE + " INT)";



    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
        Log.d(TAG, "DBManager: ");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQLQuery);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(TAG, "onUpgrade: ");
    }

    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, contact.getmName());
        values.put(ADDRESS, contact.getmAddress());
        values.put(EMAIL, contact.getmEmail());
        values.put(PHONE_NUMBER, contact.getmNumber());
        values.put(ISMALE, contact.getIsMale());

        db.insert(TABLE_NAME, null, values);
        db.close();
        Log.d(TAG, "addContact Successfuly");
    }

    public List<Contact> getAllContact() {
        List<Contact> listContact = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setmID(cursor.getInt(0));
                contact.setmName(cursor.getString(1)+"");
                contact.setmAddress(cursor.getString(2));
                contact.setmEmail(cursor.getString(3));
                contact.setmNumber(cursor.getString(4));
                contact.setIsMale(cursor.getInt(5));

                listContact.add(contact);

            } while (cursor.moveToNext());
        }
        db.close();
        return listContact;
    }
    public int updateContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,contact.getmName());
        contentValues.put(ADDRESS,contact.getmAddress());
        contentValues.put(EMAIL,contact.getmEmail());
        contentValues.put(PHONE_NUMBER,contact.getmNumber());
        //contentValues.put(ISMALE,contact.getIsMale());
        return db.update(TABLE_NAME,contentValues,ID+"=?",new String[]{String.valueOf(contact.getmID())});
    }
    public int deleteContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,ID+"=?",new String[] {String.valueOf(id)});
    }
}