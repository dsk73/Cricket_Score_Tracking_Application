package com.coepzest.zest23;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.coepzest.zest23.R;

import java.util.ArrayList;

public class EventsIconAdapter extends RecyclerView.Adapter<EventsIconAdapter.ViewHolder> {

    Context context;
    ArrayList<EventsIconModel> arrayList;
    EventsIconInterface eventsIconInterface;

    EventsIconAdapter(Context context, ArrayList<EventsIconModel> arrayList,EventsIconInterface eventsIconInterface){
        this.context = context;
        this.arrayList = arrayList;
        this.eventsIconInterface=eventsIconInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.events_icon_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.Icon.setImageResource(arrayList.get(position).Icon);
        holder.EventName.setText(arrayList.get(position).EventName);

        // Set click listener on EventName TextView
        holder.EventName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the context from the TextView
                Context context = holder.EventName.getContext();

                // Create an intent to launch CurrentEventDetails activity
                Intent intent = new Intent(context, CurrentEventDetails.class);

                // Pass data (event name) to the intent
                intent.putExtra("CurrentEvent", arrayList.get(position).EventName);

                // Start the activity
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView Icon;
        TextView EventName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Icon=itemView.findViewById(R.id.Icon);
            EventName=itemView.findViewById(R.id.EventName);
        }
    }
}
