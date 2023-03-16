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
import android.widget.TextView;
import android.widget.Toast;

public class updatenote extends Activity {
    EditText txtEdit;
    TextView txtTit;
    Button btnUpdate;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatenote);
        txtTit=(TextView)findViewById(R.id.txtTit);
        txtEdit = (EditText) findViewById(R.id.txtEdit);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        db = openOrCreateDatabase("NoteDB", Context.MODE_PRIVATE, null);
        Intent intent = getIntent();
        txtTit.setText(intent.getStringExtra("message_key"));
        txtEdit.setText(intent.getStringExtra("message_key1"));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    db.execSQL("UPDATE note SET descript='" + txtEdit.getText() + "' WHERE title='" + txtTit.getText() + "'");
                    Toast.makeText(getApplicationContext(), "RECORD MODIFIED", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void showStatus(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
