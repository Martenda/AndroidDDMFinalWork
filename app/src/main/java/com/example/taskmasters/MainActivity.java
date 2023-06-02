package com.example.taskmasters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.taskmasters.databinding.ActivityMainBinding;
import com.example.taskmasters.ui.mainFragments.tasks.createService.CreateServiceActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NavController navController;
    private FloatingActionButton fab_create_service;
    private FloatingActionButton fab_mark_notifications_as_seen;
    private FloatingActionButton fab_clear_seen_notifications;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_tasks, R.id.navigation_notifications)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        FloatingActionButton fab = findViewById(R.id.fab_settings);
        fab.setOnClickListener(e -> {
            Intent myIntent = new Intent(MainActivity.this, SettingsActivity.class);
            MainActivity.this.startActivity(myIntent);
        });

        fab_create_service = findViewById(R.id.fab_create_task);
        fab_create_service.setOnClickListener(e -> {
            Intent myIntent = new Intent(MainActivity.this, CreateServiceActivity.class);
            MainActivity.this.startActivity(myIntent);
        });
        fab_create_service.hide();

        fab_mark_notifications_as_seen = findViewById(R.id.fab_mark_not_as_seen);
        fab_clear_seen_notifications = findViewById(R.id.fab_clear_all_seen_noti);
        fab_mark_notifications_as_seen.hide();
        fab_clear_seen_notifications.hide();
    }

    @Override
    protected void onStart() {
        super.onStart();
        navController.addOnDestinationChangedListener(MainOnDestinationChangedListener.getInstance(this));
    }

    @Override
    protected void onStop() {
        navController.removeOnDestinationChangedListener(MainOnDestinationChangedListener.getInstance(this));
        MainOnDestinationChangedListener.destroy();
        super.onStop();
    }

    public static class MainOnDestinationChangedListener implements NavController.OnDestinationChangedListener {

        private static MainOnDestinationChangedListener instance;
        private final MainActivity parentView;

        public MainOnDestinationChangedListener(MainActivity mainActivity) {
            parentView = mainActivity;
        }


        public static MainOnDestinationChangedListener getInstance(MainActivity mainActivity) {
            if (instance != null) {
                return instance;
            }
            instance = new MainOnDestinationChangedListener(mainActivity);
            return instance;
        }

        public static void destroy() {
            instance = null;
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
            NavDestination destination = navController.getCurrentDestination();
            switch (Objects.requireNonNull(destination).getId()) {
                case R.id.navigation_home: {
                    parentView.fab_create_service.hide();
                    parentView.fab_mark_notifications_as_seen.hide();
                    parentView.fab_clear_seen_notifications.hide();
                    break;
                }
                case R.id.navigation_tasks: {
                    parentView.fab_create_service.show();
                    parentView.fab_mark_notifications_as_seen.hide();
                    parentView.fab_clear_seen_notifications.hide();
                    break;
                }
                case R.id.navigation_notifications: {
                    parentView.fab_create_service.hide();
                    parentView.fab_clear_seen_notifications.show();
                    parentView.fab_mark_notifications_as_seen.show();
                    break;
                }
                default:
                    throw new IllegalStateException("Unexpected value: " + Objects.requireNonNull(destination).getId());
            }
        }
    }

}