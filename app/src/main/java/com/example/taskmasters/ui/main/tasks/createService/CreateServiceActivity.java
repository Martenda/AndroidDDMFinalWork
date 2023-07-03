package com.example.taskmasters.ui.main.tasks.createService;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.taskmasters.R;
import com.example.taskmasters.ui.main.tasks.createService.ui.main.CreateServiceFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CreateServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_service);
        if (savedInstanceState == null) {
            String taskId = getIntent().getStringExtra("taskId");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CreateServiceFragment.newInstance(taskId))
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