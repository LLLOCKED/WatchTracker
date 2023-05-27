package com.example.watchtracker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.watchtracker.Adapter.WatchAdapter;
import com.example.watchtracker.Dao.WatchDao;
import com.example.watchtracker.Model.WatchModel;
import com.example.watchtracker.RoomDatabase.WatchListRoomDatabase;
import com.example.watchtracker.ViewModel.WatchViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WatchTrackerDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WatchTrackerDetailsFragment extends Fragment {

    private WatchViewModel mWatchViewModel;
    private static final String ARG_ID = "id";

    private String mID;

    public static WatchTrackerDetailsFragment newInstance(int id) {
        WatchTrackerDetailsFragment fragment = new WatchTrackerDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID, String.valueOf(id));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mID = getArguments().getString(ARG_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_watch_tracker_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView text = requireView().findViewById(R.id.title);
        TextView vote = requireView().findViewById(R.id.vote);

        mWatchViewModel = ViewModelProviders.of(this).get(WatchViewModel.class);

        mWatchViewModel.getWatchItemById(Integer.parseInt(mID)).observe(getViewLifecycleOwner(), new Observer<WatchModel>() {
            @Override
            public void onChanged(WatchModel item) {
                if (item != null) {
                    String voteString = String.valueOf(item.getVote());
                    text.setText(item.getTitle());
                    vote.setText(voteString);
                } else {
                    text.setText(mID);
                }
            }
        });
    }
}