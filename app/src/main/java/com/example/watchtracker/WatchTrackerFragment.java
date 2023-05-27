package com.example.watchtracker;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.watchtracker.Adapter.WatchAdapter;
import com.example.watchtracker.Model.WatchModel;
import com.example.watchtracker.ViewModel.WatchViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class WatchTrackerFragment extends Fragment implements SetOnWatchItemClick {

    private RecyclerView watchRecyclerView;
    private WatchViewModel mWatchViewModel;
    private WatchAdapter watchAdapter;
    private FloatingActionButton addWatchItem;
    private SearchView search;

    private ItemTouchHelper itemTouchHelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_watch_tracker, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        search = requireView().findViewById(R.id.searchView);

        watchRecyclerView = requireView().findViewById(R.id.watchRecyclerView);
        watchAdapter = new WatchAdapter(requireContext(), this);

        watchRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        watchRecyclerView.setAdapter(watchAdapter);

        mWatchViewModel = ViewModelProviders.of(this).get(WatchViewModel.class);
        mWatchViewModel.getWatchList().observe(getViewLifecycleOwner(), new Observer<List<WatchModel>>() {
            @Override
            public void onChanged(@Nullable final List<WatchModel> items) {
                watchAdapter.setWatch(items);
            }
        });


        addWatchItem = requireView().findViewById(R.id.add);
        addWatchItem.setOnClickListener((v) -> {
            addWatchItemDialogBox();
        });


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchItem(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchItem(newText);
                return true;
            }
        });
    }

    public void searchItem(String title) {
        String value = title + "%";
        mWatchViewModel.searchTitle(value).observe(getViewLifecycleOwner(), new Observer<List<WatchModel>>() {
            @Override
            public void onChanged(@Nullable final List<WatchModel> items) {
                watchAdapter.setWatch(items);
            }
        });
    }

    private void addWatchItemDialogBox() {
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.add_dialog);


        final EditText input = dialog.findViewById(R.id.edit_text_dialog);
        final NumberPicker numberPicker = dialog.findViewById(R.id.number_picker);
        Button submitButton = dialog.findViewById(R.id.submit_button);

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(10);


        submitButton.setOnClickListener((v) -> {
            String item = input.getText().toString();
            int vote = numberPicker.getValue();
            mWatchViewModel.addItem(new WatchModel(item, vote));
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public void onWatchItemClicked(int id) {
        this.getParentFragmentManager().beginTransaction().replace(R.id.mainFragmentView, WatchTrackerDetailsFragment.newInstance(id), null).addToBackStack(null).commit();
    }

    @Override
    public void onWatchItemDelete(WatchModel item) {
        mWatchViewModel.deleteItem(item);
    }

}