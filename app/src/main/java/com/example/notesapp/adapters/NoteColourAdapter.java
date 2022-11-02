package com.example.notesapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.R;
import com.example.notesapp.viewmodels.NoteColourViewModel;

import java.util.List;

public class NoteColourAdapter extends RecyclerView.Adapter<NoteColourAdapter.NoteColourViewHolder> {

    private Context context;
    private List<NoteColourViewModel> noteColours;
    private NoteColourViewModel selectedColour;

    public NoteColourAdapter(Context context, List<NoteColourViewModel> noteColours) {
        this.context = context;
        this.noteColours = noteColours;
    }

    public NoteColourViewModel getSelectedColour() {
        return selectedColour;
    }

    @NonNull
    @Override
    public NoteColourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteColourViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.note_colour_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteColourViewHolder holder, int position) {
        bindNoteColour(holder, noteColours.get(position));
    }

    private void bindNoteColour(NoteColourViewHolder holder, NoteColourViewModel noteColourViewModel) {
        Log.e("ERRRRRR", noteColourViewModel.getNoteColour().getColourCode());
        String colourCodeString = noteColourViewModel.getNoteColour().getColourCode();
        holder.colourView.setBackgroundColor(Color.parseColor(colourCodeString));
        if(noteColourViewModel.isSelected()) {
            holder.containerLayout.setBackground(context.getResources().getDrawable(R.drawable.selected_item_background));
        } else {
            holder.containerLayout.setBackground(null);
        }

        holder.containerLayout.setOnClickListener(v -> selectNoteColour(noteColourViewModel));
    }

    private void selectNoteColour(NoteColourViewModel noteColourViewModel) {
        if (selectedColour != null) {
            selectedColour.setSelected(false);
        }
        noteColourViewModel.setSelected(!noteColourViewModel.isSelected());
        selectedColour = noteColourViewModel;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return noteColours.size();
    }

    static class NoteColourViewHolder extends RecyclerView.ViewHolder {

        View colourView;
        FrameLayout containerLayout;

        public NoteColourViewHolder(@NonNull View itemView) {
            super(itemView);

            colourView = itemView.findViewById(R.id.colourView);
            containerLayout = itemView.findViewById(R.id.containerLayout);
        }
    }
}
