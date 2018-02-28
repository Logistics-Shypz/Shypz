package com.shypz.theasoft.shypz;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.shypz.theasoft.shypz.constants.Constants;
import com.shypz.theasoft.shypz.interfaces.TextWatcherInterface;
import com.shypz.theasoft.shypz.model.User;
import com.shypz.theasoft.shypz.utilities.NetworkConnection;
import com.shypz.theasoft.shypz.utilities.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements TextWatcherInterface{

    private static final String TAG = "SignupActivity";

    private NetworkConnection ntConn;
    private SessionManager session;

    public static final String USER_SERVICE_URL = Constants.User_Service_Url + "users";



    public EditText name;
    public EditText email;
    public EditText password;
    public EditText mobile;
    private boolean name_validate=false;
    private boolean email_validate=false;
    private boolean password_validate=false;
    private boolean mobile_validate=false;
    private boolean validate;
    private Button signUp;
    ProgressDialog pdcircle;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        session = new SessionManager(getApplicationContext());

        name = (EditText)findViewById(R.id.input_name);
        email = (EditText)findViewById(R.id.input_email);
        password = (EditText)findViewById(R.id.input_password);
        mobile = (EditText)findViewById(R.id.input_mobile);
        signUp = (Button)findViewById(R.id.btn_signup);


        //validateFields();
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG,"In Before Text Chahnged");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG,"In OnText Changed");

                name_validate = validateName();
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG,"In AfterText Changed");

                Log.d(TAG, name_validate?"true":"false");
            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                email_validate = validateEmail();

            }

            @Override
            public void afterTextChanged(Editable s) {
               Log.d(TAG, name_validate?"true":"false");
                Log.d(TAG,email_validate?"true":"false");


            }
        });



        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                password_validate = validatePassword();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(name_validate && email_validate && password_validate){
                    Log.d(TAG,"both validated true");
                    signUp.setEnabled(true);
                }
                else{
                    Log.d(TAG,"Invalid values");
                    signUp.setEnabled(false);
                }
            }
        });


        mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mobile_validate = validateMobile();
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG,mobile_validate?"true":"false");
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signup();
            }
        });

    }

    private boolean validateName() {

        boolean valid = true;

        String _name = name.getText().toString();


        if (_name.isEmpty() || _name.length() < 3) {
            name.setError("at least 3 characters");
            valid = false;
        } else {
            name.setError(null);
        }

        return valid;
    }

    private boolean validateEmail() {

        boolean valid = true;


        String _email = email.getText().toString();

        if (_email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(_email).matches()) {
            email.setError("enter a valid email address");
            valid = false;
        } else {
            email.setError(null);
        }


        return valid;
    }


    private boolean validatePassword() {

        boolean valid = true;


        String _password = password.getText().toString();

        if (_password.isEmpty() || _password.length() < 4 || _password.length() > 20) {
            password.setError("between 4 and 20 alphanumeric characters");
            valid = false;
        } else {
            password.setError(null);
        }

        return valid;
    }

    private boolean validateMobile(){
        boolean valid = true;

        String _mobile = mobile.getText().toString();

        if(_mobile.isEmpty() || Pattern.matches("[a-zA-Z]+",_mobile)){
            mobile.setError("mobile number is not valid");
            valid = false;
        }else{
            mobile.setError(null);
        }

        return valid;

    }



    private void signup(){


        ntConn = new NetworkConnection(getApplicationContext());

        boolean nc_check = ntConn.checkNetworkConnection();

        if(nc_check){
            Log.d(TAG,"In SignUp Method");

            pdcircle = new ProgressDialog(this);

            String user_name =  name.getText().toString();
            String user_email = email.getText().toString();
            String user_mobile = mobile.getText().toString();
            String user_password = password.getText().toString();


            JSONObject uobj = new JSONObject();
            try {
                uobj.put("username",user_name);
                uobj.put("userEmail",user_email);
                uobj.put("user_Password",user_password);
                uobj.put("usermobile",user_mobile);
            } catch (JSONException e) {
                e.printStackTrace();
            }



            new SignUpTask().execute(uobj);

        }else{
            Toast.makeText(this,"Network is not available",Toast.LENGTH_LONG).show();
        }




    }

    private void validateFields() {

            //boolean valid = true;




            email.addTextChangedListener(new TextWatcher() {

                TextWatcherInterface ti;

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String _email = email.getText().toString();
                    if (_email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(_email).matches()) {
                        email.setError("enter a valid email address");
                        ti.setValue(false);
                        if(validate){
                            signUp.setEnabled(true);
                        }else{
                            signUp.setEnabled(false);
                        }
                    } else {

                        ti.setValue(true);
                        if(validate){
                            signUp.setEnabled(true);
                        }else{
                            signUp.setEnabled(false);
                        }
                    }
                }
            });




    }


    @Override
    public void setValue(boolean valid) {
            validate = valid;
    }

    @Override
    public boolean getValue() {
            return validate;
    }



   /* public class SignUpTask extends AsyncTask<JSONObject,String,String>{

        @Override
        protected String doInBackground(JSONObject... jsonObjects) {


          String user_name =  name.getText().toString();
          String user_email = email.getText().toString();
          String user_mobile = mobile.getText().toString();
          String user_password = password.getText().toString();



          User u = new User(user_name,user_email,user_password,user_mobile);

          Log.d(TAG,u.toString());

            try {
                URL url = new URL(USER_SERVICE_URL);

                HttpURLConnection uconn = (HttpURLConnection)url.openConnection();

                JSONObject jobj = new JSONObject();
                jobj.put("username",user_name);
                jobj.put("userEmail",user_email);
                jobj.put("user_Password",user_password);
                jobj.put("user_Mobile",user_mobile);


                uconn.setRequestMethod("GET");

                Log.d(TAG,uconn.getResponseMessage());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return u.toString();


        }

    }*/

    public class SignUpTask extends AsyncTask<JSONObject,Void,String>{


        @Override
        protected void onPreExecute() {
            pdcircle.setTitle("Shypz");
            pdcircle.setMessage("Signing Up....");
            pdcircle.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pdcircle.setCancelable(false);
            pdcircle.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    pdcircle.dismiss();
                }
            }, 3000); // 3000 milliseconds delay
        }

        @Override
        protected String doInBackground(JSONObject... jsonObjects) {

            HttpURLConnection uconn = null;

            JSONObject userPostObj = jsonObjects[0];

            try {
                Log.d(TAG,userPostObj.get("username").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                URL ureq = new URL(USER_SERVICE_URL);
                uconn = (HttpURLConnection) ureq.openConnection();
                uconn.setRequestProperty("Content-Type","application/json");
                //uconn.setDoInput(true);
                //uconn.setDoInput(true);
                uconn.setConnectTimeout(7000);
                uconn.setReadTimeout(7000);



                uconn.setRequestMethod("POST");


                OutputStreamWriter os = new OutputStreamWriter(uconn.getOutputStream());
                os.write(userPostObj.toString());
                os.flush();




                int statusCode = uconn.getResponseCode();
               // if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    // handle unauthorized (if service requires user login)
               // } //else if (statusCode != HttpURLConnection.HTTP_OK) {
                    // handle any other errors, like 404, 500,..


               // }

                // create JSON object from content
                if(statusCode == 200) {
                    InputStream in = new BufferedInputStream(
                            uconn.getInputStream());

                    BufferedReader br = new BufferedReader(new InputStreamReader(in));

                    String result = br.readLine();

                    Log.d(TAG, result);


                    return result;
                }else{
                   if(statusCode != HttpURLConnection.HTTP_OK){
                       Log.d(TAG,"In Error Stream");
                       InputStream in = new BufferedInputStream(uconn.getErrorStream());
                       BufferedReader br = new BufferedReader(new InputStreamReader(in));

                       String result = br.readLine();

                       Log.d(TAG, result);


                       return result;
                   }
                }
               // return new JSONObject(getResponseText(in));

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {


            Log.d(TAG,s);
            String message = "Not a valid";

            try {
                JSONObject reader = new JSONObject(s);


                int user_register_success_code = reader.getInt("success_code");
                message = reader.getString("message");

                Log.d(TAG,message);

                if(user_register_success_code == 1){
                   /* pdcircle.setTitle("Shypz");
                    pdcircle.setMessage(message);
                    pdcircle.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pdcircle.setCancelable(false);
                    pdcircle.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            pdcircle.dismiss();
                        }
                    }, 1000); // 3000 milliseconds delay
                    */
                   pdcircle.dismiss();
                   session.createLoginSession(name.getText().toString(),email.getText().toString(),mobile.getText().toString());
                   Log.d(TAG,"Created Login Session");
                    Intent intent = new Intent(getApplicationContext(),ShypzHome.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent);
                    finish();

                }else{
                    if(user_register_success_code == 2){
                       Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();

                    }else{
                        if(user_register_success_code == 0){
                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                        }
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}
