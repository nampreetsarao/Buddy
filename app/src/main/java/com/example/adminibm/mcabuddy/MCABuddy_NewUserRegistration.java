package com.example.adminibm.mcabuddy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adminibm.mcabuddy.bean.NewUserBean;
import com.example.adminibm.mcabuddy.bean.Requester;
import com.example.adminibm.mcabuddy.bean.Roles;
import com.example.adminibm.mcabuddy.bean.Subject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MCABuddy_NewUserRegistration extends Activity {

    public EditText fname;
    public EditText lname;
    public EditText email;
    public EditText phone;
    public EditText password;
    //public EditText accesstoken;
    public CheckBox adminCheckBox;
    public CheckBox smeCheckBox;
    public CheckBox userCheckBox;

    public Button signin;

    private ProgressDialog pd;
    private JSONObject jsonObject;

    private static String url="http://169.44.9.228:8080/mcabuddy/user/new";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcabuddy__new_user_registration);

        registerViews();
    }

    private void registerViews() {
        fname = (EditText) findViewById(R.id.fname_editText);
        // TextWatcher would let us check validation error on the fly
        fname.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validations.hasText(fname);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        lname = (EditText) findViewById(R.id.lname_editText);
        // TextWatcher would let us check validation error on the fly
        lname.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validations.hasText(lname);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        email = (EditText) findViewById(R.id.email_editText);
        email.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                Validations.isEmailAddress(email, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        phone = (EditText) findViewById(R.id.phone_edittext);
        phone.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validations.isPhoneNumber(phone, false);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        password = (EditText) findViewById(R.id.password_edittext);
        // TextWatcher would let us check validation error on the fly
        password.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validations.hasText(password);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

/*        accesstoken = (EditText) findViewById(R.id.accesstoken_edittext);
        accesstoken.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validations.isPhoneNumber(accesstoken, false);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });*/

        adminCheckBox = (CheckBox)findViewById(R.id.AdmincheckBox);
        smeCheckBox = (CheckBox)findViewById(R.id.SMEcheckBox);
        userCheckBox = (CheckBox)findViewById(R.id.UsercheckBox);

        signin = (Button) findViewById(R.id.signin_button);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Validation class will check the error and display the error on respective fields
                but it won't resist the form submission, so we need to check again before submit
                 */
                if (checkValidation())
                    submitForm();
                else
                    Toast.makeText(MCABuddy_NewUserRegistration.this, "Form contains error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void submitForm() {
        Requester requester = new Requester("393654c8d354b64ba0c588e2b3944c7a40ae4965","mcabuddyAdmin@in.ibm.com");
        List<String> roles = new ArrayList<>();
        if(adminCheckBox.isChecked() == true){
            roles.add(adminCheckBox.getText().toString());
        }

        if(smeCheckBox.isChecked() == true){
            roles.add(smeCheckBox.getText().toString());
        }

        if(userCheckBox.isChecked() == true){
            roles.add(userCheckBox.getText().toString());
        }

        if(adminCheckBox.isChecked() == false && smeCheckBox.isChecked() == false && userCheckBox.isChecked() == false){
            roles.add("user");
        }

        /*roles.add("admin");
        roles.add("sme");
        roles.add("user");*/

        List<String> aoe = new ArrayList<>();
        aoe.add("Java");
        aoe.add("ejs");

        Subject subject = new Subject(fname.getText().toString(),lname.getText().toString(),email.getText().toString(),phone.getText().toString(),password.getText().toString(),roles,aoe);
        NewUserBean newUserBean = new NewUserBean(requester, subject);
        Gson gson = new Gson();
        Type type = new TypeToken<NewUserBean>() {}.getType();
        String json = gson.toJson(newUserBean, type);
        createNewUser(json);
    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validations.hasText(fname)) ret = false;
        if (!Validations.hasText(lname)) ret = false;
        if (!Validations.isEmailAddress(email, true)) ret = false;
        if (!Validations.isPhoneNumber(phone, false)) ret = false;
        if (!Validations.hasText(password)) ret = false;
        //if (!Validations.isPhoneNumber(accesstoken, false)) ret = false;

        return ret;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mcabuddy__new_user_registration, menu);
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

    /**
     * Create new user
     * @param jsonString
     */
    private void createNewUser(String jsonString) {

        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("content-type", "application/json");
        StringEntity stringEntity =null;
        try {
            stringEntity = new StringEntity(jsonString);
            stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,"application/json"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error Occurred unable to form String Entity", Toast.LENGTH_LONG).show();
        }

        client.put(getBaseContext(), url, null, stringEntity, "application/json", new AsyncHttpResponseHandler() {
                    @Override
                    public void onStart() {
                        pd = ProgressDialog.show(MCABuddy_NewUserRegistration.this, "", "Creating new user", false);
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
                                Toast.makeText(getApplicationContext(), "User Added successfully !!", Toast.LENGTH_LONG).show();
                                //check if the user is admin or not
                            } else {
                                Toast.makeText(getApplicationContext(), "Unable to add user at this time, try later!!", Toast.LENGTH_LONG).show();
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
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                    }
                });
    }
}
