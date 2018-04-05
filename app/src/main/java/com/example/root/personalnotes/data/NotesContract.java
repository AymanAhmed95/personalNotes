package com.example.root.personalnotes.data;

import android.provider.BaseColumns;

/**
 * Created by root on 3/4/18.
 */

public class NotesContract {

    public static final class NotesTable implements BaseColumns{
        public static final  String TABLE_NAME = "note";
        public static final  String COLUMN_NOTE_TITLE = "title";
        public static final  String COLUMN_NOTE_CONTENT = "content";
        public static final  String COLUMN_NOTE_TIMESTAMP = "date";


    }
}
