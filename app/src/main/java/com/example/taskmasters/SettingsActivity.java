package com.example.taskmasters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.example.taskmasters.ui.login.LoginActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment(getApplicationContext()))
                    .commit();
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        private final Context context;

        public SettingsFragment(Context context) {
            this.context = context;
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            Preference button = findPreference(getString(R.string.exitText));
            assert button != null;
            button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(@NonNull Preference preference) {
                    showConfirmationDialog();
                    return true;
                }
            });
        }

        private void showConfirmationDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Atenção")
                    .setMessage("Tem certeza de que deseja desconectar sua conta?")
                    .setPositiveButton("Desconectar", (dialog, which) -> {
                        performLogout();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        }

        private void performLogout() {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            SharedPreferences sharedPreferences = context.getSharedPreferences("login", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", false);
            editor.putString("email", "");
            editor.putString("password", "");
            editor.putString("display_name", "");
            editor.apply();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
