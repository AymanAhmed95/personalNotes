package com.example.root.personalnotes.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.root.personalnotes.data.NotesContract.NotesTable;

/**
 * Created by root on 3/4/18.
 */

public class NotesDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "notes.db";
    public static final int DATABASE_VERSION = 1;
    public NotesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE " + NotesTable.TABLE_NAME +
                        "(" + NotesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        NotesTable.COLUMN_NOTE_TITLE + " TEXT NOT NULL, " +
                        NotesTable.COLUMN_NOTE_CONTENT + " TEXT NOT NULL, " +
                        NotesTable.COLUMN_NOTE_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP "+
                        ");";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NotesTable.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
