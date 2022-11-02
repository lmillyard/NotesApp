package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notesapp.callbacks.SignUpCallback;
import com.example.notesapp.repository.NotesRepository;
import com.example.notesapp.repository.RepositoryProvider;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity implements SignUpCallback {

    private EditText usernameEditText, passwordEditText;
    private TextInputLayout usernameTextInputLayout, passwordTextInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        findViewById(R.id.saveButton).setOnClickListener(v ->
                trySignUp(usernameEditText.getText().toString(), passwordEditText.getText().toString()));

        setupUI();
    }

    private void trySignUp(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            usernameEditText.requestFocus();
            usernameTextInputLayout.setError("Username cannot be empty!");
            return;
        }
        if(TextUtils.isEmpty(password)) {
            passwordEditText.requestFocus();
            passwordTextInputLayout.setError("Password cannot be empty!");
            return;
        }

        NotesRepository notesRepository = RepositoryProvider.getInstance(this);
        notesRepository.signUp(username, password, this);

    }

    private void setupUI() {
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        usernameTextInputLayout = findViewById(R.id.usernameTextInputLayout);
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout);
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, "User added!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}