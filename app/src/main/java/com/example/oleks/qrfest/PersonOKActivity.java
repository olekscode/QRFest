package com.example.oleks.qrfest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PersonOKActivity extends AppCompatActivity {
    TextView okTitle;
    TextView personId;
    TextView productId;
    Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_ok);

        okTitle = (TextView) findViewById(R.id.okTitle);
        personId = (TextView) findViewById(R.id.personId);
        productId = (TextView) findViewById(R.id.productId);
        okButton = (Button) findViewById(R.id.okButton);

        Bundle b = getIntent().getExtras();
        Boolean isPerson = b.getBoolean("isPerson");
        final String id = b.getString("id");
        final String prevId = b.getString("prevId");

        if (isPerson) {
            okTitle.setText("Attendee QR was scanned");
            personId.setText("Attendee ID: " + id);
            okButton.setText("Scan Product QR");

            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(PersonOKActivity.this, QRPerson.class);
                    Bundle b = new Bundle();
                    b.putBoolean("isPerson", false);
                    b.putString("prevId", id);
                    intent.putExtras(b);
                    startActivity(intent);
                    finish();
                }
            });
        }
        else {
            okTitle.setText("Product QR was scanned");
            personId.setText("Attendee ID: " + prevId);
            productId.setText("Product ID: " + id);
            okButton.setText("Make transaction");
        }
    }
}
