package com.example.taskmasters.ui.main.tasks.createService.ui.main;

import androidx.appcompat.app.AlertDialog;
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
import com.example.taskmasters.model.task.Category;
import com.example.taskmasters.model.task.Task;
import com.example.taskmasters.model.task.dao.TaskDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseError;

import java.util.Objects;

public class CreateServiceFragment extends Fragment {

    private TaskDAO taskDAO;
    private CreateServiceViewModel mViewModel;

    private FloatingActionButton fab_delete_task;
    public static CreateServiceFragment newInstance() {
        return new CreateServiceFragment();
    }

    public static CreateServiceFragment newInstance(String taskId) {
        CreateServiceFragment fragment = new CreateServiceFragment();
        Bundle args = new Bundle();
        args.putString("taskId", taskId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CreateServiceViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        
        View root = inflater.inflate(R.layout.fragment_create_service, container, false);

        taskDAO = new TaskDAO();

        Button buttonCreate = root.findViewById(R.id.buttonCreate);
        fab_delete_task = root.findViewById(R.id.fab_delete_task);

        assert getArguments() != null;
        String taskId = getArguments().getString("taskId", "none");
        if (!taskId.equals("none")) {
            loadTask(taskId, root);
            buttonCreate.setText(R.string.update_task_text);
        }else{
            buttonCreate.setText(R.string.create_task_task);
            fab_delete_task.hide();
        }

        fab_delete_task.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Atenção")
                    .setMessage("Tem certeza remover esse serviço do catalogo?")
                    .setPositiveButton("EXCLUIR", (dialog, which) -> {
                        taskDAO.deleteTaskById(taskId);
                        requireActivity().finish();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();

        });

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
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", "none");
        task.setUserId(userId);

        assert getArguments() != null;
        String taskId = getArguments().getString("taskId", "none");
        if (!taskId.equals("none")) {
            task.setId(taskId);
            taskDAO.updateTask(task);
        } else {
            taskDAO.insertTask(task);
        }

        requireActivity().finish();
    }

    private void loadTask(String taskId, View root) {
        EditText editTextTitle = root.findViewById(R.id.editTextTitle);
        EditText editTextDescription = root.findViewById(R.id.editTextDescription);
        EditText editTextPrice = root.findViewById(R.id.editTextPrice);
        Spinner spinnerCategory = root.findViewById(R.id.spinnerCategory);
        taskDAO.getTaskById(taskId, new TaskDAO.TaskCallback() {
            @Override
            public void onTaskLoad(Task task) {
                editTextTitle.setText(task.getTitle());
                editTextDescription.setText(task.getDetails());
                editTextPrice.setText(String.valueOf(task.getPrice()));
                spinnerCategory.setSelection(mapStringToCategoryInt(task.getCategory().toString()));
            }

            @Override
            public void onTaskLoadError(DatabaseError databaseError) {

            }
        });
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

    private int mapStringToCategoryInt(String categoryString) {
        switch (categoryString.toUpperCase()) {
            case "GERAL":
                return 0;
            case "ENCANADOR":
                return 1;
            case "MARCENEIRO":
                return 2;
            case "FRETE":
                return 3;
            case "CONSERTO DE ELETRÔNICOS":
                return 4;
            default:
                return 0;
        }
    }
}