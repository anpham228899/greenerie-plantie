package connectors;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import models.User;

public class UserConnector {
    private final DatabaseReference ref;

    public UserConnector() {
        ref = FirebaseDatabase.getInstance().getReference("users");
    }

    // Thêm user mới
    public void addUser(User user) {
        ref.child(user.uid).setValue(user);
    }

    // Lấy user theo UID
    public void getUserById(String uid, ValueEventListener listener) {
        ref.child(uid).addListenerForSingleValueEvent(listener);
    }

    // Cập nhật user
    public void updateUser(User user) {
        ref.child(user.uid).setValue(user);
    }
    public void getAllUsers(ValueEventListener listener) {
        ref.addListenerForSingleValueEvent(listener);
    }
}
