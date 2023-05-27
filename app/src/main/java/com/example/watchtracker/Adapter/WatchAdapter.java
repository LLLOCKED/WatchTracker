package com.example.watchtracker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watchtracker.Model.WatchModel;
import com.example.watchtracker.R;
import com.example.watchtracker.SetOnWatchItemClick;

import java.util.List;

public class WatchAdapter extends RecyclerView.Adapter<WatchAdapter.ViewHolder> {
    private List<WatchModel> watchList;
    private final LayoutInflater mInflater;

    private SetOnWatchItemClick onWatchListener;

    public WatchAdapter(Context context, SetOnWatchItemClick onWatchListener) {
        mInflater = LayoutInflater.from(context);
        this.onWatchListener = onWatchListener;
    }

    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.watch_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        if (watchList != null) {
            WatchModel current = watchList.get(position);
            holder.title.setText(current.getTitle());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onWatchListener.onWatchItemClicked(current.getId());
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onWatchListener.onWatchItemDelete(current);
                    watchList.remove(current);
                    notifyDataSetChanged();
                    return true;
                }
            });
        } else {
            // Covers the case of data not being ready yet.
            holder.title.setText("No Word");
        }
    }


    public int getItemCount() {
        if (watchList != null) return watchList.size();
        else return 0;
    }

    public void setWatch(List<WatchModel> watchList) {
        this.watchList = watchList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        Button deleteItem;

        private ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.titleText);
        }
    }
}

