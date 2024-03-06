package com.example.the2games;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ViewHolder>{

    private ArrayList<Score> mScoresdata;
    private Context mContext;

    public ScoreAdapter(Context mContext, ArrayList<Score> mScoresData) {
        this.mScoresdata = mScoresData;
        this.mContext = mContext;
    }

    @Override
    public ScoreAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.score_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ScoreAdapter.ViewHolder holder, int position) {
        Score currentScore = mScoresdata.get(position);
        holder.bindTo(currentScore);
    }

    @Override
    public int getItemCount() {
        return mScoresdata.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mUsernameText;
        private TextView mbestScoreText;

        ViewHolder(View itemView) {
            super(itemView);

            mUsernameText = itemView.findViewById(R.id.scoreUser);
            mbestScoreText = itemView.findViewById(R.id.scoreValue);
        }

        void bindTo(Score currentScore){
            mUsernameText.setText(currentScore.getUsername());
            mbestScoreText.setText(String.valueOf(currentScore.getBestScore()));
        }

        @Override
        public void onClick(View v) {

        }
    }
}
