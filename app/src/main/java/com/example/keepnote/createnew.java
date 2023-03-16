package com.example.keepnote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class createnew extends Activity {
    EditText txtTitle,txtDescription;
    ImageButton btnSave;
    public SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnew);
        txtTitle=(EditText)findViewById(R.id.txtTitle);
        txtDescription=(EditText)findViewById(R.id.txtDescription);
        btnSave =(ImageButton) findViewById(R.id.btnSave);
        txtTitle.requestFocus();
        db = openOrCreateDatabase("NoteDB", Context.MODE_PRIVATE, null);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtTitle.getText().toString().trim().length()==0||
                        txtDescription.getText().toString().trim().length()==0)
                {
                    showStatus("Error", "Please enter TITLE and NOTE");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM note WHERE title='"+txtTitle.getText()+"'", null);
                while(c.moveToLast()){
                    if(c.moveToFirst()){
                        showStatus("Error","Title already exists!!");
                        return;
                    }
                }
                db.execSQL("INSERT INTO note VALUES('"+txtTitle.getText()+"','"+txtDescription.getText()+ "');");
                showStatus("Success", "Note added");
                clearText();
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
        txtTitle.setText("");
        txtDescription.setText("");
        txtTitle.requestFocus();
    }

}