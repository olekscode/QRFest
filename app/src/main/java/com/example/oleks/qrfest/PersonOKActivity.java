package com.example.oleks.qrfest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class PersonOKActivity extends AppCompatActivity {
    TextView okTitle;
    TextView personId;
    TextView productId;
    Button okButton;
    View transactionForm;
    View transactionProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_ok);

        okTitle = (TextView) findViewById(R.id.okTitle);
        personId = (TextView) findViewById(R.id.personId);
        productId = (TextView) findViewById(R.id.productId);
        okButton = (Button) findViewById(R.id.okButton);
        transactionForm = findViewById(R.id.transaction_form);
        transactionProgress = findViewById(R.id.transaction_progress);

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

            okButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showProgress(true);

                    RequestQueue queue = Volley.newRequestQueue(PersonOKActivity.this);
                    String url = "https://stark-lowlands-20761.herokuapp.com/auth/sign_in";

                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response) {
                                    // response
                                    Intent intent = new Intent(PersonOKActivity.this, ResultActivity.class);
                                    Bundle b = new Bundle();
                                    b.putBoolean("isSuccess", true);
                                    intent.putExtras(b);
                                    startActivity(intent);
                                    finish();
                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Intent intent = new Intent(PersonOKActivity.this, ResultActivity.class);
                                    Bundle b = new Bundle();
                                    b.putBoolean("isSuccess", false);
                                    intent.putExtras(b);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String>  params = new HashMap<String, String>();
                            params.put("email", "seller@test.test");
                            params.put("password", "password");

                            return params;
                        }
                    };

                    // Add the request to the RequestQueue.
                    queue.add(postRequest);
                }
            });
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            transactionForm.setVisibility(show ? View.GONE : View.VISIBLE);
            transactionForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    transactionForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            transactionProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            transactionProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    transactionProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            transactionProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            transactionForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
