package com.example.dam.mycrush;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;

import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class SignUpActivity extends AppCompatActivity
{

    private final String[] genders = new String[]{"Male", "Female", "Others"};
    private static final String TAG = SignUpActivity.class.getSimpleName();
    Boolean isFbSignUp;
    String linkOfProfilePicture, requestData;
    Button signUp;
    Spinner genderSpinner, interestedInSpinner;
    LoginButton fbSignUp;
    EditText nameEditText;
    EditText contactNumberEditText;
    EditText emailEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;
    DatePicker dob;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        isFbSignUp = false;
        signUp = (Button) findViewById(R.id.signup_signup_button);
        fbSignUp = (LoginButton) findViewById(R.id.fb_signup);
        nameEditText = (EditText) findViewById(R.id.signup_name);
        contactNumberEditText = (EditText) findViewById(R.id.signup_contact_number);
        dob = (DatePicker) findViewById(R.id.signup_date);
        emailEditText = (EditText) findViewById(R.id.signup_email);
        passwordEditText = (EditText) findViewById(R.id.signup_password);
        confirmPasswordEditText = (EditText) findViewById(R.id.signup_confirm_password);

        signUp.setOnClickListener(new SignUpOnClickListener());
        callbackManager = CallbackManager.Factory.create();
        fbSignUp.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        fbSignUp.registerCallback(callbackManager, new SignUpActivity.FBCallBack());

        genderSpinner = (Spinner) findViewById(R.id.signup_gender);
        interestedInSpinner = (Spinner) findViewById(R.id.signup_interested_in);
        ArrayAdapter<String> adapterGender = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genders);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapterGender);
        ArrayAdapter<String> adapterInterestedIn = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genders);
        adapterInterestedIn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        interestedInSpinner.setAdapter(adapterInterestedIn);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private class SignUpOnClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            if (confirmPasswordEditText.getText().toString().equals(passwordEditText.getText().toString()))
            {
                if (contactNumberEditText.getText().toString().isEmpty() || passwordEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(SignUpActivity.this, "please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String name = nameEditText.getText().toString();
                    String emailId = emailEditText.getText().toString();
                    String contactNumber = contactNumberEditText.getText().toString();
                    String password = passwordEditText.getText().toString();
                    String birthDate = "" + dob.getDayOfMonth() + "/" + (dob.getMonth() + 1) + "/" + dob.getYear();
                    Log.e(TAG, "onClick: " + name + emailId + contactNumber + password + birthDate);
                    String interestedIn = interestedInSpinner.getSelectedItem().toString();
                    String gender = genderSpinner.getSelectedItem().toString();
                    if (isFbSignUp)
                    {

                    }
                    else
                    {
                        linkOfProfilePicture = "goo.gl/Tfoynm";
                    }
                }

            }
            else
            {
                Toast.makeText(SignUpActivity.this, "passwords didn't match", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class FBCallBack implements FacebookCallback<LoginResult>
    {

        @Override
        public void onSuccess(final LoginResult loginResult)
        {
            Log.d(TAG, "onSuccess: " + loginResult.getAccessToken().getUserId());
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback()
                    {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response)
                        {
                            Log.v(TAG, response.toString());
                            try
                            {

                                Bundle bundle = new Bundle();
                                bundle.putString("fields", "name,picture,id");
                                new GraphRequest(
                                        loginResult.getAccessToken(),
                                        "/me/friends",
                                        null,
                                        HttpMethod.GET,
                                        new GraphRequest.Callback()
                                        {
                                            public void onCompleted(GraphResponse response)
                                            {
                                                Log.e(TAG, "onCompleted: " + response.getJSONObject().toString());
                                            }
                                        }
                                ).executeAsync();

                                String email = object.getString("email");
                                String birthday = object.getString("birthday");
                                String name = object.getString("name");
                                int date = (birthday.charAt(3) - '0') * 10 + birthday.charAt(4) - '0';
                                int month = (birthday.charAt(0) - '0') * 10 + birthday.charAt(1) - '0' - 1;
                                int year = (birthday.charAt(6) - '0') * 1000 + (birthday.charAt(7) - '0') * 100 + (birthday.charAt(8) - '0') * 10 + birthday.charAt(9) - '0';
                                dob.init(year, month, date, null);
                                Log.e(TAG, "onCompleted: " + date + " " + month + " " + year);
                                linkOfProfilePicture = ("https://graph.facebook.com/" + object.getString("id") + "/picture?type=large");
                                emailEditText.setText(email);
                                nameEditText.setText(name);
                                isFbSignUp = true;

                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
            );
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender,birthday");
            request.setParameters(parameters);
            request.executeAsync();

        }

        @Override
        public void onCancel()
        {

        }

        @Override
        public void onError(FacebookException error)
        {
            Toast.makeText(SignUpActivity.this, "Sorry there was an error", Toast.LENGTH_SHORT).show();
        }
    }
}

