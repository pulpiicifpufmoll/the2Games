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

public class ActivityRegister extends AppCompatActivity {
    private EditText textViewUsername;
    private EditText textViewPassword;
    private EditText textViewConfirmPassword;
    private TextView textViewToLogin;
    private Button registerButton;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        textViewUsername = findViewById(R.id.editUsernameRegister);
        textViewPassword = findViewById(R.id.editPasswordRegister);
        textViewConfirmPassword = findViewById(R.id.passwordConfirm);
        registerButton = findViewById(R.id.buttonRegister);
        textViewToLogin = findViewById(R.id.textViewToLogin);
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        addListenersToButtons();
    }

    private void addListenersToButtons() {
        this.registerButton.setOnClickListener(this::register);
        this.textViewToLogin.setOnClickListener(this::goToLogin);
    }

    public void register(View v){
        String username = textViewUsername.getText().toString();
        String password = textViewPassword.getText().toString();
        String passwordConfirm = textViewConfirmPassword.getText().toString();

        if((!password.equals("") || !passwordConfirm.equals("")) && password.equals(passwordConfirm)){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(username + "_user", username);
            editor.putString(username + "_password", hashPassword(password));
            editor.putInt(username + "_score2048", 0);
            editor.apply();

            Intent intent = new Intent(this, ActivityLogin.class);
            startActivity(intent);
            finish();
        } else if (checkIfUserExists(username)){
            Toast.makeText(this, "This user already exists", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkIfUserExists(String username){
        String userSaved = sharedPreferences.getString(username + "_user", "");
        if (!userSaved.equals("")){
            return true;
        }
        return false;
    }

    public void goToLogin(View v){
        Intent intent = new Intent(this, ActivityLogin.class);
        startActivity(intent);
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
