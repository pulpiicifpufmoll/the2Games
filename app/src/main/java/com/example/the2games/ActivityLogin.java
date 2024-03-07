package com.example.the2games;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class ActivityLogin extends AppCompatActivity {
    private EditText textViewUsername;
    private EditText textViewPassword;
    private TextView textViewRegister;
    private Button loginButton;
    private static SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textViewUsername = findViewById(R.id.editUsernameLogin);
        textViewPassword = findViewById(R.id.editPasswordLogin);
        textViewRegister = findViewById(R.id.textViewToRegister);
        loginButton = findViewById(R.id.buttonLogin);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        addListenersToButtons();
    }

    private void addListenersToButtons() {
        this.loginButton.setOnClickListener(this::login);
        this.textViewRegister.setOnClickListener(this::goToRegister);
    }

    public void login(View v){
        String username = textViewUsername.getText().toString();
        String password = textViewPassword.getText().toString();

        String savedUsername = sharedPreferences.getString(username + "_user", "");
        String savedPassword = sharedPreferences.getString(username + "_password", "");

        if (username.equals(savedUsername) && ActivityRegister.hashPassword(password).equals(savedPassword)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            removeLastLoggedUser();
            editor.putString(username + "_logged", username);
            editor.apply();

            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Username or password incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    public void goToRegister(View v){
        Intent intent = new Intent(this, ActivityRegister.class);
        startActivity(intent);
    }

    public static int getUserBestScore() {

        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().endsWith("_logged")) {
                String usernameKey = entry.getKey();
                String username = sharedPreferences.getString(usernameKey, "");
                int score = sharedPreferences.getInt(username + "_score2048", 0);
                return score;
            }
        }
        return 0;
    }

    public static String getUsername(){
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().endsWith("_logged")) {
                String usernameKey = entry.getKey();
                String username = sharedPreferences.getString(usernameKey, "");
                return username;
            }
        }
        return null;
    }

    private void removeLastLoggedUser(){
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().endsWith("_logged")) {
                String usernameKey = entry.getKey();
                editor.remove(usernameKey);
                editor.apply();
            }
        }
    }
}
