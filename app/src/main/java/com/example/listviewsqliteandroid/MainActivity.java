package com.example.listviewsqliteandroid;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class MainActivity extends Activity {

    DBHelper dbHelper = new DBHelper(this);

    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText)findViewById(R.id.editText);

        ListView listView = (ListView) findViewById(R.id.lstNotes);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                dbHelper.deleteNote(id);
                Toast.makeText(getApplicationContext(),"Efase mo sa!", Toast.LENGTH_SHORT).show();
                populateList();

            }
        });
        populateList();
    }

    public void onClickCreate(View view) {

        String mot = editText.getText().toString();
        dbHelper.getWritableDatabase();
        dbHelper.insertNote("16/06/2015", mot);
        populateList();
    }

    public void populateList(){
        ListView listView = (ListView) findViewById(R.id.lstNotes);
        Cursor cursor = dbHelper.getAllNotes();
        String[] from = new String[] {dbHelper.COLUMN_DATE, dbHelper.COLUMN_NOTE};
        int[] to = new int[] {R.id.txtDate, R.id.txtNote};
        SimpleCursorAdapter simpleCursorAdapter;
        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.custom_row,cursor,from,to,0);
        listView.setAdapter(simpleCursorAdapter);
    }
}
