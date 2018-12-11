package com.takeahike.photoupload;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

//    final String TAG = this.getClass().getName();

    DatabaseReference database;

    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance().getReference();

        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        if(fbUser != null) {
            // User already signed in

            // get the FCM token
            String token = FirebaseInstanceId.getInstance().getToken();

            // save the user info in the database to users/UID/
            // we'll use the UID as part of the path
            User user = new User(fbUser.getUid(), fbUser.getDisplayName(), token);
            database.child("users").child(user.uid).setValue(user);

            // go to feed activity
            Intent intent = new Intent(this, FeedActivity.class);
            startActivity(intent);
        }
    }

    public void signIn(View view) {
        startActivityForResult(
                // Get an instance of AuthUI based on the default app
                AuthUI.getInstance().createSignInIntentBuilder().build(),
                RC_SIGN_IN);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in

                // get the Firebase user
                FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();

                // get the FCM token
                String token = FirebaseInstanceId.getInstance().getToken();

                // save the user info in the database to users/UID/
                // we'll use the UID as part of the path
                User user = new User(fbUser.getUid(), fbUser.getDisplayName(), token);
                database.child("users").child(user.uid).setValue(user);

                // go to feed activity
                Intent intent = new Intent(this, FeedActivity.class);
                startActivity(intent);
            } else {
                // Sign in failed, check response for error code
                if (response != null) {
                    Toast.makeText(this, response.getError().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

//    boolean twice = false;
//
//    @Override
//    public void onBackPressed() {
//
//        Log.d(TAG, "click");
//
//        if(twice == true){
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//            finish();
//            System.exit(0);
//        }
//        twice = true;
//        Log.d(TAG, "twice: " + twice);
//
//        Toast.makeText(MainActivity.this, "Please press BACK again to exit", Toast.LENGTH_SHORT).show();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                twice = false;
//                Log.d(TAG, "twice: " + twice);
//            }
//        }, 3000);
////        super.onBackPressed();
//    }
}
