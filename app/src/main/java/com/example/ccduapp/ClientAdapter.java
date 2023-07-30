package com.example.ccduapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.ViewHolder>{
    private final ArrayList<ClientInfo> clientInfoArrayList;
    public ClientAdapter(ArrayList<ClientInfo> clientInfoArrayList) {
        this.clientInfoArrayList = clientInfoArrayList;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ClientAdapter.ViewHolder holder, int position){
        ClientInfo model = clientInfoArrayList.get(position);
        holder.username.setText(model.getClient_username());
        holder.problems.setText("" + model.getClient_problems());
        holder.displayPicture.setImageResource(model.getClient_displayPicture());
    }
    @NonNull
    @Override
    public ClientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_viewing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return clientInfoArrayList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView displayPicture;
        private final TextView username;
        private final TextView problems;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            displayPicture = itemView.findViewById(R.id.displayPicture);
            username = itemView.findViewById(R.id.username);
            problems = itemView.findViewById(R.id.problems);
        }
    }
}
