package com.example.keepnote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class viewnote extends Activity {
    EditText txtRequest;
    TextView txtAnswer;
    Button btnSea,btnEdit,btnDelete;
    public SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewnote);
        txtRequest=(EditText) findViewById(R.id.txtRequest);
        txtAnswer=(TextView) findViewById(R.id.txtAnswer);
        btnSea=(Button)findViewById(R.id.btnSea);
        btnEdit=(Button)findViewById(R.id.btnEdit);
        btnDelete=(Button)findViewById(R.id.btnDelete);
        txtRequest.requestFocus();
        db = openOrCreateDatabase("NoteDB", Context.MODE_PRIVATE, null);
        btnSea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Checking for empty roll number
                if(txtRequest.getText().toString().trim().length()==0)
                {
                    showStatus("Error", "Please enter title");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM note WHERE title='"+txtRequest.getText()+"'", null);
                if(c.moveToFirst())
                {
                    txtAnswer.setText(c.getString(1));
                }
                else
                {
                    showStatus("Error", "Invalid title");
                    clearText();
                }
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Checking for empty roll number
                if(txtRequest.getText().toString().trim().length()==0)
                {
                    showStatus("Error", "Please enter title");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM note WHERE title='"+txtRequest.getText()+"'", null);
                if(c.moveToFirst())
                {
                    Intent intent = new Intent(getApplicationContext(), updatenote.class);
                    intent.putExtra("message_key", txtRequest.getText().toString());
                    intent.putExtra("message_key1",c.getString(1));
                    startActivity(intent);
                }
                else
                {
                    showStatus("Error", "Invalid title");
                    clearText();
                }

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // Checking for empty roll number
                if(txtRequest.getText().toString().trim().length()==0)
                {
                    showStatus("Error", "Please enter title");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM note WHERE title='"+txtRequest.getText()+"'", null);
                if(c.moveToFirst())
                {
                    db.execSQL("DELETE FROM note WHERE title='"+txtRequest.getText()+"'");
                    showStatus("Success", "Record Deleted");
                }
                else
                {
                    showStatus("Error", "Invalid title");
                    clearText();
                }

            }
        });
    }
    public void showStatus(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText()
    {
        txtRequest.setText("");
        txtAnswer.setText("");
        txtRequest.requestFocus();
    }
}