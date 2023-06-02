package com.example.taskmasters.ui.createUser.selectCredentials;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.taskmasters.CreateUser;
import com.example.taskmasters.MainActivity;
import com.example.taskmasters.R;
import com.example.taskmasters.ui.login.LoginActivity;
import com.example.taskmasters.ui.main.PlaceholderFragment;

public class SelectCredentialsFragment extends Fragment {

    private SelectCredentialsViewModel mViewModel;
    private final PlaceholderFragment father;

    private String emailText = "";
    private String passwordText = "";

    private Button createAccountButton;

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

        createAccountButton = root.findViewById(R.id.createAcc);

        TextView tv_email = root.findViewById(R.id.usernameCreate);
        TextView tv_password = root.findViewById(R.id.passwordCreate);

        createAccountButton.setOnClickListener(v -> {
            // TODO callback, and create user
            Intent intent = new Intent(this.getContext(), MainActivity.class);
            startActivity(intent);
        });

        tv_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                emailText = s.toString();
                fireTextChanged();
            }
        });
        tv_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                passwordText = s.toString();
                fireTextChanged();
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SelectCredentialsViewModel.class);
        // TODO: Use the ViewModel
    }

    private void fireTextChanged(){
        createAccountButton.setEnabled(emailText.length() >= 6 & emailText.contains("@") & passwordText.length() >= 6);
    }

}