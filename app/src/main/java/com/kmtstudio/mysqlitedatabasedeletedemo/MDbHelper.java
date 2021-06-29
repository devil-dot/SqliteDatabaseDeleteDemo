package com.kmtstudio.mysqlitedatabasedeletedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Student";
    private static final String TABLE_NAME = "student_details";
    private static final String ID = "_id";
    private static final String NAME = "Name";
    private static final String AGE = "Age";
    private static final String GENDER = "Gender";
    private static final int VERSION_CODE = 1;
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(255) NOT NULL, " + AGE + " INTEGER NOT NULL, " + GENDER + " VARCHAR NOT NULL)";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private static final String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;

    private Context context;

    public MDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION_CODE);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {

            Toast.makeText(context, "onCreate is called", Toast.LENGTH_LONG).show();
            db.execSQL(CREATE_TABLE);

        } catch (Exception e) {

            Toast.makeText(context, "Exception : " + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {

            Toast.makeText(context, "onUpgrade is called", Toast.LENGTH_LONG).show();
            db.execSQL(DROP_TABLE);
            onCreate(db);

        } catch (Exception e) {

            Toast.makeText(context, "Exception : " + e, Toast.LENGTH_LONG).show();
        }
    }

    public long insertData(String name, String age, String gender) {

        SQLiteDatabase liteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(AGE, age);
        values.put(GENDER, gender);

        return liteDatabase.insert(TABLE_NAME, null, values);
    }

    public Cursor displayData() {

        SQLiteDatabase database = this.getWritableDatabase();

        return database.rawQuery(SELECT_ALL, null);
    }

    public boolean updateData(String id, String name, String age, String gender) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, id);
        values.put(NAME, name);
        values.put(AGE, age);
        values.put(GENDER, gender);

        sqLiteDatabase.update(TABLE_NAME, values, ID + " = ?", new String[]{id});

        return true;
    }

    public int deleteData(String id) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME,ID+" = ?",new String[]{id});
    }
}
