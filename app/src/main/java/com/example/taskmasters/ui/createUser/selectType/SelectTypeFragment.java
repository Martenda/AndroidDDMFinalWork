package com.example.taskmasters.ui.createUser.selectType;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.taskmasters.R;
import com.example.taskmasters.model.UserType;
import com.example.taskmasters.ui.main.PlaceholderFragment;

import java.util.Objects;

public class SelectTypeFragment extends Fragment {

    private SelectTypeViewModel mViewModel;
    private final PlaceholderFragment father;
    public SelectTypeFragment(PlaceholderFragment father) {
        this.father = father;
    }

    public static SelectTypeFragment newInstance(PlaceholderFragment father) {
        return new SelectTypeFragment(father);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_select_type, container, false);

        Button buttonWorker = root.findViewById(R.id.btnWorker);
        Button buttonClient = root.findViewById(R.id.btnCliente);

        buttonWorker.setOnClickListener(e -> {
            father.getUser().setUserType(UserType.SERVICE_PROVIDER);
            father.Callback("Prestador de serviços", 2);
            buttonWorker.setEnabled(false);
            buttonClient.setEnabled(true);
        });

        buttonClient.setOnClickListener(e -> {
            father.getUser().setUserType(UserType.CONSUMER);
            father.Callback("Cliente", 2);
            buttonWorker.setEnabled(true);
            buttonClient.setEnabled(false);
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SelectTypeViewModel.class);
        // TODO: Use the ViewModel
    }

}