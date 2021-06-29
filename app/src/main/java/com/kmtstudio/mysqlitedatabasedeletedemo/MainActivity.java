package com.kmtstudio.mysqlitedatabasedeletedemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText idTxt, nameTxt, ageTxt, genderTxt;
    private Button addBtn, displayBtn, updateBtn,deleteBtn;

    MDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new MDbHelper(this);
        SQLiteDatabase database = mDbHelper.getWritableDatabase();


        idTxt = findViewById(R.id.idEditTextID);
        nameTxt = findViewById(R.id.nameEditTextID);
        ageTxt = findViewById(R.id.ageEditTextID);
        genderTxt = findViewById(R.id.genderEditTextID);

        addBtn = findViewById(R.id.addBtnID);
        displayBtn = findViewById(R.id.displayBtnID);
        updateBtn = findViewById(R.id.updateBtnID);
        deleteBtn = findViewById(R.id.deleteBtnID);


        addBtn.setOnClickListener(this);
        displayBtn.setOnClickListener(this);
        updateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        String id = idTxt.getText().toString();
        String name = nameTxt.getText().toString();
        String age = ageTxt.getText().toString();
        String gender = genderTxt.getText().toString();


        if (v.getId() == R.id.addBtnID) {

            long rowID = mDbHelper.insertData(name, age, gender);

            if (rowID == -1) {

                Toast.makeText(getApplicationContext(), "Row data insert unsuccessful", Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(getApplicationContext(), " Row " + rowID + " data successfully inserted ", Toast.LENGTH_SHORT).show();
            }

        }

        if (v.getId() == R.id.displayBtnID) {

            Cursor cursor = mDbHelper.displayData();

            if (cursor.getCount() == 0) {

                //there is no data so we will show message
                showMessage("Error", "No Data Found");
            }

            StringBuilder stringBuilder = new StringBuilder();


            while (cursor.moveToNext()) {

                stringBuilder.append("ID : ").append(cursor.getString(0)).append("\n");
                stringBuilder.append("Name : ").append(cursor.getString(1)).append("\n");
                stringBuilder.append("Age : ").append(cursor.getString(2)).append("\n");
                stringBuilder.append("Gender : ").append(cursor.getString(3)).append("\n").append("\n").append("\n");
            }

            showMessage("result", stringBuilder.toString());


        } else if (v.getId() == R.id.updateBtnID) {

            boolean isUpdated = mDbHelper.updateData(id, name, age, gender);

            if (isUpdated) {

                Toast.makeText(getApplicationContext(), "Row data is updated", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(getApplicationContext(), "Row data is not updated", Toast.LENGTH_SHORT).show();
            }
        }


        if (v.getId() == R.id.deleteBtnID) {

            int value =  mDbHelper.deleteData(id);

            if (value>0) {

                Toast.makeText(getApplicationContext(),"Data is deleted",Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(getApplicationContext(),"Data is not deleted",Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void showMessage(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.show();

    }
}