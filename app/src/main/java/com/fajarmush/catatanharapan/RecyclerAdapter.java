package com.fajarmush.catatanharapan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.viewHolder> {

    Context context;
    Activity activity;
    ArrayList id, title, description, date;

    RecyclerAdapter(Activity activity, Context context, ArrayList id, ArrayList title, ArrayList description, ArrayList date) {
        this.activity = activity;
        this.context = context;
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycler_row, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.title.setText(String.valueOf(title.get(position)));
        holder.description.setText(String.valueOf(description.get(position)));
        holder.date.setText(String.valueOf(date.get(position)));
        holder.noteRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FormActivity.class);
                intent.putExtra("action", "edit");
                intent.putExtra("button", "delete");
                intent.putExtra("id", String.valueOf(id.get(position)));
                intent.putExtra("title", String.valueOf(title.get(position)));
                intent.putExtra("description", String.valueOf(description.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return title.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView title, description, date;
        LinearLayout noteRow;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
            noteRow = itemView.findViewById(R.id.noteRow);
        }
    }
}
