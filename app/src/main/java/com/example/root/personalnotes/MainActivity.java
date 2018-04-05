package com.example.root.personalnotes;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.root.personalnotes.data.Note;
import com.example.root.personalnotes.data.NotesContract;
import com.example.root.personalnotes.data.NotesContract.NotesTable;
import com.example.root.personalnotes.data.NotesDBHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NotesAdapter.NoteClickListener {

    RecyclerView recyclerViewObj;
    SQLiteDatabase dbObj;
    NotesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NotesDBHelper dbHelper = new NotesDBHelper(this);
        dbObj = dbHelper.getWritableDatabase();
//        for(int i = 0;i<5;i++){
//            dbObj.execSQL("insert into "+ NotesContract.NotesTable.TABLE_NAME + "("+ NotesContract.NotesTable.COLUMN_NOTE_TITLE +","+ NotesContract.NotesTable.COLUMN_NOTE_CONTENT +") values ('note','note')");
//        }
        List<Note> noteList = this.getAllNotes();
        adapter = new NotesAdapter(noteList,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewObj = findViewById(R.id.recycler);
        recyclerViewObj.setAdapter(adapter);
        recyclerViewObj.setLayoutManager(layoutManager);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long id = (long) viewHolder.itemView.getTag();
                int rowsNumber = dbObj.delete(NotesTable.TABLE_NAME,NotesTable._ID + "="+id,null);
                if(rowsNumber > 0){
                    List<Note> updatedList = getAllNotes();
                    adapter.updateDataSet(updatedList);

                }
            }
        }).attachToRecyclerView(recyclerViewObj);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.add_note){
//            PendingIntent appIntent = PendingIntent.getActivity(this,1000,new Intent(this,MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
//            String channelID = "toDoChannel";
//            NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this,channelID)
//                                                                                .setContentTitle("Title")
//                                                                                .setContentText("just test notification")
//                                                                                .setContentIntent(appIntent)
//                                                                                .setPriority(NotificationCompat.PRIORITY_HIGH).setAutoCancel(true)
//                                                                                .setSmallIcon(R.drawable.ic_launcher_background);
//
//            NotificationManagerCompat nManager = NotificationManagerCompat.from(this);
//            nManager.notify(100,nBuilder.build());

            Intent addNoteIntent = new Intent(this,AddNote.class);
            startActivity(addNoteIntent);
        }
        return super.onOptionsItemSelected(item);
    }
   // use android.app.IntentService
    public List<Note> getAllNotes(){
         Cursor result = dbObj.query(NotesTable.TABLE_NAME,null,null,null,null,null,NotesTable.COLUMN_NOTE_TIMESTAMP);
         List<Note> resultList  = new ArrayList<>();
         while (result.moveToNext()){
             long id  = result.getLong(result.getColumnIndex(NotesTable._ID));
             String title = result.getString(result.getColumnIndex(NotesTable.COLUMN_NOTE_TITLE));
             String content = result.getString(result.getColumnIndex(NotesTable.COLUMN_NOTE_CONTENT));
             String date = result.getString(result.getColumnIndex(NotesTable.COLUMN_NOTE_TIMESTAMP));
             Note noteObj = new Note(id,title,content,date);
             resultList.add(noteObj);
         }

         return resultList;
    }

    @Override
    public void onNoteClick(Note note) {
        Toast.makeText(this,"note"+note.getDate()+"clicked",Toast.LENGTH_LONG).show();
    }
}
