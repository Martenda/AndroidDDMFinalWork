package com.example.taskmasters.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.taskmasters.databinding.FragmentHomeBinding;
import com.example.taskmasters.model.AppDatabase;
import com.example.taskmasters.model.DatabaseClient;
import com.example.taskmasters.model.user.dao.UserDAO;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        DatabaseClient databaseClient = DatabaseClient.getInstance(requireContext().getApplicationContext());
        AppDatabase appDatabase = databaseClient.getAppDatabase();
        UserDAO userDao = appDatabase.userDao();

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.setUserDAO(userDao);

        homeViewModel.fetchUserData();

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}