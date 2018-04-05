package com.example.root.personalnotes;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.personalnotes.data.NotesContract;
import com.example.root.personalnotes.data.NotesDBHelper;

public class AddNote extends AppCompatActivity {

    EditText noteTitle,noteContent;
    SQLiteDatabase dbObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);
        noteTitle =  findViewById(R.id.add_note_title);
        noteContent =  findViewById(R.id.add_note_content);
        NotesDBHelper notesDBHelper = new NotesDBHelper(this);
        dbObj = notesDBHelper.getWritableDatabase();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void addNote(View view) {
        String title = noteTitle.getText().toString();
        String content = noteContent.getText().toString();
        if (title.length() > 0 && content.length() > 0) {
            dbObj.execSQL("insert into " + NotesContract.NotesTable.TABLE_NAME + "(" + NotesContract.NotesTable.COLUMN_NOTE_TITLE + "," + NotesContract.NotesTable.COLUMN_NOTE_CONTENT + ") values ('" + title + "','" + content + "')");
            noteTitle.getText().clear();
            noteContent.getText().clear();
        }else {
            Toast.makeText(this,"Please fill all fields",Toast.LENGTH_LONG).show();
        }
    }

}
