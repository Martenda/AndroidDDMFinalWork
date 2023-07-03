package com.example.taskmasters.model.user.dao;

import androidx.annotation.NonNull;

import com.example.taskmasters.model.user.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private DatabaseReference userRef;

    public interface UserListCallback {
        void onUserListLoaded(List<User> userList);
        void onUserListError(DatabaseError databaseError);
    }

    public interface UserCallback {
        void onUserLoaded(User user);
        void onUserError(DatabaseError databaseError);
    }
    public UserDAO() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users");
    }

    public void insertUser(User user) {
        String userId = userRef.push().getKey();
        user.setId(userId);
        assert userId != null;
        userRef.child(userId).setValue(user);
    }


    public void getAllUsers(UserListCallback callback) {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<User> userList = new ArrayList<>();
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if (user != null) {
                        userList.add(user);
                    }
                }
                callback.onUserListLoaded(userList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onUserListError(databaseError);
            }
        });
    }

    public void getUserById(String userId, final UserCallback callback) {
        final User[] user = new User[1];

        Query query = userRef.child(userId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                callback.onUserLoaded(dataSnapshot.getValue(User.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onUserError(databaseError);
            }
        });
    }

    public void updateUser(User user) {
        userRef.child(user.getId()).setValue(user);
    }
}
