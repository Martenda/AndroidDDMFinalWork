package com.example.taskmasters.ui.mainFragments.tasks.createService.ui.main;

import static android.content.Context.MODE_PRIVATE;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.taskmasters.R;
import com.example.taskmasters.model.DatabaseClient;
import com.example.taskmasters.model.task.Category;
import com.example.taskmasters.model.task.Task;
import com.example.taskmasters.model.task.dao.TaskDAO;
import com.example.taskmasters.model.user.UserType;

import java.util.Objects;

public class CreateServiceFragment extends Fragment {

    private TaskDAO taskDAO;

    private CreateServiceViewModel mViewModel;

    public static CreateServiceFragment newInstance() {
        return new CreateServiceFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CreateServiceViewModel.class);
        // TODO: Use the ViewModel
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        
        View root = inflater.inflate(R.layout.fragment_create_service, container, false);

        DatabaseClient databaseClient = DatabaseClient.getInstance(getContext());
        taskDAO = databaseClient.getAppDatabase().taskDao();

        Button buttonCreate = root.findViewById(R.id.buttonCreate);
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextTitle = root.findViewById(R.id.editTextTitle);
                EditText editTextDescription = root.findViewById(R.id.editTextDescription);
                EditText editTextPrice = root.findViewById(R.id.editTextPrice);
                Spinner spinnerCategory = root.findViewById(R.id.spinnerCategory);

                String title = editTextTitle.getText().toString();
                String description = editTextDescription.getText().toString();
                double price = Double.parseDouble(editTextPrice.getText().toString());
                String category = spinnerCategory.getSelectedItem().toString();
                try{
                    createTask(title, description, price, mapStringToCategory(category));
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        });

        return root;
    }

    private void createTask(String title, String description, double price, Category category) {
        Task task = new Task();
        task.setTitle(title);
        task.setDetails(description);
        task.setPrice(price);
        task.setCategory(category);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        int userId = sharedPreferences.getInt("user_id", 0);
        task.setUserId(userId);
        taskDAO.insertTask(task);

        requireActivity().finish();
    }

    private Category mapStringToCategory(String categoryString) {
        switch (categoryString) {
            case "Geral":
                return Category.GERAL;
            case "Encanador":
                return Category.ENCANADOR;
            case "Marceneiro":
                return Category.MARCENEIRO;
            case "Frete":
                return Category.FRETE;
            case "Conserto de Eletrônicos":
                return Category.CONSERTO_DE_ELETRONICOS;
            default:
                return null;
        }
    }


}