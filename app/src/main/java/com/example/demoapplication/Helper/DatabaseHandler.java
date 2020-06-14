package com.example.demoapplication.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context, Object name, Object factory, int version) {
        super(context,  DATABASE_NAME, null, DATABASE_VERSION);
    }


    String password;
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "Mydatabase.db";

    // Contacts table name
    private static final String TABLE_REGISTER= "register";
    public static final String KEY_ID = "id";
    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_PASSWORD = "password";
    public static final String CREATE_TABLE="CREATE TABLE " + TABLE_REGISTER + "("
            + KEY_ID + " INTEGER PRIMARY KEY," + KEY_FIRST_NAME + " TEXT," + KEY_PASSWORD + " TEXT " + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTER);

        // Create tables again
        onCreate(db);
    }

    public void addregister(String username,String password)
    // code to add the new register
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIRST_NAME,username); // register first Name
        values.put(KEY_PASSWORD, password);
        db.insert(TABLE_REGISTER, null, values);
        db.close(); // Closing database connection

    }



    //code to get the register
    public boolean getregister(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_REGISTER,null,  "first_name=?",new String[]{username},null, null, null, null);
        if(cursor.getCount()<1){
            cursor.close();
            return false;
        }
        else if(cursor.getCount()>=1 && cursor.moveToFirst()){
            password = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD));
            cursor.close();
        }
        return true;
    }


    public String getDatabaseName() {
        return DATABASE_NAME;
    }

    public static String getKeyId() {
        return KEY_ID;
    }

    public static String getTableContacts() {
        return TABLE_REGISTER;
    }

    public static int getDatabaseVersion() {
        return DATABASE_VERSION;
    }

}