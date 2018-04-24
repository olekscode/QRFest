package com.example.oleks.qrfest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by oleks on 4/24/18.
 */

public class LoginActivity extends AppCompatActivity {
    final Button loginButton = (Button)findViewById(R.id.loginButton);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, QRPersonActivity.class);
                startActivity(i);
            }
        });
    }
}
