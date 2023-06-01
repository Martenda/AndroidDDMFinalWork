package com.example.taskmasters.ui.createUser.selectCredentials;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.taskmasters.R;
import com.example.taskmasters.ui.main.PlaceholderFragment;

public class SelectCredentialsFragment extends Fragment {

    private SelectCredentialsViewModel mViewModel;
    private final PlaceholderFragment father;

    public SelectCredentialsFragment(PlaceholderFragment father) {
        this.father = father;
    }

    public static SelectCredentialsFragment newInstance(PlaceholderFragment father) {
        return new SelectCredentialsFragment(father);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_select_credentials, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SelectCredentialsViewModel.class);
        // TODO: Use the ViewModel
    }

}