package com.example.taskmasters.ui.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import androidx.exifinterface.media.ExifInterface;

import android.os.Bundle;
import android.util.Base64;
import android.widget.TextView;

import com.example.taskmasters.R;
import com.example.taskmasters.ui.settings.SettingsActivity;
import com.example.taskmasters.databinding.ActivityMainBinding;
import com.example.taskmasters.model.user.User;
import com.example.taskmasters.model.user.UserType;
import com.example.taskmasters.model.user.dao.UserDAO;
import com.example.taskmasters.ui.main.tasks.createService.CreateServiceActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseError;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static UserType userType;
    private NavController navController;
    private FloatingActionButton fab_create_service;
    private FloatingActionButton fab_mark_notifications_as_seen;
    private FloatingActionButton fab_clear_seen_notifications;

    private static final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.taskmasters.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_tasks, R.id.navigation_notifications)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        try {
            handleComponentsInstantiation();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void handleComponentsInstantiation() {

        // Get logged user
        TextView userNameDisplay = findViewById(R.id.main_activity_user);
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        String newDisplayText = getString(R.string.welcome_text) + sharedPreferences.getString("display_name", "");
        userNameDisplay.setText(newDisplayText.length() <= 25 ? newDisplayText + ".." : newDisplayText.substring(0, 25) + "..");

        // Define UserType
        userType = sharedPreferences.getInt("user_type", 0) == UserType.CONSUMER.type ? UserType.CONSUMER : UserType.SERVICE_PROVIDER;

        // Instantiate components
        FloatingActionButton fab_access_settings = findViewById(R.id.fab_settings);
        fab_create_service = findViewById(R.id.fab_create_task);
        fab_mark_notifications_as_seen = findViewById(R.id.fab_mark_not_as_seen);
        fab_clear_seen_notifications = findViewById(R.id.fab_clear_all_seen_noti);
        fab_create_service.hide();
        fab_mark_notifications_as_seen.hide();
        fab_clear_seen_notifications.hide();

        // Load user image
        String userId = sharedPreferences.getString("user_id", "none");
        if (!userId.equals("none")) {
            UserDAO userDAO = new UserDAO();
            userDAO.getUserById(userId, new UserDAO.UserCallback() {
                @Override
                public void onUserLoaded(User user) {
                    if (user != null && user.getImage() != null) {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;

                        String encodedImage = user.getImage();
                        byte[] imageBytes = Base64.decode(encodedImage, Base64.DEFAULT);

                        BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length, options);

                        int targetSize = Math.min(options.outWidth, options.outHeight);

                        options.inJustDecodeBounds = false;
                        options.inSampleSize = calculateInSampleSize(options, targetSize, targetSize);
                        Bitmap userBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length, options);
                        userBitmap = rotateBitmap(userBitmap, getOrientation(imageBytes));

                        ImageView userImage = findViewById(R.id.userImage);
                        userImage.setImageBitmap(userBitmap);
                    }
                }

                @Override
                public void onUserError(DatabaseError databaseError) {

                }
            });

        }

        // Add event listeners
        fab_access_settings.setOnClickListener(e -> {
            Intent myIntent = new Intent(MainActivity.this, SettingsActivity.class);
            MainActivity.this.startActivity(myIntent);
        });


        fab_create_service.setOnClickListener(e -> {
            Intent myIntent = new Intent(MainActivity.this, CreateServiceActivity.class);
            MainActivity.this.startActivity(myIntent);
        });

        ImageView userImage = findViewById(R.id.userImage);
        userImage.setOnClickListener(v -> openCamera());

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
                    if (userType == UserType.SERVICE_PROVIDER) {
                        parentView.fab_create_service.show();
                    } else {
                        parentView.fab_create_service.hide();
                    }
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

    private void openCamera() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
            return;
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_IMAGE_CAPTURE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            if (imageBitmap != null) {
                int targetSize = Math.min(imageBitmap.getWidth(), imageBitmap.getHeight());
                Bitmap resizedBitmap = Bitmap.createBitmap(imageBitmap, 0, 0, targetSize, targetSize);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                resizedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] imageBytes = stream.toByteArray();

                SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                String userId = sharedPreferences.getString("user_id", "none");
                if (!userId.equals("none")) {
                    UserDAO userDAO = new UserDAO();
                    userDAO.getUserById(userId, new UserDAO.UserCallback() {
                        @Override
                        public void onUserLoaded(User user) {
                            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                            user.setImage(encodedImage);
                            userDAO.updateUser(user);

                            ImageView userImage = findViewById(R.id.userImage);
                            int orientation = getOrientation(imageBytes);
                            Bitmap rotatedBitmap = rotateBitmap(resizedBitmap, orientation);
                            userImage.setImageBitmap(rotatedBitmap);
                        }

                        @Override
                        public void onUserError(DatabaseError databaseError) {

                        }
                    });
                }
            }
        }
    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private int getOrientation(byte[] image) {
        try {
            ExifInterface exifInterface = new ExifInterface(new ByteArrayInputStream(image));
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return 90;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return 180;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return 270;
                default:
                    return 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
        if (degrees != 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(degrees);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
        return bitmap;
    }
}