package com.example.dam.mycrush;

import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
    import com.facebook.GraphResponse;

import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity
{
    public static final String IP_ADDRESS = "http://139.59.15.102:3000";
    public static final String SUB_URL = "/signup";
    private final String[] genders = new String[]{"Male", "Female", "Others"};
    private static final String TAG = SignUpActivity.class.getSimpleName();
    private String url = IP_ADDRESS + SUB_URL;
    public static final String IS_FB_SIGN_UP = "isFbSignUp";
    public static final String E_MAIL_ID = "emailId";
    public static final String NAME = "name";
    public static final String BIRTH_DATE = "birthdate";
    public static final String PASSWORD = "password";
    public static final String LINK_OF_PROFILE_PICTURE = "linkOfProfilePicture";
    public static final String CONTACT_NUMBER = "contactNumber";
    public static final String GENDER = "gender";
    public static final String INTERESTED_IN = "interestedIn";
    public static final String SALT = "salt";
    public static final String IS_NUMBER_VISIBLE = "isNumberVisible";
    public static final String FB_ID = "fbId";
    public static final String FRIEND_LIST = "friendList";
    Boolean isFbSignUp;
    JSONArray friendList;
    String fbId;
    CheckBox isNumberVisible;
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
        isNumberVisible = (CheckBox) findViewById(R.id.signup_is_number_visible);
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
                    final String name = nameEditText.getText().toString().replace(' ', '_');

                    final String emailId = emailEditText.getText().toString();
                    final String contactNumber = contactNumberEditText.getText().toString();
                    final String password = passwordEditText.getText().toString();
                    final String birthDate = "" + dob.getDayOfMonth() + "/" + (dob.getMonth() + 1) + "/" + dob.getYear();
                    Log.e(TAG, "onClick: " + name + emailId + contactNumber + password + birthDate);
                    final String interestedIn = interestedInSpinner.getSelectedItem().toString();
                    final String gender = genderSpinner.getSelectedItem().toString();
                    if (isFbSignUp)
                    {

                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        Log.e(TAG, "onClick: " +  linkOfProfilePicture);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {

                            }
                        }, new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {

                            }
                        }

                        ){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError
                            {
                                Map<String,String> params = new HashMap<String, String>();
                                params.put(IS_FB_SIGN_UP, "1");
                                params.put(E_MAIL_ID, emailId);
                                params.put(NAME, name);
                                params.put(BIRTH_DATE, birthDate);
                                params.put(PASSWORD, password);
                                params.put(LINK_OF_PROFILE_PICTURE, linkOfProfilePicture);
                                params.put(CONTACT_NUMBER, contactNumber);
                                params.put(GENDER, gender);
                                params.put(INTERESTED_IN, interestedIn);
                                params.put(SALT, "salt");
                                params.put(IS_NUMBER_VISIBLE, isNumberVisible.isChecked()?"true":"false");
                                params.put(FB_ID, fbId);
                                String toBeSend = "";
                                for (int i = 0; i < friendList.length(); ++i)
                                {
                                    try
                                    {
                                        toBeSend += friendList.getJSONObject(i).getString("id") + ",";
                                    }
                                    catch (JSONException e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                                toBeSend = toBeSend.substring(0, toBeSend.length() - 1);
                                params.put(FRIEND_LIST,toBeSend);
                                return params;
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                    else
                    {
                        linkOfProfilePicture = "goo.gl/Tfoynm";
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        Log.e(TAG, "onClick: " +  linkOfProfilePicture);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {

                            }
                        }, new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {

                            }
                        }

                        ){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError
                            {
                                Map<String,String> params = new HashMap<String, String>();
                                params.put(IS_FB_SIGN_UP, "0");
                                params.put(E_MAIL_ID, emailId);
                                params.put(NAME, name);
                                params.put(BIRTH_DATE, birthDate);
                                params.put(PASSWORD, password);
                                params.put(LINK_OF_PROFILE_PICTURE, linkOfProfilePicture);
                                params.put(CONTACT_NUMBER, contactNumber);
                                params.put(GENDER, gender);
                                params.put(INTERESTED_IN, interestedIn);
                                params.put(SALT, "salt");
                                params.put(IS_NUMBER_VISIBLE, isNumberVisible.isChecked()?"true":"false");
                                return params;
                            }
                        };
                        requestQueue.add(stringRequest);
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
                                                try
                                                {
                                                    friendList = response.getJSONObject().getJSONArray("data");

                                                }
                                                catch (JSONException e)
                                                {
                                                    e.printStackTrace();
                                                }
                                                // send volley request.
                                              //  JSONArray friendsArray = response.getJSONArray();
                                            }
                                        }
                                ).executeAsync();


                                String email = object.getString("email");

                                fbId = object.getString("id");
                                String birthday = object.getString("birthday");
                                String name = object.getString("name");
                                int date = (birthday.charAt(3) - '0') * 10 + birthday.charAt(4) - '0';
                                int month = (birthday.charAt(0) - '0') * 10 + birthday.charAt(1) - '0' - 1;
                                int year = (birthday.charAt(6) - '0') * 1000 + (birthday.charAt(7) - '0') * 100 + (birthday.charAt(8) - '0') * 10 + birthday.charAt(9) - '0';
                                dob.init(year, month, date, null);
                                Log.e(TAG, "onCompleted " + object.toString() );
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
            isFbSignUp = false;
        }

        @Override
        public void onError(FacebookException error)
        {
            Toast.makeText(SignUpActivity.this, "Sorry there was an error", Toast.LENGTH_SHORT).show();
            isFbSignUp = false;
        }
    }
}

