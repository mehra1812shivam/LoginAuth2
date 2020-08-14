package com.example.loginauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class Register extends AppCompatActivity {
    FirebaseAuth fAuth;
    EditText phonenumber,codeEnter;
    Button nextbtn;
    ProgressBar progressBar;
    TextView state;
    CountryCodePicker codePicker;
    String verificationId;
    PhoneAuthProvider.ForceResendingToken Token;
    Boolean verificationInProgress=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fAuth=FirebaseAuth.getInstance();
        phonenumber=findViewById(R.id.phone);
        codeEnter=findViewById(R.id.codeEnter);
        nextbtn=findViewById(R.id.nextBtn);
        state=findViewById(R.id.state);
        progressBar=findViewById(R.id.progressBar);
        codePicker=findViewById(R.id.ccp);


        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!verificationInProgress){
                    if (!phonenumber.getText().toString().isEmpty() && phonenumber.getText().toString().length()==10){
                        String phoneNo="+"+codePicker.getSelectedCountryCode()+phonenumber.getText().toString();
                        progressBar.setVisibility(View.VISIBLE);
                        state.setText("Sending OTP..");
                        state.setVisibility(View.VISIBLE);
                        requestforOTP(phoneNo);



                    }else {
                        phonenumber.setError("Phone Number invalid.");
                    }
                }else {
                    String userOTP=codeEnter.getText().toString();
                    if (!userOTP.isEmpty() && userOTP.length()==6){
                        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationId,userOTP);
                        verifyuser(credential);
                    }else {
                        codeEnter.setError("invalid otp");
                    }

                }
            }
        });
    }

    private void verifyuser(PhoneAuthCredential credential) {
        fAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Register.this, "Authentication Successful", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Register.this,MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(Register.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void requestforOTP(String phoneNo) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNo, 60L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                progressBar.setVisibility(View.GONE);
                state.setVisibility(View.GONE);
                codeEnter.setVisibility(View.VISIBLE);
                verificationId=s;
                Token=forceResendingToken;
                nextbtn.setText("Verify");
                verificationInProgress=true;

            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(Register.this, "Cannot Create Account"+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}