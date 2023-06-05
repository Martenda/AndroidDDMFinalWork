package com.example.taskmasters.ui.createUser.selectData;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.taskmasters.R;
import com.example.taskmasters.model.user.GenderOptions;
import com.example.taskmasters.model.user.User;
import com.example.taskmasters.ui.main.PlaceholderFragment;
import com.google.android.material.snackbar.Snackbar;

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
        btnCadastrar.setOnClickListener(v -> {

            TextView nameTextView = root.findViewById(R.id.editTextNome);
            TextView surnameTextView = root.findViewById(R.id.editTextSobrenome);
            TextView ageTextView = root.findViewById(R.id.editTextIdade);
            Spinner genderSpinner = root.findViewById(R.id.spinnerSexo);

            String name = nameTextView.getText().toString();
            String surname = surnameTextView.getText().toString();
            String age = ageTextView.getText().toString();
            String gender = genderSpinner.getSelectedItem().toString();

            if (name.isEmpty()) {
                father.Callback("O nome é obrigatório", 2);
                return;
            }

            if (surname.isEmpty()) {
                father.Callback("O sobrenome é obrigatório", 2);
                return;
            }

            if (age.isEmpty()) {
                father.Callback("Insira a sua idade", 2);
                return;
            }else if(Integer.parseInt(age) < 18){
                father.Callback("Você deve ter no minímo 18 anos", 2);
                return;
            }

            father.getUser().setName(name);
            father.getUser().setSurname(surname);
            father.getUser().setAge(Integer.parseInt(age));


            // Default
            father.getUser().setGender(GenderOptions.OTHER);
            if (gender.equals("Masculino")) {
                father.getUser().setGender(GenderOptions.MALE);
            }
            if (gender.equals("Feminino")) {
                father.getUser().setGender(GenderOptions.FEMALE);
            }

            father.Callback("Sucesso", 3);
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