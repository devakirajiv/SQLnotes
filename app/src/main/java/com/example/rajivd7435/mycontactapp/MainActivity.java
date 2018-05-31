package com.example.rajivd7435.mycontactapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    DatabaseHelper myDb;
    EditText editName;
    EditText editPhone;
    EditText editAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editText_name);
        editPhone = findViewById(R.id.editText_phone);
        editAddress = findViewById(R.id.editText_address);

        myDb = new DatabaseHelper(this);
        Log.d("MyContactApp", "MainActivity: instantiated DatabaseHelper");
    }

    public void addData(View view){
        Log.d("MyContactApp", "MainActivity: instantiated DatabaseHelper");

        boolean isInserted = myDb.insertData(editName.getText().toString(), editPhone.getText().toString(), editAddress.getText().toString());

        if(isInserted == true){
            Toast.makeText(MainActivity.this, "Success - contact inserted", Toast.LENGTH_LONG).show();
} else {
            Toast.makeText(MainActivity.this, "FAILED - contact not inserted", Toast.LENGTH_LONG).show();
}

    }

    public void viewData(View view){
        Cursor res = myDb.getAllData();
        Log.d("MyContactApp", "MainActivity: viewDate: received cursor " + res.getCount() );
        if(res.getCount() == 0){
            showMessage("Error", "No data found in the database");
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            //Append res column 0,1,2,3 (ID plus three fields) to the buffer, delimited by *\n*. 4 lines, appending each of those columns
            buffer.append("ID: ");
            buffer.append(res.getString(0));
            buffer.append("\n");
            buffer.append("Name: ");
            buffer.append(res.getString(1));
            buffer.append("\n");
            buffer.append("Phone Number: ");
            buffer.append(res.getString(2));
            buffer.append("\n");
            buffer.append("Address: ");
            buffer.append(res.getString(3));
            buffer.append("\n");
            buffer.append("\n");


        }
        Log.d("MyContactApp", "MainActivity: viewDate: assembled stringbuffer");
        showMessage("Data", buffer.toString());

    }

    public void showMessage(String title, String message) {
        Log.d("MyContactApp", "MainActivity: showMessage: building alert dialog");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

    public static final String EXTRA_MESSAGE = "com.example.rajivd7435.mycontactapp.MESSAGE";
    public void SearchRecord(View view){

        Cursor cursor = myDb.getAllData();
        StringBuffer message = new StringBuffer();

        while(cursor.moveToNext()){
            if(cursor.getString(1).matches(editName.getText().toString())){
                message.append("Name: ");
                message.append(cursor.getString(1));
                message.append("\n");
                message.append("Phone: ");
                message.append(cursor.getString(2));
                message.append("\n");
                message.append("Address: ");
                message.append(cursor.getString(3));
                message.append("\n");
                message.append("\n");
            }
        }



        Log.d("MyContactApp", "MainActivity: launching SearchActivity");
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message.toString());
        startActivity(intent);

    }
}
