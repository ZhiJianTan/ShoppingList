package com.example.zaphk.shoppinglist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterPasswordActivity extends AppCompatActivity {

    EditText entered_pass;
    Button b_entry;

    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);

        //load the password
        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        password = settings.getString("password","");

        entered_pass = (EditText) findViewById(R.id.enter_user_pass);
        b_entry = (Button) findViewById(R.id.b_enter);

        b_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = entered_pass.getText().toString();

                if(text.equals(password)){
                    //enter app
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(EnterPasswordActivity.this,"Incorrect Password Entered. Try Again.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
