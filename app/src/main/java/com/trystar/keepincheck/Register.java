package com.trystar.keepincheck;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Register extends AppCompatActivity {

    boolean ans=false;
    EditText number,name,inviteCode;
    Button btn;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fAuth = FirebaseAuth.getInstance();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        number=(EditText)findViewById(R.id.number);
        name = findViewById(R.id.name);
        btn = (Button) findViewById(R.id.btn);
        inviteCode = findViewById(R.id.invite);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = inviteCode.getText().toString();
                String Name = name.getText().toString();
                String mNumber = number.getText().toString();
                if(Name.length()<1){
                    name.setError("Name can not be NULL");
                    name.requestFocus();
                }
                if (mNumber.length() != 10)
                {
                    number.setError("Length of Number should be 10");
                    number.requestFocus();
                }
                if(code.length()==6)
                {
                    if(checkExist(code))
                    {
                        if(Name.length()>1 && mNumber.length()==10)
                        {
                            saveOnFirestore();
                            Intent intent = new Intent(Register.this, Otp.class);
                            intent.putExtra("mobile", "+91" + number.getText().toString());
                            startActivity(intent);
                        }
                    }
                    else {
                        Toast.makeText(Register.this,"Incorrect invite code", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    inviteCode.setError("Length should be 6");
                    inviteCode.requestFocus();
                }

            }

            private void saveOnFirestore() {
            }

            private boolean checkExist(String companyCode) {
                try {
                    db.collection("Owner detail")
                            .whereEqualTo("Invite Code",companyCode)//this is for checking the existence of company code
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if (document.exists())
                                            {
                                                ans = true;
                                            }
                                            //document.getString("Company Code");
                                        }
                                    } else {
                                        Toast.makeText(Register.this,"error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });}
                catch (Exception e)
                {

                    Toast.makeText(Register.this,e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
                return ans;
            }
        });
    }
}