package com.example.adminibm.mcabuddy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;



public class MCABuddy_Login extends ActionBarActivity {

    public EditText EmailEditText;
    public EditText PasswordEditText;
    public Button LoginButton;

    public String email;
    public String password;

    //Getting credential file name (will be local stoaraged) from resource file
    public String loginCredentialsFileName;

    private Handler dashboardHandler = new Handler();

    private Bundle bundle;

    private Intent adminDashboardIntent;
    private Intent subscriberDashboardIntent;

    private ProgressDialog pd;
    private JSONObject jsonObject;
    private static String admin="admin";
    private static String url="http://169.44.9.228:8080/mcabuddy/user/authenticate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcabuddy__login);

        EmailEditText = (EditText) findViewById(R.id.email_editText);
        PasswordEditText = (EditText) findViewById(R.id.password_editText);
        LoginButton = (Button) findViewById(R.id.login_button);

        loginCredentialsFileName = "logIn";

        adminDashboardIntent = new Intent(MCABuddy_Login.this, MCABuddy_AdminDashboard.class);
        subscriberDashboardIntent = new Intent(MCABuddy_Login.this, MCABuddy_UserDashboard.class);

        //loginAuth();
    }

    private void loginAuth() {
        EmailEditText.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                Validations.isEmailAddress(EmailEditText, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        PasswordEditText = (EditText) findViewById(R.id.password_edittext);
        // TextWatcher would let us check validation error on the fly
        PasswordEditText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validations.hasText(PasswordEditText);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validations.isEmailAddress(EmailEditText, true)) ret = false;
        if (!Validations.hasText(PasswordEditText)) ret = false;

        return ret;
    }

    public void onClick(View v) {
        email = EmailEditText.getText().toString();
        password=PasswordEditText.getText().toString();
        if(email.isEmpty() & password.isEmpty()){
            EmailEditText.setError("Email cannot be empty");
            PasswordEditText.setError("Password cannot be empty");
        }else if(email.isEmpty()){
            EmailEditText.setError("Email cannot be empty");

        }else if(password.isEmpty()){
            EmailEditText.setError("Password cannot be empty");
        }else {
            //adminDashboardIntent.putExtras(bundle);
            RequestParams requestParams = new RequestParams();
            requestParams.add("email", email);
            requestParams.add("pwd", password);
            authenticateUser(requestParams);
        }

        /*if(checkValidation()){*//*
            adminDashboardIntent.putExtras(bundle);
            RequestParams requestParams = new RequestParams();
            requestParams.add("email", email);
            requestParams.add("pwd", password);
            authenticateUser(requestParams);*/
        /*}
        else{
            Toast.makeText(MCABuddy_Login.this, "Form contains error", Toast.LENGTH_LONG).show();
        }*/

    }

    /**
     * Authenticate user
     * @param requestParams
     */
    private void authenticateUser(RequestParams requestParams) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("content-type", "application/x-www-form-urlencoded");
        client.post(url, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {

                pd= ProgressDialog.show(MCABuddy_Login.this, "", "Authenticating User..", false);
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                try {
                    pd.dismiss();
                    // JSON Object
                    String json = new String(response, "UTF-8");
                    jsonObject = new JSONObject(json);
                    if (jsonObject.getString("status").equals("SUCCESS")) {
                        Toast.makeText(getApplicationContext(), "Authentication Passed", Toast.LENGTH_LONG).show();
                        //check if the user is admin or not
                        JSONObject responseObj =jsonObject.getJSONObject("response");
                        boolean isAdmin=false;
                        for(int i =0;i<responseObj.getJSONArray("roles").length(); i++){
                            if(responseObj.getJSONArray("roles").getString(i).equals(admin)){
                                isAdmin=true;
                            }
                        }

                        //If the user belongs to the admin group, then admin dashboard is launched
                        if(isAdmin){
                            bundle = new Bundle();
                            bundle.putString("loginRole", "admin");
                            adminDashboardIntent.putExtras(bundle);
                            startActivity(adminDashboardIntent);
                        }else{
                            bundle = new Bundle();
                            bundle.putString("loginRole", "user");
                            subscriberDashboardIntent.putExtras(bundle);
                            startActivity(subscriberDashboardIntent);
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Authentication Failed", Toast.LENGTH_LONG).show();
                    }
                    pd.dismiss();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occurred [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Mostly parsing error", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Error Occurred - Server returned bad message :" + e.toString(), Toast.LENGTH_LONG).show();
                //temp code -- will be removed post Tanmay's update
                subscriberDashboardIntent.putExtras(bundle);
                startActivity(subscriberDashboardIntent);

            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });}



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mcabuddy__login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
