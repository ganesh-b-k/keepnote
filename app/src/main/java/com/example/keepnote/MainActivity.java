package com.example.keepnote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends Activity implements OnClickListener {
    Button btnCreate, btnView, btnDeleteAll, btnQuote;
    public SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCreate = (Button) findViewById(R.id.btnNew);
        btnView = (Button) findViewById(R.id.btnSearch);
        btnDeleteAll = (Button) findViewById(R.id.btnDeleteAll);
        btnQuote = (Button) findViewById(R.id.btnQuote);
        btnCreate.setOnClickListener(this);
        btnView.setOnClickListener(this);
        btnDeleteAll.setOnClickListener(this);
        btnQuote.setOnClickListener(this);
        db = openOrCreateDatabase("NoteDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS note(title VARCHAR,descript VARCHAR);");

    }

    public void onClick(View v) {

        if (v == btnCreate) {
            Intent intent = new Intent(MainActivity.this, createnew.class);
            startActivity(intent);
        }
        if (v == btnView) {
            Intent intent = new Intent(MainActivity.this, viewnote.class);
            startActivity(intent);
        }
        if (v == btnDeleteAll) {
            Cursor c = db.rawQuery("SELECT * FROM note", null);
            while (c.moveToNext()) {
                db.execSQL("DELETE FROM note");
                showStatus("Success", "All NOTES DELETED SUCCESFULLY");

            }
        }


            if (v == btnQuote) {
                Random random = new Random();
                int rand = random.nextInt(3);
                int ch;
                ch = rand % 4;
                switch (ch) {
                    case 0:
                        Toast.makeText(getApplicationContext(), "FEAR KILLS MORE DREAMS THAN FAILURE EVER WILL", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "\"A LITTLE PROGRSS EACH DAY ADDS UP TO BIGG RESULTS!!!\"", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "STRIVE FOR PROGRESS NOT PERFECTION", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "OOPS!! TRY AGAIN!", Toast.LENGTH_LONG).show();
                        break;
                }

            }
        }
    public void showStatus(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
