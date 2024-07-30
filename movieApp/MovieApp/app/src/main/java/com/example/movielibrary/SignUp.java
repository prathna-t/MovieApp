package com.example.movielibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.movielibrary.provider.LoginTable;
import com.example.movielibrary.provider.LoginViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignUp extends AppCompatActivity {
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    public void signUp(View view) {
        // creates an executor that executes a single task at a time.
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler uiHandler=new Handler(Looper.getMainLooper());

        EditText emailTextBox = findViewById(R.id.emailTextBox);
        EditText passwordTextBox = findViewById(R.id.passwordTextBox);

        String email = emailTextBox.getText().toString();
        String password = passwordTextBox.getText().toString();

        // constructor of LoginTable
        LoginTable loginTable = new LoginTable(email, password);


        executor.execute(() -> {
            LoginTable test = loginViewModel.getUserCredentials(email);
            uiHandler.post(() -> {
                if (test != null) {
                    Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_LONG).show();
                }
                else {
                    loginViewModel.signUp(loginTable);
                    Toast.makeText(getApplicationContext(), "Sign Up Successful", Toast.LENGTH_LONG).show();
                    SharedPreferences sharedPreferences = getSharedPreferences("email", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("emailName", loginTable.getEmail());
                    editor.apply();
                    finish();
                }
            });
        });


    }
}