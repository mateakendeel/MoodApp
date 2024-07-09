package com.example.moodapp.adapters;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moodapp.R;
import com.example.moodapp.entities.Mood;
import com.example.moodapp.listeners.MoodsListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.MoodViewHolder> {

    private List<Mood> moods;

    private final List<Mood> moodsSource;
    private MoodsListener moodsListener;

    private Timer timer;

    public MoodAdapter(List<Mood> moods, MoodsListener moodsListener) {
        this.moods = moods;
        this.moodsListener = moodsListener;
        moodsSource = moods;
    }

    @NonNull
    @Override
    public MoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MoodViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_mood,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MoodViewHolder holder, final int position) {

        holder.setMood(moods.get(position));
        holder.layoutMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    moodsListener.onClickedNote(moods.get(adapterPosition), adapterPosition);
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return moods.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class MoodViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle, textSubtitle, textDateTime;
        LinearLayout layoutMood;
        MoodViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textSubtitle = itemView.findViewById(R.id.textSubtitle);
            textDateTime = itemView.findViewById(R.id.textDateTime);
            layoutMood = itemView.findViewById(R.id.layoutMood);
        }

        void setMood(Mood mood){
            textTitle.setText(mood.getTitle());
            if(mood.getSubtitle().trim().isEmpty()){
                textSubtitle.setVisibility(View.GONE);
            }
            else{
                textSubtitle.setText(mood.getSubtitle());
            }
            textDateTime.setText(mood.getDateTime());
            GradientDrawable gradientDrawable = (GradientDrawable) layoutMood.getBackground();
            if(mood.getColor() != null) {
                gradientDrawable.setColor(Color.parseColor(mood.getColor()));
            }else {
                gradientDrawable.setColor(Color.parseColor("#333333"));
            }
        }
    }

    public void searchMoods(final String searchKeyword) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (searchKeyword.trim().isEmpty()) {
                    moods = moodsSource;
                } else {
                    ArrayList<Mood> temp = new ArrayList<>();
                    for (Mood mood : moodsSource) {
                        if (mood.getTitle().toLowerCase().contains(searchKeyword.toLowerCase()) ||
                                mood.getSubtitle().toLowerCase().contains(searchKeyword.toLowerCase()) ||
                                mood.getNoteText().toLowerCase().contains(searchKeyword.toLowerCase())) {
                            temp.add(mood);
                        }
                    }
                    moods = temp;
                }

                new Handler(Looper.getMainLooper()).post(() -> notifyDataSetChanged());
            }
        }, 500);
    }

    public void cancelTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }
}

