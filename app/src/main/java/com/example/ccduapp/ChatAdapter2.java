package com.example.ccduapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ChatAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    static final int receivedMessage= 1;
    static final int sentMessage = 2;

    private final Context yourContext;
    private final ArrayList<ChatClass2> list;

    public ChatAdapter2(Context context, ArrayList<ChatClass2> list) {
        this.yourContext = context;
        this.list = list;
    }

    private class rMessage extends RecyclerView.ViewHolder {
        TextView text;

        rMessage(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.receivedMessage);
        }

        void bind(int position) {
            ChatClass2 recyclerViewModel = list.get(position);
            text.setText(recyclerViewModel.getTheText());
        }
    }

    private class sMessage extends RecyclerView.ViewHolder{
        TextView text;
        sMessage(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.sentMessage);
        }

        void bind(int position) {
            ChatClass2 recyclerViewModel = list.get(position);
            text.setText(recyclerViewModel.getTheText());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        if (viewType == receivedMessage){
            View view = LayoutInflater.from(yourContext).inflate(R.layout.recievedmessage, parent, false);
            return new rMessage(view);
        }
        else{
            View view = LayoutInflater.from(yourContext).inflate(R.layout.sentmessage, parent, false);
            return new sMessage(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position){
        if (list.get(position).getTheTime()==receivedMessage){
            ((rMessage) holder).bind(position);
        } else {
            ((sMessage) holder).bind(position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getTheTime();
    }
}
