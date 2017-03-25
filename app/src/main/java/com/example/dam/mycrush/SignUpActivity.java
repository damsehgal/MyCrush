package com.example.dam.mycrush;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class SignUpActivity extends AppCompatActivity
{

    private static final String TAG = SignUpActivity.class.getSimpleName();
    Button signup;
    LoginButton fbSignup;
    EditText name;
    EditText contactNumber;
    EditText email;
    EditText password;
    EditText confirmPassword;
    DatePicker dob;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signup = (Button) findViewById(R.id.signup_signup_button);
        fbSignup = (LoginButton) findViewById(R.id.fb_signup);
        name = (EditText) findViewById(R.id.signup_name);
        contactNumber = (EditText) findViewById(R.id.signup_contact_number);
        dob = (DatePicker) findViewById(R.id.signup_date);
        email = (EditText) findViewById(R.id.signup_email);
        password = (EditText) findViewById(R.id.signup_password);
        confirmPassword = (EditText) findViewById(R.id.signup_confirm_password);
        signup.setOnClickListener(new SignupOnClickListener());
        callbackManager = CallbackManager.Factory.create();
        fbSignup.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        fbSignup.registerCallback(callbackManager, new SignUpActivity.FBCallBack());

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private class SignupOnClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            if (confirmPassword.getText().equals(password.getText()))
            {
                //ToDo
            } else
            {
                Toast.makeText(SignUpActivity.this, "passwords didn't match", Toast.LENGTH_SHORT).show();
            }

        }
    }
    private class FBCallBack implements FacebookCallback<LoginResult>
    {

        @Override
        public void onSuccess(LoginResult loginResult)
        {
            Log.d(TAG, "onSuccess: " + loginResult.getAccessToken().getUserId());
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback()
                    {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response)
                        {
                            Log.v("LoginActivity", response.toString());
                            try
                            {
                                String email = object.getString("email");
                                String birthday = object.getString("birthday");
                                Log.d(TAG, "onCompleted: " + email + " " + birthday);
                            } catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    });
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

