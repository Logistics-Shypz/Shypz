package com.shypz.theasoft.shypz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.shypz.theasoft.shypz.interfaces.TextWatcherInterface;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity implements TextWatcherInterface{

    private static final String TAG = "SignupActivity";

    private EditText name;
    private EditText email;
    private EditText password;
    private EditText mobile;
    private boolean name_validate=false;
    private boolean email_validate=false;
    private boolean password_validate=false;
    private boolean mobile_validate=false;
    private boolean validate;
    private Button signUp;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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



    private void signup() {

        Log.d(TAG,"In SignUp Method");


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
}
