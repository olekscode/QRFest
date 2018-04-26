package com.example.oleks.qrfest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        result = (TextView) findViewById(R.id.result);

        Bundle b = getIntent().getExtras();
        Boolean isSuccess = b.getBoolean("isSuccess");

        if (isSuccess) {
            result.setText("Transaction was successful!");
        }
        else {
            result.setText("Transaction failed");
        }
    }
}
