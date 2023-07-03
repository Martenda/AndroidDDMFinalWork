package com.example.taskmasters.ui.main.tasks;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmasters.R;
import com.example.taskmasters.ui.main.tasks.placeholder.TaskContent;
import com.google.firebase.database.DatabaseError;


/**
 * A fragment representing a list of Items.
 */
public class TaskFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    private TaskContent.PlaceholderCallback taskContentCallback;

    public TaskFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            taskContentCallback = createPlaceHolderCallback(recyclerView, context);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        TaskContent.loadTasksFromDatabase(requireContext(), taskContentCallback);
    }

    private TaskContent.PlaceholderCallback createPlaceHolderCallback(RecyclerView recyclerView, Context context){
        return new TaskContent.PlaceholderCallback() {

            @Override
            public void onTasksLoaded() {
                assert recyclerView != null;
                recyclerView.setAdapter(new TaskRecyclerViewAdapter(TaskContent.ITEMS, context));
            }

            @Override
            public void onTasksError(DatabaseError databaseError) {
            }
        };
    }

}