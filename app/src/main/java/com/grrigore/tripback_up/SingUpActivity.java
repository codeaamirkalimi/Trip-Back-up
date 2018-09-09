package com.grrigore.tripback_up;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.grrigore.tripback_up.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

//todo on screen rotate

public class SingUpActivity extends AppCompatActivity {

    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etPassword)
    EditText etPassword;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        //bind views with butterknife
        ButterKnife.bind(this);

        //hide toolbar
        getSupportActionBar().hide();

        //get firebase auth instance
        firebaseAuth = FirebaseAuth.getInstance();

        //get database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    //todo email verification

    /**
     * @param view This method allows the user to create a new account.
     */
    public void signup(View view) {
        String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            ToastUtil.showToast(getString(R.string.no_email), getApplicationContext());
        }

        if (TextUtils.isEmpty(password)) {
            ToastUtil.showToast(getString(R.string.no_password), getApplicationContext());
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SingUpActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    if (password.length() < 6) {
                        ToastUtil.showToast(getString(R.string.password_shot), getApplicationContext());
                    } else {
                        ToastUtil.showToast(getString(R.string.create_account_error), getApplicationContext());
                    }
                } else {
                    initialiseTripNumber();
                    ToastUtil.showToast(getString(R.string.account_created), SingUpActivity.this);
                    startActivity(new Intent(SingUpActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
    }

    public void switchToLoginActivity(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(new Intent(SingUpActivity.this, MainActivity.class), ActivityOptions.makeSceneTransitionAnimation(SingUpActivity.this).toBundle());
        } else {
            startActivity(new Intent(SingUpActivity.this, MainActivity.class));
        }
    }

    private void initialiseTripNumber() {
        databaseReference.child("users").child(firebaseAuth.getUid()).child("tripNumber").setValue(0);
    }

}
