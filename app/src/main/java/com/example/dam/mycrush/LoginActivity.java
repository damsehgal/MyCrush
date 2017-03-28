package com.example.dam.mycrush;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity
{

    private static final String TAG = LoginActivity.class.getSimpleName();
    LoginButton fbLogin;
    CallbackManager callbackManager;
    Button signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);
        fbLogin = (LoginButton) findViewById(R.id.fb_login);
        fbLogin.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        fbLogin.registerCallback(callbackManager, new FBCallBack());
        signUp = (Button) findViewById(R.id.sign_up);
        signUp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent  = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
                            Log.e(TAG, "onCompleted: " + object);
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
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        }

        @Override
        public void onError(FacebookException error)
        {
            Toast.makeText(LoginActivity.this, "Sorry there was an error", Toast.LENGTH_SHORT).show();
        }
    }
}

