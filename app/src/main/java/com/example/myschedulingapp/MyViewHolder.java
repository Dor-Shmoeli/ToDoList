package com.example.myschedulingapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder{
    public View mView;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setTask(String task) {
        TextView taskTextView = mView.findViewById(R.id.tv_task);
        taskTextView.setText(task);

    }

    public void setDescription(String description) {
        TextView descriptionTextView = mView.findViewById(R.id.tv_description);
        descriptionTextView.setText(description);
    }

    public void setDate(String date) {
        TextView dateTextView = mView.findViewById(R.id.tv_date);
        dateTextView.setText(date);

    }
}



