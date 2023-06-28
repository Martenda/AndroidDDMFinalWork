package com.example.taskmasters.ui.mainFragments.tasks.createService;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.taskmasters.R;
import com.example.taskmasters.model.DatabaseClient;
import com.example.taskmasters.model.task.Category;
import com.example.taskmasters.model.task.Task;
import com.example.taskmasters.model.task.dao.TaskDAO;
import com.example.taskmasters.ui.mainFragments.tasks.createService.ui.main.CreateServiceFragment;

public class CreateServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_service);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CreateServiceFragment.newInstance())
                    .commitNow();
        }

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}