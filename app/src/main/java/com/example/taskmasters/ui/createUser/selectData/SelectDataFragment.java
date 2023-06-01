package com.example.taskmasters.ui.createUser.selectData;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.taskmasters.R;
import com.example.taskmasters.exceptions.AgeException;
import com.example.taskmasters.exceptions.NameSizeException;
import com.example.taskmasters.model.GenderOptions;
import com.example.taskmasters.model.User;
import com.example.taskmasters.ui.main.PlaceholderFragment;

public class SelectDataFragment extends Fragment {

    private SelectDataViewModel mViewModel;

    private final PlaceholderFragment father;

    public SelectDataFragment(PlaceholderFragment father) {
        this.father = father;
    }

    public static SelectDataFragment newInstance(PlaceholderFragment father) {
        return new SelectDataFragment(father);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_select_data, container, false);


        Button btnCadastrar = root.findViewById(R.id.buttonProcedeToStep3);
        btnCadastrar.setEnabled(true);
        btnCadastrar.setOnClickListener(v -> {

            TextView nameTextView = root.findViewById(R.id.editTextNome);
            TextView surnameTextView = root.findViewById(R.id.editTextSobrenome);
            TextView ageTextView = root.findViewById(R.id.editTextIdade);
            Spinner genderSpinner = root.findViewById(R.id.spinnerSexo);

            String name = nameTextView.getText().toString();
            String surname = surnameTextView.getText().toString();
            String age = ageTextView.getText().toString();
            String gender = genderSpinner.getSelectedItem().toString();

            User user = father.getUser();
            try {
                user.setName(name);
            } catch (NameSizeException e) {
                father.Callback("Nome inválido",2);
                return;
            }
            try {
                user.setSurname(surname);
            } catch (NameSizeException e) {
                father.Callback("Sobrenome inválido",2);
                return;
            }
            try {
                if (age.length() > 0)
                    user.setAge(Integer.parseInt(age));
            } catch (AgeException e) {
                father.Callback("Idade inválida",2);
                return;
            }
            try {
                user.setGender(GenderOptions.valueOf(gender));
            } catch (Exception e) {
                father.Callback("Genero inválido",2);
                return;
            }

            father.Callback("Sucesso",3);
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SelectDataViewModel.class);
        // TODO: Use the ViewModel
    }

}