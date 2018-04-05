package com.example.root.personalnotes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.root.personalnotes.data.Note;

import java.util.List;

/**
 * Created by root on 3/6/18.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    List<Note> noteList;
    NoteClickListener noteClickListener;

    public NotesAdapter(List<Note> noteList,NoteClickListener listener) {
        this.noteList = noteList;
        this.noteClickListener = listener;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View noteCardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_layout,null);
        NoteViewHolder holder = new NoteViewHolder(noteCardView);
        return holder;
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note noteObj = noteList.get(position);
        holder.noteTitle.setText(noteObj.getTitle());
        holder.noteContent.setText(noteObj.getContent());
        holder.noteDate.setText(noteObj.getDate());
        holder.itemView.setTag(noteObj.getId());

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public void updateDataSet(List<Note> updatedList){
        this.noteList = updatedList;
        notifyDataSetChanged();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView noteTitle,noteContent,noteDate;
        public NoteViewHolder(View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.note_title);
            noteContent = itemView.findViewById(R.id.note_content);
            noteDate = itemView.findViewById(R.id.note_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Note clickedNote = noteList.get(position);
            noteClickListener.onNoteClick(clickedNote);
        }

    }

    public interface NoteClickListener{
            void onNoteClick(Note note);
    }
}
